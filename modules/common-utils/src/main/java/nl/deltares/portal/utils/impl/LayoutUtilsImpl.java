package nl.deltares.portal.utils.impl;


import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.LayoutUtils;
import nl.deltares.portal.utils.PermissionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    service = LayoutUtils.class
)
public class LayoutUtilsImpl implements LayoutUtils {

  @Reference
  private PermissionUtils permissionUtils;

  @Reference
  private LayoutLocalService layoutLocalService;

  @Reference
  private AssetEntryLocalService assetEntryLocalService;

  @Override
  public boolean isLayoutPublic(Layout layout) throws PortalException {
    long companyId = layout.getCompanyId();
    return permissionUtils.isResourcePublic(companyId, Layout.class.getName(),
        String.valueOf(layout.getPrimaryKey()));
  }

  @Override
  public Layout getLinkToPageLayout(String linkToPage) {
    if(Validator.isNotNull(linkToPage)) {
      final Map<String, String> jsonToMap;
      try {
        jsonToMap = JsonContentUtils.parseJsonToMap(linkToPage);
      } catch (JSONException e) {
        return null;
      }
      long layoutId = Long.parseLong(jsonToMap.get("layoutId"));
      boolean isPrivate = Boolean.parseBoolean(jsonToMap.getOrDefault("privateLayout", "false"));
      long groupId = Long.parseLong(jsonToMap.get("groupId"));
      return layoutLocalService.fetchLayout(groupId, isPrivate, layoutId);
    } else {
      return null;
    }
  }

  @Override
  public Layout getLinkToPageLayout(long groupId, boolean privateLayout, long layoutId) {
    return layoutLocalService.fetchLayout(groupId, privateLayout, layoutId);
  }

  @Override
  public Layout getLayoutFromCategory(long categoryId) {
    AssetEntryQuery query = new AssetEntryQuery();
    query.setClassName(Layout.class.getName());
    query.setVisible(null);
    query.setAllCategoryIds(new long[]{categoryId});
    query.setStart(0);
    query.setEnd(1);

    return assetEntryLocalService.getEntries(query)
        .stream()
        .map(asset -> layoutLocalService.fetchLayout(asset.getClassPK()))
        .filter(Objects::nonNull)
        .findFirst().orElse(null);
  }
}
