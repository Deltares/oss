package nl.deltares.portal.kernel.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.display.context.RegistrationDisplayContext;

import java.util.Comparator;

public class RegistrationDisplayContextComparator extends OrderByComparator<RegistrationDisplayContext> implements Comparator<RegistrationDisplayContext> {

    @Override
    public int compare(RegistrationDisplayContext context1, RegistrationDisplayContext context2) {
        final int i = Long.compare(context1.getStartDateMillis(), context2.getStartDateMillis());
        if (i != 0) return i;
        return context1.getRegistration().getTitle().compareTo(context2.getRegistration().getTitle());
    }

}
