package nl.worth.deltares.oss.subversion.exception;


import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.exception.NoSuchModelException;


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
