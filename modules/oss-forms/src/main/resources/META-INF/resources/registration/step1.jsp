<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<%

%>
<c:forEach var="registrationId" items="${registrationList}">

    <c:if test="${not empty registrationId}">

        <%

            String registrationId = (String) pageContext.getAttribute("registrationId");
            final DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
            Registration mainRegistration;
            try {
                mainRegistration = dsdParserUtils.getRegistration(
                        themeDisplay.getScopeGroupId(), registrationId);
            } catch (PortalException e) {
                throw new RuntimeException(e);
            }
            String price = "FREE";
            if (!mainRegistration.isHidden()) {
                //load the stored attributes from the database.
                Map<String, String> userPreferences;
                try {
                    userPreferences = dsdSessionUtils.getUserPreferences(user, mainRegistration);
                } catch (PortalException e) {
                    throw new RuntimeException(e);
                }
                if (!userPreferences.isEmpty()) attributes.putAll(userPreferences);

                if (mainRegistration.getPrice() > 0)
                    price = String.format("%s %s", mainRegistration.getCurrency(), decimalFormat.format(mainRegistration.getPrice()));
                else
                    price = LanguageUtil.format(locale, "dsd.theme.session.free", java.util.Optional.empty());

            %>
            <div class="registration-item">

                <div class="row">
                    <div class="col-md-12">
                        <div class="d-flex">
                            <div class="float-left w-100">
                                <aui:input
                                        name="parent_registration_${registrationId}"
                                        label=""
                                        type="hidden"
                                        data-price="<%= mainRegistration.getPrice() %>"
                                        course="<%=mainRegistration.isCourse() %>"
                                        cssClass="parent-registration"
                                        value="true" />
                                <%
                                    JournalArticleDisplay articleDisplay = registrationFormDisplayContext
                                            .getArticleDisplay(ddmTemplateKey, registrationId, themeDisplay);
                                %>
                                <liferay-journal:journal-article-display
                                        articleDisplay="<%= articleDisplay %>"
                                />
                            </div>
                            <div class="float-right">
                                <aui:input
                                        name="parent_registration_count_${registrationId}"
                                        label=""
                                        value="1"
                                        min="1"
                                        type="number"
                                        data-article-id="${registrationId}"
                                        onChange='<%="DsdRegistrationFormsUtil.updateTable('" +  namespace  + "', this);"%>'
                                />
                            </div>
                            <div class="float-right">
                                <aui:input
                                        name="parent_registration_price_${registrationId}"
                                        label=""
                                        value="<%=price%>"
                                        data-price="<%= mainRegistration.getPrice() %>"
                                        data-tax="<%= mainRegistration.getVAT() %>"
                                        data-currency="<%= mainRegistration.getCurrency() %>"
                                        disabled="true"
                                        cssClass="parent-registration-price"

                                />
                            </div>
                            <div class="float-right col-md-2"><%=(int)mainRegistration.getVAT()%>% <liferay-ui:message key="registrationform.price.tax"/> </div>
                            <div class="float-right">
                                <a href="#" data-article-id="${registrationId}" class="btn-lg btn-primary remove-from-cart" role="button"
                                   aria-pressed="true"  style="color:#fff" >
                                    <clay:icon symbol="times-circle" />
                                </a>
                            </div>
                            </div>
                            <br/>
                            <%
                                List<Registration> children = registrationFormDisplayContext
                                        .getChildRegistrations(scopeGroupId, registrationId);
                                if (!children.isEmpty() ) {
                                    if (childHeaderText == null || childHeaderText.isEmpty()) { %>
                            <h3>
                                <liferay-ui:message key="dsd.registration.step1.child.registrations"/>
                            </h3>
                            <% } else { %>
                            <%= childHeaderText %>
                            <% }
                            }
                            %>
                        <br/>
                            <c:forEach var="childRegistration" items="<%= children %>">
                                <%
                                    Registration childRegistration = (Registration) pageContext.getAttribute("childRegistration");
                                    boolean checked = registrationList.contains(childRegistration.getArticleId()) || dsdSessionUtils.isUserRegisteredFor(user, childRegistration);
                                %>
                                <div class="d-flex">
                                <div class="float-left">
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
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <br/><liferay-ui:message key="dsd.registration.step1.user.information"/><br/>
                        <table id="<portlet:namespace />users_table_${registrationId}">
                            <colgroup>
                                <col style="width: 20%"/>
                                <col style="width: 10%"/>
                                <col style="width: 20%"/>
                                <col style="width: 20%"/>
                                <col style="width: 30%"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <th>
                                    <liferay-ui:message key="registrationform.job.titles"/>
                                </th>
                                <th>
                                    <liferay-ui:message key="registrationform.salutation"/>
                                </th>
                                <th>
                                    <liferay-ui:message key="registrationform.firstname"/>
                                </th>
                                <th>
                                    <liferay-ui:message key="registrationform.lastname"/>
                                </th>
                                <th>
                                    <liferay-ui:message key="registrationform.email"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <aui:input
                                                label=""
                                                name="jobTitles"
                                                value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.jobTitle.name()) %>">
                                            <aui:validator name="maxLength">75</aui:validator>
                                        </aui:input>
                                    </td>
                                    <td>
                                        <aui:input
                                                label=""
                                                name="salutation"
                                                value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.salutation.name()) %>">
                                            <aui:validator name="maxLength">15</aui:validator>
                                        </aui:input>
                                    </td>
                                    <td>
                                        <aui:input
                                                label=""
                                                name="firstName"
                                                value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.first_name.name()) %>">
                                                <aui:validator name="maxLength">75</aui:validator>
                                                <aui:validator name="required">
                                                    function () {
                                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                                    }
                                                </aui:validator>
                                        </aui:input>
                                    </td>
                                    <td>
                                        <aui:input
                                                label=""
                                                name="lastName"
                                                value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.last_name.name()) %>">
                                            <aui:validator name="maxLength">75</aui:validator>
                                            <aui:validator name="required">
                                                function () {
                                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                                }
                                            </aui:validator>
                                        </aui:input>
                                    </td>
                                    <td >
                                        <aui:input
                                                label=""
                                                name="email"
                                                value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.email.name()) %>">
                                            <aui:validator name="maxLength">75</aui:validator>
                                            <aui:validator name="required">
                                                function () {
                                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                                }
                                            </aui:validator>
                                            <aui:validator name="email"/>
                                        </aui:input>

                                    </td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>

            </div>
        <% } %>
    </c:if>

</c:forEach>

<div class="registration-item">

    <div class="row">
        <div class="col-md-12">
            <table id="<portlet:namespace />total_price_table" class="float-right col-md-4">
                <colgroup>
                    <col style="width: 40%"/>
                    <col style="width: 60%"/>
                </colgroup>
                <tbody>
                <tr>
                    <td style="text-align:right">
                        <liferay-ui:message key="registrationform.price.subtotal"/>
                    </td>
                    <td style="text-align:right">

                    </td>
                </tr>
                <tr>
                    <td style="text-align:right">
                        <liferay-ui:message key="registrationform.price.tax"/>
                    </td>
                    <td style="text-align:right">

                    </td>
                </tr>
                <tr>
                    <td style="text-align:right">
                        <liferay-ui:message key="registrationform.price.total"/>
                    </td>
                    <td style="text-align:right">

                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

<aui:script use="event, node, aui-base, aui-progressbar">

    let removeButtons = $(document.getElementsByClassName("remove-from-cart"));
    [...removeButtons].forEach(function (button) {
        button.onclick = function (event){
            let srcElement;
            if (event.target.tagName === 'use') {
                srcElement = event.target.parentElement.parentElement;
            } else if ('svg' === event.srcElement.tagName) {
                srcElement = event.srcElement.parentElement;
            } else {
                srcElement = event.srcElement;
            }

            let myArticleId = srcElement.attributes.getNamedItem('data-article-id').value;
            let childCheckBoxes = $(document.getElementsByClassName('child-registration'));
            let url = window.location.href;
            [...childCheckBoxes].forEach(function (checkBox) {
                if (checkBox.dataset.parentid === myArticleId){
                    let childId = checkBox.dataset.childid;
                    shoppingCart._removeFromCart(Number(childId), 'registration');
                    url = CommonFormsUtil.removeArticleFromUrl(url, "<portlet:namespace />ids", childId );
                }
            })
            shoppingCart._removeFromCart(Number(myArticleId), 'registration');
            url = CommonFormsUtil.removeArticleFromUrl(url, "<portlet:namespace />ids", myArticleId );
            window.location.href = url;
            event.preventDefault();
        };

        DsdRegistrationFormsUtil.updateTotalPrice('<portlet:namespace />');
    });

</aui:script>