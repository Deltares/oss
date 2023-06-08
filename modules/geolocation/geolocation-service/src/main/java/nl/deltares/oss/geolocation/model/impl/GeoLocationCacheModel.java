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

package nl.deltares.oss.geolocation.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import nl.deltares.oss.geolocation.model.GeoLocation;

/**
 * The cache model class for representing GeoLocation in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GeoLocationCacheModel
	implements CacheModel<GeoLocation>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GeoLocationCacheModel)) {
			return false;
		}

		GeoLocationCacheModel geoLocationCacheModel =
			(GeoLocationCacheModel)object;

		if (locationId == geoLocationCacheModel.locationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, locationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", locationId=");
		sb.append(locationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", cityName=");
		sb.append(cityName);
		sb.append(", latitude=");
		sb.append(latitude);
		sb.append(", longitude=");
		sb.append(longitude);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public GeoLocation toEntityModel() {
		GeoLocationImpl geoLocationImpl = new GeoLocationImpl();

		if (uuid == null) {
			geoLocationImpl.setUuid("");
		}
		else {
			geoLocationImpl.setUuid(uuid);
		}

		geoLocationImpl.setLocationId(locationId);
		geoLocationImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			geoLocationImpl.setCreateDate(null);
		}
		else {
			geoLocationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			geoLocationImpl.setModifiedDate(null);
		}
		else {
			geoLocationImpl.setModifiedDate(new Date(modifiedDate));
		}

		geoLocationImpl.setCountryId(countryId);

		if (cityName == null) {
			geoLocationImpl.setCityName("");
		}
		else {
			geoLocationImpl.setCityName(cityName);
		}

		geoLocationImpl.setLatitude(latitude);
		geoLocationImpl.setLongitude(longitude);

		geoLocationImpl.resetOriginalValues();

		return geoLocationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		locationId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		countryId = objectInput.readLong();
		cityName = objectInput.readUTF();

		latitude = objectInput.readDouble();

		longitude = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(locationId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(countryId);

		if (cityName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cityName);
		}

		objectOutput.writeDouble(latitude);

		objectOutput.writeDouble(longitude);
	}

	public String uuid;
	public long locationId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long countryId;
	public String cityName;
	public double latitude;
	public double longitude;

}