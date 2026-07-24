/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.geolocation.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the GeoLocation service. Represents a row in the &quot;GeoLocations_GeoLocation&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocationModel
 * @generated
 */
@ImplementationClassName(
	"nl.deltares.oss.geolocation.model.impl.GeoLocationImpl"
)
@ProviderType
public interface GeoLocation extends GeoLocationModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.oss.geolocation.model.impl.GeoLocationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<GeoLocation, Long> LOCATION_ID_ACCESSOR =
		new Accessor<GeoLocation, Long>() {

			@Override
			public Long get(GeoLocation geoLocation) {
				return geoLocation.getLocationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<GeoLocation> getTypeClass() {
				return GeoLocation.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1687175333