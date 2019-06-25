/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package nl.worth.deltares.oss.subversion.model.impl;


import aQute.bnd.annotation.ProviderType;
import java.util.List;

import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil;


/**
 * The extended model implementation for the RepositoryFolder service. Represents a row in the &quot;Subversion_RepositoryFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.model.RepositoryFolder<code> interface.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@ProviderType
public class RepositoryFolderImpl extends RepositoryFolderBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a repository folder model instance should use the {@link nl.worth.deltares.oss.subversion.model.RepositoryFolder} interface instead.
	 */
	public RepositoryFolderImpl() {
	}

	private static final long serialVersionUID = -6125541187749420969L;

	public List<RepositoryFolder> getChildren() {
		return RepositoryFolderLocalServiceUtil.getRepositoryFolders(getRepositoryId(), getName());
	}
}