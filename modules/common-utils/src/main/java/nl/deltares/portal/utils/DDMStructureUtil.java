package nl.deltares.portal.utils;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface DDMStructureUtil {
    Optional<DDMStructure> getDDMStructureByName(long groupId, String name, Locale locale);

    List<Optional<DDMStructure>> getDDMStructuresByName(long groupId, String[] names, Locale locale);

    Optional<DDMTemplate> getDDMTemplateByName(long groupId, String name, Locale locale);

    List<Optional<DDMTemplate>> getDDMTemplatesByName(long groupId, String[] names, Locale locale);
}
