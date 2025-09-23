/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.util.comparator;

import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;
import nl.deltares.forms.util.DeltaresCheckoutStep;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Marco Leo
 */
public class CheckoutStepServiceWrapperOrderComparator
	implements Comparator<ServiceWrapper<DeltaresCheckoutStep>>, Serializable {

	public CheckoutStepServiceWrapperOrderComparator() {
		this(true);
	}

	public CheckoutStepServiceWrapperOrderComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<DeltaresCheckoutStep> serviceWrapper1,
		ServiceWrapper<DeltaresCheckoutStep> serviceWrapper2) {

		int displayOrder1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(), "checkout.step.order",
			Integer.MAX_VALUE);
		int displayOrder2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(), "checkout.step.order",
			Integer.MAX_VALUE);

		int value = Integer.compare(displayOrder1, displayOrder2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}