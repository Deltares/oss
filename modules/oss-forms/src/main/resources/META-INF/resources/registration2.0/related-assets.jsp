<%@ include file="init.jsp" %>
<%
    RelatedAssetsDisplayContext displayContext = (RelatedAssetsDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    String relatedAssetsTemplate = (String) request.getAttribute("relatedAssetsTemplate");
    String selectedAssetsTemplate = (String) request.getAttribute("selectedAssetsTemplate");

    List<String> selectedArticleIds = displayContext != null ? displayContext.getSelectedArticleIds() : Collections.emptyList();
    List<Registration> relatedArticles = displayContext != null ? displayContext.getRelatedArticles() : Collections.emptyList();

%>
    <div class="prose prose--app">
        <h3><liferay-ui:message key="registrationform.selected.assets"/></h3>
    </div>
<br />
    <%
        for (String selectedArticleId : selectedArticleIds) {
            JournalArticleDisplay articleDisplay = displayContext
                    .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, selectedAssetsTemplate, themeDisplay.getSiteGroupId(),
                            selectedArticleId, themeDisplay);

    %>
    <liferay-journal:journal-article-display
            articleDisplay="<%= articleDisplay %>"
    />
    <%
        }

        if (relatedArticles.size() > 0) {
    %>
<br />
    <div class="prose prose--app">
        <h4><liferay-ui:message key="registrationform.related.assets"/></h4>
    </div>

    <div class="flex flex-row pb-2 lg:pb-0 spotlight-slider">
    <%
        for (Registration relatedArticle : relatedArticles) {
            JournalArticleDisplay articleDisplay = displayContext
                    .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, relatedAssetsTemplate, relatedArticle.getGroupId(),
                            relatedArticle.getJournalArticle().getArticleId(), themeDisplay);

    %>
    <liferay-journal:journal-article-display
            articleDisplay="<%= articleDisplay %>"
    />
    <%
        }
    %>
    </div>
<%
    }
%>
<aui:script >

    var addToCartButtons = document.getElementsByClassName('add-to-cart')

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

    var slider = tns({
        container: '.spotlight-slider',
        arrowKeys: true,
        autoWidth: false,
        controls: true,
        controlsPosition: 'bottom',
        edgePadding: 16,
        gutter: 10,
        items: 2,
        lazyload: true,
        loop: false,
        mouseDrag: true,
        navPosition: 'bottom',
        preventScrollOnTouch: 'auto',
        fixedWidth: 254,
        responsive: {
            760: {
                edgePadding: 50
            },
            1024: {
                edgePadding: 80
            }
        },
        speed: 400,
        swipeAngle: false
    });
</aui:script>