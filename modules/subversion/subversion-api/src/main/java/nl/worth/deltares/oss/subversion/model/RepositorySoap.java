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

package nl.worth.deltares.oss.subversion.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link nl.worth.deltares.oss.subversion.service.http.RepositoryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RepositorySoap implements Serializable {

	public static RepositorySoap toSoapModel(Repository model) {
		RepositorySoap soapModel = new RepositorySoap();

		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setRepositoryName(model.getRepositoryName());
		soapModel.setCreatedDate(model.getCreatedDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static RepositorySoap[] toSoapModels(Repository[] models) {
		RepositorySoap[] soapModels = new RepositorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositorySoap[][] toSoapModels(Repository[][] models) {
		RepositorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositorySoap[] toSoapModels(List<Repository> models) {
		List<RepositorySoap> soapModels = new ArrayList<RepositorySoap>(
			models.size());

		for (Repository model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositorySoap[soapModels.size()]);
	}

	public RepositorySoap() {
	}

	public long getPrimaryKey() {
		return _repositoryId;
	}

	public void setPrimaryKey(long pk) {
		setRepositoryId(pk);
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	public Date getCreatedDate() {
		return _createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		_createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private long _repositoryId;
	private long _companyId;
	private long _groupId;
	private long _classNameId;
	private long _classPK;
	private String _repositoryName;
	private Date _createdDate;
	private Date _modifiedDate;

}