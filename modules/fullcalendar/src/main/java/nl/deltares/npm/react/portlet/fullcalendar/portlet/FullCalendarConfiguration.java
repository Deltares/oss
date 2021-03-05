package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = "oss-general"
)
@Meta.OCD(
        id = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration",
        localization = "content/Language", name = "full-calendar-configuration"
)
public interface FullCalendarConfiguration {

    @Meta.AD(required = false, deflt = "/o/public/dsd/calendar")
    String baseUrl();

    @Meta.AD(required = false, deflt = "{}")
    String sessionColorMap();
}
