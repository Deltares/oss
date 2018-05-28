package com.worth.deltares.subversion.exception;


import com.liferay.portal.kernel.exception.PortalException;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@SuppressWarnings("serial")
public class DuplicateRepositoryFolderPermissionException extends PortalException {

  public DuplicateRepositoryFolderPermissionException(String message) {

    super(message);
  }
}