package nl.worth.deltares.oss.subversion.tasks;


import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import nl.worth.deltares.oss.subversion.model.RepositoryLog;
import nl.worth.deltares.oss.subversion.service.RepositoryLogLocalServiceUtil;
import nl.worth.deltares.oss.subversion.tasks.constants.SubversionServiceConstants;
import nl.worth.deltares.oss.subversion.tasks.impl.StorageTypeAwareSchedulerEntryImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;


@Component(
    immediate = true,
    property = {
//        "cron.expression=0 0,30 * * * ?"
        "cron.expression=0 * * * * ?"
    },
    service = RepositoryLogTask.class
)
public class RepositoryLogTask extends BaseMessageListener {

  private static final Log LOG = LogFactoryUtil.getLog(RepositoryLogTask.class);

  private volatile boolean _initialized;
  private TriggerFactory _triggerFactory;
  private SchedulerEngineHelper _schedulerEngineHelper;
  private SchedulerEntryImpl _schedulerEntryImpl = null;

  private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";

  protected void doReceive(Message message) throws Exception {
    if (PropsUtil.get(SubversionServiceConstants.DB_TASK_ENABLE_PROP)
        != null && PropsUtil.get(SubversionServiceConstants.DB_TASK_ENABLE_PROP).equals("true")) {
      runTask();
    }
  }

  private void runTask() {
    LOG.info("Starting RepositoryLog update task...");

    Thread thread = Thread.currentThread();
    ClassLoader originalLoader = thread.getContextClassLoader();
    thread.setContextClassLoader(PortalClassLoaderUtil.getClassLoader());

    InitialContext ctx = null;
    DataSource dataSource = null;
    try {
      ctx = new InitialContext();
      dataSource = (DataSource) ctx.lookup("java:comp/env/"
          + PropsUtil.get(SubversionServiceConstants.APACHE_LOGS_JNDI_PROP));
    } catch (NamingException e) {
      LOG.error("Error setting classpath", e);
      return;
    }

    long maxCreateDate = 0;

    if (RepositoryLogLocalServiceUtil.getRepositoryLogsCount() > 0) {
      DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryLog.class, originalLoader);
      query.setProjection(ProjectionFactoryUtil.max("createDate"));
      List<?> maxResult = RepositoryLogLocalServiceUtil.dynamicQuery(query);
      maxCreateDate = (Long) maxResult.get(0);
    }
    try (Connection conn = dataSource.getConnection();
        Statement stat = conn.createStatement();
        ResultSet results = stat.executeQuery(getSql(maxCreateDate))){
      while (results.next()) {
        String repository = "";

        if (results.getString(SubversionServiceConstants.REQUEST_URI) != null) {
          if (results.getString(SubversionServiceConstants.REQUEST_URI).startsWith("/repos/")) {
            repository = results.getString(SubversionServiceConstants.REQUEST_URI).substring(7);

            if (repository.contains("/")) {
              repository = repository.substring(0, repository.indexOf("/"));
            }
          }
        }

        long logId = CounterLocalServiceUtil.increment(RepositoryLog.class.getName());
        RepositoryLog repositoryLog = RepositoryLogLocalServiceUtil.createRepositoryLog(logId);
        repositoryLog.setAction(results.getString(SubversionServiceConstants.REQUEST_METHOD));
        repositoryLog.setIpAddress(results.getString(SubversionServiceConstants.REMOTE_HOST));
        repositoryLog.setScreenName(results.getString(SubversionServiceConstants.REMOTE_USER));
        repositoryLog.setCreateDate(results.getLong(SubversionServiceConstants.TIME_STAMP));
        repositoryLog.setRepository(repository);
        repositoryLog.setPrimaryKey(logId);

        if (repositoryLog.getAction().equals(SubversionServiceConstants.ACTION_CHECKOUT)) {
          if (RepositoryLogLocalServiceUtil
              .getRepositoryLogsCount(repositoryLog.getScreenName(), repositoryLog.getIpAddress(),
                  repositoryLog.getRepository()) == 0) {
            RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
          }
        } else {
          RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
        }
      }

      stat.execute("DELETE FROM oss_svn");

    } catch (SQLException | SystemException e) {
      LOG.error("Error executing RepositoryLog update task", e);
    } catch (Exception e) {
      LOG.error("Unhandled exception", e);
    } finally {
      thread.setContextClassLoader(originalLoader);
      LOG.info("RepositoryLog update task completed");
    }
  }

  private String getSql(long maxCreateDate) {
    return "SELECT agg_method AS request_method, remote_host, remote_user, time_stamp, request_uri "
        +
        "FROM " +
        "  (" +
        "    (SELECT 'CHECKOUT' as agg_method, remote_host, remote_user, request_method, time_stamp, request_uri "
        +
        "    FROM oss_svn as a " +
        "    WHERE request_method IN ('OPTIONS', 'PROPFIND') " +
        "    GROUP BY remote_user " +
        "    ORDER BY time_stamp)" +
        "  UNION " +
        "    (SELECT 'COMMIT' as agg_method, remote_host, remote_user, request_method, time_stamp, request_uri "
        +
        "    FROM oss_svn as a " +
        "    WHERE request_method IN ('COPY', 'MOVE', 'DELETE', 'PUT', 'PROPPATCH', 'MKCOL') "
        +
        "    GROUP BY DATE(FROM_UNIXTIME(time_stamp))) " +
        "  ) AS oss " +
        "WHERE remote_user LIKE '%.x' " +
        "  AND NOT remote_user = '-' " +
        "  AND NOT remote_user = '\"\"' " +
        "  AND time_stamp > " + maxCreateDate + " " +
        "ORDER BY time_stamp";
  }

  @Activate
  @Modified
  protected void activate(Map<String, Object> properties) throws SchedulerException {

    String cronExpression = GetterUtil
        .getString(properties.get("cron.expression"), _DEFAULT_CRON_EXPRESSION);

    String listenerClass = getClass().getName();

    Trigger jobTrigger = _triggerFactory
        .createTrigger(listenerClass, listenerClass, new Date(), null, cronExpression);

    _schedulerEntryImpl = new SchedulerEntryImpl();
    _schedulerEntryImpl = new StorageTypeAwareSchedulerEntryImpl(_schedulerEntryImpl,
        StorageType.PERSISTED);

    _schedulerEntryImpl.setTrigger(jobTrigger);

    if (_initialized) {
      deactivate();
    }

    _schedulerEngineHelper.register(this, _schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);

    _initialized = true;
  }

  @Deactivate
  protected void deactivate() {
    if (_initialized) {
      try {
        _schedulerEngineHelper.unschedule(_schedulerEntryImpl, getStorageType());
      } catch (SchedulerException e) {
        if (LOG.isWarnEnabled()) {
          LOG.warn("Unable to unschedule trigger", e);
        }
      }
    }

    _initialized = false;
  }

  private StorageType getStorageType() {
    if (_schedulerEntryImpl instanceof StorageTypeAware) {
      return ((StorageTypeAware) _schedulerEntryImpl).getStorageType();
    }

    return StorageType.MEMORY_CLUSTERED;
  }

  @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
  protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
  }

  @Reference(unbind = "-")
  protected void setTriggerFactory(TriggerFactory triggerFactory) {
    _triggerFactory = triggerFactory;
  }

  @Reference(unbind = "-")
  protected void setSchedulerEngineHelper(SchedulerEngineHelper schedulerEngineHelper) {
    _schedulerEngineHelper = schedulerEngineHelper;
  }
}