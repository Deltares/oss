package nl.worth.deltares.oss.subversion.exception;


import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.exception.NoSuchModelException;


@ProviderType
public class NoSuchRepositoryFolderPermissionException extends NoSuchModelException {

  public NoSuchRepositoryFolderPermissionException() {}

  public NoSuchRepositoryFolderPermissionException(String message) {
    super(message);
  }

  public NoSuchRepositoryFolderPermissionException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchRepositoryFolderPermissionException(Throwable cause) {
    super(cause);
  }
}
