package nl.deltares.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.Period;
import nl.deltares.portal.utils.XmlContentUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static nl.deltares.portal.utils.XmlContentUtils.parseContent;

public class RegistrationTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Test
    public void testAET() throws FileNotFoundException, PortalException, ParseException {

        final TimeZone timeZone = TimeZone.getTimeZone("AET");
        parseRegistrationPeriods(simpleDateFormat, timeZone);
    }

    @Test
    public void testGMT10() throws FileNotFoundException, PortalException, ParseException {

        final TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
        parseRegistrationPeriods(simpleDateFormat, timeZone);
    }

    private void parseRegistrationPeriods(SimpleDateFormat simpleDateFormat, TimeZone timeZone) throws PortalException, FileNotFoundException, ParseException {
        simpleDateFormat.setTimeZone(timeZone);

        URL xml = this.getClass().getResource("/data/testAETSession.xml");

        assert xml != null;
        Document document = parseContent("testParseContent", new FileInputStream(xml.getFile()));

        final List<Node> registrationDateFieldSet = XmlContentUtils.getDynamicElementsByNameAsList(document, "registrationDateFieldSet");

        final String startDate = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(0), "registrationDate", false);
        final String startStartTime = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(0), "startTime", false);
        final String startEndTime = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(0), "endTime", false);

        final Date startStart = simpleDateFormat.parse(startDate + "T" + startStartTime);
        final Date endStart = simpleDateFormat.parse(startDate + "T" + startEndTime);

        final Period startDay = new Period(startStart, endStart);

        final String endDate = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(1), "registrationDate", false);
        final String endStartTime = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(1), "startTime", false);
        final String endEndTime = XmlContentUtils.getDynamicContentByName(registrationDateFieldSet.get(1), "endTime", false);

        final Date startEnd = simpleDateFormat.parse(endDate + "T" + endStartTime);
        final Date endEnd = simpleDateFormat.parse(endDate + "T" + endEndTime);

        final Period endDay = new Period(startEnd, endEnd);

        final ArrayList<Period> periods = new ArrayList<>();
        periods.add(endDay);
        periods.add(startDay);

        try {
            new Period(periods.get(0).getStartDate(), periods.get(1).getEndDate());
            Assert.fail("expected exception: endTime can not be before startTime");
        } catch (Exception e) {
            //correct result
        }

        periods.sort(Comparator.comparing(Period::getStartDate));

        final Period period = new Period(periods.get(0).getStartDate(), periods.get(1).getEndDate());
        Assert.assertNotNull(period);
    }


}
