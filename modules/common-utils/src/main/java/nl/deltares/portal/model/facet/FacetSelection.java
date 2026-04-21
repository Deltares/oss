package nl.deltares.portal.model.facet;

public class FacetSelection implements java.io.Serializable {

    private final long userId;

    private final long siteGroupId;

    private final long companyId;

    public FacetSelection(long companyId, long siteGroupId, long userId) {
        this.companyId = companyId;
        this.siteGroupId = siteGroupId;
        this.userId = userId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public long getSiteGroupId() {
        return siteGroupId;
    }

    public long getUserId() {
        return userId;
    }
}
