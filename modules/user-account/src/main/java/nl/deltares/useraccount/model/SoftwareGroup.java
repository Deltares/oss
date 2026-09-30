package nl.deltares.useraccount.model;

import java.util.*;

public class SoftwareGroup {


    private final String groupName;
    private final List<String> packageNames = new ArrayList<>();

    public SoftwareGroup(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getPackageNames() {
        return packageNames;
    }

    public void addPackageName(String packageName) {
        if(packageNames.contains(packageName)) return;
        packageNames.add(packageName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SoftwareGroup that = (SoftwareGroup) o;
        return Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }
}
