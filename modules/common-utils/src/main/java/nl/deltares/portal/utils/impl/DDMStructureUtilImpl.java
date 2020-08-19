package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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

        List<DDMStructure> allDDMStructures = _ddmStructureLocalService.getDDMStructures(ALL, ALL);

        for (DDMStructure currentDDMStructure : allDDMStructures) {
            if (matchName(name, currentDDMStructure.getName(locale))) {
                structureOptional = Optional.of(currentDDMStructure);
                break;
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

    @Reference
    private DDMStructureLocalService _ddmStructureLocalService;

    @Reference
    private DDMTemplateLocalService _ddmTemplateLocalService;
}
