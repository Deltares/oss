<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>


<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="nl.deltares.forms.internal.RegistrationFormDisplayContext" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.utils.DsdSessionUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>

<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.portal.model.impl.Event" %>
<%@ page import="com.liferay.portal.kernel.util.DateUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />