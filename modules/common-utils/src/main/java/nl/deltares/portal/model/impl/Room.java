package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.Locale;

public class Room extends AbsDsdArticle {
    private int capacity;

    public Room(JournalArticle dsdArticle, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(dsdArticle, dsdParserUtils, locale);
        init();
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Room.name();
    }

    private void init() throws PortalException {
        try {
            this.capacity = Integer.parseInt(getFormFieldValue( "capacity", false));

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public int getCapacity() {
        return capacity;
    }

}
