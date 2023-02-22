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

package nl.deltares.oss.download.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.model.DownloadModel;

/**
 * The base model implementation for the Download service. Represents a row in the &quot;Downloads_Download&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>DownloadModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DownloadImpl}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadImpl
 * @generated
 */
public class DownloadModelImpl
	extends BaseModelImpl<Download> implements DownloadModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a download model instance should use the <code>Download</code> interface instead.
	 */
	public static final String TABLE_NAME = "Downloads_Download";

	public static final Object[][] TABLE_COLUMNS = {
		{"id_", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"downloadId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"filePath", Types.VARCHAR},
		{"expiryDate", Types.TIMESTAMP}, {"organization", Types.VARCHAR},
		{"countryCode", Types.VARCHAR}, {"city", Types.VARCHAR},
		{"shareId", Types.INTEGER}, {"directDownloadUrl", Types.VARCHAR},
		{"licenseDownloadUrl", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("id_", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("downloadId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("filePath", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("expiryDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("organization", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("countryCode", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("city", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("shareId", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("directDownloadUrl", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("licenseDownloadUrl", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Downloads_Download (id_ LONG not null primary key,companyId LONG,groupId LONG,downloadId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,filePath STRING null,expiryDate DATE null,organization VARCHAR(75) null,countryCode VARCHAR(75) null,city VARCHAR(75) null,shareId INTEGER,directDownloadUrl STRING null,licenseDownloadUrl VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Downloads_Download";

	public static final String ORDER_BY_JPQL =
		" ORDER BY download.modifiedDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Downloads_Download.modifiedDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long DOWNLOADID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SHAREID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long USERID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long MODIFIEDDATE_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public DownloadModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _id;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _id;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Download.class;
	}

	@Override
	public String getModelClassName() {
		return Download.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Download, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Download, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Download, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Download)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Download, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Download, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Download)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Download, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Download, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Download>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Download.class.getClassLoader(), Download.class,
			ModelWrapper.class);

		try {
			Constructor<Download> constructor =
				(Constructor<Download>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<Download, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Download, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Download, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Download, Object>>();
		Map<String, BiConsumer<Download, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Download, ?>>();

		attributeGetterFunctions.put("id", Download::getId);
		attributeSetterBiConsumers.put(
			"id", (BiConsumer<Download, Long>)Download::setId);
		attributeGetterFunctions.put("companyId", Download::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Download, Long>)Download::setCompanyId);
		attributeGetterFunctions.put("groupId", Download::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<Download, Long>)Download::setGroupId);
		attributeGetterFunctions.put("downloadId", Download::getDownloadId);
		attributeSetterBiConsumers.put(
			"downloadId", (BiConsumer<Download, Long>)Download::setDownloadId);
		attributeGetterFunctions.put("userId", Download::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Download, Long>)Download::setUserId);
		attributeGetterFunctions.put("createDate", Download::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<Download, Date>)Download::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", Download::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Download, Date>)Download::setModifiedDate);
		attributeGetterFunctions.put("filePath", Download::getFilePath);
		attributeSetterBiConsumers.put(
			"filePath", (BiConsumer<Download, String>)Download::setFilePath);
		attributeGetterFunctions.put("expiryDate", Download::getExpiryDate);
		attributeSetterBiConsumers.put(
			"expiryDate", (BiConsumer<Download, Date>)Download::setExpiryDate);
		attributeGetterFunctions.put("organization", Download::getOrganization);
		attributeSetterBiConsumers.put(
			"organization",
			(BiConsumer<Download, String>)Download::setOrganization);
		attributeGetterFunctions.put("countryCode", Download::getCountryCode);
		attributeSetterBiConsumers.put(
			"countryCode",
			(BiConsumer<Download, String>)Download::setCountryCode);
		attributeGetterFunctions.put("city", Download::getCity);
		attributeSetterBiConsumers.put(
			"city", (BiConsumer<Download, String>)Download::setCity);
		attributeGetterFunctions.put("shareId", Download::getShareId);
		attributeSetterBiConsumers.put(
			"shareId", (BiConsumer<Download, Integer>)Download::setShareId);
		attributeGetterFunctions.put(
			"directDownloadUrl", Download::getDirectDownloadUrl);
		attributeSetterBiConsumers.put(
			"directDownloadUrl",
			(BiConsumer<Download, String>)Download::setDirectDownloadUrl);
		attributeGetterFunctions.put(
				"licenseDownloadUrl", Download::getLicenseDownloadUrl);
		attributeSetterBiConsumers.put(
				"licenseDownloadUrl",
				(BiConsumer<Download, String>)Download::setLicenseDownloadUrl);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public void setId(long id) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_id = id;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
	}

	@Override
	public long getDownloadId() {
		return _downloadId;
	}

	@Override
	public void setDownloadId(long downloadId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_downloadId = downloadId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalDownloadId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("downloadId"));
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalUserId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("userId"));
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getFilePath() {
		if (_filePath == null) {
			return "";
		}
		else {
			return _filePath;
		}
	}

	@Override
	public void setFilePath(String filePath) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_filePath = filePath;
	}

	@Override
	public Date getExpiryDate() {
		return _expiryDate;
	}

	@Override
	public void setExpiryDate(Date expiryDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_expiryDate = expiryDate;
	}

	@Override
	public String getOrganization() {
		if (_organization == null) {
			return "";
		}
		else {
			return _organization;
		}
	}

	@Override
	public void setOrganization(String organization) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_organization = organization;
	}

	@Override
	public String getCountryCode() {
		if (_countryCode == null) {
			return "";
		}
		else {
			return _countryCode;
		}
	}

	@Override
	public void setCountryCode(String countryCode) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_countryCode = countryCode;
	}

	@Override
	public String getCity() {
		if (_city == null) {
			return "";
		}
		else {
			return _city;
		}
	}

	@Override
	public void setCity(String city) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_city = city;
	}

	@Override
	public int getShareId() {
		return _shareId;
	}

	@Override
	public void setShareId(int shareId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_shareId = shareId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public int getOriginalShareId() {
		return GetterUtil.getInteger(
			this.<Integer>getColumnOriginalValue("shareId"));
	}

	@Override
	public String getDirectDownloadUrl() {
		if (_directDownloadUrl == null) {
			return "";
		}
		else {
			return _directDownloadUrl;
		}
	}

	@Override
	public void setDirectDownloadUrl(String directDownloadUrl) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_directDownloadUrl = directDownloadUrl;
	}

	@Override
	public String getLicenseDownloadUrl() {
		if (_licenseDownloadUrl == null) {
			return "";
		}
		else {
			return _licenseDownloadUrl;
		}
	}

	@Override
	public void setLicenseDownloadUrl(String licenseDownloadUrl) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_licenseDownloadUrl = licenseDownloadUrl;
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Download.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Download toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Download>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DownloadImpl downloadImpl = new DownloadImpl();

		downloadImpl.setId(getId());
		downloadImpl.setCompanyId(getCompanyId());
		downloadImpl.setGroupId(getGroupId());
		downloadImpl.setDownloadId(getDownloadId());
		downloadImpl.setUserId(getUserId());
		downloadImpl.setCreateDate(getCreateDate());
		downloadImpl.setModifiedDate(getModifiedDate());
		downloadImpl.setFilePath(getFilePath());
		downloadImpl.setExpiryDate(getExpiryDate());
		downloadImpl.setOrganization(getOrganization());
		downloadImpl.setCountryCode(getCountryCode());
		downloadImpl.setCity(getCity());
		downloadImpl.setShareId(getShareId());
		downloadImpl.setDirectDownloadUrl(getDirectDownloadUrl());
		downloadImpl.setLicenseDownloadUrl(getLicenseDownloadUrl());

		downloadImpl.resetOriginalValues();

		return downloadImpl;
	}

	@Override
	public Download cloneWithOriginalValues() {
		DownloadImpl downloadImpl = new DownloadImpl();

		downloadImpl.setId(this.<Long>getColumnOriginalValue("id_"));
		downloadImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		downloadImpl.setGroupId(this.<Long>getColumnOriginalValue("groupId"));
		downloadImpl.setDownloadId(
			this.<Long>getColumnOriginalValue("downloadId"));
		downloadImpl.setUserId(this.<Long>getColumnOriginalValue("userId"));
		downloadImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		downloadImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		downloadImpl.setFilePath(
			this.<String>getColumnOriginalValue("filePath"));
		downloadImpl.setExpiryDate(
			this.<Date>getColumnOriginalValue("expiryDate"));
		downloadImpl.setOrganization(
			this.<String>getColumnOriginalValue("organization"));
		downloadImpl.setCountryCode(
			this.<String>getColumnOriginalValue("countryCode"));
		downloadImpl.setCity(this.<String>getColumnOriginalValue("city"));
		downloadImpl.setShareId(
			this.<Integer>getColumnOriginalValue("shareId"));
		downloadImpl.setDirectDownloadUrl(
			this.<String>getColumnOriginalValue("directDownloadUrl"));
		downloadImpl.setLicenseDownloadUrl(
				this.<String>getColumnOriginalValue("licenseDownloadUrl"));

		return downloadImpl;
	}

	@Override
	public int compareTo(Download download) {
		int value = 0;

		value = DateUtil.compareTo(
			getModifiedDate(), download.getModifiedDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Download)) {
			return false;
		}

		Download download = (Download)object;

		long primaryKey = download.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<Download> toCacheModel() {
		DownloadCacheModel downloadCacheModel = new DownloadCacheModel();

		downloadCacheModel.id = getId();

		downloadCacheModel.companyId = getCompanyId();

		downloadCacheModel.groupId = getGroupId();

		downloadCacheModel.downloadId = getDownloadId();

		downloadCacheModel.userId = getUserId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			downloadCacheModel.createDate = createDate.getTime();
		}
		else {
			downloadCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			downloadCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			downloadCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		downloadCacheModel.filePath = getFilePath();

		String filePath = downloadCacheModel.filePath;

		if ((filePath != null) && (filePath.length() == 0)) {
			downloadCacheModel.filePath = null;
		}

		Date expiryDate = getExpiryDate();

		if (expiryDate != null) {
			downloadCacheModel.expiryDate = expiryDate.getTime();
		}
		else {
			downloadCacheModel.expiryDate = Long.MIN_VALUE;
		}

		downloadCacheModel.organization = getOrganization();

		String organization = downloadCacheModel.organization;

		if ((organization != null) && (organization.length() == 0)) {
			downloadCacheModel.organization = null;
		}

		downloadCacheModel.countryCode = getCountryCode();

		String countryCode = downloadCacheModel.countryCode;

		if ((countryCode != null) && (countryCode.length() == 0)) {
			downloadCacheModel.countryCode = null;
		}

		downloadCacheModel.city = getCity();

		String city = downloadCacheModel.city;

		if ((city != null) && (city.length() == 0)) {
			downloadCacheModel.city = null;
		}

		downloadCacheModel.shareId = getShareId();

		downloadCacheModel.directDownloadUrl = getDirectDownloadUrl();

		String directDownloadUrl = downloadCacheModel.directDownloadUrl;

		if ((directDownloadUrl != null) && (directDownloadUrl.length() == 0)) {
			downloadCacheModel.directDownloadUrl = null;
		}

		downloadCacheModel.licenseDownloadUrl = getLicenseDownloadUrl();

		String licenseDownloadUrl = downloadCacheModel.licenseDownloadUrl;

		if ((licenseDownloadUrl != null) &&
			(licenseDownloadUrl.length() == 0)) {

			downloadCacheModel.licenseDownloadUrl = null;
		}

		return downloadCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Download, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Download, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Download, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply((Download)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<Download, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Download, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Download, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Download)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Download>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _id;
	private long _companyId;
	private long _groupId;
	private long _downloadId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _filePath;
	private Date _expiryDate;
	private String _organization;
	private String _countryCode;
	private String _city;
	private int _shareId;
	private String _directDownloadUrl;
	private String _licenseDownloadUrl;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<Download, Object> function = _attributeGetterFunctions.get(
			columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((Download)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("id_", _id);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("downloadId", _downloadId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("filePath", _filePath);
		_columnOriginalValues.put("expiryDate", _expiryDate);
		_columnOriginalValues.put("organization", _organization);
		_columnOriginalValues.put("countryCode", _countryCode);
		_columnOriginalValues.put("city", _city);
		_columnOriginalValues.put("shareId", _shareId);
		_columnOriginalValues.put("directDownloadUrl", _directDownloadUrl);
		_columnOriginalValues.put("licenseDownloadUrl", _licenseDownloadUrl);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("id_", "id");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("id_", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("downloadId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("filePath", 128L);

		columnBitmasks.put("expiryDate", 256L);

		columnBitmasks.put("organization", 512L);

		columnBitmasks.put("countryCode", 1024L);

		columnBitmasks.put("city", 2048L);

		columnBitmasks.put("shareId", 4096L);

		columnBitmasks.put("directDownloadUrl", 8192L);

		columnBitmasks.put("licenseDownloadUrl", 16384L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private Download _escapedModel;

}