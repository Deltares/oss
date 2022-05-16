package nl.deltares.portal.kernel.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringComparator;
import nl.deltares.portal.display.context.DownloadDisplayContext;

import java.util.Comparator;

public class DownloadDisplayContextComparator extends OrderByComparator<DownloadDisplayContext> implements Comparator<DownloadDisplayContext> {

    final private boolean ascending;
    final private boolean caseSensitive;

    public DownloadDisplayContextComparator(boolean ascending, boolean caseSensitive) {
        this.ascending = ascending;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public int compare(DownloadDisplayContext context1, DownloadDisplayContext context2) {
        final String name1 = context1.getDownload().getFileName();
        final String name2 = context2.getDownload().getFileName();
        return new StringComparator(ascending, caseSensitive).compare(name1, name2);
    }

}
