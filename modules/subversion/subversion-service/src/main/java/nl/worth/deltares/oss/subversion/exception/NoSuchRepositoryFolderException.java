package nl.worth.deltares.oss.subversion.exception;


import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.exception.NoSuchModelException;


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
