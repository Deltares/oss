package nl.worth.deltares.oss.subversion.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.repository.model.Folder;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryFolderPermissionServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the repository folder permission remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=subversion",
		"json.web.service.context.path=RepositoryFolderPermission"
	},
	service = AopService.class
)
public class RepositoryFolderPermissionServiceImpl
	extends RepositoryFolderPermissionServiceBaseImpl {
}