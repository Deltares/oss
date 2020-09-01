package nl.worth.fews.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.worth.fews.constants.JournalArticleManagementConstants;

@ExtendedObjectClassDefinition(
        category = "dsd-general"
)
@Meta.OCD(
    id = JournalArticleManagementConstants.JOURNAL_ARTICLE_MANAGEMENT_CONFIGURATION_PID
)
public interface JournalArticleManagementConfiguration {

  @Meta.AD(required = false, deflt = "", description = "Configure siteIds of all sites for which to apply folder mapping.")
  String configuredGroupIds();

  @Meta.AD(required = false, deflt = "0", description = "Configure the siteId of the Parent DSD site.")
  long dsdParentSiteID();

  @Meta.AD(required = false, deflt = "", description = "Configure the folders in which to create instances of the different structures.")
  String structureKeyToFolderJsonMap();

}
