<%@ page import="nl.deltares.model.BadgeInfo" %>
<%@ include file="init.jsp" %>

<%
    if (SessionErrors.contains(request, RegistrationFormException.class)) {
        List<RegistrationFormException> errors = (List<RegistrationFormException>) SessionErrors.get(request, RegistrationFormException.class);

        for (RegistrationFormException error : errors) {
%>
<liferay-ui:error exception="<%=RegistrationFormException.class%>">
    <%= error.getMessage() %>
</liferay-ui:error>
<%
        }
    }
%>

<h3><strong><liferay-ui:message key="registrationform.badge.information"/></strong></h3>
<br/>
<%
    BadgeConfigCheckoutStepDisplayContext displayContext = (BadgeConfigCheckoutStepDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    BadgeInfo badgeInfo = displayContext.getBadgeInfo();
%>

<div class="row">
    <div class="col-md-12">
        <%
            String emailBannerURL = displayContext.getBannerUrl();
            String eventTitle = displayContext.getEventTitle();
            String eventDate = displayContext.getEventDate();
        %>
        <div class="card mb-3">
            <% if (emailBannerURL != null) { %>
            <img src="<%=emailBannerURL%>" width="100%" alt="">
            <% } else { %>
            <div class="card-header">
                <%= eventTitle %> <span
                    class="d-block event-edition"><%= eventDate %></span>

            </div>
            <% } %>
            <div class="registration-container">
                <div class="card-body px-5 py-6">
                    <h1 class="card-title" id="badge-title"></h1>
                </div>
            </div>
        </div>


        <span><liferay-ui:message key="dsd.registration.step2.show.title"/></span>
        <%
            String title_setting = badgeInfo.getTitleSetting();
            boolean yes_checked = "yes".equals(title_setting);
            boolean no_checked = "no".equals(title_setting);
            if (!(yes_checked || no_checked)) no_checked = true;
        %>
        <div class="d-flex justify-content-start">
            <aui:input
                    name="badge-info"
                    type="hidden"
                    data-salutation="<%= badgeInfo.getTitle() %>"
                    data-initials="<%= badgeInfo.getInitials()%>"
                    data-firstname="<%= badgeInfo.getFirstName()%>"
                    data-lastname="<%= badgeInfo.getLastName()%>"
            />
            <div class="pr-3">
                <aui:input
                        name="<%= BadgeInfo.ATTRIBUTES.badge_title_setting.name() %>"
                        label="yes"
                        cssClass="update-badge"
                        type="radio"
                        value="yes"
                        checked="<%=yes_checked%>" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= BadgeInfo.ATTRIBUTES.badge_title_setting.name() %>"
                        label="no"
                        cssClass="update-badge"
                        type="radio"
                        value="no"
                        checked="<%=no_checked%>"  />
            </div>
        </div>

        <span><liferay-ui:message key="dsd.registration.step2.badge.name"/></span>
        <%
            String name_setting = badgeInfo.getNameSetting();
            boolean name_checked = "name".equals(name_setting);
            boolean initials_checked = "initials".equals(name_setting);
            boolean both_checked = "both".equals(name_setting);

            if (!(name_checked || initials_checked || both_checked)) name_checked = true;
        %>
        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="<%= BadgeInfo.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.1"
                        cssClass="update-badge"
                        type="radio"
                        value="name"
                        checked="<%=name_checked%>"/>
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= BadgeInfo.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.2"
                        cssClass="update-badge"
                        type="radio"
                        value="initials"
                        checked="<%=initials_checked%>" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="<%= BadgeInfo.ATTRIBUTES.badge_name_setting.name() %>"
                        label="dsd.registration.step2.badge.name.3"
                        cssClass="update-badge"
                        type="radio"
                        value="both"
                        checked="<%=both_checked%>" />
            </div>
        </div>

    </div>

</div>

<aui:script use="event, node, aui-base">

    RegistrationFormsUtil.updateBadge("<portlet:namespace />");
    let badgeListeners = $(document.getElementsByClassName("update-badge"));
    [...badgeListeners].forEach(function (item) {
        item.onchange = function (){
            RegistrationFormsUtil.updateBadge("<portlet:namespace />");
        };
    });

</aui:script>