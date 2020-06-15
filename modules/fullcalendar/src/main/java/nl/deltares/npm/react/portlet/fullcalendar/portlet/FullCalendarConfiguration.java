package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import aQute.bnd.annotation.metatype.Meta;

//@ExtendedObjectClassDefinition(
//        category = "dynamic-data-mapping"
//)
@Meta.OCD(
        id = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration"
)
public interface FullCalendarConfiguration {

    @Meta.AD(required = false, deflt = "/o/public/dsd/calendar")
    String baseUrl();

    @Meta.AD(required = false, deflt = "0")
    long eventID();
}
