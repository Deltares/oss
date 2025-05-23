/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RegistrationResource service. Represents a row in the &quot;Service_builder_RegistrationResource&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResourceModel
 * @generated
 */
@ImplementationClassName(
	"nl.deltares.data.service.registration.model.impl.RegistrationResourceImpl"
)
@ProviderType
public interface RegistrationResource
	extends PersistedModel, RegistrationResourceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.data.service.registration.model.impl.RegistrationResourceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RegistrationResource, Long>
		REGISTRATION_RESOURCE_ID_ACCESSOR =
			new Accessor<RegistrationResource, Long>() {

				@Override
				public Long get(RegistrationResource registrationResource) {
					return registrationResource.getRegistrationResourceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<RegistrationResource> getTypeClass() {
					return RegistrationResource.class;
				}

			};

}