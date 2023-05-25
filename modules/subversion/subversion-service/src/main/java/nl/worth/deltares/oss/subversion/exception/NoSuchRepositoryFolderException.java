package nl.worth.deltares.oss.subversion.exception;


import com.liferay.portal.kernel.exception.NoSuchModelException;
import org.osgi.annotation.versioning.ProviderType;


@ProviderType
public class NoSuchRepositoryFolderException extends NoSuchModelException {

  public NoSuchRepositoryFolderException() {}

  public NoSuchRepositoryFolderException(String message) {
    super(message);
  }

  public NoSuchRepositoryFolderException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchRepositoryFolderException(Throwable cause) {
    super(cause);
  }
}
