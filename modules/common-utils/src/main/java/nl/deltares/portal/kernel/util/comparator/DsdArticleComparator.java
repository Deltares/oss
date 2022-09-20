package nl.deltares.portal.kernel.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;

import java.util.Comparator;

public class DsdArticleComparator extends OrderByComparator<DsdArticle> implements Comparator<DsdArticle> {

    @Override
    public int compare(DsdArticle dsdo1, DsdArticle dsdo2) {

        if (dsdo1 instanceof Registration && dsdo2 instanceof Registration) {
            return ((Registration) dsdo1).getStartTime().compareTo(((Registration) dsdo2).getStartTime());
        } else if (dsdo1 instanceof Event && dsdo2 instanceof Event) {
            return ((Event) dsdo1).getStartTime().compareTo(((Event) dsdo2).getStartTime());
        } else if (dsdo1 instanceof Download && dsdo2 instanceof Download) {
            return ((Download) dsdo1).getFileName().compareTo(((Download) dsdo2).getFileName());
        }
//reverse order
        return dsdo1.getTitle().compareTo(dsdo2.getTitle());
    }


}
