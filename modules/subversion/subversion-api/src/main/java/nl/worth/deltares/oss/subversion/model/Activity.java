package nl.worth.deltares.oss.subversion.model;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import nl.worth.deltares.oss.subversion.model.constants.PropConstants;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Activity {

  private static final Log LOG = LogFactoryUtil.getLog(Activity.class);

  private String latitude;
  private String longitude;
  private String location;
  private String message;
  private String type;

  public Activity(String type) { this.type = type; }

  public Activity(RepositoryLog repositoryLog) {

    this.type = repositoryLog.getAction();
    this.message = "Repository: " + repositoryLog.getRepository();

    buildObject(repositoryLog);
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private void buildObject(RepositoryLog repositoryLog) {

    try {
      InetAddress inetAddress = InetAddress.getByName(repositoryLog.getIpAddress());

      String dbDir = PropsUtil.get(PropConstants.PROP_GEOIP_DB_DIR);
      String dbName = PropsUtil.get(PropConstants.PROP_GEOIP_DB_NAME);

      File database = new File(dbDir + "/" + dbName);

      CityResponse cityResponse;

      DatabaseReader reader = new DatabaseReader.Builder(database).build();

      cityResponse = reader.city(inetAddress);

      this.location = cityResponse.getCity().getName();
      this.latitude = Double.toString(cityResponse.getLocation().getLatitude());
      this.longitude = Double.toString(cityResponse.getLocation().getLongitude());
    } catch (UnknownHostException e) {
      LOG.warn("Error resolving IP from RepositoryLog, blank values will be used");
    } catch (IOException e) {
      LOG.warn("Error retrieving database file, blank values will be used");
    } catch (GeoIp2Exception e) {
      LOG.warn("Error building GeoIp database, blank values will be used");
    }
  }
}
