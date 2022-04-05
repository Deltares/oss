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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DownloadCount}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCount
 * @generated
 */
@ProviderType
public class DownloadCountWrapper
	implements DownloadCount, ModelWrapper<DownloadCount> {

	public DownloadCountWrapper(DownloadCount downloadCount) {
		_downloadCount = downloadCount;
	}

	@Override
	public Class<?> getModelClass() {
		return DownloadCount.class;
	}

	@Override
	public String getModelClassName() {
		return DownloadCount.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("downloadId", getDownloadId());
		attributes.put("count", getCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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
	public Object clone() {
		return new DownloadCountWrapper((DownloadCount)_downloadCount.clone());
	}

	@Override
	public int compareTo(
		nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return _downloadCount.compareTo(downloadCount);
	}

	/**
	 * Returns the count of this download count.
	 *
	 * @return the count of this download count
	 */
	@Override
	public int getCount() {
		return _downloadCount.getCount();
	}

	/**
	 * Returns the download ID of this download count.
	 *
	 * @return the download ID of this download count
	 */
	@Override
	public long getDownloadId() {
		return _downloadCount.getDownloadId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _downloadCount.getExpandoBridge();
	}

	/**
	 * Returns the primary key of this download count.
	 *
	 * @return the primary key of this download count
	 */
	@Override
	public long getPrimaryKey() {
		return _downloadCount.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _downloadCount.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _downloadCount.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _downloadCount.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _downloadCount.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _downloadCount.isNew();
	}

	@Override
	public void persist() {
		_downloadCount.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_downloadCount.setCachedModel(cachedModel);
	}

	/**
	 * Sets the count of this download count.
	 *
	 * @param count the count of this download count
	 */
	@Override
	public void setCount(int count) {
		_downloadCount.setCount(count);
	}

	/**
	 * Sets the download ID of this download count.
	 *
	 * @param downloadId the download ID of this download count
	 */
	@Override
	public void setDownloadId(long downloadId) {
		_downloadCount.setDownloadId(downloadId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_downloadCount.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_downloadCount.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_downloadCount.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_downloadCount.setNew(n);
	}

	/**
	 * Sets the primary key of this download count.
	 *
	 * @param primaryKey the primary key of this download count
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_downloadCount.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_downloadCount.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<nl.deltares.oss.download.model.DownloadCount> toCacheModel() {

		return _downloadCount.toCacheModel();
	}

	@Override
	public nl.deltares.oss.download.model.DownloadCount toEscapedModel() {
		return new DownloadCountWrapper(_downloadCount.toEscapedModel());
	}

	@Override
	public String toString() {
		return _downloadCount.toString();
	}

	@Override
	public nl.deltares.oss.download.model.DownloadCount toUnescapedModel() {
		return new DownloadCountWrapper(_downloadCount.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _downloadCount.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DownloadCountWrapper)) {
			return false;
		}

		DownloadCountWrapper downloadCountWrapper = (DownloadCountWrapper)obj;

		if (Objects.equals(
				_downloadCount, downloadCountWrapper._downloadCount)) {

			return true;
		}

		return false;
	}

	@Override
	public DownloadCount getWrappedModel() {
		return _downloadCount;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _downloadCount.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _downloadCount.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_downloadCount.resetOriginalValues();
	}

	private final DownloadCount _downloadCount;

}