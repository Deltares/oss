package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.JournalArticleServiceUtils;

public class JournalArticleServiceUtilsImpl implements JournalArticleServiceUtils {
    @Override
    public JournalArticle getLatestArticle(long classPK) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(classPK);
    }
}
