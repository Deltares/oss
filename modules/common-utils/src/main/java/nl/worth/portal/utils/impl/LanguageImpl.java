package nl.worth.portal.utils.impl;

import com.liferay.portal.kernel.theme.ThemeDisplay;

public class LanguageImpl {

    private final String id;
    private final String name;
    private final String url;
    private final ThemeDisplay display;

    public LanguageImpl(String id, String name, String url, ThemeDisplay display) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
