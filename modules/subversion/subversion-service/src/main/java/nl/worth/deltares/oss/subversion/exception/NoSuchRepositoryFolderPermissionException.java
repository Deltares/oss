package nl.worth.deltares.oss.subversion.exception;


import com.liferay.portal.kernel.exception.NoSuchModelException;
import org.osgi.annotation.versioning.ProviderType;


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
