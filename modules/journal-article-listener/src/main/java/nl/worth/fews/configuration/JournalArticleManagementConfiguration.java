package nl.worth.fews.configuration;

import aQute.bnd.annotation.metatype.Meta;
import nl.worth.fews.constants.JournalArticleManagementConstants;

@Meta.OCD(
    id = JournalArticleManagementConstants.JOURNAL_ARTICLE_MANAGEMENT_CONFIGURATION_PID
)
public interface JournalArticleManagementConfiguration {

  @Meta.AD(required = false, deflt = "", description = "Configure siteIds of all sites for which to apply folder mapping.")
  String configuredGroupIds();

  @Meta.AD(required = false, deflt = "0", description = "Configure the siteId of the Parent DSD site.")
  long dsdParentSiteID();

  @Meta.AD(required = false, deflt = "{\"Blog\":\"Blogs\", \"New idea\":\"New ideas\", \"Best practice\":\"Best practices\", " +
          "\"Location\":\"Locations\", \"Eventlocation\":\"Locations\",  \"Building\":\"Locations\",  \"Room\":\"Locations\", " +
          "\"Expert\":\"Experts\", \"Registration\":\"Registrations\", \"Session\":\"Registrations\", \"Dinner\":\"Registrations\", " +
          "\"Bustransfer\":\"Registrations\"}", description = "Configure the folders in which to create instances of the different structures.")
  String structureKeyToFolderJsonMap();

  @Meta.AD(required = false, deflt = "Session", description = "Configure the structure key for DSD Sessions.")
  String dsdSessionStructureKey();
}
