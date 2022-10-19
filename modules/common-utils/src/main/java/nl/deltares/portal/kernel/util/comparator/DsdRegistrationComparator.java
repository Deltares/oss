package nl.deltares.portal.kernel.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import nl.deltares.portal.model.impl.Registration;

import java.util.Comparator;

public class DsdRegistrationComparator extends OrderByComparator<Registration> implements Comparator<Registration> {

    @Override
    public int compare(Registration dsdo1, Registration dsdo2) {

        final int i = (dsdo1).getStartTime().compareTo(dsdo2.getStartTime());
        if (i != 0) return i;
        return dsdo1.getTitle().compareTo(dsdo2.getTitle());
    }


}
