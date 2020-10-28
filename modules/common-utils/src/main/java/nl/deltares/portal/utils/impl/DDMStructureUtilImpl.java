package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.utils.DDMStructureUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DDMStructureUtil.class
)
public class DDMStructureUtilImpl implements DDMStructureUtil {

    private static final Log LOGGER = LogFactoryUtil.getLog(DDMStructureUtil.class);
    private static final int ALL = QueryUtil.ALL_POS;

    @Override
    public List<Optional<DDMStructure>> getDDMStructuresByName(long groupId, String[] names, Locale locale) {
        List<Optional<DDMStructure>> optionalList = new ArrayList<>();
        long structureOwnerGroup = getStructureOwnerGroup(groupId);

        if (structureOwnerGroup > 0) {
            List<DDMStructure> allDDMStructures = null;
            for (String name : names) {
                Optional<DDMStructure> cachedDDMStructure = getCachedDDMStructure(name);
                if (cachedDDMStructure.isPresent()){
                    optionalList.add(cachedDDMStructure);
                    continue;
                }
                if (allDDMStructures == null) {
                    allDDMStructures = _ddmStructureLocalService.getStructures(structureOwnerGroup);
                    if(allDDMStructures == null) return optionalList;
                }
                Optional<DDMStructure> matchingDDMStructure = findMatchingDDMStructure(name, allDDMStructures, locale);
                if (matchingDDMStructure.isPresent()){
                    cacheDDMStructure(name, matchingDDMStructure.get());
                    optionalList.add(matchingDDMStructure);
                }
            }
        }
        return optionalList;
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

    private long getStructureOwnerGroup(long groupId)  {
        Group group;
        try {
            group = GroupServiceUtil.getGroup(groupId);
        } catch (PortalException e) {
            LOGGER.warn(String.format("Cannot find group for %d : %s", groupId, e.getMessage()));
            return groupId;
        }
        Group parentGroup = group.getParentGroup();
        if (parentGroup != null) return parentGroup.getGroupId();
        return group.getGroupId();
    }

    private static void cacheDDMStructure(String name, DDMStructure structure) {
        if (Boolean.parseBoolean(PropsUtil.get("nocache"))) return;

        PortalCache<String, Serializable> cache = MultiVMPoolUtil.getPortalCache("deltares", true);
        cache.put("STRUCTURE_" + name, structure);
        cache.put("STRUCTURE_EXP_" + name, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));

    }

    private static Optional<DDMStructure> getCachedDDMStructure(String name) {
        if (name == null) return Optional.empty();
        if (Boolean.parseBoolean(PropsUtil.get("nocache"))) return Optional.empty();
        PortalCache<String, Serializable> cache = MultiVMPoolUtil.getPortalCache("deltares", true);
        DDMStructure structure = (DDMStructure) cache.get("STRUCTURE_" + name);
        if (structure != null) {
            Long expiryTime = (Long) cache.get("STRUCTURE_EXP_" + name);
            if (expiryTime != null && expiryTime > System.currentTimeMillis()){
                return Optional.of(structure);
            }
        }
        return Optional.empty();
    }

    @Reference
    private DDMStructureLocalService _ddmStructureLocalService;

    @Reference
    private DDMTemplateLocalService _ddmTemplateLocalService;
}
