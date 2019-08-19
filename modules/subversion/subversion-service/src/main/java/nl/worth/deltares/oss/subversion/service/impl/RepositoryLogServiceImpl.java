package nl.worth.deltares.oss.subversion.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.worth.deltares.oss.subversion.model.RepositoryLog;
import nl.worth.deltares.oss.subversion.service.RepositoryLogLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryLogServiceBaseImpl;

/**
 * The implementation of the repository log remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryLogService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogServiceBaseImpl
 */
public class RepositoryLogServiceImpl extends RepositoryLogServiceBaseImpl {

	private static Log LOG = LogFactoryUtil.getLog(RepositoryLogServiceImpl.class);

	private long companyId = 1;
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>nl.worth.deltares.oss.subversion.service.RepositoryLogServiceUtil</code> to access the repository log remote service.
	 */
	public void addRepositoryLog(String requestMethod, String remoteHost, String remoteUser, String requestUri){

		if (requestMethod == null) {
			throw new IllegalArgumentException("requestMethod == null");
		}
		if (remoteHost == null) {
			throw new IllegalArgumentException("remoteHost == null");
		}
		if (remoteUser == null) {
			throw new IllegalArgumentException("remoteUser == null");
		}
		if (requestUri == null) {
			throw new IllegalArgumentException("requestUri == null");
		}

		String aggMethod;
		switch (requestMethod) {
			case "OPTIONS":
			case "PROPFIND":
				aggMethod = "CHECKOUT";
				break;
			case "COPY":
			case "MOVE":
			case "DELETE":
			case "PUT":
			case "PROPPATCH":
			case "MKCOL":
				aggMethod = "COMMIT";
				break;
			default:
				return;

		}
		String repository;
		int index = requestUri.indexOf("/repos/");
		if (index > -1) {
			repository = requestUri.substring(index + "/repos/".length());
			if (repository.contains("/")) {
				repository = repository.substring(0, repository.indexOf("/"));
			}
		} else {
			throw new UnsupportedOperationException("Unexpected repository URI format: " + requestUri);
		}

		try {
			User user = UserLocalServiceUtil.getUserByScreenName(getCompanyId(), remoteUser);
			String emailAddress = user.getEmailAddress();
			if (emailAddress.toLowerCase().endsWith("@deltares.nl")) {
				LOG.debug("Not logging repository action for Deltares user: " + remoteUser);
				return;
			}
		} catch (PortalException e) {
			LOG.error("Error getting user for screenName " + remoteUser + ": " + e.getMessage());
			return;
		}

		long logId = CounterLocalServiceUtil.increment(RepositoryLog.class.getName());
		RepositoryLog repositoryLog = RepositoryLogLocalServiceUtil.createRepositoryLog(logId);
		repositoryLog.setAction(aggMethod);
		repositoryLog.setIpAddress(remoteHost);
		repositoryLog.setScreenName(remoteUser);
		repositoryLog.setCreateDate(System.currentTimeMillis());
		repositoryLog.setRepository(repository);
		repositoryLog.setPrimaryKey(logId);

		if (repositoryLog.getAction().equals("COMMIT")) {
			if (RepositoryLogLocalServiceUtil
					.getRepositoryLogsCount(repositoryLog.getScreenName(), repositoryLog.getIpAddress(),
							repositoryLog.getRepository()) == 0) {
				RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
			}
		} else {
			RepositoryLogLocalServiceUtil.addRepositoryLog(repositoryLog);
		}
	}

	private long getCompanyId(){

	    if (companyId > -1) return companyId;
	    try {
            Company company = CompanyLocalServiceUtil.getCompanyByMx(PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));
            companyId = company.getGroup().getGroupId();
        } catch (Exception e){
	        LOG.error("Error getting companyId: " + e.getMessage());
        }
        return  companyId;
    }
}