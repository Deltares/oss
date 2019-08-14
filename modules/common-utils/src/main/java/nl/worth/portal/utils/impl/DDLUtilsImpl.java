package nl.worth.portal.utils.impl;


import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import lombok.extern.slf4j.Slf4j;
import nl.worth.portal.utils.DDLUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Slf4j
@Component(
        immediate = true,
        service = DDLUtils.class
)
public class DDLUtilsImpl implements DDLUtils {

    @Reference
    private DLAppLocalService dlAppLocalService;

    public String getFileEntryImage(String fileEntryJson){

        if (Validator.isNull(fileEntryJson)) {
            return StringPool.BLANK;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = JSONFactoryUtil.createJSONObject(fileEntryJson);

            String uuid = jsonObject.getString("uuid");
            long groupId = jsonObject.getLong("groupId");

            FileEntry fileEntry =
                    dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);

            return DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK, false, true);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
