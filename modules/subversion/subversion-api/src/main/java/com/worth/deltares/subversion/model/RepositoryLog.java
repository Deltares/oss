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
 * The extended model interface for the RepositoryLog service. Represents a row in the &quot;deltares_RepositoryLog&quot; database table, with each column mapped to a property of this class.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogModel
 * @see com.worth.deltares.subversion.model.impl.RepositoryLogImpl
 * @see com.worth.deltares.subversion.model.impl.RepositoryLogModelImpl
 * @generated
 */
@ImplementationClassName("com.worth.deltares.subversion.model.impl.RepositoryLogImpl")
@ProviderType
public interface RepositoryLog extends RepositoryLogModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.worth.deltares.subversion.model.impl.RepositoryLogImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RepositoryLog, Long> LOG_ID_ACCESSOR = new Accessor<RepositoryLog, Long>() {
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

	public com.maxmind.geoip.Location getLocation();

	public java.util.Date getDate();

	public com.liferay.portal.kernel.model.Country getCountry();

	public java.lang.String getCountryName();

	public void setCountry(com.liferay.portal.kernel.model.Country c);

	public java.lang.String getCity();

	public java.lang.String getCityName();

	public java.lang.String getLatitude();

	public java.lang.String getLongitude();

	public void setCity(java.lang.String c);
}