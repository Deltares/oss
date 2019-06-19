package nl.worth.deltares.oss.subversion.exception;


import com.liferay.portal.kernel.exception.PortalException;


@SuppressWarnings("serial")
public class DuplicateRepositoryFolderPermissionException extends PortalException {

  public DuplicateRepositoryFolderPermissionException(String message) {
    super(message);
  }
}
