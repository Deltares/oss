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

package nl.worth.deltares.oss.subversion.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import nl.worth.deltares.oss.subversion.model.Repository;
import nl.worth.deltares.oss.subversion.model.RepositoryModel;
import nl.worth.deltares.oss.subversion.model.RepositorySoap;

/**
 * The base model implementation for the Repository service. Represents a row in the &quot;Subversion_Repository&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>RepositoryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RepositoryImpl}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryImpl
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class RepositoryModelImpl
	extends BaseModelImpl<Repository> implements RepositoryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a repository model instance should use the <code>Repository</code> interface instead.
	 */
	public static final String TABLE_NAME = "Subversion_Repository";

	public static final Object[][] TABLE_COLUMNS = {
		{"repositoryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"repositoryName", Types.VARCHAR},
		{"createdDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("repositoryName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createdDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Subversion_Repository (repositoryId LONG not null primary key,companyId LONG,groupId LONG,classNameId LONG,classPK LONG,repositoryName VARCHAR(75) null,createdDate DATE null,modifiedDate DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table Subversion_Repository";

	public static final String ORDER_BY_JPQL =
		" ORDER BY repository.groupId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Subversion_Repository.groupId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.nl.worth.deltares.oss.subversion.model.Repository"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.nl.worth.deltares.oss.subversion.model.Repository"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.nl.worth.deltares.oss.subversion.model.Repository"),
		true);

	public static final long GROUPID_COLUMN_BITMASK = 1L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Repository toModel(RepositorySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Repository model = new RepositoryImpl();

		model.setRepositoryId(soapModel.getRepositoryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setGroupId(soapModel.getGroupId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setRepositoryName(soapModel.getRepositoryName());
		model.setCreatedDate(soapModel.getCreatedDate());
		model.setModifiedDate(soapModel.getModifiedDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Repository> toModels(RepositorySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Repository> models = new ArrayList<Repository>(soapModels.length);

		for (RepositorySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"lock.expiration.time.nl.worth.deltares.oss.subversion.model.Repository"));

	public RepositoryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _repositoryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRepositoryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _repositoryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Repository.class;
	}

	@Override
	public String getModelClassName() {
		return Repository.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Repository, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Repository, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Repository, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Repository)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Repository, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Repository, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Repository)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Repository, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Repository, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Repository>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Repository.class.getClassLoader(), Repository.class,
			ModelWrapper.class);

		try {
			Constructor<Repository> constructor =
				(Constructor<Repository>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Repository, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Repository, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Repository, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Repository, Object>>();
		Map<String, BiConsumer<Repository, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Repository, ?>>();

		attributeGetterFunctions.put(
			"repositoryId",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getRepositoryId();
				}

			});
		attributeSetterBiConsumers.put(
			"repositoryId",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object repositoryId) {
					repository.setRepositoryId((Long)repositoryId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object companyId) {
					repository.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"groupId",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getGroupId();
				}

			});
		attributeSetterBiConsumers.put(
			"groupId",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object groupId) {
					repository.setGroupId((Long)groupId);
				}

			});
		attributeGetterFunctions.put(
			"classNameId",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getClassNameId();
				}

			});
		attributeSetterBiConsumers.put(
			"classNameId",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object classNameId) {
					repository.setClassNameId((Long)classNameId);
				}

			});
		attributeGetterFunctions.put(
			"classPK",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getClassPK();
				}

			});
		attributeSetterBiConsumers.put(
			"classPK",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object classPK) {
					repository.setClassPK((Long)classPK);
				}

			});
		attributeGetterFunctions.put(
			"repositoryName",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getRepositoryName();
				}

			});
		attributeSetterBiConsumers.put(
			"repositoryName",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(
					Repository repository, Object repositoryName) {

					repository.setRepositoryName((String)repositoryName);
				}

			});
		attributeGetterFunctions.put(
			"createdDate",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getCreatedDate();
				}

			});
		attributeSetterBiConsumers.put(
			"createdDate",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object createdDate) {
					repository.setCreatedDate((Date)createdDate);
				}

			});
		attributeGetterFunctions.put(
			"modifiedDate",
			new Function<Repository, Object>() {

				@Override
				public Object apply(Repository repository) {
					return repository.getModifiedDate();
				}

			});
		attributeSetterBiConsumers.put(
			"modifiedDate",
			new BiConsumer<Repository, Object>() {

				@Override
				public void accept(Repository repository, Object modifiedDate) {
					repository.setModifiedDate((Date)modifiedDate);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask = -1L;

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
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@JSON
	@Override
	public String getRepositoryName() {
		if (_repositoryName == null) {
			return "";
		}
		else {
			return _repositoryName;
		}
	}

	@Override
	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	@JSON
	@Override
	public Date getCreatedDate() {
		return _createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		_createdDate = createdDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Repository.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Repository toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = _escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setRepositoryId(getRepositoryId());
		repositoryImpl.setCompanyId(getCompanyId());
		repositoryImpl.setGroupId(getGroupId());
		repositoryImpl.setClassNameId(getClassNameId());
		repositoryImpl.setClassPK(getClassPK());
		repositoryImpl.setRepositoryName(getRepositoryName());
		repositoryImpl.setCreatedDate(getCreatedDate());
		repositoryImpl.setModifiedDate(getModifiedDate());

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	@Override
	public int compareTo(Repository repository) {
		int value = 0;

		if (getGroupId() < repository.getGroupId()) {
			value = -1;
		}
		else if (getGroupId() > repository.getGroupId()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		if (!(obj instanceof Repository)) {
			return false;
		}

		Repository repository = (Repository)obj;

		long primaryKey = repository.getPrimaryKey();

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
		RepositoryModelImpl repositoryModelImpl = this;

		repositoryModelImpl._originalGroupId = repositoryModelImpl._groupId;

		repositoryModelImpl._setOriginalGroupId = false;

		repositoryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Repository> toCacheModel() {
		RepositoryCacheModel repositoryCacheModel = new RepositoryCacheModel();

		repositoryCacheModel.repositoryId = getRepositoryId();

		repositoryCacheModel.companyId = getCompanyId();

		repositoryCacheModel.groupId = getGroupId();

		repositoryCacheModel.classNameId = getClassNameId();

		repositoryCacheModel.classPK = getClassPK();

		repositoryCacheModel.repositoryName = getRepositoryName();

		String repositoryName = repositoryCacheModel.repositoryName;

		if ((repositoryName != null) && (repositoryName.length() == 0)) {
			repositoryCacheModel.repositoryName = null;
		}

		Date createdDate = getCreatedDate();

		if (createdDate != null) {
			repositoryCacheModel.createdDate = createdDate.getTime();
		}
		else {
			repositoryCacheModel.createdDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			repositoryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			repositoryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		return repositoryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Repository, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Repository, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Repository, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Repository)this));
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
		Map<String, Function<Repository, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Repository, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Repository, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Repository)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final Function<InvocationHandler, Repository>
		_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	private long _repositoryId;
	private long _companyId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _classNameId;
	private long _classPK;
	private String _repositoryName;
	private Date _createdDate;
	private Date _modifiedDate;
	private long _columnBitmask;
	private Repository _escapedModel;

}