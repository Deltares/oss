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

package nl.worth.deltares.oss.subversion.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the RepositoryLog service. Represents a row in the &quot;Subversion_RepositoryLog&quot; database table, with each column mapped to a property of this class.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogModel
 * @generated
 */
@ImplementationClassName(
	"nl.worth.deltares.oss.subversion.model.impl.RepositoryLogImpl"
)
@ProviderType
public interface RepositoryLog extends PersistedModel, RepositoryLogModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryLogImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RepositoryLog, Long> LOG_ID_ACCESSOR =
		new Accessor<RepositoryLog, Long>() {

			@Override
			public Long get(RepositoryLog repositoryLog) {
				return repositoryLog.getLogId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RepositoryLog> getTypeClass() {
				return RepositoryLog.class;
			}

		};

}