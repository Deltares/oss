package nl.worth.deltares.oss.subversion.exception;


import com.liferay.portal.kernel.exception.NoSuchModelException;
import org.osgi.annotation.versioning.ProviderType;


@ProviderType
public class NoSuchRepositoryException extends NoSuchModelException {

  public NoSuchRepositoryException() {}

  public NoSuchRepositoryException(String message) {
    super(message);
  }

  public NoSuchRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchRepositoryException(Throwable cause) {
    super(cause);
  }
}
