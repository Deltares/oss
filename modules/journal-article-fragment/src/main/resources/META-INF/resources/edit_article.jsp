<%@ page import="com.liferay.document.library.kernel.exception.FileSizeException" %>
<%@ page import="com.liferay.document.library.kernel.util.DLValidatorUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.kernel.DDMStructure" %>
<%@ page import="com.liferay.dynamic.data.mapping.kernel.DDMTemplate" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil" %>
<%@ page import="com.liferay.exportimport.kernel.exception.ExportImportContentValidationException" %>
<%@ page import="com.liferay.journal.model.JournalArticle" %>
<%@ page import="com.liferay.journal.model.JournalArticleConstants" %>
<%@ page import="com.liferay.journal.model.JournalFolder" %>
<%@ page import="com.liferay.journal.model.JournalFolderConstants" %>
<%@ page import="com.liferay.journal.service.JournalFolderLocalServiceUtil" %>
<%@ page import="com.liferay.journal.web.internal.portlet.JournalPortlet" %>
<%@ page import="com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission" %>
<%@ page import="com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission" %>
<%@ page import="com.liferay.journal.web.util.JournalUtil" %>
<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Role" %>
<%@ page import="com.liferay.portal.kernel.model.RoleConstants" %>
<%@ page import="com.liferay.portal.kernel.model.UserGroupGroupRole" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ page import="com.liferay.portal.kernel.security.permission.ActionKeys" %>
<%@ page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants" %>
<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %><%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
    String redirect = ParamUtil.getString(request, "redirect");

    String portletResource = ParamUtil.getString(request, "portletResource");

    long referringPlid = ParamUtil.getLong(request, "referringPlid");
    String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

    boolean changeStructure = GetterUtil
            .getBoolean(ParamUtil.getString(request, "changeStructure"));

    JournalArticle article = journalDisplayContext.getArticle();

    long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

    long folderId = BeanParamUtil
            .getLong(article, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

    long classNameId = BeanParamUtil.getLong(article, request, "classNameId");
    long classPK = BeanParamUtil.getLong(article, request, "classPK");

    String articleId = BeanParamUtil.getString(article, request, "articleId");

    double version = BeanParamUtil
            .getDouble(article, request, "version", JournalArticleConstants.VERSION_DEFAULT);

    String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

    if (Validator.isNull(ddmStructureKey) && (article != null)) {
        ddmStructureKey = article.getDDMStructureKey();
    }

    DDMStructure ddmStructure = null;

    long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

    if (ddmStructureId > 0) {
        ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId);
    } else if (Validator.isNotNull(ddmStructureKey)) {
        ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
                (article != null) ? article.getGroupId() : themeDisplay.getSiteGroupId(),
                PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey, true);
    }

    String ddmTemplateKey = ParamUtil.getString(request, "ddmTemplateKey");

    if (Validator.isNull(ddmTemplateKey) && (article != null) && Objects
            .equals(article.getDDMStructureKey(), ddmStructureKey)) {
        ddmTemplateKey = article.getDDMTemplateKey();
    }

    DDMTemplate ddmTemplate = null;

    long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

    if (ddmTemplateId > 0) {
        ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(ddmTemplateId);
    } else if (Validator.isNotNull(ddmTemplateKey)) {
        ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
                (article != null) ? article.getGroupId() : themeDisplay.getSiteGroupId(),
                PortalUtil.getClassNameId(DDMStructure.class), ddmTemplateKey, true);
    }

    if (ddmTemplate == null) {
        List<DDMTemplate> ddmTemplates = DDMTemplateServiceUtil
                .getTemplates(company.getCompanyId(), ddmStructure.getGroupId(),
                        PortalUtil.getClassNameId(DDMStructure.class),
                        ddmStructure.getStructureId(),
                        PortalUtil.getClassNameId(JournalArticle.class), true,
                        WorkflowConstants.STATUS_APPROVED);

        if (!ddmTemplates.isEmpty()) {
            ddmTemplate = ddmTemplates.get(0);
        }
    }

    String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

    if (article != null) {
        defaultLanguageId = LocalizationUtil
                .getDefaultLanguageId(article.getContent(), LocaleUtil.getSiteDefault());
    }

    boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

    boolean hideDefaultSuccessMessage = ParamUtil
            .getBoolean(request, "hideDefaultSuccessMessage", false);

    request.setAttribute("edit_article.jsp-redirect", redirect);

    request.setAttribute("edit_article.jsp-structure", ddmStructure);
    request.setAttribute("edit_article.jsp-template", ddmTemplate);

    request.setAttribute("edit_article.jsp-defaultLanguageId", defaultLanguageId);

    request.setAttribute("edit_article.jsp-changeStructure", changeStructure);

    if (showHeader) {
        portletDisplay.setShowBackIcon(true);

        if (Validator.isNotNull(redirect)) {
            portletDisplay.setURLBack(redirect);
        } else if ((classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) && (article
                != null)) {
            PortletURL backURL = liferayPortletResponse.createRenderURL();

            backURL.setParameter("groupId", String.valueOf(article.getGroupId()));
            backURL.setParameter("folderId", String.valueOf(article.getFolderId()));

            portletDisplay.setURLBack(backURL.toString());
        }

        String title;

        if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
            title = LanguageUtil.get(request, "structure-default-values");
        } else if ((article != null) && !article.isNew()) {
            title = article.getTitle(locale);
        } else {
            title = LanguageUtil.get(request, "new-web-content");
        }

        renderResponse.setTitle(title);
    }

    boolean approved = false;
    boolean pending = false;

    long inheritedWorkflowDDMStructuresFolderId = JournalFolderLocalServiceUtil
            .getInheritedWorkflowFolderId(folderId);

    boolean hasInheritedWorkflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil
            .hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId,
                    JournalArticle.class.getName());

    if (inheritedWorkflowDDMStructuresFolderId > 0) {
        JournalFolder inheritedWorkflowDDMStructuresFolder = JournalFolderLocalServiceUtil
                .getFolder(inheritedWorkflowDDMStructuresFolderId);

        hasInheritedWorkflowDefinitionLink = false;

        if (inheritedWorkflowDDMStructuresFolder.getRestrictionType()
                == JournalFolderConstants.RESTRICTION_TYPE_INHERIT) {
            hasInheritedWorkflowDefinitionLink = true;
        }
    }

    boolean workflowEnabled =
            hasInheritedWorkflowDefinitionLink || WorkflowDefinitionLinkLocalServiceUtil
                    .hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId,
                            JournalFolder.class.getName(), folderId, ddmStructure.getStructureId())
                    || WorkflowDefinitionLinkLocalServiceUtil
                    .hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId,
                            JournalFolder.class.getName(), inheritedWorkflowDDMStructuresFolderId,
                            ddmStructure.getStructureId()) || WorkflowDefinitionLinkLocalServiceUtil
                    .hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId,
                            JournalFolder.class.getName(), inheritedWorkflowDDMStructuresFolderId,
                            JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

    if ((article != null) && (version > 0)) {
        approved = article.isApproved();

        if (workflowEnabled) {
            pending = article.isPending();
        }
    }

    boolean hasSavePermission = false;

    if ((article != null) && !article.isNew()) {
        hasSavePermission = JournalArticlePermission
                .contains(permissionChecker, article, ActionKeys.UPDATE);
    } else {
        hasSavePermission = JournalFolderPermission
                .contains(permissionChecker, groupId, folderId, ActionKeys.ADD_ARTICLE);
    }

    String saveButtonLabel = "save";

    if ((article == null) || article.isApproved() || article.isDraft() || article.isExpired()
            || article.isScheduled()) {
        saveButtonLabel = "save-as-draft";
    }

    String publishButtonLabel = "publish";

    if (workflowEnabled) {
        publishButtonLabel = "submit-for-publication";
    }

    if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
        publishButtonLabel = "save";
    }
%>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>"/>

<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
    <portlet:param name="mvcPath" value="/edit_article.jsp"/>
    <portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>"/>
</portlet:actionURL>

<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
    <portlet:param name="mvcPath" value="/edit_article.jsp"/>
</portlet:renderURL>

<liferay-frontend:edit-form
        action="<%= editArticleActionURL %>"
        enctype="multipart/form-data"
        method="post"
        name="fm1"
        onSubmit="event.preventDefault();"
>
    <aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden"/>
    <aui:input name="hideDefaultSuccessMessage" type="hidden"
               value="<%= hideDefaultSuccessMessage || (classNameId == PortalUtil.getClassNameId(DDMStructure.class)) %>"/>
    <aui:input name="redirect" type="hidden" value="<%= redirect %>"/>
    <aui:input name="portletResource" type="hidden" value="<%= portletResource %>"/>
    <aui:input name="referringPlid" type="hidden" value="<%= referringPlid %>"/>
    <aui:input name="referringPortletResource" type="hidden"
               value="<%= referringPortletResource %>"/>
    <aui:input name="groupId" type="hidden" value="<%= groupId %>"/>
    <aui:input name="privateLayout" type="hidden" value="<%= layout.isPrivateLayout() %>"/>
    <aui:input name="folderId" type="hidden" value="<%= folderId %>"/>
    <aui:input name="classNameId" type="hidden" value="<%= classNameId %>"/>
    <aui:input name="classPK" type="hidden" value="<%= classPK %>"/>
    <aui:input name="articleId" type="hidden" value="<%= articleId %>"/>
    <aui:input name="articleIds" type="hidden"
               value="<%= articleId + JournalPortlet.VERSION_SEPARATOR + version %>"/>
    <aui:input name="version" type="hidden"
               value="<%= ((article == null) || article.isNew()) ? version : article.getVersion() %>"/>
    <aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>"/>
    <aui:input name="changeStructure" type="hidden"/>
    <aui:input name="ddmStructureId" type="hidden"/>
    <aui:input name="ddmTemplateId" type="hidden"/>
    <aui:input name="workflowAction" type="hidden"
               value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>"/>

    <liferay-frontend:edit-form-body>
        <liferay-ui:error exception="<%= ArticleContentSizeException.class %>"
                          message="you-have-exceeded-the-maximum-web-content-size-allowed"/>
        <liferay-ui:error exception="<%= ArticleFriendlyURLException.class %>"
                          message="you-must-define-a-friendly-url-for-default-language"/>
        <liferay-ui:error exception="<%= DuplicateFileEntryException.class %>"
                          message="a-file-with-that-name-already-exists"/>

        <liferay-ui:error exception="<%= ExportImportContentValidationException.class %>">

            <%
                ExportImportContentValidationException eicve = (ExportImportContentValidationException) errorException;
            %>

            <c:choose>
                <c:when test="<%= eicve.getType() == ExportImportContentValidationException.ARTICLE_NOT_FOUND %>">
                    <liferay-ui:message key="unable-to-validate-referenced-journal-article"/>
                </c:when>
                <c:when test="<%= eicve.getType() == ExportImportContentValidationException.FILE_ENTRY_NOT_FOUND %>">
                    <liferay-ui:message
                            arguments="<%= new String[] {MapUtil.toString(eicve.getDlReferenceParameters())} %>"
                            key="unable-to-validate-referenced-file-entry-because-it-cannot-be-found-with-the-following-parameters-x"/>
                </c:when>
                <c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_GROUP_NOT_FOUND %>">
                    <liferay-ui:message
                            arguments="<%= new String[] {eicve.getLayoutURL(), eicve.getGroupFriendlyURL()} %>"
                            key="unable-to-validate-referenced-page-with-url-x-because-the-page-group-with-url-x-cannot-be-found"/>
                </c:when>
                <c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_NOT_FOUND %>">
                    <liferay-ui:message
                            arguments="<%= new String[] {MapUtil.toString(eicve.getLayoutReferenceParameters())} %>"
                            key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-the-following-parameters-x"/>
                </c:when>
                <c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_WITH_URL_NOT_FOUND %>">
                    <liferay-ui:message arguments="<%= new String[] {eicve.getLayoutURL()} %>"
                                        key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-url-x"/>
                </c:when>
                <c:otherwise>
                    <liferay-ui:message key="an-unexpected-error-occurred"/>
                </c:otherwise>
            </c:choose>
        </liferay-ui:error>

        <liferay-ui:error exception="<%= FileSizeException.class %>">

            <%
                long fileMaxSize = DLValidatorUtil.getMaxAllowableSize();
            %>

            <liferay-ui:message
                    arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>"
                    key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x"
                    translateArguments="<%= false %>"/>
        </liferay-ui:error>

        <liferay-ui:error exception="<%= LiferayFileItemException.class %>">
            <liferay-ui:message
                    arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>"
                    key="please-enter-valid-content-with-valid-content-size-no-larger-than-x"
                    translateArguments="<%= false %>"/>
        </liferay-ui:error>

        <c:if test="<%= (article != null) && !article.isNew() && (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
            <liferay-frontend:info-bar>
                <aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>"
                                     markupView="lexicon" showHelpMessage="<%= false %>"
                                     showIcon="<%= false %>" showLabel="<%= false %>"
                                     status="<%= article.getStatus() %>"
                                     version="<%= String.valueOf(article.getVersion()) %>"/>
            </liferay-frontend:info-bar>
        </c:if>

        <c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
            <c:if test="<%= approved %>">
                <div class="alert alert-info">
                    <liferay-ui:message
                            key="a-new-version-is-created-automatically-if-this-content-is-modified"/>
                </div>
            </c:if>

            <c:if test="<%= pending %>">
                <div class="alert alert-info">
                    <liferay-ui:message key="there-is-a-publication-workflow-in-process"/>
                </div>
            </c:if>
        </c:if>

        <liferay-frontend:form-navigator
                formModelBean="<%= article %>"
                id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL %>"
                showButtons="<%= false %>"
        />
    </liferay-frontend:edit-form-body>

    <liferay-frontend:edit-form-footer>
        <div class="journal-article-button-row">
            <c:if test="<%= hasSavePermission %>">
                <aui:button data-actionname="<%= Constants.PUBLISH %>" disabled="<%= pending %>"
                            name="publishButton" type="submit" value="<%= publishButtonLabel %>"/>

                <c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
                    <aui:button
                            data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "addArticle" : "updateArticle" %>'
                            name="saveButton" primary="<%= false %>" type="submit"
                            value="<%= saveButtonLabel %>"/>
                </c:if>
            </c:if>

            <aui:button href="<%= redirect %>" type="cancel"/>
        </div>
    </liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>"
                           var="previewArticleContentURL"
                           windowState="<%= LiferayWindowState.POP_UP.toString() %>">
    <portlet:param name="mvcPath" value="/preview_article_content.jsp"/>

    <c:if test="<%= article != null %>">
        <portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>"/>
        <portlet:param name="articleId" value="<%= article.getArticleId() %>"/>
        <portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>"/>
        <portlet:param name="ddmTemplateKey"
                       value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : article.getDDMTemplateKey() %>"/>
    </c:if>
</liferay-portlet:renderURL>

<portlet:renderURL var="editArticleURL">
    <portlet:param name="redirect" value="<%= redirect %>"/>
    <portlet:param name="mvcPath" value="/edit_article.jsp"/>
    <portlet:param name="groupId" value="<%= String.valueOf(groupId) %>"/>
    <portlet:param name="articleId" value="<%= articleId %>"/>
    <portlet:param name="version" value="<%= String.valueOf(version) %>"/>
</portlet:renderURL>

<aui:script use="liferay-portlet-journal">
    new Liferay.Portlet.Journal(
    {
    article: {
    editUrl: '<%= editArticleURL %>',
    id: '<%= (article != null) ? HtmlUtil.escape(articleId) : StringPool.BLANK %>',

    <c:if test="<%= (article != null) && !article.isNew() %>">
        previewUrl: '<%= HtmlUtil.escapeJS(previewArticleContentURL.toString()) %>',
    </c:if>

    title: '<%= (article != null) ? HtmlUtil.escapeJS(article.getTitle(locale))
        : StringPool.BLANK %>'
    },
    namespace: '<portlet:namespace/>',
    'strings.addTemplate': '<liferay-ui:message
        key="please-add-a-template-to-render-this-structure"/>',
    'strings.saveAsDraftBeforePreview': '<liferay-ui:message
        key="in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft"/>'
    }
    );
</aui:script>

<!-- WORTH changes -->
<%
    List<Role> roles = RoleLocalServiceUtil.getUserGroupRoles(
            themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
    boolean showOptions = permissionChecker.isOmniadmin() || roles.size() > 1;

    if (roles.size() == 1) {
        showOptions = showOptions || !roles.get(0).getName().equals(RoleConstants.SITE_MEMBER);
    }

    List<UserGroupGroupRole> userGroupGroupRoles = UserGroupGroupRoleLocalServiceUtil
            .getUserGroupGroupRolesByUser(themeDisplay.getUserId());

    for (UserGroupGroupRole sel : userGroupGroupRoles) {
        if (sel.getGroup().getGroupId() == themeDisplay.getScopeGroupId()) {
            showOptions = showOptions || !sel.getRole().getName().equals(RoleConstants.SITE_MEMBER);
        }
    }
%>

<c:if test="<%= !showOptions %>">
    <style>
        #p_p_id<portlet:namespace /> fieldset.panel:not(:first-child) {
            display: none;
        }

        [data-fieldname*="_hide"] {
            display: none;
        }
    </style>
</c:if>
<!-- END WORTH changes -->
