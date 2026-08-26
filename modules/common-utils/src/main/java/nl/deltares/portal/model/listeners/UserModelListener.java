package nl.deltares.portal.model.listeners;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

    private static final Log LOGGER = LogFactoryUtil.getLog(UserModelListener.class);

    @Override
    public void onBeforeRemove(User model) throws ModelListenerException {

        try {
            int count = RegistrationLocalServiceUtil.deleteAllUserRegistrations(model.getUserId());
            if (count > 0) {
                LOGGER.info(String.format("Deleting %d Registration references for user %d with email %s", count, model.getUserId(), model.getEmailAddress()));
            }

            count = DownloadLocalServiceUtil.deleteAllUserDownloads(model.getUserId());
            if (count > 0) {
                LOGGER.info(String.format("Deleting %d Download references for user %d with email %s", count, model.getUserId(), model.getEmailAddress()));
            }
        } finally {
            super.onBeforeRemove(model);
        }

    }
}
