package nl.deltares.include;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import org.osgi.service.component.annotations.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(
        immediate = true,
        service = DynamicInclude.class
)
public class TopHeadDynamicInclude extends BaseDynamicInclude {
    @Override
    public void include(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
        PrintWriter printWriter = response.getWriter();

        String content = "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/tiny-slider.css\">";
        printWriter.println(content);
    }

    @Override
    public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
        dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
    }
}
