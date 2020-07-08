package nl.deltares.services.rest.registration.modules;

import javax.ws.rs.WebApplicationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateParam {

    // Declare the date format for the parsing to be correct
    private static final SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
    static {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    private final Date date;

    public DateParam(String dateStr) {

        try {
            date = new Date( df.parse( dateStr ).getTime() );
        } catch ( ParseException ex ) {
            // Wrap up any expection as javax.ws.rs.WebApplicationException
            throw new WebApplicationException( ex );
        }
    }

    public Date getDate() {
        return date;
    }

}
