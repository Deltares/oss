package nl.worth.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;

public interface LayoutUtils {

  boolean isLayoutPublic(Layout layout) throws PortalException;

  Layout getLinkToPageLayout(String linkToPage);

  Layout getLinkToPageLayout(long groupId, boolean privateLayout, String friendlyUrl);

  Layout getLayoutFromCategory(long categoryId);

}
