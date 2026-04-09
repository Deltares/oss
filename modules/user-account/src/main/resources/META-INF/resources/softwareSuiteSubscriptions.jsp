<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="nl.deltares.useraccount.model.SoftwareSuiteSubscription" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="nl.deltares.useraccount.model.CustomerContact" %>
<%@ page import="nl.deltares.useraccount.model.Asset" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    List<SoftwareSuiteSubscription> subscriptionList = softwareSuite.getSubscriptionList();

    for (SoftwareSuiteSubscription entry : subscriptionList) {
        Date startDate = entry.getStartDate();
        Date endDate = entry.getEndDate();
        String subscriptionPeriod = (startDate == null ? "" : format.format(startDate)) + " - " + (endDate == null ? "" : format.format(endDate));

        String subscriptionState = entry.getSubscriptionState();
        String subscriptionStateKey = subscriptionState.toLowerCase();

        if ("Active".equals(subscriptionState) ){
            subscriptionState = "Running";
            subscriptionStateKey = "running";
        } else if ("ActiveButTerminated".equals(subscriptionState)){
            subscriptionState = "Running but terminated";
            subscriptionStateKey = "running";
        }

        String licenseCount;
        if (entry.getLicenseCount() == -1){
            licenseCount = "&infin;";
        } else {
            licenseCount = String.valueOf(entry.getLicenseCount());
        }
        String lincenseUsed;
        if (entry.getLicenseUsed() == -1){
            lincenseUsed = "&infin;";
        } else {
            lincenseUsed = String.valueOf(entry.getLicenseUsed());
        }
        String contractType = LanguageUtil.get(request, entry.getContractType());

        String supportHours;
        if (entry.getSupportHours() == -1){
            supportHours = "Within reasonable limits";
        } else {
            supportHours = entry.getSupportHours() + " hours";
        }


%>

<aui:fieldset cssClass="c-subscription-container">
    <aui:row>

        <aui:col width="33">
            <div>Status:</div>
            <span class="c-subscription c-state-<%=subscriptionStateKey%>"><%=subscriptionState%></span>
        </aui:col>
        <aui:col width="33">
            <div>Contract type:</div>
            <div><strong><%=contractType%></strong></div>
        </aui:col>
        <aui:col width="33">
            <div>Start date - End date:</div>
            <div><strong><%=subscriptionPeriod%></strong></div>
        </aui:col>

    </aui:row>
<br/>
    <aui:row>
        <aui:col width="66">
            <div>Software product:</div>
            <div><strong><%=entry.getSoftwareProductName()%></strong></div>
        </aui:col>
        <aui:col width="33">
            <div>Version:</div>
            <div><strong><%=entry.getSoftwareVersion()%></strong></div>
        </aui:col>

    </aui:row>
    <br/>
    <%
        if (entry.getSupportLevelName() != null){
    %>
    <aui:row>
        <aui:col width="33">
            <div>Support level:</div>
            <span class="c-subscription c-support"><%=entry.getSupportLevelName()%>&nbsp;<%=entry.getSupportLevelValue()%></span>
        </aui:col>
        <aui:col width="33">
            <div>Support hours:</div>
            <div><strong><%= supportHours %></strong></div>
        </aui:col>
        <aui:col width="33">
            <div>Number of users / licenses:</div>
            <strong><span><%=lincenseUsed%> / <%= licenseCount %></span></strong>
        </aui:col>
    </aui:row>
    <br/>
    <%
        }
    %>

    <%
        if (!entry.getCustomerContactList().isEmpty()){
    %>
    <div class="lfr-form-content">
        <div aria-multiselectable="true" class>
            <aui:fieldset id='<%="customer_contact" + (entry.getSubscriptionId())%>' collapsible="true"
                          label="Contact person:">
                <% for (CustomerContact customerContact : entry.getCustomerContactList()) {%>
                <aui:row>
                    <aui:col width="66">
                        <div>
                            <strong><%=customerContact.getContactSalutation()%>&nbsp;<%=customerContact.getContactName()%>
                            </strong>
                        </div>
                    </aui:col>
                    <aui:col width="33">

                    </aui:col>
                </aui:row>
                <%}%>

            </aui:fieldset>
        </div>
    </div>
    <%
        }
    %>

    <%
        if (!entry.getAssetList().isEmpty()){
    %>
    <div class="lfr-form-content">
        <div aria-multiselectable="true" class>
            <aui:fieldset id='<%="asset" + (entry.getSubscriptionId())%>' collapsible="true"
                          label="Assets:">
                <aui:row>
                    <aui:col width="33">
                        <div>
                            Asset type:
                        </div>
                    </aui:col>
                    <aui:col width="33">
                        <div>Mac address / Server name:</div>
                    </aui:col>
                    <aui:col width="33">
                        <div>Number of users:</div>
                    </aui:col>
                </aui:row>
                <% for (Asset asset : entry.getAssetList()) {
                %>
                <aui:row>
                    <aui:col width="33">
                        <strong><%="StandAlone".equals(asset.getType()) ? "Local" : asset.getType() %></strong>
                    </aui:col>
                    <aui:col width="33">
                        <div>
                            <strong><%=asset.getFormattedHardwareId()%></strong>
                        </div>
                        <div>
                            <h4><%=asset.getServerName() == null ? "" : asset.getServerName()%></h4>
                        </div>
                    </aui:col>
                    <aui:col width="33">
                        <div><strong><%=asset.getUserCount()%></strong></div>
                    </aui:col>
                </aui:row>
                <%}%>

            </aui:fieldset>
        </div>
    </div>
    <%
        }
    %>

</aui:fieldset>

<hr/>

<%
    }
%>