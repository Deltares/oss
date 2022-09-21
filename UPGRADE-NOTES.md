Upgrade notes for upgrade Liferay 7.1 to 7.4:


<h1>System Settings</h1>

<h2>Upgrade issues</h2>
Upgrading from 7.1 db to 7.4 messes up the structures containing nested content. Related issue https://issues.liferay.com/browse/LPS-134191
If this persists we must look into 1st upgrading to 7.3 and then 7.4

Upgrading to 7.3 does not break nested structures 
Followed by upgrade to 7.4 results in the structures still containing content, however the nesting has been removed
which causes (velocity) templates to get confused.

velocity seems not to be supported.

<h2>Project folder</h2>
- gradle-local.properties is not being picked up by command ./gradlew startLiferay 
  --updated build.gradle to load local properties explicitly

<h2>Elastic search:</h2>
- renamed config <a href="./configs/docker/osgi/configs/com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration.config" >elasticsearch7</a>
- After starting docker containers make sure Elastic configuration is set to REMOTE and path is set to http://elasticsearch:9200
- Re-index all item

<h2>Java Script</h2>
- jQuery is default turned off
- switch on: System Settings -> Third Party -> JQuery -> enable

<h2>JAVA</h2>
- bump java version from 1.8 to 11 for both project and gradle
- when switching between 7.1 and 7.4 gradle java version needs to be set manually

- 
<h2>Freemarker Scripts</h2>
- System Settings -> Template Engines -> FreeMarker Engine
- Add Allowed Classes; nl.deltares.portal.utils.*
- Remove restricted Variables; staticUtil (not sure yet about this)
- Remove restricted Variables; objectUtils in order to retieve footer in init_custom.ftl
- Servicelocator does not return Deltares utils classes -> as of 7.4 portalext.property "template.engine.service.locator.restrict=true" 
  should be set to false;

<h2>Deltares Forms</h2>
- single oss forms module has been split into separate forms.
- Registration Form throws error 3.0 opt-in:
  - see: https://help.liferay.com/hc/en-us/articles/360017902892-Introduction-to-Breaking-Changes-#upgrade-considerations for resolution.

<h2>journal-article-* modules</h2>
<p>
JournalArticle content is no longer available when listener is triggered. Therefore JournalArticleListener no longer works.
It is possible to override the onBeforeUpdate method instead. However we should rethink if these modules 
can be removed. The functionality is not being used.
</p> 

<h2>Structures configurations</h2>
- session type field renamed from 'type' -> 'registrationType'. Update if necessary for structures; SESSION, DINNER, BUSTRANSFER
- Geolocation fields have been renamed; latitude -> lat, longitude -> lng
- Deactivate all locals in structure fields that do not require translation.
- Multiselect and single select fields do not work with Asset Publisher filtering options. Looks to be a bug. 
  - Apparently this is a known issue: resolve by making sure the field is localizable

<h2>Themes configuration</h2>
- When opening program item the '.header-back-to' appears and redirects to non-existing page. User main.css to set display: none 

<h2>Templates configurations</h2>
- nested elements have automatically been converted to fieldsets. This needs to be changed for all templates. (eg. Footer.ftl)

<h2>REST service</h2>
- security issues when accessing REST service as Guest user:  Access denied to com.liferay.portal.kernel.service.GroupService#getGroup
  - Solution is to add to System_Default in Configuration->Service Access Policy of DSD site: 
    - com.liferay.portal.kernel.service.GroupService#getGroup
    - com.liferay.portal.kernel.service.LayoutService#getLayoutByUuidAndGroupId

<h2>Steps to initialize Docker instance</h2>
- Remove any existing volumes
- First deploy without themes and modules
- Check that the database upgrade processes complete successfully
- Set the required Freemarker properties 