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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DownloadCount}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCount
 * @generated
 */
public class DownloadCountWrapper
	extends BaseModelWrapper<DownloadCount>
	implements DownloadCount, ModelWrapper<DownloadCount> {

	public DownloadCountWrapper(DownloadCount downloadCount) {
		super(downloadCount);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("id", getId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("downloadId", getDownloadId());
		attributes.put("count", getCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long downloadId = (Long)attributes.get("downloadId");

		if (downloadId != null) {
			setDownloadId(downloadId);
		}

		Integer count = (Integer)attributes.get("count");

		if (count != null) {
			setCount(count);
		}
	}

	@Override
	public DownloadCount cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this download count.
	 *
	 * @return the company ID of this download count
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the count of this download count.
	 *
	 * @return the count of this download count
	 */
	@Override
	public int getCount() {
		return model.getCount();
	}

	/**
	 * Returns the download ID of this download count.
	 *
	 * @return the download ID of this download count
	 */
	@Override
	public long getDownloadId() {
		return model.getDownloadId();
	}

	/**
	 * Returns the group ID of this download count.
	 *
	 * @return the group ID of this download count
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the ID of this download count.
	 *
	 * @return the ID of this download count
	 */
	@Override
	public long getId() {
		return model.getId();
	}

	/**
	 * Returns the primary key of this download count.
	 *
	 * @return the primary key of this download count
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this download count.
	 *
	 * @param companyId the company ID of this download count
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the count of this download count.
	 *
	 * @param count the count of this download count
	 */
	@Override
	public void setCount(int count) {
		model.setCount(count);
	}

	/**
	 * Sets the download ID of this download count.
	 *
	 * @param downloadId the download ID of this download count
	 */
	@Override
	public void setDownloadId(long downloadId) {
		model.setDownloadId(downloadId);
	}

	/**
	 * Sets the group ID of this download count.
	 *
	 * @param groupId the group ID of this download count
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the ID of this download count.
	 *
	 * @param id the ID of this download count
	 */
	@Override
	public void setId(long id) {
		model.setId(id);
	}

	/**
	 * Sets the primary key of this download count.
	 *
	 * @param primaryKey the primary key of this download count
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected DownloadCountWrapper wrap(DownloadCount downloadCount) {
		return new DownloadCountWrapper(downloadCount);
	}

}