package nl.deltares.mock;

import nl.deltares.portal.configuration.WebinarSiteConfiguration;

public class MockWebinarSiteConfiguration implements WebinarSiteConfiguration {

    private String gotoTokenUrl;
    private String gotoUrl;
    private String clientId;
    private String clientSecret;
    private String userName;
    private String password;
    private String gotoOrganizerKey;

    @Override
    public String gotoURL() {
        return gotoUrl;
    }

    @Override
    public String gotoTokenURL() {
        return gotoTokenUrl;
    }

    @Override
    public String gotoOrganizerKey() {
        return gotoOrganizerKey;
    }

    @Override
    public String gotoClientId() {
        return clientId;
    }

    @Override
    public String gotoClientSecret() {
        return clientSecret;
    }

    @Override
    public String gotoUserName() {
        return userName;
    }

    @Override
    public String gotoUserPassword() {
        return password;
    }

    public void setGotoOrganizerKey(String gotoOrganizerKey) {
        this.gotoOrganizerKey = gotoOrganizerKey;
    }

    public void setGotoTokenUrl(String gotoTokenUrl) {
        this.gotoTokenUrl = gotoTokenUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean gotoCacheToken() {
        return false;
    }

    @Override
    public boolean gotoCacheResponse() {
        return false;
    }

    @Override
    public String aNewSpringURL() {
        return null;
    }

    @Override
    public String aNewSpringApiKey() {
        return null;
    }

    @Override
    public boolean aNewSpringCacheToken() {
        return false;
    }
}
