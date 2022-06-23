<%@ page import="nl.deltares.model.LicenseInfo" %>
<%
    if(showLockTypes){
%>
<div class="row">
    <span>
        <liferay-ui:message key="download.step3b.lock.title"/>
    </span>
</div>

<div class="row">
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.LOCKTYPES.new_usb_dongle.name()%>"
                label="download.step3b.lock.new"
                type="checkbox"
                checked="false"
                onClick="updateLockSelection('lock-new')"
        />
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.LOCKTYPES.existing_usb_dongle.name()%>"
                label="download.step3b.lock.existing"
                type="checkbox"
                checked="false"
                onClick="updateLockSelection('lock-existing')"
        />
    </div>
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.ATTRIBUTES.lock_address.name()%>"
                label=""
                type="text"
                value="9- "
        />
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.LOCKTYPES.mac_address.name()%>"
                label="download.step3b.lock.mac"
                type="checkbox"
                checked="false"
                onClick="updateLockSelection('lock-mac')"
        />
    </div>
</div>
<%
    }
%>

<%
    if(showLicenseTypes){
%>
<div class="row">
    <span>
        <liferay-ui:message key="download.step3b.license.title"/>
    </span>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.LICENSETYPES.network.name()%>"
                label="download.step3b.license.network"
                type="checkbox"
                checked="false"
                onClick="updateLicenseSelection('license-network')"
        />
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.LICENSETYPES.standalone.name()%>"
                label="download.step3b.license.standalone"
                type="checkbox"
                checked="false"
                onClick="updateLicenseSelection('license-standalone')"
        />
    </div>
</div>
<%
    }
%>
