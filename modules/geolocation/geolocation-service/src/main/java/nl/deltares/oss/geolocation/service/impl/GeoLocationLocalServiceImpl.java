/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.geolocation.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.base.GeoLocationLocalServiceBaseImpl;

import nl.deltares.oss.geolocation.service.persistence.GeoLocationUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.oss.geolocation.model.GeoLocation",
	service = AopService.class
)
public class GeoLocationLocalServiceImpl
	extends GeoLocationLocalServiceBaseImpl {

	public GeoLocation fetchByCity(long countryId, String cityName) {
		return GeoLocationUtil.fetchByCity(countryId, cityName);
	}
}