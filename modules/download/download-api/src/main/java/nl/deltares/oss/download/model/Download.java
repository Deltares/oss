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
 * The extended model interface for the Download service. Represents a row in the &quot;Downloads_Download&quot; database table, with each column mapped to a property of this class.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadModel
 * @generated
 */
@ImplementationClassName("nl.deltares.oss.download.model.impl.DownloadImpl")
@ProviderType
public interface Download extends DownloadModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.oss.download.model.impl.DownloadImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Download, Long> ID_ACCESSOR =
		new Accessor<Download, Long>() {

			@Override
			public Long get(Download download) {
				return download.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Download> getTypeClass() {
				return Download.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1093380071