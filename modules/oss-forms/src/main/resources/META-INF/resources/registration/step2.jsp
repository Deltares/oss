<%
    for (DeltaresProduct registration : registrationList) {
        final DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");

        String price = "FREE";
        if (registration.getUnitPrice() > 0)
            price = String.format("%s %s", registration.getCurrency(), decimalFormat.format(registration.getUnitPrice()));
        else
            price = LanguageUtil.format(locale, "dsd.theme.session.free", java.util.Optional.empty());

%>

<div class="registration-item">

    <div class="row">
        <div class="col-md-12">
            <div class="d-flex">
                <div class="float-left">
                    <aui:input
                            name='<%="parent_registration_" + (registration.getOrderItemId())%>'
                            label=""
                            type="checkbox"
                            data-price="<%= price %>"
                            course="<%=registration.isCourse() %>"
                            cssClass="parent-registration hidden"
                            checked="true"/>
                </div>
                <div class="float-left w-100">
                    <liferay-commerce-product:product-list-entry-renderer
                            CPCatalogEntry="<%= registration.getCpCatalogEntry() %>"
                    />
                </div>
                <div class="float-right">
                    <aui:input
                            name='<%="count_parent_registration_" + (registration.getOrderItemId())%>'
                            label=""
                            value="<%=registration.getQuantity()%>"
                            type="number"
                            data-article-id="<%=registration.getOrderItemId()%>"
                            onChange='<%="DsdRegistrationFormsUtil.updateTable('" +  namespace  + "', this);"%>'
                            cssClass="parent-registration-quantity"
                    />
                </div>
                <div class="col-2">
                    <div id="parent_registration_price_<%=registration.getOrderItemId()%>" style="display: grid; text-align: right"></div>

                </div>
                <div class="float-right">
                    <clay:button href="#" cssClass="remove-from-cart" data-orderid="<%=registration.getOrderItemId()%>" icon="times-circle"/>
                </div>
                <div class="float-right">
                </div>
            </div>
            <%
                final List<DeltaresProduct> children = registration.getRelatedChildren();
                if (!children.isEmpty()) {
                    if (childHeaderText == null || childHeaderText.isEmpty()) { %>
                        <h3>
                            <liferay-ui:message key="dsd.registration.step1.child.registrations"/>
                        </h3>
                    <% } else { %>
                        <%= childHeaderText %>
                    <% }
                }
            %>
            <%
                for (DeltaresProduct childRegistration : children) {
            %>
            <div class="d-flex">
                <div class="float-left p-3">
                    <aui:input
                            name='<%="child_registration_" + (childRegistration.getOrderItemId())%>'
                            label=""
                            type="checkbox"
                            data-price="<%=childRegistration.getUnitPrice()%>"
                            course="<%=childRegistration.isCourse()%>"
                            data-parentorderid="<%=registration.getOrderItemId()%>"
                            data-childorderid="<%=childRegistration.getOrderItemId()%>"
                            cssClass="child-registration"
                            checked="<%=childRegistration.isSelected()%>"
                    />
                </div>
                <div class="float-left w-100">
                    <liferay-commerce-product:product-list-entry-renderer
                            CPCatalogEntry="<%= childRegistration.getCpCatalogEntry() %>"
                    />
                </div>
            </div>
            <%
                }
            %>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <br/><liferay-ui:message key="dsd.registration.step1.user.information"/><br/>
            <table class="w-100" id="<portlet:namespace />users_table_<%=registration.getOrderItemId()%>">
                <colgroup>
                    <col style="width: 20%"/>
                    <col style="width: 20%"/>
                    <col style="width: 30%"/>
                    <col style="width: 30%"/>
                </colgroup>
                <thead>
                <tr>
                    <th>
                        <liferay-ui:message key="registrationform.job.titles"/>
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
                                name='<%="jobTitles_" + (registration.getOrderItemId())%>'
                                value="<%=user.getJobTitle()%>" max="75">
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="firstName_" + (registration.getOrderItemId())%>'
                                value="<%=user.getFirstName()%>" max="75">
                            <aui:validator name="required">
                                function () {
                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace/>'), 2);
                                }
                            </aui:validator>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="lastName_" + (registration.getOrderItemId())%>'
                                value="<%= user.getLastName() %>" max="75">
                            <aui:validator name="required">
                                function () {
                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace/>'), 2);
                                }
                            </aui:validator>
                        </aui:input>
                    </td>
                    <td>
                        <aui:input
                                label=""
                                name='<%="email_" + (registration.getOrderItemId())%>'
                                value="<%= user.getEmailAddress() %>" max="75">
                            <aui:validator name="email"/>
                            <aui:validator errorMessage="registrationform.validator.emaildomain" name="custom">
                                function(val, fldNode, rule) {
                                return DsdRegistrationFormsUtil.checkEmailDomain('<portlet:namespace/>', val, fldNode);
                                }
                            </aui:validator>
                            <aui:validator name="required">
                                function () {
                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace/>'), 2);
                                }
                            </aui:validator>
                        </aui:input>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
    </div>

</div>

<%
    }
%>

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
                        <liferay-ui:message key="registrationform.price.discount"/>
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

    let children = $(document.getElementsByClassName("child-registration"));
    [...children].forEach(function (registration) {
    registration.onchange = function (){
    DsdRegistrationFormsUtil.checkSelection('<portlet:namespace/>');
    };
    });

    let removeButtons = $(document.getElementsByClassName("remove-from-cart"));
    [...removeButtons].forEach(function (button) {
        button.onclick = function (event){
            let srcElement = event.target.closest("button");
            let orderItem = srcElement.dataset.orderid ;
            let childCheckBoxes = $(document.getElementsByClassName('child-registration'));
            [...childCheckBoxes].forEach(function (checkBox) {
                if (checkBox.dataset.parentorderid === orderItem && checkBox.dataset.childorderid > 0){
                    CommerceUtils.removeFromCart(checkBox.dataset.childorderid);
                }
            })
            CommerceUtils.callDeleteCartItem(orderItem, function (success){
                CommerceUtils.callGetCartItems(Liferay.CommerceContext.order.orderId, function (newOrder){
                    DsdRegistrationFormsUtil.updateTotalPrice('<portlet:namespace/>', newOrder);
                });
            });

            let itemToRemove = document.getElementById('<portlet:namespace/>parent_registration_' + orderItem);
            let containerToRemove = itemToRemove.closest(".registration-item")
            containerToRemove.remove();
            DsdRegistrationFormsUtil.checkSelection("<portlet:namespace/>");

            //looses changes in form
            // location.reload();
        };
    });

</aui:script>