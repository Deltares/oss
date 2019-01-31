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

package com.worth.deltares.subversion.model.impl;

import aQute.bnd.annotation.ProviderType;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.persistence.CountryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;


/**
 * The extended model implementation for the RepositoryLog service. Represents a row in the &quot;deltares_RepositoryLog&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.model.RepositoryLog} interface.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@ProviderType
public class RepositoryLogImpl extends RepositoryLogBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a repository log model instance should use the {@link com.worth.deltares.subversion.model.RepositoryLog} interface instead.
	 */
	public RepositoryLogImpl() {
	}

	private CityResponse cityResponse = null;
	private Country country = null;
	private String city = null;

	private Location getLocation() {
		if (cityResponse != null) return cityResponse.getLocation();

		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(getIpAddress());
		} catch (UnknownHostException e) {
			return null;
		}

		//Configuration configuration = ConfigurationFactoryUtil.getConfiguration(getClass().getClassLoader(), "service");
		//String datafile = configuration.get("maxmind.geoip.database.dir") + configuration.get("maxmind.geoip.database.name");
		String dbDir = PropsUtil.get("maxmind.geoip.database.dir");
		String dbName = PropsUtil.get("maxmind.geoip.database.name");

		// A File object pointing to your GeoIP2 or GeoLite2 database
		File database = new File(dbDir + "/" + dbName);
		// This creates the DatabaseReader object. To improve performance, reuse
		// the object across lookups. The object is thread-safe.
		try (DatabaseReader reader = new DatabaseReader.Builder(database).build()){
			// Replace "city" with the appropriate method for your database, e.g.,
			// "country".
			cityResponse = reader.city(inetAddress);
			if (cityResponse == null) return null;
			return cityResponse.getLocation();
		} catch (Exception e) {
			return null;
		}
	}

	public Date getDate() {
		long cd = this.getCreateDate();

		return new Date(cd * 1000);
	}

	public Country getCountry() {
		try {
			Country c = CountryUtil.fetchByA2(cityResponse.getCountry().getIsoCode());
			this.country = c;

			return country;
		} catch (Exception e) {}

		return null;
	}

	public String getCountryName() {
		String countryName = "";

		if (this.getLocation() != null) {
			countryName = cityResponse.getCountry().getName();
		}

		return countryName;
	}

	public void setCountry(Country c) {
		this.country = c;
	}

	public String getCity() {
		String c = "";

		if (this.getLocation() != null) {
			c = cityResponse.getCity().getName();
			this.city = c;
		}

		return c;
	}

	public String getCityName() {
		String cityName = "";

		if (this.getLocation() != null) {
			cityName = cityResponse.getCity().getName();
		}

		return cityName;
	}

	public String getLatitude() {
		String latitude = "";

		if (this.getLocation() != null) {
			latitude = Double.toString(this.getLocation().getLatitude());
		}

		return latitude;
	}

	public String getLongitude() {
		String longitude = "";

		if (this.getLocation() != null) {
			longitude = Double.toString(this.getLocation().getLongitude());
		}

		return longitude;
	}

	public void setCity(String c) {
		this.city = c;
	}
}