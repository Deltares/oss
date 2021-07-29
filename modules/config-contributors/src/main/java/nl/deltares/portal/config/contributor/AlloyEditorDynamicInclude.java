package nl.deltares.portal.config.contributor;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(immediate = true, service = DynamicInclude.class)
public class AlloyEditorDynamicInclude extends BaseDynamicInclude {
    @Override
    public void include(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
        PrintWriter printWriter = response.getWriter();

        printWriter.println("<link  rel=\"stylesheet\" href=\"/o/config-contributors/css/main.css\">");
    }

    @Override
    public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
        //Below section only adds to editor. We also want to add to all pages where style is being used.
//        dynamicIncludeRegistry.register(
//                "com.liferay.frontend.editor.alloyeditor.web#alloyeditor#" +
//                        "additionalResources");

        dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
    }

}
