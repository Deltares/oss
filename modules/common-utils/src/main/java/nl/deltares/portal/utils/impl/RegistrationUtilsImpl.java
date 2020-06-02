package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.RegistrationUtils;
import org.osgi.service.component.annotations.Component;

import static com.liferay.journal.service.JournalArticleLocalServiceUtil.getLatestArticle;

@Component(
        immediate = true,
        service = RegistrationUtils.class
)
public class RegistrationUtilsImpl implements RegistrationUtils{


    @Override
    public void validateRegistration(long groupId, long articleId, long userId) throws PortalException {
        JournalArticle article = getLatestArticle(groupId, String.valueOf(articleId));

    }
}
