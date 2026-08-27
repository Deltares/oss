/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.download.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

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
	public static final Accessor<DownloadCount, Long> ID_ACCESSOR =
		new Accessor<DownloadCount, Long>() {

			@Override
			public Long get(DownloadCount downloadCount) {
				return downloadCount.getId();
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
// LIFERAY-SERVICE-BUILDER-HASH:-508704173