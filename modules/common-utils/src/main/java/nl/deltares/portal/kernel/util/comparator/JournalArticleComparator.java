package nl.deltares.portal.kernel.util.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
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

        if (dsdo1 instanceof Registration && dsdo2 instanceof Registration){
            return ((Registration)dsdo1).getStartTime().compareTo(((Registration) dsdo2).getStartTime());
        } else if (dsdo1 instanceof Event && dsdo2 instanceof Event){
            return ((Event)dsdo1).getStartTime().compareTo(((Event) dsdo2).getStartTime());
        }
//reverse order
        return dsdo1.getTitle().compareTo(dsdo2.getTitle());
    }


}
