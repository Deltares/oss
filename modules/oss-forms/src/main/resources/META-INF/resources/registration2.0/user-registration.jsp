<%@ include file="init.jsp" %>
<h3><strong><liferay-ui:message key="registrationform.user.information"/></strong></h3>
<br/>
<%
    UserRegistrationDisplayContext displayContext = (UserRegistrationDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    List<Registration> registrations = displayContext != null ? displayContext.getRegistrations() : Collections.emptyList();
    NumberFormat currencyInstance = NumberFormat.getInstance(themeDisplay.getLocale());
    currencyInstance.setMaximumFractionDigits(2);
    currencyInstance.setMinimumFractionDigits(2);
    String currency = "€";
    boolean first = true;
    String srcArticleId= "";
%>
<c:forEach var="registration" items="<%=registrations%>">
    <%
        Registration registration = (Registration) pageContext.getAttribute("registration");
        String articleId = registration.getArticleId();
        currency = registration.getCurrency();
        String price;
        if (registration.getPrice() > 0) {
            price = String.format("%s %s", currency, currencyInstance.format(registration.getPrice()));
        } else {
            price = LanguageUtil.format(locale, "dsd.theme.session.free", java.util.Optional.empty());
        }
        List<RegistrationInfo> registrationInfos = displayContext.getRegistrationInfos(registration);
        int quantity = registrationInfos.size();
        String removeFromCartText = LanguageUtil.get(request, "registrationform.item.remove");
        String copyFormPreviousText = LanguageUtil.get(request, "registrationform.item.copy");
        if (first) {
            srcArticleId = articleId;
        }
    %>
    <div class="row">
        <div class="col-md-12">
            <div class="d-flex">
                <div class="float-left w-100">
                    <%@ include file="registration-view.jsp" %>
                </div>
                <div class="col-4 float-right">
                    <div class="row">
                        <div class="col"><liferay-ui:message key="registrationform.item.count"/></div>
                        <div class="col"><liferay-ui:message key="registrationform.item.price"/></div>
                        <div class="col"></div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <aui:input
                                    name='<%="count_registration_" + articleId%>'
                                    label=""
                                    value="<%=quantity%>"
                                    type="number"
                                    data-articleId="<%=articleId%>"
                                    data-vat="<%=registration.getVAT()%>"
                                    data-currency="<%=registration.getCurrency()%>"
                                    data-price="<%=registration.getPrice()%>"
                                    cssClass="registration-quantity" wrapperCssClass="form-group-item"
                            />

                        </div>
                        <div class="col">
                            <div id="registration_price_<%=articleId%>" style="display: grid; text-align: right">
                                <%=price%>
                            </div>
                        </div>
                        <div class="col"><clay:button href="#" cssClass="remove-from-cart" title="<%= removeFromCartText %>"
                                                      data-articleId="<%=articleId%>" icon="times-circle"/></div>
                        <% if (!first) { %>
                        <div class="col"><clay:button href="#" cssClass="copy-from-previous" title="<%= copyFormPreviousText %>"
                                                      data-srcArticleId="<%=srcArticleId%>" data-destArticleId="<%=articleId%>" icon="copy"/></div>
                        <% } %>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="form-group-autofit">
        <table class="w-100" id="<portlet:namespace />users_table_<%=articleId%>">
            <colgroup>
                <col style="width: 10%"/>
                <col style="width: 20%"/>
                <col style="width: 20%"/>
                <col style="width: 20%"/>
                <col style="width: 30%"/>
            </colgroup>
            <thead>
            <tr>
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
                <th>
                    <liferay-ui:message key="registrationform.remarks"/>
                </th>
            </tr>
            </thead>
            <tbody>

            <%
                int counter = 0;
                String postfix = "";
            %>
            <c:forEach items="<%=registrationInfos%>" var="registrationInfo">
                <tr>
                    <td>
                        <aui:input
                                label=""
                                name='<%="salutation_" + articleId + postfix%>'
                                data-rownumber="<%=counter%>"
                                data-articleId="<%=articleId%>"
                                value='${registrationInfo.salutation}'>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="firstName_" + articleId + postfix%>'
                                data-rownumber="<%=counter%>"
                                data-articleId="<%=articleId%>"
                                value='${registrationInfo.firstName}'>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="lastName_" + articleId + postfix%>'
                                data-rownumber="<%=counter%>"
                                data-articleId="<%=articleId%>"
                                value='${registrationInfo.lastName}'>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="email_" + articleId + postfix%>'
                                data-rownumber="<%=counter%>"
                                data-articleId="<%=articleId%>"
                                value='${registrationInfo.email}'>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                name='<%="remarks_" + articleId + postfix%>'
                                type="textarea"
                                cssClass="remarks"
                                data-rownumber="<%=counter%>"
                                data-articleId="<%=articleId%>"
                                value="${registrationInfo.remarks}"
                                label="">
                        </aui:input>
                    </td>
                </tr>
                <%
                    counter++;
                    postfix = "_" + counter;
                %>
            </c:forEach>

            </tbody>
        </table>

    </div>
    <% first = false; %>
</c:forEach>

<div class="form-group-autofit">
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
                <td>
                    <div id="registrationform.price.subtotal" style="text-align:right">
                    </div>
                </td>
            </tr>
            <tr>
                <td style="text-align:right">
                    <liferay-ui:message key="registrationform.price.tax"/>
                </td>
                <td>
                    <div id="registrationform.price.tax" style="text-align:right">

                    </div>
                </td>
            </tr>

            <tr>
                <td style="text-align:right">
                    <liferay-ui:message key="registrationform.price.total"/>
                </td>
                <td>
                    <div id="registrationform.price.total" style="text-align:right">

                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<aui:script use="event, node, aui-base">

    let quantityButtons = document.getElementsByClassName("registration-quantity");
    Array.from(quantityButtons).forEach(function (button){
        button.onchange = function (event){
            event.constructor
            RegistrationFormsUtil.updateTable('<portlet:namespace/>', event.target);
            RegistrationFormsUtil.updatePrice('<portlet:namespace/>', event.target);
        }
        button.setAttribute('min', 1);
        button.setAttribute('max', 10);
    });

    let removeButtons = document.getElementsByClassName("remove-from-cart");
    Array.from(removeButtons).forEach(function (button) {
        button.onclick = function (event){
            let srcElement = event.target.closest("button");
            let removeArticleId = srcElement.dataset.articleid ;
            shoppingCart._removeFromCart(removeArticleId, 'registration');
            let url = window.location.href;
            url = CommonFormsUtil.removeArticleFromUrl(url, "<portlet:namespace/>ids", removeArticleId );
            window.location.href = url;
        }
    });

    let copyButtons = document.getElementsByClassName("copy-from-previous");
    Array.from(copyButtons).forEach(function (button) {
        button.onclick = function (event){
            let srcElement = event.target.closest("button");
            let srcArticleId = srcElement.dataset.srcarticleid ;
            let destArticleId = srcElement.dataset.destarticleid ;
            RegistrationFormsUtil.copyTable('<portlet:namespace/>', srcArticleId, destArticleId);
        }
    });


    RegistrationFormsUtil.updatePrice('<portlet:namespace/>', null)
</aui:script>
