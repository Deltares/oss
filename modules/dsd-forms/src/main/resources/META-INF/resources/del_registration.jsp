<%@ include file="/init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/registration.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="delRegistration" var="delRegistrationURL"></portlet:actionURL>
