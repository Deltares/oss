package nl.deltares.csvviewer.configuration;

import aQute.bnd.annotation.metatype.Meta;
import nl.deltares.csvviewer.constants.CsvViewerPortletKeys;

@Meta.OCD(
        id = CsvViewerPortletKeys.CSV_VIEWER_CONFIGUTATION_PID
)
public interface CsvViewerConfiguration {

    @Meta.AD(
            required = false,
            deflt = "",
            name = "CSV file URL",
            description = "Location of the CSV file to read and display"
    )
    String csvFileUrl();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Username",
            description = "Username for accessing CSV file URL"
    )
    String username();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Password",
            description = "Password for accessing CSV file URL"
    )
    String password();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Display columns",
            description = "Comma-separated list of columns that may be displayed"
    )
    String displayColumns();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Resource column",
            description = "Column to use for time-line resource values"
    )
    String resourceColumn();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Event title column",
            description = "Column to use for time-line event title values"
    )
    String eventTitleColumn();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Event start column",
            description = "Column to use for time-line event start time values"
    )
    String eventStartColumn();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Event end column",
            description = "Column to use for time-line event end time values"
    )
    String eventEndColumn();

    @Meta.AD(
            required = false,
            deflt = "",
            name = "Event color column",
            description = "Column to use for time-line event color keys"
    )
    String eventColorColumn();


    @Meta.AD(
            required = false,
            deflt = "",
            name = "Color map",
            description = "JSON string object containing key <-> color mappings"
    )
    String colorMap();


}

