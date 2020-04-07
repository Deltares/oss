package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = "dynamic-data-mapping"
)
@Meta.OCD(
        id = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration"
)
public interface FullCalendarConfiguration {

    @Meta.AD(
            description = "ID of current event",
            required = false
    )
    public String eventId();

    @Meta.AD(
            deflt = "/o/dsd/", description = "Path to event resources endpoint",
            required = false
    )
    public String baseUrl();

//    @Meta.AD(
//            description = "Basic authentication user name",
//            required = false
//    )
//    public String authUser();
//
//    @Meta.AD(
//            description = "Basic authentication user password",
//            required = false
//    )
//    public String authPassword();

}
