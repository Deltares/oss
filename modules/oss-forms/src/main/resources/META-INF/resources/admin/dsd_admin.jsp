<%@ page import="com.liferay.journal.model.JournalArticle" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    List<JournalArticle> events = (List<JournalArticle>) renderRequest.getAttribute("events");
%>

<span id="<portlet:namespace/>group-message-block"></span>
<aui:input name="runningProcess" type="hidden" />
<aui:fieldset label="dsd.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="dsd.admin.siteConfigTitle"/>  </div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.siteConfigText"/>  </div>
        </aui:col>
    </aui:row>
    <hr>
    <aui:form name="downloadRegistrations" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50" >
                    <div class="panel-title" > <liferay-ui:message key="dsd.admin.downloadTitle"/>  </div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label" > <liferay-ui:message key="dsd.admin.selectDownload"/>  </div>
                </aui:col>
                <aui:col width="25">
                    <aui:row>
                        <aui:select name="eventSelection" label="">
                            <aui:option value="all" >All Events</aui:option>
                            <% for (JournalArticle event : events) { %>
                            <aui:option value="<%=event.getArticleId() %>" ><%= event.getTitle() %></aui:option>
                            <% } %>
                        </aui:select>
                    </aui:row>
                    <aui:row>
                        <aui:input
                                label="dsd.admin.removeMissing"
                                name="removeMissing"
                                type="checkbox"
                                helpMessage="dsd.admin.removeMissingHelp"
                                value="false"/>

                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button name="downloadRegistrationsButton"  type="submit" value="dsd.admin.download" />
                    <aui:button name="downloadReproButton"  type="submit" value="dsd.admin.download.repro" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form name="deleteRegistrations" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50" >
                    <div class="panel-title" > <liferay-ui:message key="dsd.admin.deleteTitle"/>  </div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label" > <liferay-ui:message key="dsd.admin.enterEventId"/>  </div>
                </aui:col>
                <aui:col width="25">
                    <aui:row>
                        <aui:input name="articleId" value="" label="" />
                    </aui:row>
                    <aui:row>
                        <aui:input
                                label="dsd.admin.resourcePrimKey"
                                name="isResourcePrimKey"
                                type="checkbox"
                                helpMessage="dsd.admin.resourcePrimKeyHelp"
                                value="false"/>

                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button name="deleteRegistrationsButton"  type="submit" value="dsd.admin.delete" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
        </aui:col>
    </aui:row>

</aui:fieldset>

<aui:script use="event, io, aui-io-request, node, aui-base, aui-progressbar">

    let downloadRegistrationsButton = document.getElementById('<portlet:namespace/>downloadRegistrationsButton');
    downloadRegistrationsButton.addEventListener('click', function (ev) {
        ev.preventDefault();
        DsdAdminFormsUtil.downloadRegistrations("<portlet:resourceURL/>", "<portlet:namespace/>")
    });

    let downloadReproButton = document.getElementById('<portlet:namespace/>downloadReproButton');
    downloadReproButton.addEventListener('click', function (ev) {
    ev.preventDefault();
    DsdAdminFormsUtil.downloadRepro("<portlet:resourceURL/>", "<portlet:namespace/>")
    });

    let deleteRegistrationsButton = document.getElementById('<portlet:namespace/>deleteRegistrationsButton');
    deleteRegistrationsButton.addEventListener('click', function (ev) {
        ev.preventDefault();
        DsdAdminFormsUtil.deleteRegistrations("<portlet:resourceURL/>", "<portlet:namespace/>")
    });

    let eventSelection = document.getElementById('<portlet:namespace/>eventSelection');
    eventSelection.addEventListener('change', function (ev) {
        ev.preventDefault();
        CommonFormsUtil.clearError('<portlet:namespace/>')
    });

</aui:script>
