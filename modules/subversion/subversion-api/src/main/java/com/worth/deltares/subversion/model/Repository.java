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

package com.worth.deltares.subversion.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Repository service. Represents a row in the &quot;deltares_Repository&quot; database table, with each column mapped to a property of this class.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryModel
 * @see com.worth.deltares.subversion.model.impl.RepositoryImpl
 * @see com.worth.deltares.subversion.model.impl.RepositoryModelImpl
 * @generated
 */
@ImplementationClassName("com.worth.deltares.subversion.model.impl.RepositoryImpl")
@ProviderType
public interface Repository extends RepositoryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.worth.deltares.subversion.model.impl.RepositoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Repository, Long> REPOSITORY_ID_ACCESSOR = new Accessor<Repository, Long>() {
			@Override
			public Long get(Repository repository) {
				return repository.getRepositoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Repository> getTypeClass() {
				return Repository.class;
			}
		};

	public java.lang.String getRepositoryRoot();
}