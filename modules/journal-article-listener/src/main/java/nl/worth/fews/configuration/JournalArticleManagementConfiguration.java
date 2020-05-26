package nl.worth.fews.configuration;

import aQute.bnd.annotation.metatype.Meta;
import nl.worth.fews.constants.JournalArticleManagementConstants;

@Meta.OCD(
    id = JournalArticleManagementConstants.JOURNAL_ARTICLE_MANAGEMENT_CONFIGURATION_PID
)
public interface JournalArticleManagementConfiguration {

  @Meta.AD(required = false, deflt = "")
  String configuredGroupIds();

  @Meta.AD(required = false, deflt = "0")
  long dsdParentSiteID();

  @Meta.AD(required = false, deflt = "{\"Blog\":\"Blogs\", \"New idea\":\"New ideas\", \"Best practice\":\"Best practices\", \"Expert\":\"Experts\"}")
  String structureKeyToFolderJsonMap();
}
