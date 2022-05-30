<%@ page import="nl.deltares.search.facet.program.UserProgramFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
    UserProgramFacetConfiguration configuration =
            (UserProgramFacetConfiguration)
                    renderRequest.getAttribute(UserProgramFacetConfiguration.class.getName());
    String linkToRegistrationsPageForOthers = "";
    if (Validator.isNotNull(configuration)){
        linkToRegistrationsPageForOthers  =  portletPreferences.getValue("linkToRegistrationsPageForOthers", configuration.linkToRegistrationsPageForOthers());
    }

    Boolean hasMadeRegistrationsForOthers = (Boolean)renderRequest.getAttribute("hasMadeRegistrationsForOthers");

    if (hasMadeRegistrationsForOthers && !linkToRegistrationsPageForOthers.isEmpty()){

%>
<aui:a href="<%=linkToRegistrationsPageForOthers%>" > <liferay-ui:message key="facet.program.registrations.for.others"/></aui:a>
<%
} else {
%>

<div class="alert alert-info text-center">
    <liferay-ui:message key="facet.program.caption"/>
</div>

<%
    }
%>
