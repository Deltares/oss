<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<span id="group-message-block"></span>
<aui:fieldset label="download.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="download.admin.siteConfigTitle"/>  </div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label" > <liferay-ui:message key="download.admin.siteConfigText"/>  </div>
        </aui:col>
    </aui:row>

</aui:fieldset>
