package nl.deltares.portal.utils;

import com.liferay.dynamic.data.mapping.model.DDMStructure;

import java.util.Locale;
import java.util.Optional;

public interface DDMStructureUtil {
    Optional<DDMStructure> getDDMStructureByName(String name, Locale locale);
}
