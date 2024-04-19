package nl.deltares.mock;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.RemotePreference;

import java.io.Serializable;
import java.util.*;

public class MockUser implements User {
    private String email;
    private String fistName;
    private String lastName;
    private String screenName;

    @Override
    public void addRemotePreference(RemotePreference remotePreference) {
    }

    @Override
    public Contact fetchContact() {
        return null;
    }

    @Override
    public String fetchPortraitURL(ThemeDisplay themeDisplay) {
        return null;
    }

    @Override
    public List<Address> getAddresses() {
        return null;
    }

    @Override
    public Date getBirthday() throws PortalException {
        return null;
    }

    @Override
    public String getCompanyMx() throws PortalException {
        return null;
    }

    @Override
    public Contact getContact() throws PortalException {
        return null;
    }

    @Override
    public String getDigest(String password) {
        return null;
    }

    @Override
    public String getDisplayEmailAddress() {
        return null;
    }

    @Override
    public String getDisplayURL(ThemeDisplay themeDisplay) throws PortalException {
        return null;
    }

    @Override
    public String getDisplayURL(ThemeDisplay themeDisplay, boolean privateLayout) throws PortalException {
        return null;
    }

    @Override
    public List<EmailAddress> getEmailAddresses() {
        return null;
    }

    @Override
    public boolean getFemale() throws PortalException {
        return false;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getFullName(boolean usePrefix, boolean useSuffix) {
        return null;
    }

    @Override
    public Group getGroup() {
        return null;
    }

    @Override
    public long getGroupId() {
        return 0;
    }

    @Override
    public long[] getGroupIds() {
        return new long[0];
    }

    @Override
    public List<Group> getGroups() {
        return null;
    }

    @Override
    public String getInitials() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public String getLogin() throws PortalException {
        return null;
    }

    @Override
    public boolean getMale() throws PortalException {
        return false;
    }

    @Override
    public List<Group> getMySiteGroups() throws PortalException {
        return null;
    }

    @Override
    public List<Group> getMySiteGroups(int max) throws PortalException {
        return null;
    }

    @Override
    public List<Group> getMySiteGroups(String[] classNames, int max) throws PortalException {
        return null;
    }

    @Override
    public long[] getOrganizationIds() throws PortalException {
        return new long[0];
    }

    @Override
    public long[] getOrganizationIds(boolean includeAdministrative) throws PortalException {
        return new long[0];
    }

    @Override
    public List<Organization> getOrganizations() throws PortalException {
        return null;
    }

    @Override
    public List<Organization> getOrganizations(boolean includeAdministrative) throws PortalException {
        return null;
    }

    @Override
    public String getOriginalEmailAddress() {
        return null;
    }

    @Override
    public boolean getPasswordModified() {
        return false;
    }

    @Override
    public PasswordPolicy getPasswordPolicy() throws PortalException {
        return null;
    }

    @Override
    public String getPasswordUnencrypted() {
        return null;
    }

    @Override
    public List<Phone> getPhones() {
        return null;
    }

    @Override
    public String getPortraitURL(ThemeDisplay themeDisplay) throws PortalException {
        return null;
    }

    @Override
    public int getPrivateLayoutsPageCount() throws PortalException {
        return 0;
    }

    @Override
    public int getPublicLayoutsPageCount() throws PortalException {
        return 0;
    }

    @Override
    public Set<String> getReminderQueryQuestions() throws PortalException {
        return null;
    }

    @Override
    public RemotePreference getRemotePreference(String name) {
        return null;
    }

    @Override
    public Iterable<RemotePreference> getRemotePreferences() {
        return null;
    }

    @Override
    public long[] getRoleIds() {
        return new long[0];
    }

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public List<Group> getSiteGroups() throws PortalException {
        return null;
    }

    @Override
    public List<Group> getSiteGroups(boolean includeAdministrative) throws PortalException {
        return null;
    }

    @Override
    public long[] getTeamIds() {
        return new long[0];
    }

    @Override
    public List<Team> getTeams() {
        return null;
    }

    @Override
    public TimeZone getTimeZone() {
        return null;
    }

    @Override
    public Date getUnlockDate() throws PortalException {
        return null;
    }

    @Override
    public Date getUnlockDate(PasswordPolicy passwordPolicy) {
        return null;
    }

    @Override
    public long[] getUserGroupIds() {
        return new long[0];
    }

    @Override
    public List<UserGroup> getUserGroups() {
        return null;
    }

    @Override
    public List<Website> getWebsites() {
        return null;
    }

    @Override
    public boolean hasCompanyMx() throws PortalException {
        return false;
    }

    @Override
    public boolean hasCompanyMx(String emailAddress) throws PortalException {
        return false;
    }

    @Override
    public boolean hasMySites() throws PortalException {
        return false;
    }

    @Override
    public boolean hasOrganization() {
        return false;
    }

    @Override
    public boolean hasPrivateLayouts() throws PortalException {
        return false;
    }

    @Override
    public boolean hasPublicLayouts() throws PortalException {
        return false;
    }

    @Override
    public boolean hasReminderQuery() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isEmailAddressComplete() {
        return false;
    }

    @Override
    public boolean isEmailAddressVerificationComplete() {
        return false;
    }

    @Override
    public boolean isFemale() throws PortalException {
        return false;
    }

    @Override
    public boolean isGuestUser() {
        return false;
    }

    @Override
    public boolean isMale() throws PortalException {
        return false;
    }

    @Override
    public boolean isOnDemandUser() {
        return false;
    }

    @Override
    public boolean isPasswordModified() {
        return false;
    }

    @Override
    public boolean isReminderQueryComplete() {
        return false;
    }

    @Override
    public boolean isServiceAccountUser() {
        return false;
    }

    @Override
    public boolean isSetupComplete() {
        return false;
    }

    @Override
    public boolean isTermsOfUseComplete() {
        return false;
    }

    @Override
    public void setContact(Contact contact) {

    }

    @Override
    public void setGroupIds(long[] longs) {

    }

    @Override
    public void setOrganizationIds(long[] longs) {

    }

    @Override
    public void setPasswordModified(boolean passwordModified) {

    }

    @Override
    public void setPasswordUnencrypted(String passwordUnencrypted) {

    }

    @Override
    public void setRoleIds(long[] longs) {

    }

    @Override
    public void setTeamIds(long[] longs) {

    }

    @Override
    public void setUserGroupIds(long[] longs) {

    }

    @Override
    public void persist() {

    }

    @Override
    public long getPrimaryKey() {
        return 0;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {

    }

    @Override
    public long getMvccVersion() {
        return 0;
    }

    @Override
    public void setMvccVersion(long mvccVersion) {

    }

    @Override
    public long getCtCollectionId() {
        return 0;
    }

    @Override
    public void setCtCollectionId(long ctCollectionId) {

    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public void setUuid(String uuid) {

    }

    @Override
    public String getExternalReferenceCode() {
        return null;
    }

    @Override
    public void setExternalReferenceCode(String externalReferenceCode) {

    }

    @Override
    public long getUserId() {
        return 0;
    }

    @Override
    public void setUserId(long userId) {

    }

    @Override
    public String getUserUuid() {
        return null;
    }

    @Override
    public void setUserUuid(String userUuid) {

    }

    @Override
    public long getCompanyId() {
        return 0;
    }

    @Override
    public void setCompanyId(long companyId) {

    }

    @Override
    public Date getCreateDate() {
        return null;
    }

    @Override
    public void setCreateDate(Date createDate) {

    }

    @Override
    public Date getModifiedDate() {
        return null;
    }

    @Override
    public StagedModelType getStagedModelType() {
        return null;
    }

    @Override
    public void setModifiedDate(Date modifiedDate) {

    }

    @Override
    public boolean isDefaultUser() {
        return false;
    }

    @Override
    public long getContactId() {
        return 0;
    }

    @Override
    public void setContactId(long contactId) {

    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public boolean getPasswordEncrypted() {
        return false;
    }

    @Override
    public boolean isPasswordEncrypted() {
        return false;
    }

    @Override
    public void setPasswordEncrypted(boolean passwordEncrypted) {

    }

    @Override
    public boolean getPasswordReset() {
        return false;
    }

    @Override
    public boolean isPasswordReset() {
        return false;
    }

    @Override
    public void setPasswordReset(boolean passwordReset) {

    }

    @Override
    public Date getPasswordModifiedDate() {
        return null;
    }

    @Override
    public void setPasswordModifiedDate(Date passwordModifiedDate) {

    }

    @Override
    public String getDigest() {
        return null;
    }

    @Override
    public void setDigest(String digest) {

    }

    @Override
    public String getReminderQueryQuestion() {
        return null;
    }

    @Override
    public void setReminderQueryQuestion(String reminderQueryQuestion) {

    }

    @Override
    public String getReminderQueryAnswer() {
        return null;
    }

    @Override
    public void setReminderQueryAnswer(String reminderQueryAnswer) {

    }

    @Override
    public int getGraceLoginCount() {
        return 0;
    }

    @Override
    public void setGraceLoginCount(int graceLoginCount) {

    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String getEmailAddress() {
        return email;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        email = emailAddress;
    }

    @Override
    public long getFacebookId() {
        return 0;
    }

    @Override
    public void setFacebookId(long facebookId) {

    }

    @Override
    public String getGoogleUserId() {
        return null;
    }

    @Override
    public void setGoogleUserId(String googleUserId) {

    }

    @Override
    public long getLdapServerId() {
        return 0;
    }

    @Override
    public void setLdapServerId(long ldapServerId) {

    }

    @Override
    public String getOpenId() {
        return null;
    }

    @Override
    public void setOpenId(String openId) {

    }

    @Override
    public long getPortraitId() {
        return 0;
    }

    @Override
    public void setPortraitId(long portraitId) {

    }

    @Override
    public String getLanguageId() {
        return null;
    }

    @Override
    public void setLanguageId(String languageId) {

    }

    @Override
    public String getTimeZoneId() {
        return null;
    }

    @Override
    public void setTimeZoneId(String timeZoneId) {

    }

    @Override
    public String getGreeting() {
        return null;
    }

    @Override
    public void setGreeting(String greeting) {

    }

    @Override
    public String getComments() {
        return null;
    }

    @Override
    public void setComments(String comments) {

    }

    @Override
    public String getFirstName() {
        return fistName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.fistName = firstName;
    }

    @Override
    public String getMiddleName() {
        return null;
    }

    @Override
    public void setMiddleName(String middleName) {

    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getJobTitle() {
        return null;
    }

    @Override
    public void setJobTitle(String jobTitle) {

    }

    @Override
    public Date getLoginDate() {
        return null;
    }

    @Override
    public void setLoginDate(Date loginDate) {

    }

    @Override
    public String getLoginIP() {
        return null;
    }

    @Override
    public void setLoginIP(String loginIP) {

    }

    @Override
    public Date getLastLoginDate() {
        return null;
    }

    @Override
    public void setLastLoginDate(Date lastLoginDate) {

    }

    @Override
    public String getLastLoginIP() {
        return null;
    }

    @Override
    public void setLastLoginIP(String lastLoginIP) {

    }

    @Override
    public Date getLastFailedLoginDate() {
        return null;
    }

    @Override
    public void setLastFailedLoginDate(Date lastFailedLoginDate) {

    }

    @Override
    public int getFailedLoginAttempts() {
        return 0;
    }

    @Override
    public void setFailedLoginAttempts(int failedLoginAttempts) {

    }

    @Override
    public boolean getLockout() {
        return false;
    }

    @Override
    public boolean isLockout() {
        return false;
    }

    @Override
    public void setLockout(boolean lockout) {

    }

    @Override
    public Date getLockoutDate() {
        return null;
    }

    @Override
    public void setLockoutDate(Date lockoutDate) {

    }

    @Override
    public boolean getAgreedToTermsOfUse() {
        return false;
    }

    @Override
    public boolean isAgreedToTermsOfUse() {
        return false;
    }

    @Override
    public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {

    }

    @Override
    public boolean getEmailAddressVerified() {
        return false;
    }

    @Override
    public boolean isEmailAddressVerified() {
        return false;
    }

    @Override
    public void setEmailAddressVerified(boolean emailAddressVerified) {

    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public void setStatus(int status) {

    }

    @Override
    public User cloneWithOriginalValues() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public void resetOriginalValues() {

    }

    @Override
    public void setNew(boolean n) {

    }

    @Override
    public boolean isCachedModel() {
        return false;
    }

    @Override
    public boolean isEntityCacheEnabled() {
        return false;
    }

    @Override
    public void setCachedModel(boolean cachedModel) {

    }

    @Override
    public boolean isEscapedModel() {
        return false;
    }

    @Override
    public boolean isFinderCacheEnabled() {
        return false;
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        return null;
    }

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj) {

    }

    @Override
    public ExpandoBridge getExpandoBridge() {
        return null;
    }

    @Override
    public Class<?> getModelClass() {
        return null;
    }

    @Override
    public String getModelClassName() {
        return null;
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        return null;
    }

    @Override
    public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {

    }

    @Override
    public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {

    }

    @Override
    public void setExpandoBridgeAttributes(ServiceContext serviceContext) {

    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {

    }

    @Override
    public int compareTo(User user) {
        return 0;
    }

    @Override
    public CacheModel<User> toCacheModel() {
        return null;
    }

    @Override
    public User toEscapedModel() {
        return null;
    }

    @Override
    public User toUnescapedModel() {
        return null;
    }

    @Override
    public String toXmlString() {
        return null;
    }
}
