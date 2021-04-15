package nl.deltares.portal.kernel.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.utils.DsdParserUtils;

public class SearchResultsComparator extends OrderByComparator<com.liferay.portal.kernel.search.Document> {
    private final DsdParserUtils dsdParserUtil;
    public SearchResultsComparator(DsdParserUtils dsdParserUtils) {
        this.dsdParserUtil = dsdParserUtils;
    }

    @Override
    public int compare(Document o1, Document o2) {

        try {
            JournalArticle o1Article = JournalArticleLocalServiceUtil.getLatestArticle(Long.parseLong(o1.get("entryClassPK")));
            JournalArticle o2Article = JournalArticleLocalServiceUtil.getLatestArticle(Long.parseLong(o2.get("entryClassPK")));
            return  new JournalArticleComparator(dsdParserUtil).compare(o1Article, o2Article);
        } catch (PortalException e) {
            return 0;
        }

    }


}
