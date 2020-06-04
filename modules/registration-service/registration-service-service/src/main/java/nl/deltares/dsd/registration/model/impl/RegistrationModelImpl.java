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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
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
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.RegistrationModel;

/**
 * The base model implementation for the Registration service. Represents a row in the &quot;Registrations_Registration&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>RegistrationModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RegistrationImpl}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationImpl
 * @generated
 */
@ProviderType
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
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"articleId", Types.BIGINT}, {"userPreferences", Types.VARCHAR},
		{"startTime", Types.TIMESTAMP}, {"endTime", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("registrationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("articleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userPreferences", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("startTime", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("endTime", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Registrations_Registration (registrationId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,articleId LONG,userPreferences VARCHAR(75) null,startTime DATE null,endTime DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table Registrations_Registration";

	public static final String ORDER_BY_JPQL =
		" ORDER BY registration.startTime DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Registrations_Registration.startTime DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.deltares.dsd.registration.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.nl.deltares.dsd.registration.model.Registration"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.deltares.dsd.registration.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.nl.deltares.dsd.registration.model.Registration"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		nl.deltares.dsd.registration.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.nl.deltares.dsd.registration.model.Registration"),
		true);

	public static final long ARTICLEID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long USERID_COLUMN_BITMASK = 4L;

	public static final long STARTTIME_COLUMN_BITMASK = 8L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		nl.deltares.dsd.registration.service.util.ServiceProps.get(
			"lock.expiration.time.nl.deltares.dsd.registration.model.Registration"));

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

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

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

	private static Function<InvocationHandler, Registration>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Registration.class.getClassLoader(), Registration.class,
			ModelWrapper.class);

		try {
			Constructor<Registration> constructor =
				(Constructor<Registration>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
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
			"registrationId",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getRegistrationId();
				}

			});
		attributeSetterBiConsumers.put(
			"registrationId",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(
					Registration registration, Object registrationId) {

					registration.setRegistrationId((Long)registrationId);
				}

			});
		attributeGetterFunctions.put(
			"groupId",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getGroupId();
				}

			});
		attributeSetterBiConsumers.put(
			"groupId",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(Registration registration, Object groupId) {
					registration.setGroupId((Long)groupId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(
					Registration registration, Object companyId) {

					registration.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"userId",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getUserId();
				}

			});
		attributeSetterBiConsumers.put(
			"userId",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(Registration registration, Object userId) {
					registration.setUserId((Long)userId);
				}

			});
		attributeGetterFunctions.put(
			"articleId",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getArticleId();
				}

			});
		attributeSetterBiConsumers.put(
			"articleId",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(
					Registration registration, Object articleId) {

					registration.setArticleId((Long)articleId);
				}

			});
		attributeGetterFunctions.put(
			"userPreferences",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getUserPreferences();
				}

			});
		attributeSetterBiConsumers.put(
			"userPreferences",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(
					Registration registration, Object userPreferences) {

					registration.setUserPreferences((String)userPreferences);
				}

			});
		attributeGetterFunctions.put(
			"startTime",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getStartTime();
				}

			});
		attributeSetterBiConsumers.put(
			"startTime",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(
					Registration registration, Object startTime) {

					registration.setStartTime((Date)startTime);
				}

			});
		attributeGetterFunctions.put(
			"endTime",
			new Function<Registration, Object>() {

				@Override
				public Object apply(Registration registration) {
					return registration.getEndTime();
				}

			});
		attributeSetterBiConsumers.put(
			"endTime",
			new BiConsumer<Registration, Object>() {

				@Override
				public void accept(Registration registration, Object endTime) {
					registration.setEndTime((Date)endTime);
				}

			});

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
		_registrationId = registrationId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public long getArticleId() {
		return _articleId;
	}

	@Override
	public void setArticleId(long articleId) {
		_columnBitmask |= ARTICLEID_COLUMN_BITMASK;

		if (!_setOriginalArticleId) {
			_setOriginalArticleId = true;

			_originalArticleId = _articleId;
		}

		_articleId = articleId;
	}

	public long getOriginalArticleId() {
		return _originalArticleId;
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
		_userPreferences = userPreferences;
	}

	@Override
	public Date getStartTime() {
		return _startTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		_columnBitmask = -1L;

		_startTime = startTime;
	}

	@Override
	public Date getEndTime() {
		return _endTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		_endTime = endTime;
	}

	public long getColumnBitmask() {
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
		registrationImpl.setCompanyId(getCompanyId());
		registrationImpl.setUserId(getUserId());
		registrationImpl.setArticleId(getArticleId());
		registrationImpl.setUserPreferences(getUserPreferences());
		registrationImpl.setStartTime(getStartTime());
		registrationImpl.setEndTime(getEndTime());

		registrationImpl.resetOriginalValues();

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Registration)) {
			return false;
		}

		Registration registration = (Registration)obj;

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

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		RegistrationModelImpl registrationModelImpl = this;

		registrationModelImpl._originalGroupId = registrationModelImpl._groupId;

		registrationModelImpl._setOriginalGroupId = false;

		registrationModelImpl._originalUserId = registrationModelImpl._userId;

		registrationModelImpl._setOriginalUserId = false;

		registrationModelImpl._originalArticleId =
			registrationModelImpl._articleId;

		registrationModelImpl._setOriginalArticleId = false;

		registrationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Registration> toCacheModel() {
		RegistrationCacheModel registrationCacheModel =
			new RegistrationCacheModel();

		registrationCacheModel.registrationId = getRegistrationId();

		registrationCacheModel.groupId = getGroupId();

		registrationCacheModel.companyId = getCompanyId();

		registrationCacheModel.userId = getUserId();

		registrationCacheModel.articleId = getArticleId();

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

		return registrationCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Registration, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Registration, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Registration, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Registration)this));
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
		Map<String, Function<Registration, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Registration, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Registration, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Registration)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Registration>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _registrationId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _articleId;
	private long _originalArticleId;
	private boolean _setOriginalArticleId;
	private String _userPreferences;
	private Date _startTime;
	private Date _endTime;
	private long _columnBitmask;
	private Registration _escapedModel;

}