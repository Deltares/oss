package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import nl.deltares.portal.utils.DDMStructureUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        service = DDMStructureUtil.class
)
public class DDMStructureUtilImpl implements DDMStructureUtil {

    private static final int ALL = QueryUtil.ALL_POS;

    public Optional<DDMStructure> getDDMStructureByName(String name, Locale locale) {
        Optional<DDMStructure> structureOptional = Optional.empty();
        Optional<Group> dsdSite = getDSDParentSite(locale);

        if (dsdSite.isPresent()) {
            List<DDMStructure> allDDMStructures = _ddmStructureLocalService.getStructures(dsdSite.get().getGroupId());

            for (DDMStructure currentDDMStructure : allDDMStructures) {
                if (matchName(name, currentDDMStructure.getName(locale))) {
                    structureOptional = Optional.of(currentDDMStructure);
                    break;
                }
            }
        }

        return structureOptional;
    }

    @Override
    public Optional<DDMTemplate> getDDMTemplateByName(String name, Locale locale) {
        Optional<DDMTemplate> templateOptional = Optional.empty();

        List<DDMTemplate> allDDMTemplates = _ddmTemplateLocalService.getDDMTemplates(ALL, ALL);

        for (DDMTemplate currentDDMTemplate : allDDMTemplates) {
            if (matchName(name, currentDDMTemplate.getName(locale))) {
                templateOptional = Optional.of(currentDDMTemplate);
                break;
            }
        }

        return templateOptional;
    }

    private boolean matchName(String source, String target) {
        boolean match = false;
        String[] parts = target.split("-");
        if (parts.length > 0) {
            String name = parts[0].trim();
            if (source.equalsIgnoreCase(name)) {
                match = true;
            }
        }
        return match;
    }

    private Optional<Group> getDSDParentSite(Locale locale) {
        // TODO rework this!
        return GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS).stream()
                .filter(GroupModel::isSite)
                .filter(site -> site.getName(locale).equals("DSD")).findFirst();
    }

    @Reference
    private DDMStructureLocalService _ddmStructureLocalService;

    @Reference
    private DDMTemplateLocalService _ddmTemplateLocalService;
}
