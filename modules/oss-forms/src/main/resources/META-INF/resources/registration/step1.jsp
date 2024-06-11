<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.forms.internal.RegistrationFormDisplayContext" %>

<%
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
    String callerAction = (String) request.getAttribute("callerAction");
    DsdSessionUtils dsdSessionUtils = (DsdSessionUtils) request.getAttribute("dsdSessionUtils");
    RegistrationFormDisplayContext registrationFormDisplayContext =
            new RegistrationFormDisplayContext(liferayPortletRequest, liferayPortletResponse,
                    dsdParserUtils, dsdSessionUtils);

    List<String> registrationList = (List<String>) request.getAttribute("registrationList");
    boolean hideButton = true;
%>

<%--@elvariable id="registrationList" type="java.util.List"--%>
<c:forEach var="registrationId" items="${registrationList}">

    <c:if test="${not empty registrationId}">

        <%
            String registrationId = (String) pageContext.getAttribute("registrationId");
        %>

        <%
            Registration mainRegistration;
            try {
                mainRegistration = dsdParserUtils.getRegistration(
                        themeDisplay.getScopeGroupId(), registrationId);
            } catch (PortalException e) {
                throw new RuntimeException(e);
            }
            if (mainRegistration != null && !mainRegistration.isHidden()) {
                //load the stored attributes from the database.
                Map<String, String> userPreferences;
                try {
                    userPreferences = dsdSessionUtils.getUserPreferences(user, mainRegistration);
                } catch (PortalException e) {
                    throw new RuntimeException(e);
                }
                if (!userPreferences.isEmpty()) attributes.putAll(userPreferences);
                hideButton = dsdSessionUtils.isUserRegisteredFor(user, mainRegistration);
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
                    <div class="float-right p-3">
                        <% if(hideButton) {%>
                            <a href="#" data-article-id="${registrationId}" class="btn-lg btn-primary add-to-cart" role="button"
                               aria-pressed="true"  style="color:#fff" hidden >
                            </a>
                        <% } else {%>
                            <a href="#" data-article-id="${registrationId}" class="btn-lg btn-primary add-to-cart" role="button"
                               aria-pressed="true"  style="color:#fff" >
                            </a>
                        <% } %>

                    </div>
                </div>

                <%
                    List<Registration> children = registrationFormDisplayContext
                            .getChildRegistrations(scopeGroupId, registrationId);
                %>
                <% if (!children.isEmpty()) { %>
                <h3>
                    <liferay-ui:message key="dsd.registration.step1.child.registrations"/>

                </h3>
                <% } %>
                <c:forEach var="childRegistration" items="<%= children %>">
                    <%
                        Registration childRegistration = (Registration) pageContext.getAttribute("childRegistration");
                        boolean checked = registrationList.contains(childRegistration.getArticleId()) || dsdSessionUtils.isUserRegisteredFor(user, childRegistration);
                    %>
                    <div class="d-flex">
                        <div class="float-left p-3">
                            <aui:input
                                    name="child_registration_${childRegistration.articleId}"
                                    label=""
                                    type="checkbox"
                                    data-price="${childRegistration.getPrice()}"
                                    course="${childRegistration.isCourse()}"
                                    data-parentid="${registrationId}"
                                    data-childid="${childRegistration.articleId}"
                                    cssClass="child-registration"
                                    checked="<%=checked%>"
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
        <% } %>
    </c:if>

</c:forEach>
<aui:script use="event, node, aui-base, aui-progressbar">

    let removeButtons = $(document.getElementsByClassName("add-to-cart"));
    [...removeButtons].forEach(function (button) {
        button.onclick = function (event){
            let myArticleId = event.srcElement.attributes.getNamedItem('data-article-id').value;
            let childCheckBoxes = $(document.getElementsByClassName('child-registration'));
            let url = window.location.href;
            [...childCheckBoxes].forEach(function (checkBox) {
                if (checkBox.dataset.parentid === myArticleId){
                    let childId = checkBox.dataset.childid;
                    shoppingCart._removeFromCart(Number(childId), 'registration');
                    url = CommonFormsUtil.removeArticleFromUrl(url, "<portlet:namespace />ids", childId );
                }
            })
            url = CommonFormsUtil.removeArticleFromUrl(url, "<portlet:namespace />ids", myArticleId );
            window.location.href = url;
            event.preventDefault();
        };
    });

</aui:script>