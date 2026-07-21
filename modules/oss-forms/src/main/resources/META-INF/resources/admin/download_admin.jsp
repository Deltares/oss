<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="download.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="download.admin.siteConfigTitle"/>  </div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label" > <liferay-ui:message key="download.admin.siteConfigText"/>  </div>
        </aui:col>
    </aui:row>
    <hr>
    <aui:form name="sendTestEmail" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50" >
                    <div class="panel-title" > <liferay-ui:message key="download.admin.testEmailTitle"/>  </div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label" > <liferay-ui:message key="download.admin.testEmailLabel"/>  </div>
                </aui:col>
                <aui:col width="25">
                    <aui:row>
                        <aui:input name="email"  label="" type="email" />
                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button name="sendEmailButton"  type="submit" value="download.admin.testEmailSend" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>

</aui:fieldset>
<aui:script use="event, io, aui-io-request, node, aui-base, aui-progressbar">

    let sendEmailButton = document.getElementById('<portlet:namespace/>sendEmailButton');
    sendEmailButton.addEventListener('click', function (ev) {
    ev.preventDefault();
    DownloadFormsUtil.sendTestEmail("<portlet:resourceURL/>", "<portlet:namespace/>", "testEmail")
    });

</aui:script>
