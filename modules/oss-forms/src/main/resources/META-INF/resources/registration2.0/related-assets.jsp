<%@ include file="init.jsp" %>
<%
    RelatedAssetsDisplayContext displayContext = (RelatedAssetsDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    String relatedAssetsTemplate = displayContext.getRelatedAssetsTemplate();
    String selectedAssetsTemplate = displayContext.getSelectedAssetsTemplate();

    List<String> selectedArticleIds = displayContext.getSelectedArticleIds();
    List<String> relatedArticleIds = displayContext.getRelatedArticleIds();

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

        if (!relatedArticleIds.isEmpty()) {
    %>
<br />
    <div class="prose prose--app">
        <h4><liferay-ui:message key="registrationform.related.assets"/></h4>
    </div>

    <div class="flex flex-row pb-2 lg:pb-0 spotlight-slider">
    <%
        for (String relatedArticleId : relatedArticleIds) {
            JournalArticleDisplay articleDisplay = displayContext
                    .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, relatedAssetsTemplate, themeDisplay.getSiteGroupId(),
                            relatedArticleId, themeDisplay);

    %>
        <div class="flex flex-col items-center justify-center w-full">
    <liferay-journal:journal-article-display
            articleDisplay="<%= articleDisplay %>"
    />
        </div>
    <%
        }
    %>
    </div>
    <div class="tns-controls" aria-label="Carousel Navigation" tabindex="0">
        <button id="tns-prev-button" data-controls="prev" tabindex="-1" aria-controls="tns1" >prev</button>
        <button id="tns-next-button" data-controls="next" tabindex="-1" aria-controls="tns1">next</button>
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
    if (document.getElementsByClassName('spotlight-slider').length > 0) {
        var slider = tns({
            container: '.spotlight-slider',
            arrowKeys: true,
            autoWidth: false,
            controls: false,
            controlsPosition: 'bottom',
            edgePadding: 5,
            gutter: 5,
            items: 2,
            lazyload: true,
            loop: false,
            mouseDrag: false,
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

        document.querySelector('#tns-prev-button').addEventListener('click', function (evt) {
        evt.preventDefault();
        slider.goTo('prev');
        });
        document.querySelector('#tns-next-button').addEventListener('click', function (evt) {
        evt.preventDefault();
        slider.goTo('next');
        });
    }
</aui:script>