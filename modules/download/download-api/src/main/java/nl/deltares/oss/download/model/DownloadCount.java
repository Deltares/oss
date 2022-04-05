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

package nl.deltares.oss.download.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the DownloadCount service. Represents a row in the &quot;Downloads_DownloadCount&quot; database table, with each column mapped to a property of this class.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCountModel
 * @generated
 */
@ImplementationClassName(
	"nl.deltares.oss.download.model.impl.DownloadCountImpl"
)
@ProviderType
public interface DownloadCount extends DownloadCountModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.oss.download.model.impl.DownloadCountImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DownloadCount, Long> DOWNLOAD_ID_ACCESSOR =
		new Accessor<DownloadCount, Long>() {

			@Override
			public Long get(DownloadCount downloadCount) {
				return downloadCount.getDownloadId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DownloadCount> getTypeClass() {
				return DownloadCount.class;
			}

		};

}