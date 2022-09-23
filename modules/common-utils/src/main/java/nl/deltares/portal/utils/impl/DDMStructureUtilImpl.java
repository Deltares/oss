package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import nl.deltares.portal.utils.DDMStructureUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        service = DDMStructureUtil.class
)
public class DDMStructureUtilImpl implements DDMStructureUtil {

    private static final Log LOGGER = LogFactoryUtil.getLog(DDMStructureUtil.class);
    private static final int ALL = QueryUtil.ALL_POS;

    @Override
    public List<String> getEncodedFieldNamesForStructures(long groupId, String fieldReference, String[] structureNames, Locale locale) {
        List<Optional<DDMStructure>> optionalList = getDDMStructuresByName(groupId, structureNames, locale);
        List<String> fieldNameValues = new ArrayList<>();
        for (Optional<DDMStructure> ddmStructureOptional : optionalList) {
            ddmStructureOptional.ifPresent(ddmStructure -> {
                        if (!ddmStructure.hasFieldByFieldReference(fieldReference)) return;
                        final long structureId = ddmStructure.getStructureId();
                        fieldNameValues.add( _ddmIndexer.encodeName(structureId, fieldReference, locale));
                    }
            );}
        return fieldNameValues;
    }

    @Override
    public List<Optional<DDMStructure>> getDDMStructuresByName(long groupId, String[] names, Locale locale) {
        List<Optional<DDMStructure>> optionalList = new ArrayList<>();

        if (groupId > 0) {
            List<DDMStructure> allDDMStructures = null;
            for (String name : names) {

                if (allDDMStructures == null) {
                    allDDMStructures = getDDStructuresForGroup(groupId);
                    if (allDDMStructures.isEmpty()) return optionalList;
                }
                Optional<DDMStructure> matchingDDMStructure = findMatchingDDMStructure(name, allDDMStructures, locale);
                if (matchingDDMStructure.isPresent()){
                    optionalList.add(matchingDDMStructure);
                }
            }
        }
        return optionalList;
    }

    private List<DDMStructure> getDDStructuresForGroup(long groupId) {
        List<DDMStructure> structures = new ArrayList<>();
        long parentGroupId = getParentGroup(groupId);
        if (parentGroupId > 0){
            structures.addAll(_ddmStructureLocalService.getStructures(parentGroupId));
        }
        structures.addAll(_ddmStructureLocalService.getStructures(groupId));
        return structures;
    }

    private Optional<DDMStructure> findMatchingDDMStructure(String name, List<DDMStructure> allDDMStructures, Locale locale) {
        for (DDMStructure ddmStructure : allDDMStructures) {
            if (matchName(name, ddmStructure.getName(locale))) {
                return Optional.of(ddmStructure);
            }
        }
        return Optional.empty();
    }

    private Optional<DDMTemplate> findMatchingDDMTemplate(String name, List<DDMTemplate> allDDMTemplates, Locale locale) {

        for (DDMTemplate ddmTemplate : allDDMTemplates) {
            if (matchName(name, ddmTemplate.getName(locale))) {
                return Optional.of(ddmTemplate);
            }
        }
        return Optional.empty();
    }

    public Optional<DDMStructure> getDDMStructureByName(long groupId, String name, Locale locale) {
        List<Optional<DDMStructure>> ddmStructuresByName = getDDMStructuresByName(groupId, new String[]{name}, locale);
        return ddmStructuresByName.size() > 0 ? ddmStructuresByName.get(0): Optional.empty() ;
    }

    @Override
    public List<Optional<DDMTemplate>> getDDMTemplatesByName(long groupId, String[] names, Locale locale) {
        List<Optional<DDMTemplate>> optionalList = new ArrayList<>();
        List<DDMTemplate> allDDMTemplates = _ddmTemplateLocalService.getDDMTemplates(ALL, ALL);

        for (String name : names) {
            Optional<DDMTemplate> matchingDDMTemplate = findMatchingDDMTemplate(name, allDDMTemplates, locale);
            if (matchingDDMTemplate.isPresent()){
                optionalList.add(matchingDDMTemplate);
            }
        }
        return optionalList;
    }

    @Override
    public Optional<DDMTemplate> getDDMTemplateByName(long groupId, String name, Locale locale) {
        List<Optional<DDMTemplate>> ddmTemplatesByName = getDDMTemplatesByName(groupId, new String[]{name}, locale);
        return ddmTemplatesByName.size() > 0? ddmTemplatesByName.get(0): Optional.empty();
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

    private long getParentGroup(long groupId)  {
        Group group;
        try {
            group = GroupLocalServiceUtil.getGroup(groupId);
        } catch (PortalException e) {
            LOGGER.warn(String.format("Cannot find group for %d : %s", groupId, e.getMessage()));
            return 0;
        }
        Group parentGroup = group.getParentGroup();
        if (parentGroup != null) return parentGroup.getGroupId();
        return 0;
    }

    @Reference
    private DDMStructureLocalService _ddmStructureLocalService;

    @Reference
    private DDMTemplateLocalService _ddmTemplateLocalService;

    @Reference
    private DDMIndexer _ddmIndexer;
}