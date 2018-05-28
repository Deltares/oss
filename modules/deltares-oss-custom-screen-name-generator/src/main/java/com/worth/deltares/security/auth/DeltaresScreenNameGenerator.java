package com.worth.deltares.security.auth;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.DefaultScreenNameValidator;
import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=100"
	}
)
public class DeltaresScreenNameGenerator implements ScreenNameGenerator {

	private static Log _log = LogFactoryUtil.getLog(DeltaresScreenNameGenerator.class);
	
	@Override
	public String generate(long companyId, long userId, String emailAddress) throws Exception {
		
		_log.info("DeltaresScreenNameGenerator.generate");
		
		String screenName = null;

		if (Validator.isNotNull(emailAddress)) {
			screenName = StringUtil.extractFirst(
					emailAddress, StringPool.AT).toLowerCase() + ".x";
			screenName = StringUtil.replace(
				screenName,
				new String[] {StringPool.SLASH, StringPool.UNDERLINE},
				new String[] {StringPool.PERIOD, StringPool.PERIOD});

			if (screenName.equals(DefaultScreenNameValidator.POSTFIX)) {

				screenName += StringPool.PERIOD + userId;
			}
		}
		else {
			screenName = String.valueOf(userId);
		}

		String[] reservedScreenNames = PrefsPropsUtil.getStringArray(
			companyId, PropsKeys.ADMIN_RESERVED_SCREEN_NAMES,
			StringPool.NEW_LINE, _ADMIN_RESERVED_SCREEN_NAMES);

		for (String reservedScreenName : reservedScreenNames) {
			if (screenName.equalsIgnoreCase(reservedScreenName)) {
				return getUnusedScreenName(companyId, screenName);
			}
		}

		try {
			UserLocalServiceUtil.getUserByScreenName(companyId, screenName);
		}
		catch (NoSuchUserException nsue) {
			try {
				GroupLocalServiceUtil.getFriendlyURLGroup(
					companyId, StringPool.SLASH + screenName);
			}
			catch (NoSuchGroupException nsge) {
				return screenName;
			}
		}

		return getUnusedScreenName(companyId, screenName);
	}
	protected String getUnusedScreenName(long companyId, String screenName)
			throws PortalException, SystemException {

			for (int i = 1;; i++) {
				String tempScreenName = screenName + StringPool.PERIOD + i;

				try {
					UserLocalServiceUtil.getUserByScreenName(
						companyId, tempScreenName);
				}
				catch (NoSuchUserException nsue) {
					try {
						GroupLocalServiceUtil.getFriendlyURLGroup(
							companyId, StringPool.SLASH + tempScreenName);
					}
					catch (NoSuchGroupException nsge) {
						screenName = tempScreenName;

						break;
					}
				}
			}

			return screenName;
		}

		private static final String[] _ADMIN_RESERVED_SCREEN_NAMES =
			StringUtil.split(
				PrefsPropsUtil.getString(PropsKeys.ADMIN_RESERVED_SCREEN_NAMES),
				StringPool.NEW_LINE);

}
