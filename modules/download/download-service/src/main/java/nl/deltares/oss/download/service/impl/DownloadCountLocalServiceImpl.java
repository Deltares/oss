package nl.deltares.oss.download.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.service.base.DownloadCountLocalServiceBaseImpl;

import nl.deltares.oss.download.service.persistence.DownloadCountUtil;
import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the download count local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.deltares.oss.download.service.DownloadCountLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 */
@Component(
	property = "model.class.name=nl.deltares.oss.download.model.DownloadCount",
	service = AopService.class
)
public class DownloadCountLocalServiceImpl
	extends DownloadCountLocalServiceBaseImpl {

	public DownloadCount getDownloadCount(long groupId, long downloadId) {
		return DownloadCountUtil.fetchByDownloadCountByGroup(groupId, downloadId);
	}
}