<div class="row">
    <span>
        <liferay-ui:message key="dsd.registration.step5.conditions.title"/>
        <p>
            <liferay-ui:message key="dsd.registration.step5.conditions.accept"/>:
        </p>
    </span>
</div>
<%
    for (Terms aTerms : terms) {
        final String name = aTerms.getName();
        final String id = "terms-" + aTerms.getArticleId();
        final String termsURL = aTerms.getTermsURL();
%>
<aui:row>
    <aui:col width="50">
        <aui:input
                name="<%=id%>"
                label="<%=name%>"
                type="checkbox">
            <aui:validator name="required">
                function () {
                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 5);
                }
            </aui:validator>
        </aui:input>
    </aui:col>
    <aui:col width="50">
        <aui:a href="<%=termsURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step5.conditions.download"/></aui:a>
    </aui:col>
</aui:row>
<%
    }
%>