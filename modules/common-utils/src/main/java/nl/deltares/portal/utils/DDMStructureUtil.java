package nl.deltares.portal.utils;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;

import java.util.Locale;
import java.util.Optional;

public interface DDMStructureUtil {
    Optional<DDMStructure> getDDMStructureByName(String name, Locale locale);

    Optional<DDMTemplate> getDDMTemplateByName(String name, Locale locale);
}
