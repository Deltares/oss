package nl.deltares.search.facet.date.builder;

import com.liferay.petra.string.StringBundler;

public class DateRangeFactory {

    public String getRangeString(String from, String to) {
        StringBundler sb = new StringBundler(5);

        sb.append("[");
        sb.append(from);
        sb.append(" TO ");
        sb.append(to);
        sb.append("]");

        return sb.toString();
    }
}
