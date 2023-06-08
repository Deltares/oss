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

package nl.deltares.oss.geolocation.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the GeoLocation service. Represents a row in the &quot;GeoLocations_GeoLocation&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>nl.deltares.oss.geolocation.model.impl.GeoLocationImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocation
 * @generated
 */
@ProviderType
public interface GeoLocationModel
	extends BaseModel<GeoLocation>, ShardedModel, StagedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a geo location model instance should use the {@link GeoLocation} interface instead.
	 */

	/**
	 * Returns the primary key of this geo location.
	 *
	 * @return the primary key of this geo location
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this geo location.
	 *
	 * @param primaryKey the primary key of this geo location
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this geo location.
	 *
	 * @return the uuid of this geo location
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this geo location.
	 *
	 * @param uuid the uuid of this geo location
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the location ID of this geo location.
	 *
	 * @return the location ID of this geo location
	 */
	public long getLocationId();

	/**
	 * Sets the location ID of this geo location.
	 *
	 * @param locationId the location ID of this geo location
	 */
	public void setLocationId(long locationId);

	/**
	 * Returns the company ID of this geo location.
	 *
	 * @return the company ID of this geo location
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this geo location.
	 *
	 * @param companyId the company ID of this geo location
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this geo location.
	 *
	 * @return the create date of this geo location
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this geo location.
	 *
	 * @param createDate the create date of this geo location
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this geo location.
	 *
	 * @return the modified date of this geo location
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this geo location.
	 *
	 * @param modifiedDate the modified date of this geo location
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the country ID of this geo location.
	 *
	 * @return the country ID of this geo location
	 */
	public long getCountryId();

	/**
	 * Sets the country ID of this geo location.
	 *
	 * @param countryId the country ID of this geo location
	 */
	public void setCountryId(long countryId);

	/**
	 * Returns the city name of this geo location.
	 *
	 * @return the city name of this geo location
	 */
	@AutoEscape
	public String getCityName();

	/**
	 * Sets the city name of this geo location.
	 *
	 * @param cityName the city name of this geo location
	 */
	public void setCityName(String cityName);

	/**
	 * Returns the latitude of this geo location.
	 *
	 * @return the latitude of this geo location
	 */
	public double getLatitude();

	/**
	 * Sets the latitude of this geo location.
	 *
	 * @param latitude the latitude of this geo location
	 */
	public void setLatitude(double latitude);

	/**
	 * Returns the longitude of this geo location.
	 *
	 * @return the longitude of this geo location
	 */
	public double getLongitude();

	/**
	 * Sets the longitude of this geo location.
	 *
	 * @param longitude the longitude of this geo location
	 */
	public void setLongitude(double longitude);

	@Override
	public GeoLocation cloneWithOriginalValues();

}