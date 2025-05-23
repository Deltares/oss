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
 * The extended model interface for the RegistrationAttribute service. Represents a row in the &quot;Service_builder_RegistrationAttribute&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttributeModel
 * @generated
 */
@ImplementationClassName(
	"nl.deltares.data.service.registration.model.impl.RegistrationAttributeImpl"
)
@ProviderType
public interface RegistrationAttribute
	extends PersistedModel, RegistrationAttributeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.data.service.registration.model.impl.RegistrationAttributeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RegistrationAttribute, Long>
		REGISTRATION_ATTRIBUTE_ID_ACCESSOR =
			new Accessor<RegistrationAttribute, Long>() {

				@Override
				public Long get(RegistrationAttribute registrationAttribute) {
					return registrationAttribute.getRegistrationAttributeId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<RegistrationAttribute> getTypeClass() {
					return RegistrationAttribute.class;
				}

			};

}