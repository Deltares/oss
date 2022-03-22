<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign downloadGroup = parserUtils.toDsdArticle(groupId, articleId) />
<#assign imageUrl = downloadGroup.getSmallImageURL(themeDisplay) />
<div>
    <a href="${downloadGroup.getGroupPage()}">
        <img alt="${downloadGroup.getTitle()}" src="${imageUrl}" width="91"/>
        <ul>
            <li><h2>${downloadGroup.getName()}</h2></li>
            <li><h3>${downloadGroup.getDownloadCount()}</h3></li>
        </ul>
        <div class="description"><p>${downloadGroup.getDescription()}</p></div>
    </a>
</div>