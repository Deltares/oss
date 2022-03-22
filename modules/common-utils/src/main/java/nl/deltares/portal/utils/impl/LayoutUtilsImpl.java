package nl.deltares.portal.utils.impl;


import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Validator;
import java.util.Objects;
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
      String[] strings = linkToPage.split("@");
      long layoutId = Long.parseLong(strings[0]);
      boolean isPrivate = "private".equals(strings[1]);
      long groupId = Long.parseLong(strings[2]);
      return layoutLocalService.fetchLayout(groupId, isPrivate, layoutId);
    } else {
      return null;
    }
  }

  @Override
  public Layout getLinkToPageLayout(long groupId, boolean privateLayout, String layoutId) {
    return layoutLocalService.fetchLayout(groupId, privateLayout, Long.parseLong(layoutId));
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
