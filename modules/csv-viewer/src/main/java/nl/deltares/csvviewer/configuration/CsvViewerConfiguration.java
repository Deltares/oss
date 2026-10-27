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
            name = "Filterable columns",
            description = "Comma-separated list of columns that may be filtered"
    )
    String filterableColumns();

    @Meta.AD(
            required = false,
            deflt = "true",
            name = "Strict validation",
            description = "Report invalid rows and empty values"
    )
    boolean strictValidation();
}

