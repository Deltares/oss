package com.worth.deltares.subversion.tasks;


import com.worth.deltares.subversion.model.RepositoryLog;
import com.worth.deltares.subversion.service.RepositoryLogLocalServiceUtil;
import com.worth.deltares.subversion.tasks.impl.StorageTypeAwareSchedulerEntryImpl;

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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
  immediate = true,
  property = {"cron.expression=0 0,30 * * * ?"},
  service = RepositoryLogTaskMessageListener.class
)
public class RepositoryLogTaskMessageListener extends BaseMessageListener {

  private static final Log _log = LogFactoryUtil.getLog(RepositoryLogTaskMessageListener.class);

  @Override
  protected void doReceive(Message message) throws Exception {

    _log.info("Started RepositoryLog update task...");

    try {
      Class.forName("com.mysql.jdbc.Driver");

      try {
        Connection conn = DriverManager.getConnection(
          "jdbc:mysql://" + PropsUtil.get("apache.logs.host") + "/" + PropsUtil.get("apache.logs.database"),
          PropsUtil.get("apache.logs.username"),
          PropsUtil.get("apache.logs.password")
        );

        Statement stat = conn.createStatement();
        long maxCreateDate = 0;

        if (RepositoryLogLocalServiceUtil.getRepositoryLogsCount() > 0) {
          DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryLog.class);
          query.setProjection(ProjectionFactoryUtil.max("createDate"));
          List<?> maxResult = RepositoryLogLocalServiceUtil.dynamicQuery(query);
          maxCreateDate = (Long) maxResult.get(0);
        }

        ResultSet results = stat.executeQuery(
          "SELECT agg_method AS request_method, remote_host, remote_user, time_stamp, request_uri " +
          "FROM " +
          "  (" +
          "    (SELECT 'CHECKOUT' as agg_method, remote_host, remote_user, request_method, time_stamp, request_uri " +
          "    FROM oss_svn as a " +
          "    WHERE request_method IN ('OPTIONS', 'PROPFIND') " +
          "    GROUP BY remote_user " +
          "    ORDER BY time_stamp)" +
          "  UNION " +
          "    (SELECT 'COMMIT' as agg_method, remote_host, remote_user, request_method, time_stamp, request_uri " +
          "    FROM oss_svn as a " +
          "    WHERE request_method IN ('COPY', 'MOVE', 'DELETE', 'PUT', 'PROPPATCH', 'MKCOL') " +
          "    GROUP BY DATE(FROM_UNIXTIME(time_stamp))) " +
          "  ) AS oss " +
          "WHERE remote_user LIKE '%.x' " +
          "  AND NOT remote_user = '-' " +
          "  AND NOT remote_user = '\"\"' " +
          "  AND time_stamp > " + maxCreateDate + " " +
          "ORDER BY time_stamp"
        );

        while (results.next()) {
          String repository = "";

          if (results.getString("request_uri") != null) {
            if (results.getString("request_uri").startsWith("/repos/")) {
              repository = results.getString("request_uri").substring(7);
              
              if (repository.indexOf("/") != -1) {
                repository = repository.substring(0, repository.indexOf("/"));
              }
            }
          }

          long logId = CounterLocalServiceUtil.increment();
          RepositoryLog repositoryLog = RepositoryLogLocalServiceUtil.createRepositoryLog(logId);
          repositoryLog.setAction(results.getString("request_method"));
          repositoryLog.setIpAddress(results.getString("remote_host"));
          repositoryLog.setScreenName(results.getString("remote_user"));
          repositoryLog.setCreateDate(results.getLong("time_stamp"));
          repositoryLog.setRepository(repository);
          repositoryLog.setPrimaryKey(logId);

          if (repositoryLog.getAction().equals("CHECKOUT")) {
            if (RepositoryLogLocalServiceUtil.getRepositoryLogsCount(repositoryLog.getScreenName(), repositoryLog.getIpAddress(), repositoryLog.getRepository()) == 0) {
              RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
            }
          } else {
            RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
          }
        }
        
        stat.execute("DELETE FROM oss_svn");
        stat.close();
        conn.close();

        _log.info("RepositoryLog update task completed.");
      } catch (SQLException | SystemException e) {
        _log.error("Error executing RepositoryLog update task. See debug log for details.");
        _log.error(e);
      }
    } catch (ClassNotFoundException e) {
      _log.error("Error loading DB driver during RepositoryLog update task. See debug log for details.");
      _log.error(e);
    }
  }

  @Activate
  @Modified
  protected void activate(Map<String,Object> properties) throws SchedulerException {

    String cronExpression = GetterUtil.getString(properties.get("cron.expression"), _DEFAULT_CRON_EXPRESSION);

    String listenerClass = getClass().getName();
    Trigger jobTrigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null, cronExpression);

    _schedulerEntryImpl = new SchedulerEntryImpl();
    _schedulerEntryImpl = new StorageTypeAwareSchedulerEntryImpl(_schedulerEntryImpl, StorageType.PERSISTED);

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
      } catch (SchedulerException se) {
        if (_log.isWarnEnabled()) {
          _log.warn("Unable to unschedule trigger", se);
        }
      }

      _schedulerEngineHelper.unregister(this);
    }
    
    _initialized = false;
  }

  protected StorageType getStorageType() {
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

  private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";

  private volatile boolean _initialized;
  private TriggerFactory _triggerFactory;
  private SchedulerEngineHelper _schedulerEngineHelper;
  private SchedulerEntryImpl _schedulerEntryImpl = null;
}