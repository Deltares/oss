<#assign journalArticleTitle = .vars['reserved-article-title'].data/>
<div class="cta_box">
    <div class="h1">${journalArticleTitle}</div>
    ${content.getData()}
    
    <#if addElementText?? &&  addElementText.getData()??>
        <p>Publish your blog</p>
    </#if>
    <a href="#" class="regular-text open_add_modal">
    <img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;
        Publish your blog
    </a>
</div>


<!--blogs-->
<script type="text/javascript" data-senna-track="temporary">
    AUI().ready( 'aui-node', 'node-event-simulate',    function(A) {
        
        $(".open_add_modal").on("click", function( event ) {
            event.preventDefault();
            A.one('[data-title="New Blog"]').simulate("click");
        });
    });
</script>