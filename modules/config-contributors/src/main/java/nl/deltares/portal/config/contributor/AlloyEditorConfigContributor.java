package nl.deltares.portal.config.contributor;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import org.osgi.service.component.annotations.Component;

import java.util.Map;
import java.util.Objects;

@Component(
        property = {
                "editor.name=alloyeditor",
                "javax.portlet.name=com_liferay_journal_web_portlet_JournalPortlet",
                "service.ranking:Integer=100",
        },

        service = EditorConfigContributor.class
)
public class AlloyEditorConfigContributor extends BaseEditorConfigContributor {


    @Override
    public void populateConfigJSONObject(JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes, ThemeDisplay themeDisplay, RequestBackedPortletURLFactory requestBackedPortletURLFactory) {
        JSONObject toolbars = jsonObject.getJSONObject("toolbars");

        if (toolbars != null) {
            JSONObject stylesToolbar = toolbars.getJSONObject("styles");
            if (stylesToolbar == null) {
                return;
            }
            JSONArray selectionsJSONArray = stylesToolbar.getJSONArray("selections");
            for (int i = 0; i < selectionsJSONArray.length(); i++) {
                JSONObject selection = selectionsJSONArray.getJSONObject(i);

                if (!Objects.equals(selection.get("name"), "text")) continue;
                JSONArray buttons = selection.getJSONArray("buttons");
                if (buttons == null) continue;
                for (int j = 0; j < buttons.length(); j++) {
                    JSONObject stylesButton = buttons.getJSONObject(j);
                    if (stylesButton == null) continue;
                    if (!Objects.equals(stylesButton.get("name"), "styles")) continue;
                    final JSONArray styles = stylesButton.getJSONObject("cfg").getJSONArray("styles");
                    //startAdding
                    addStyles(styles);
                }

            }

            stylesToolbar.put("selections", selectionsJSONArray);
            toolbars.put("styles", stylesToolbar);
            jsonObject.put("toolbars", toolbars);
        }


    }

    private void addStyles(JSONArray styles) {

        //Get list of custom styles from the css/main.css and add them to the array.
        String[] customStyles  = {"quote", "caption"};

        for (String customStyle : customStyles) {
            final JSONObject style = JSONFactoryUtil.createJSONObject();
            style.put("name", customStyle);
            final JSONObject erikStyle = JSONFactoryUtil.createJSONObject();
            erikStyle.put("type", 1);
            final JSONObject attributes = JSONFactoryUtil.createJSONObject();
            attributes.put("class", customStyle);
            erikStyle.put("attributes",attributes);
            erikStyle.put("element", "div");
            style.put("style", erikStyle);
            styles.put(style);
        }
    }
}
