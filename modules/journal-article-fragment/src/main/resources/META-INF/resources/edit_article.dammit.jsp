<liferay-util:buffer var="html">
    <liferay-util:include page="/edit_article.original.jsp" servletContext="<%=application %>" />
</liferay-util:buffer>

<%@ page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.model.Role" %>
<%@ page import="com.liferay.portal.kernel.model.RoleConstants" %>
<%@ page import="com.liferay.portal.kernel.model.UserGroupGroupRole" %>
<%@ page import="com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil" %>

<!-- WORTH changes -->
<div>
    <h1>YUP</h1>
</div>

<%
    List<Role> roles = RoleLocalServiceUtil.getUserGroupRoles(
            themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
    boolean showOptions = permissionChecker.isOmniadmin() || roles.size() > 1;

    if (roles.size() == 1) {
        showOptions = showOptions || !roles.get(0).getName().equals(RoleConstants.SITE_MEMBER);
    }

    List<UserGroupGroupRole> userGroupGroupRoles = UserGroupGroupRoleLocalServiceUtil
            .getUserGroupGroupRolesByUser(themeDisplay.getUserId());

    for (UserGroupGroupRole sel : userGroupGroupRoles) {
        if (sel.getGroup().getGroupId() == themeDisplay.getScopeGroupId()) {
            showOptions = showOptions || !sel.getRole().getName().equals(RoleConstants.SITE_MEMBER);
        }
    }
%>

<c:if test="<%= !showOptions %>">
    <style>
        #p_p_id<portlet:namespace /> fieldset.panel-default.panel {
            display: none;
        }

        [data-fieldname*="_hide"] {
            display: none;
        }
    </style>
</c:if>
<!-- END WORTH changes -->