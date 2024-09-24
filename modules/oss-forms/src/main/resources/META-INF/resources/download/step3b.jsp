<%@ page import="nl.deltares.portal.constants.LicenseConstants" %>
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
                value="<%=LicenseConstants.LOCK_TYPES_NEW_USB_DONGLE%>"
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
                value="<%=LicenseConstants.LOCK_TYPES_EXISTING_USB_DONGLE%>"
        />
    </div>
    <div class="col">
        <aui:input
                name="<%=LicenseConstants.DONGLE_NUMBER%>"
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
                value="<%=LicenseConstants.LOCK_TYPES_MAC_ADDRESS%>"
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
                value="<%=LicenseConstants.LICENSE_TYPES_NETWORK%>"
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
                value="<%=LicenseConstants.LICENSE_TYPES_STANDALONE%>"
        />
    </div>
</div>
<%
    }
%>
