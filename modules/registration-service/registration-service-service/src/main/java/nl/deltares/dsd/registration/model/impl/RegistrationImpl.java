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

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model implementation for the Registration service. Represents a row in the &quot;Registrations_Registration&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.deltares.dsd.registration.model.Registration<code> interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class RegistrationImpl extends RegistrationBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a registration model instance should use the {@link nl.deltares.dsd.registration.model.Registration} interface instead.
	 */
	public RegistrationImpl() {
	}

}