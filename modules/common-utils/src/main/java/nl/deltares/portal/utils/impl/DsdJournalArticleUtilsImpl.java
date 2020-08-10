package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Component(
        immediate = true,
        service = DsdJournalArticleUtils.class
)
public class DsdJournalArticleUtilsImpl implements DsdJournalArticleUtils {

    @Reference
    DDMStructureUtil ddmStructureUtil;

    @Override
    public JournalArticle getLatestArticle(long classPK) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(classPK);
    }

    @Override
    public List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException {

        Optional<DDMStructure> eventStructure = ddmStructureUtil.getDDMStructureByName("EVENT", locale);
        if (eventStructure.isPresent()){
            String structureKey = eventStructure.get().getStructureKey();
            DuplicateCheck check = new DuplicateCheck();
            try {
                List<JournalArticle> structureArticles = JournalArticleLocalServiceUtil.getStructureArticles(groupId, structureKey);
                return check.filterLatest(structureArticles);
            } catch (Exception e) {
                throw new PortalException(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public JournalArticle getJournalArticle(long groupId, String articleId) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId);
    }

    @Override
    public JournalArticle getJournalArticle(long resourceId) throws PortalException {
        return JournalArticleServiceUtil.getLatestArticle(resourceId);
    }

}
