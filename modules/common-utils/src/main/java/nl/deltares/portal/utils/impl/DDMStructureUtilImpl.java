package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import nl.deltares.portal.utils.DDMStructureUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;

@Component(
        immediate = true,
        service = DDMStructureUtil.class
)
public class DDMStructureUtilImpl implements DDMStructureUtil {

    private static final Log LOGGER = LogFactoryUtil.getLog(DDMStructureUtil.class);
    private static final int ALL = QueryUtil.ALL_POS;

    @Override
    public List<Optional<DDMStructure>> getDDMStructuresByName(long groupId, String[] names, Locale locale) {
        List<Optional<DDMStructure>>optionalList = new ArrayList<>();

        List<DDMStructure> groupStructures = _ddmStructureLocalService.getStructures(groupId);
        groupStructures.forEach(ddmStructure -> {
            if (Arrays.stream(names).anyMatch(searchName -> matchName(searchName, ddmStructure.getName(locale)))) {
                optionalList.add(Optional.of(ddmStructure));
            }
        });

        final long parentGroup = getParentGroup(groupId);
        if (parentGroup > 0){
            final List<DDMStructure> parentGroupStructures = _ddmStructureLocalService.getStructures(parentGroup);
            parentGroupStructures.forEach(ddmStructure -> {
                if (Arrays.stream(names).anyMatch(searchName -> matchName(searchName, ddmStructure.getName(locale)))) {
                    optionalList.add(Optional.of(ddmStructure));
                }
            });
        }
        return optionalList;
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
            group = GroupServiceUtil.getGroup(groupId);
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

}
