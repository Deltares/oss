package nl.worth.deltares.oss.subversion.service.impl;

import com.liferay.portal.aop.AopService;
import nl.worth.deltares.oss.subversion.service.base.RepositoryLocalServiceBaseImpl;
import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the repository local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLocalServiceBaseImpl
 */
@Component(
        property = "model.class.name=nl.worth.deltares.oss.subversion.model.Repository",
        service = AopService.class
)
public class RepositoryLocalServiceImpl extends RepositoryLocalServiceBaseImpl {
}