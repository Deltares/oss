/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.document.library.video.external.shortcut.provider;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.provider.DLVideoExternalShortcutProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import org.osgi.service.component.annotations.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erik de Rooij
 *
 * Add support for Slideshare external media files
 */
@Component(service = DLVideoExternalShortcutProvider.class)
public class SlideshareDLVideoExternalShortcutProvider
        implements DLVideoExternalShortcutProvider {

    @Override
    public DLVideoExternalShortcut getDLVideoExternalShortcut(String url) {
        String slidesVideoId = _getSlideshareVideoId(url);

        if (Validator.isNull(slidesVideoId)) {
            return null;
        }

        return new DLVideoExternalShortcut() {

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public String getThumbnailURL() {
                return url;
            }

            @Override
            public String getTitle() {
                return "title-" + slidesVideoId;
            }

            @Override
            public String getURL() {
                return url;
            }

            @Override
            public String renderHTML(HttpServletRequest httpServletRequest) {
                return StringBundler.concat(
                        "<iframe frameborder=\"0\" height=\"300\" \"width=\"100%\"" +
                        "marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" " +
                        "src=\"https://www.slideshare.net/slideshow/embed_code/key/" + slidesVideoId + "\"> </iframe>");
            }

        };
    }

    private String _getSlideshareVideoId(String url) {
        for (Pattern urlPattern : _urlPatterns) {
            Matcher matcher = urlPattern.matcher(url);

            if (matcher.matches()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    @SuppressWarnings("RegExpRedundantEscape")
    private static final List<Pattern> _urlPatterns = Arrays.asList(
            Pattern.compile("https?:\\/\\/(?:www\\.)?slideshare\\.com\\/slideshow\\/.*\\/(\\S*)$"),
            Pattern.compile("https?:\\/\\/(?:www\\.)?slideshare\\.net\\/slideshow\\/.*\\/(\\S*)$"));

}