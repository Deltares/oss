package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import aQute.bnd.annotation.metatype.Meta;

//@ExtendedObjectClassDefinition(
//        category = "dynamic-data-mapping"
//)
@Meta.OCD(
        id = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration"
)
public interface FullCalendarConfiguration {

    @Meta.AD(required = false)
    public String baseUrl();

    @Meta.AD(required = false)
    public String fontColor();

    @Meta.AD(required = false)
    public String fontFamily();

    @Meta.AD(required = false)
    public int fontSize();

}
