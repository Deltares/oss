package nl.deltares.search.include;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(
        immediate = true,
        service = DynamicInclude.class
)
public class TopJsDynamicInclude extends BaseDynamicInclude {

    @Override
    public void include(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
        PrintWriter printWriter = response.getWriter();

        String content = "<script charset=\"utf-8\" src=\"/o/deltares-search-web/js/bootstrap-datepicker.js\"></script>" +
                "<script charset=\"utf-8\" src=\"/o/deltares-search-web/js/bootstrap-datepicker.nl.min.js\"></script>";
        printWriter.println(content);
    }

    @Override
    public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
        dynamicIncludeRegistry.register("/html/common/themes/top_js.jspf#resources");
    }
}
