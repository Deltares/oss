package nl.deltares.useraccount.model;

public class Asset {

    private String hardwareId;
    private String serverName;
    private String type;
    private int userCount;

    public String getHardwareId() {
        return hardwareId;
    }

    public String getFormattedHardwareId(){

        if (hardwareId == null || hardwareId.length() != 12) return hardwareId;
        String upperCase = hardwareId.replaceAll("(.{2})", "$1:").toUpperCase();
        return  upperCase.substring(0, upperCase.length() -1);
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
