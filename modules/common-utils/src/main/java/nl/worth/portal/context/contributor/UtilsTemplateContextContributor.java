package nl.worth.portal.context.contributor;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import nl.worth.portal.utils.LayoutUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
    service = TemplateContextContributor.class
)
public class UtilsTemplateContextContributor implements TemplateContextContributor {

  @Reference
  private JournalArticleLocalService journalArticleLocalService;

  @Reference
  private JournalContent journalContent;

  @Reference
  private LayoutUtils layoutUtils;

  @Override
  public void prepare(Map<String, Object> contextObjects, HttpServletRequest request) {
    contextObjects.put("journalArticleLocalService", journalArticleLocalService);
    contextObjects.put("journalContentUtil", journalContent);
    contextObjects.put("layoutUtils", layoutUtils);
  }
}