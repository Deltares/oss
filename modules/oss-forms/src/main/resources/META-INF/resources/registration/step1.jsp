<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.forms.internal.RegistrationFormDisplayContext" %>

<%
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
    DsdSessionUtils dsdSessionUtils = (DsdSessionUtils) request.getAttribute("dsdSessionUtils");
    RegistrationFormDisplayContext registrationFormDisplayContext =
            new RegistrationFormDisplayContext(liferayPortletRequest, liferayPortletResponse,
                    dsdParserUtils, dsdSessionUtils);

    List<String> registrationList = (List<String>) request.getAttribute("registrationList");
%>

<c:forEach var="registrationId" items="${registrationList}">

    <c:if test="${not empty registrationId}">

        <%
            String registrationId = (String) pageContext.getAttribute("registrationId");
        %>

        <%
            Registration mainRegistration = dsdParserUtils.getRegistration(
                    themeDisplay.getScopeGroupId(), registrationId);
            //load the stored attributes from the database.
            Map<String, String> userPreferences = dsdSessionUtils.getUserPreferences(user, mainRegistration);
            if (!userPreferences.isEmpty()) attributes.putAll(userPreferences);

        %>

        <div class="registration-item">

            <div class="d-flex">
                <div class="float-left p-3">
                    <aui:input
                            name="parent_registration_${registrationId}"
                            label=""
                            type="checkbox"
                            data-price="<%= mainRegistration.getPrice() %>"
                            course="<%=mainRegistration.isCourse() %>"
                            cssClass="parent-registration"
                            checked="true"/>
                </div>
                <div class="float-left w-100">
                    <%
                        JournalArticleDisplay articleDisplay = registrationFormDisplayContext
                                .getArticleDisplay(ddmTemplateKey, registrationId, themeDisplay);
                    %>
                    <liferay-journal:journal-article-display
                            articleDisplay="<%= articleDisplay %>"
                    />
                </div>
            </div>

            <%
                List<Registration> children = registrationFormDisplayContext
                        .getChildRegistrations(scopeGroupId, registrationId);
            %>
            <% if (children.size() > 0) { %>
            <h3>
                <liferay-ui:message key="dsd.registration.step1.child.registrations"/>

            </h3>
            <% } %>
            <c:forEach var="childRegistration" items="<%= children %>">
                <div class="d-flex">
                    <div class="float-left p-3">
                        <aui:input
                                name="child_registration_${childRegistration.articleId}"
                                label=""
                                type="checkbox"
                                data-price="${childRegistration.getPrice()}"
                                course="${childRegistration.isCourse()}"
                                cssClass="child-registration"
                                checked="${dsdSessionUtils.isUserRegisteredFor(user, childRegistration)}"
                        />
                    </div>
                    <div class="float-left w-100">
                        <%
                            Registration childArticle = (Registration) pageContext.getAttribute("childRegistration");

                            JournalArticleDisplay childrenArticleDisplay = registrationFormDisplayContext
                                    .getArticleDisplay(ddmTemplateKey, childArticle.getJournalArticle().getArticleId(), themeDisplay);
                        %>
                        <liferay-journal:journal-article-display
                                articleDisplay="<%= childrenArticleDisplay %>"
                        />
                    </div>
                </div>
            </c:forEach>

        </div>
    </c:if>

</c:forEach>