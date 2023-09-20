package nl.deltares.portal.kernel.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.Comparator;

public class JournalArticleComparator extends OrderByComparator<JournalArticle> implements Comparator<JournalArticle> {
    private final DsdParserUtils dsdParserUtil;

    public JournalArticleComparator(DsdParserUtils dsdParserUtils) {
        this.dsdParserUtil = dsdParserUtils;
    }

    @Override
    public int compare(JournalArticle o1Article, JournalArticle o2Article) {

        AbsDsdArticle dsdo1;
        AbsDsdArticle dsdo2;
        try {
            dsdo1 = dsdParserUtil.toDsdArticle(o1Article);
        } catch (PortalException e) {
            return 0;
        }

        try {
            dsdo2 = dsdParserUtil.toDsdArticle(o2Article);
        } catch (PortalException e) {
            return 0;
        }

        return new DsdArticleComparator().compare(dsdo1, dsdo2);

    }


}
