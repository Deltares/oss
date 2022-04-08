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

package nl.deltares.oss.download.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class DownloadCountSoap implements Serializable {

	public static DownloadCountSoap toSoapModel(DownloadCount model) {
		DownloadCountSoap soapModel = new DownloadCountSoap();

		soapModel.setId(model.getId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setDownloadId(model.getDownloadId());
		soapModel.setCount(model.getCount());

		return soapModel;
	}

	public static DownloadCountSoap[] toSoapModels(DownloadCount[] models) {
		DownloadCountSoap[] soapModels = new DownloadCountSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DownloadCountSoap[][] toSoapModels(DownloadCount[][] models) {
		DownloadCountSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DownloadCountSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DownloadCountSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DownloadCountSoap[] toSoapModels(List<DownloadCount> models) {
		List<DownloadCountSoap> soapModels = new ArrayList<DownloadCountSoap>(
			models.size());

		for (DownloadCount model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DownloadCountSoap[soapModels.size()]);
	}

	public DownloadCountSoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
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

	public long getDownloadId() {
		return _downloadId;
	}

	public void setDownloadId(long downloadId) {
		_downloadId = downloadId;
	}

	public int getCount() {
		return _count;
	}

	public void setCount(int count) {
		_count = count;
	}

	private long _id;
	private long _companyId;
	private long _groupId;
	private long _downloadId;
	private int _count;

}