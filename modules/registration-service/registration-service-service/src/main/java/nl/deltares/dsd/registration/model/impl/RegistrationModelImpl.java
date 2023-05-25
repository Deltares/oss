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

package nl.deltares.dsd.registration.model.impl;

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

import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.RegistrationModel;

/**
 * The base model implementation for the Registration service. Represents a row in the &quot;Registrations_Registration&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>RegistrationModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RegistrationImpl}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationImpl
 * @generated
 */
public class RegistrationModelImpl
	extends BaseModelImpl<Registration> implements RegistrationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a registration model instance should use the <code>Registration</code> interface instead.
	 */
	public static final String TABLE_NAME = "Registrations_Registration";

	public static final Object[][] TABLE_COLUMNS = {
		{"registrationId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"eventResourcePrimaryKey", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"resourcePrimaryKey", Types.BIGINT},
		{"userPreferences", Types.VARCHAR}, {"startTime", Types.TIMESTAMP},
		{"endTime", Types.TIMESTAMP},
		{"parentResourcePrimaryKey", Types.BIGINT},
		{"registeredByUserId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("registrationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("eventResourcePrimaryKey", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("resourcePrimaryKey", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userPreferences", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("startTime", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("endTime", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("parentResourcePrimaryKey", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("registeredByUserId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Registrations_Registration (registrationId LONG not null primary key,groupId LONG,eventResourcePrimaryKey LONG,companyId LONG,userId LONG,resourcePrimaryKey LONG,userPreferences STRING null,startTime DATE null,endTime DATE null,parentResourcePrimaryKey LONG,registeredByUserId LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table Registrations_Registration";

	public static final String ORDER_BY_JPQL =
		" ORDER BY registration.startTime DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Registrations_Registration.startTime DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long EVENTRESOURCEPRIMARYKEY_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long PARENTRESOURCEPRIMARYKEY_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long REGISTEREDBYUSERID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long RESOURCEPRIMARYKEY_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long USERID_COLUMN_BITMASK = 32L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long STARTTIME_COLUMN_BITMASK = 64L;

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

	public RegistrationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _registrationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRegistrationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _registrationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Registration.class;
	}

	@Override
	public String getModelClassName() {
		return Registration.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Registration, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Registration, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Registration, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((Registration)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Registration, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Registration, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Registration)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Registration, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Registration, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<Registration, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Registration, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Registration, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Registration, Object>>();
		Map<String, BiConsumer<Registration, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Registration, ?>>();

		attributeGetterFunctions.put(
			"registrationId", Registration::getRegistrationId);
		attributeSetterBiConsumers.put(
			"registrationId",
			(BiConsumer<Registration, Long>)Registration::setRegistrationId);
		attributeGetterFunctions.put("groupId", Registration::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<Registration, Long>)Registration::setGroupId);
		attributeGetterFunctions.put(
			"eventResourcePrimaryKey",
			Registration::getEventResourcePrimaryKey);
		attributeSetterBiConsumers.put(
			"eventResourcePrimaryKey",
			(BiConsumer<Registration, Long>)
				Registration::setEventResourcePrimaryKey);
		attributeGetterFunctions.put("companyId", Registration::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<Registration, Long>)Registration::setCompanyId);
		attributeGetterFunctions.put("userId", Registration::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Registration, Long>)Registration::setUserId);
		attributeGetterFunctions.put(
			"resourcePrimaryKey", Registration::getResourcePrimaryKey);
		attributeSetterBiConsumers.put(
			"resourcePrimaryKey",
			(BiConsumer<Registration, Long>)
				Registration::setResourcePrimaryKey);
		attributeGetterFunctions.put(
			"userPreferences", Registration::getUserPreferences);
		attributeSetterBiConsumers.put(
			"userPreferences",
			(BiConsumer<Registration, String>)Registration::setUserPreferences);
		attributeGetterFunctions.put("startTime", Registration::getStartTime);
		attributeSetterBiConsumers.put(
			"startTime",
			(BiConsumer<Registration, Date>)Registration::setStartTime);
		attributeGetterFunctions.put("endTime", Registration::getEndTime);
		attributeSetterBiConsumers.put(
			"endTime",
			(BiConsumer<Registration, Date>)Registration::setEndTime);
		attributeGetterFunctions.put(
			"parentResourcePrimaryKey",
			Registration::getParentResourcePrimaryKey);
		attributeSetterBiConsumers.put(
			"parentResourcePrimaryKey",
			(BiConsumer<Registration, Long>)
				Registration::setParentResourcePrimaryKey);
		attributeGetterFunctions.put(
			"registeredByUserId", Registration::getRegisteredByUserId);
		attributeSetterBiConsumers.put(
			"registeredByUserId",
			(BiConsumer<Registration, Long>)
				Registration::setRegisteredByUserId);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getRegistrationId() {
		return _registrationId;
	}

	@Override
	public void setRegistrationId(long registrationId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_registrationId = registrationId;
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
	public long getEventResourcePrimaryKey() {
		return _eventResourcePrimaryKey;
	}

	@Override
	public void setEventResourcePrimaryKey(long eventResourcePrimaryKey) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_eventResourcePrimaryKey = eventResourcePrimaryKey;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalEventResourcePrimaryKey() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("eventResourcePrimaryKey"));
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
	public long getResourcePrimaryKey() {
		return _resourcePrimaryKey;
	}

	@Override
	public void setResourcePrimaryKey(long resourcePrimaryKey) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_resourcePrimaryKey = resourcePrimaryKey;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalResourcePrimaryKey() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("resourcePrimaryKey"));
	}

	@Override
	public String getUserPreferences() {
		if (_userPreferences == null) {
			return "";
		}
		else {
			return _userPreferences;
		}
	}

	@Override
	public void setUserPreferences(String userPreferences) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userPreferences = userPreferences;
	}

	@Override
	public Date getStartTime() {
		return _startTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_startTime = startTime;
	}

	@Override
	public Date getEndTime() {
		return _endTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_endTime = endTime;
	}

	@Override
	public long getParentResourcePrimaryKey() {
		return _parentResourcePrimaryKey;
	}

	@Override
	public void setParentResourcePrimaryKey(long parentResourcePrimaryKey) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_parentResourcePrimaryKey = parentResourcePrimaryKey;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalParentResourcePrimaryKey() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("parentResourcePrimaryKey"));
	}

	@Override
	public long getRegisteredByUserId() {
		return _registeredByUserId;
	}

	@Override
	public void setRegisteredByUserId(long registeredByUserId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_registeredByUserId = registeredByUserId;
	}

	@Override
	public String getRegisteredByUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(
				getRegisteredByUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setRegisteredByUserUuid(String registeredByUserUuid) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalRegisteredByUserId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("registeredByUserId"));
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
			getCompanyId(), Registration.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Registration toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Registration>
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
		RegistrationImpl registrationImpl = new RegistrationImpl();

		registrationImpl.setRegistrationId(getRegistrationId());
		registrationImpl.setGroupId(getGroupId());
		registrationImpl.setEventResourcePrimaryKey(
			getEventResourcePrimaryKey());
		registrationImpl.setCompanyId(getCompanyId());
		registrationImpl.setUserId(getUserId());
		registrationImpl.setResourcePrimaryKey(getResourcePrimaryKey());
		registrationImpl.setUserPreferences(getUserPreferences());
		registrationImpl.setStartTime(getStartTime());
		registrationImpl.setEndTime(getEndTime());
		registrationImpl.setParentResourcePrimaryKey(
			getParentResourcePrimaryKey());
		registrationImpl.setRegisteredByUserId(getRegisteredByUserId());

		registrationImpl.resetOriginalValues();

		return registrationImpl;
	}

	@Override
	public Registration cloneWithOriginalValues() {
		RegistrationImpl registrationImpl = new RegistrationImpl();

		registrationImpl.setRegistrationId(
			this.<Long>getColumnOriginalValue("registrationId"));
		registrationImpl.setGroupId(
			this.<Long>getColumnOriginalValue("groupId"));
		registrationImpl.setEventResourcePrimaryKey(
			this.<Long>getColumnOriginalValue("eventResourcePrimaryKey"));
		registrationImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		registrationImpl.setUserId(this.<Long>getColumnOriginalValue("userId"));
		registrationImpl.setResourcePrimaryKey(
			this.<Long>getColumnOriginalValue("resourcePrimaryKey"));
		registrationImpl.setUserPreferences(
			this.<String>getColumnOriginalValue("userPreferences"));
		registrationImpl.setStartTime(
			this.<Date>getColumnOriginalValue("startTime"));
		registrationImpl.setEndTime(
			this.<Date>getColumnOriginalValue("endTime"));
		registrationImpl.setParentResourcePrimaryKey(
			this.<Long>getColumnOriginalValue("parentResourcePrimaryKey"));
		registrationImpl.setRegisteredByUserId(
			this.<Long>getColumnOriginalValue("registeredByUserId"));

		return registrationImpl;
	}

	@Override
	public int compareTo(Registration registration) {
		int value = 0;

		value = DateUtil.compareTo(getStartTime(), registration.getStartTime());

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

		if (!(object instanceof Registration)) {
			return false;
		}

		Registration registration = (Registration)object;

		long primaryKey = registration.getPrimaryKey();

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

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<Registration> toCacheModel() {
		RegistrationCacheModel registrationCacheModel =
			new RegistrationCacheModel();

		registrationCacheModel.registrationId = getRegistrationId();

		registrationCacheModel.groupId = getGroupId();

		registrationCacheModel.eventResourcePrimaryKey =
			getEventResourcePrimaryKey();

		registrationCacheModel.companyId = getCompanyId();

		registrationCacheModel.userId = getUserId();

		registrationCacheModel.resourcePrimaryKey = getResourcePrimaryKey();

		registrationCacheModel.userPreferences = getUserPreferences();

		String userPreferences = registrationCacheModel.userPreferences;

		if ((userPreferences != null) && (userPreferences.length() == 0)) {
			registrationCacheModel.userPreferences = null;
		}

		Date startTime = getStartTime();

		if (startTime != null) {
			registrationCacheModel.startTime = startTime.getTime();
		}
		else {
			registrationCacheModel.startTime = Long.MIN_VALUE;
		}

		Date endTime = getEndTime();

		if (endTime != null) {
			registrationCacheModel.endTime = endTime.getTime();
		}
		else {
			registrationCacheModel.endTime = Long.MIN_VALUE;
		}

		registrationCacheModel.parentResourcePrimaryKey =
			getParentResourcePrimaryKey();

		registrationCacheModel.registeredByUserId = getRegisteredByUserId();

		return registrationCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Registration, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Registration, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Registration, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply((Registration)this);

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

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Registration>
			_escapedModelProxyProviderFunction =
				ProxyUtil.getProxyProviderFunction(
					Registration.class, ModelWrapper.class);

	}

	private long _registrationId;
	private long _groupId;
	private long _eventResourcePrimaryKey;
	private long _companyId;
	private long _userId;
	private long _resourcePrimaryKey;
	private String _userPreferences;
	private Date _startTime;
	private Date _endTime;
	private long _parentResourcePrimaryKey;
	private long _registeredByUserId;

	public <T> T getColumnValue(String columnName) {
		Function<Registration, Object> function = _attributeGetterFunctions.get(
			columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((Registration)this);
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

		_columnOriginalValues.put("registrationId", _registrationId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put(
			"eventResourcePrimaryKey", _eventResourcePrimaryKey);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("resourcePrimaryKey", _resourcePrimaryKey);
		_columnOriginalValues.put("userPreferences", _userPreferences);
		_columnOriginalValues.put("startTime", _startTime);
		_columnOriginalValues.put("endTime", _endTime);
		_columnOriginalValues.put(
			"parentResourcePrimaryKey", _parentResourcePrimaryKey);
		_columnOriginalValues.put("registeredByUserId", _registeredByUserId);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("registrationId", 1L);

		columnBitmasks.put("groupId", 2L);

		columnBitmasks.put("eventResourcePrimaryKey", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("resourcePrimaryKey", 32L);

		columnBitmasks.put("userPreferences", 64L);

		columnBitmasks.put("startTime", 128L);

		columnBitmasks.put("endTime", 256L);

		columnBitmasks.put("parentResourcePrimaryKey", 512L);

		columnBitmasks.put("registeredByUserId", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private Registration _escapedModel;

}