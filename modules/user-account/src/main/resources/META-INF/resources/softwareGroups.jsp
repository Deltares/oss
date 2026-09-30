<%@ page import="nl.deltares.useraccount.model.SoftwareGroup" %>
<%
    for (SoftwareGroup softwareGroup : (List<SoftwareGroup>) records) {
        String encodedGroupName = softwareGroup.getGroupName().replace(" ", "_");
%>


<aui:fieldset>
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
                <span class="h1"><%=softwareGroup.getGroupName()%></span>
            </span>
    </a>

    <div class="panel-collapse collapse" id="softwareGroup-<%=encodedGroupName%>">
        <%@ include file="softwareGroupSuites.jsp" %>
    </div>

</aui:fieldset>

<% } %>