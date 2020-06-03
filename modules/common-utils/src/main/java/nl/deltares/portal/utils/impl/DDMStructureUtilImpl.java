package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
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

    public Optional<DDMStructure> getDDMStructureByName(String name, Locale locale) {
        Optional<DDMStructure> structureOptional = Optional.empty();

        List<DDMStructure> allDDMStructures = _ddmStructureLocalService.getDDMStructures(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

        for (DDMStructure currentDDMStructure : allDDMStructures) {
            String[] parts = currentDDMStructure.getName(locale).split("-");
            if (parts.length > 0) {
                String structureName = parts[0].trim();
                if (name.equalsIgnoreCase(structureName)) {
                    structureOptional = Optional.of(currentDDMStructure);
                    break;
                }
            }
        }

        return structureOptional;
    }

    @Reference
    private DDMStructureLocalService _ddmStructureLocalService;
}
