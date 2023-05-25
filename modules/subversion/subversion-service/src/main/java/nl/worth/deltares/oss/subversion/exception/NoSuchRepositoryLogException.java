package nl.worth.deltares.oss.subversion.exception;


import com.liferay.portal.kernel.exception.NoSuchModelException;
import org.osgi.annotation.versioning.ProviderType;


@ProviderType
public class NoSuchRepositoryLogException extends NoSuchModelException {

  public NoSuchRepositoryLogException() {}

  public NoSuchRepositoryLogException(String message) {
    super(message);
  }

  public NoSuchRepositoryLogException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchRepositoryLogException(Throwable cause) {
    super(cause);
  }
}
