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
                name="licenseinfo.locktypes"
                label="download.step3b.lock.new"
                type="radio"
                checked="false"
                value="<%=LicenseInfo.LOCKTYPES.new_usb_dongle.name()%>"
        />
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="licenseinfo.locktypes"
                label="download.step3b.lock.existing"
                type="radio"
                checked="false"
                value="<%=LicenseInfo.LOCKTYPES.existing_usb_dongle.name()%>"
        />
    </div>
    <div class="col">
        <aui:input
                name="<%=LicenseInfo.ATTRIBUTES.lock_address.name()%>"
                label=""
                type="text"
                value="9- "
        >
            <aui:validator name="maxLength">25</aui:validator>
        </aui:input>
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="licenseinfo.locktypes"
                label="download.step3b.lock.mac"
                type="radio"
                checked="false"
                value="<%=LicenseInfo.LOCKTYPES.mac_address.name()%>"
        >
            <aui:validator name="maxLength">25</aui:validator>
        </aui:input>
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
                name="licenseinfo.licensetypes"
                label="download.step3b.license.network"
                type="radio"
                checked="false"
                value="<%=LicenseInfo.LICENSETYPES.network.name()%>"
        />
    </div>
</div>
<div class="row">
    <div class="col">
        <aui:input
                name="licenseinfo.licensetypes"
                label="download.step3b.license.standalone"
                type="radio"
                checked="false"
                value="<%=LicenseInfo.LICENSETYPES.standalone.name()%>"
        />
    </div>
</div>
<%
    }
%>
