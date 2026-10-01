<%@ page import="nl.deltares.useraccount.model.SoftwareGroup" %>
<%
    final Map<String, String> groups = (Map<String, String>) request.getAttribute("keycloakGroups");
    for (SoftwareGroup softwareGroup : (List<SoftwareGroup>) records) {
        String groupName = softwareGroup.getGroupName();
        String encodedGroupName = groupName.replace(" ", "_");
        boolean contains = groups.containsKey(groupName);
        boolean isActive = "Active".equals(filterSelection);
%>

<portlet:actionURL name="updateUserGroup" var="updateUserGroupURL">
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
    <portlet:param name="tabSelection" value="<%=tabSelection%>"/>
</portlet:actionURL>

<aui:fieldset>

    <aui:form action="<%=updateUserGroupURL%>" name='<%="form_" + (groupName)%>' >
        <aui:input
                disabled="<%=!isActive%>"
                name='<%=groupName%>'
                label="Member of group: "
                inlineLabel="left"
                type="toggle-switch"
                changesContext=""
                onChange="submit()"
                checked="<%=contains%>"/>
    </aui:form>

    <a href="#softwareGroup-<%=encodedGroupName%>" aria-controls="site_configContent" aria-expanded="false"
       class="collapse-icon collapse-icon-middle sheet-subtitle collapsed" data-toggle="liferay-collapse" role="button">
            <span class="c-inner" tabindex="-1">
                <span class="collapse-icon-closed">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-plus">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#plus"></use>
                    </svg>
                </span>
                <span class="collapse-icon-open">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-hr">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#hr"></use>
                    </svg>
                </span>
                &nbsp;
                <span class="h1"><%=groupName%></span>
            </span>
    </a>
    <div class="panel-collapse collapse" id="softwareGroup-<%=encodedGroupName%>">
        <%@ include file="softwareGroupSuites.jsp" %>
    </div>

</aui:fieldset>

<% } %>