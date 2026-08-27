package nl.deltares.portal.model.listeners;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.registration.service.RegistrationLocalService;
import nl.deltares.oss.download.service.DownloadLocalService;

import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

    private static final Log LOGGER = LogFactoryUtil.getLog(UserModelListener.class);

    @Reference
    private AccountUtils accountUtils;

    @Reference
    RegistrationLocalService  registrationLocalService;

    @Reference
    DownloadLocalService downloadLocalService;

    @Override
    public void onAfterRemove(User model) throws ModelListenerException {
        try {
            int count = registrationLocalService.deleteAllUserRegistrations(model.getUserId());
            if (count > 0) {
                LOGGER.info(String.format("Deleting %d Registration references for user %d with email %s", count, model.getUserId(), model.getEmailAddress()));
            }

            count = downloadLocalService.deleteAllUserDownloads(model.getUserId());
            if (count > 0) {
                LOGGER.info(String.format("Deleting %d Download references for user %d with email %s", count, model.getUserId(), model.getEmailAddress()));
            }

            count = accountUtils.deleteUserPersonalAccount(model.getScreenName());
            if (count > 0) {
                LOGGER.info(String.format("Deleting personal account for user %d with email %s", model.getUserId(), model.getEmailAddress()));
            }
        } finally {
            super.onAfterRemove(model);
        }
    }
}
