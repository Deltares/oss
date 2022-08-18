<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final String emailAddress = user.getEmailAddress();

    boolean deltaresUser = emailAddress.endsWith("@deltares.nl");
%>
<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/password.jsp"/>
</portlet:renderURL>

<portlet:actionURL name="savePassword" var="savePasswordForm"/>

<liferay-ui:success key="update-password-success" message="" >
    <liferay-ui:message key="update.password.success"
                        arguments='<%= SessionMessages.get(liferayPortletRequest, "update-password-success") %>'/>
</liferay-ui:success>

<liferay-ui:error key="update-password-failed">
    <liferay-ui:message key="update.password.failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "update-password-failed") %>'/>
</liferay-ui:error>

<c:if test="<%=deltaresUser%>" >
    <div class="alert alert-info">
        <liferay-ui:message key="deltares-users-password-alert"/>
    </div>
</c:if>

<div id="passwordForm" <%=(deltaresUser ? "hidden" : "") %> >
<aui:row>
    <aui:col width="100">

        <span><liferay-ui:message key="passwordform.passwordInfo"/></span>

        <aui:form name="updatePassword" action="<%=savePasswordForm%>" >
            <aui:fieldset>
                <aui:row>
                    <aui:col width="100">
                        <aui:input
                                name="currentPassword"
                                label="passwordform.current"
                                value=""
                                type="password"
                        >
                            <aui:validator name="required" />
                        </aui:input>

                    </aui:col>
                </aui:row>
                <aui:row>
                    <aui:col width="100">
                        <aui:input
                                name="newPassword"
                                label="passwordform.new"
                                value=""
                                type="password"
                        >
                            <aui:validator name="required" />
                            <aui:validator name="custom" errorMessage="passwordform.complexity">

                                function(val, fieldNode, ruleValue) {
                                    var regex = new RegExp("(?=^.{8,}$)(?=.*\\d)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
                                    return regex.test(val);
                                }
                            </aui:validator>

                        </aui:input>
                    </aui:col>
                </aui:row>
                <aui:row>
                    <aui:col width="100">
                        <aui:input
                                name="repeatPassword"
                                label="passwordform.repeat"
                                value=""
                                type="password"
                        >
                            <aui:validator name="required"/>
                            <aui:validator name="equalTo">
                                '#<portlet:namespace />newPassword'
                            </aui:validator>

                        </aui:input>
                    </aui:col>
                </aui:row>

                <aui:button-row>
                    <aui:button type="submit" name="updatePasseord" value="passwordform.save"/>
                    <aui:button type="cancel" name="cancelPassword" value="passwordform.cancel" href="<%= viewURL %>"/>
                </aui:button-row>
            </aui:fieldset>
        </aui:form>
    </aui:col>
</aui:row>
</div>