<%@ include file="init.jsp" %>
<h3><strong><liferay-ui:message key="registrationform.related.assets"/></strong></h3>
<br/>
<%
    RelatedAssetsDisplayContext displayContext = (RelatedAssetsDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    List<Registration> relatedArticles = displayContext != null ? displayContext.getRelatedArticles() : Collections.emptyList();

    for (Registration relatedArticle : relatedArticles) {
        JournalArticleDisplay articleDisplay = displayContext
                .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, "PROGRAM-LIST-1.0.1",
                        relatedArticle.getJournalArticle(), themeDisplay);

%>
<liferay-journal:journal-article-display
        articleDisplay="<%= articleDisplay %>"
/>
<%
    }
%>
<aui:script >

    let addToCartButtons = document.getElementsByClassName('add-to-cart')

    Array.from(addToCartButtons).forEach(function (button) {
        button.addEventListener('click', function (event){
            var idsInput = document.getElementById("<portlet:namespace />ids");
            let ids;
            if (idsInput.value === "") {
                ids = [];
            } else {
                ids = idsInput.value.split(',');
            }
            let id = this.dataset["articleId"];
            const index = ids.indexOf(id);
            if (index > -1){
                ids.splice(index, 1);
                idsInput.value = ids.join(',');
            } else {
                ids.push(id)
                idsInput.value = ids.join(',');
            }
        });

    })


</aui:script>