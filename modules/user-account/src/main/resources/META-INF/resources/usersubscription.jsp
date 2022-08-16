<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/usersubscription.jsp"/>
</portlet:renderURL>

<portlet:actionURL name="saveSubscriptions" var="saveURL"/>

<liferay-ui:success key="update-subscription-success" >
    <liferay-ui:message key="update.subscription.success"/>
</liferay-ui:success>

<liferay-ui:error key="update-subscription-failed">
    <liferay-ui:message key="update.subscription.failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "update-subscription-failed") %>'/>
</liferay-ui:error>

<aui:form name="saveUserSubscriptions" action="<%=saveURL%>">
    <jsp:useBean id="subscriptions" class="java.util.List" scope="request"/>

    <liferay-ui:search-container id="tableResults"
                                 emptyResultsMessage='<%=LanguageUtil.get(locale, "no-subscriptions-records")%>'
                                 total="<%=subscriptions.size()%>">
        <liferay-ui:search-container-results results="<%= subscriptions %>"/>

        <liferay-ui:search-container-row
                className="nl.deltares.portal.model.keycloak.KeycloakMailing"
                modelVar="entry"
        >

            <liferay-ui:search-container-column-text>
                <aui:input
                        name="selected_${entry.getId()}"
                        label=""
                        type="toggle-switch"
                        changesContext=""
                        cssClass="mailingTableRecord"
                        checked="${entry.isSelected()}"/>
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text property="name" name="Subscription"/>
            <liferay-ui:search-container-column-text property="description" name="Description"/>
            <liferay-ui:search-container-column-text name="Language">
                <aui:select
                        name="language_${entry.getId()}"
                        type="select"
                        label=""
                        value="${entry.getSelectedLanguage()}" >
                    <% String[] languages = entry.getLanguages(); %>
                    <%    for (String language : languages) { %>
                    <aui:option value="<%=language%>" label ="<%= language %>" />
                    <% } %>
                </aui:select>
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="Delivery">
                <aui:select
                        name="delivery_${entry.getId()}"
                        type="select"
                        label=""
                        value="${entry.getSelectedDelivery()}" >
                    <% String[] deliveries = entry.getDeliveries(); %>
                    <%    for (String delivery : deliveries) { %>
                    <aui:option value="<%=delivery%>" label ="<%= delivery %>" />
                    <% } %>
                </aui:select>
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text property="frequency" name="Frequency"/>
        </liferay-ui:search-container-row>
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
    <aui:button-row>
        <aui:button type="submit" name="updateSubscriptions" value="usersubscriptionform.save" />
        <aui:button type="cancel" name="cancelSubscriptions" value="usersubscriptionform.cancel" href="<%= viewURL %>" />
    </aui:button-row>
</aui:form>
<aui:script>

</aui:script>