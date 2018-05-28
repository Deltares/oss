<%-- 
    - Author(s): Pier-Angelo Gaetani @ Worth Systems
--%>

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace/>map"></div>

<style type="text/css">
  #<portlet:namespace/>map {
    height: 300px;
    width: 500px;
  }
</style>

<script type="text/javascript">
  $(function() {
    var mapOptions = { center: { lat: 52.370045, lng: 4.896097 }, zoom: 5 };

    var map = new google.maps.Map(document.getElementById('<portlet:namespace/>map'), mapOptions);

    var geoJSON;
    var infoWindow;

    $.ajax({
      url: '<%=lastLogsURL %>',
      method: 'GET',
      success: function(data) {
        geoJSON = data.objects;
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log(jqXHR.statusText);
        console.log(textStatus);
        console.log(errorThrown);

        geoJSON = [
          {"type":"CHECKOUT", "location": "Amsterdam", "latitude": 52.370045, "longitude": 4.896097, "message":"error getting the json object" + errorThrown, "repository":"openearthtools"},
          {"type":"COMMIT", "location": "Delft","latitude": 52.011144, "longitude": 4.357867, "message":"error getting the json object" + errorThrown, "repository":"Delft3D"},
          {"type":"CHECKOUT", "location": "Haarlem","latitude": 52.383144, "longitude": 4.637203 , "message":"error getting the json object" + errorThrown, "repository":"xBeach"},
          {"type":"CHECKOUT", "location": "Rotterdam", "latitude": 51.923943, "longitude": 4.480991, "message":"error getting the json object" + errorThrown, "repository":"openearthtools"},
          {"type":"COMMIT", "location": "Antwerpen","latitude": 51.219357, "longitude": 4.399223, "message":"error getting the json object" + errorThrown, "repository":"Delft3D"},
          {"type":"CHECKOUT", "location": "Hamburg","latitude": 53.551731, "longitude": 9.993324, "message":"error getting the json object" + errorThrown, "repository":"xBeach"},
          {"type":"CHECKOUT", "location": "Gothenburg", "latitude": 57.708183, "longitude": 11.970863, "message":"error getting the json object" + errorThrown, "repository":"openearthtools"},
          {"type":"COMMIT", "location": "Shanghai","latitude": 31.366536, "longitude": 121.750259, "message":"error getting the json object" + errorThrown, "repository":"Delft3D"},
          {"type":"CHECKOUT", "location": "Colombo","latitude": 6.942445, "longitude": 79.841566, "message":"error getting the json object" + errorThrown, "repository":"xBeach"},
          {"type":"CHECKOUT", "location": "New York", "latitude": 40.710313, "longitude": -74.010887, "message":"error getting the json object" + errorThrown, "repository":"openearthtools"}
        ];
      },
      complete: function() {
        setMarkers(geoJSON);
      }
    });

    function setMarkers(locations) {
      for (var i = 0; i < locations.length; i++) {
        var item = locations[i];
        var latLng = new google.maps.LatLng(item.latitude, item.longitude);

        var marker = new google.maps.Marker({
          position: latLng,
          title: item.repository
        });

        marker.setMap(map);
      }
    }

    var intervalCounter = 0;

    setInterval(function() {
      if (intervalCounter >= geoJSON.length) {
        intervalCounter = 0;
      }

      var panLoc = geoJSON[intervalCounter];
      var latLng = new google.maps.LatLng(panLoc.latitude, panLoc.longitude);

      map.panTo(latLng);

      if (infoWindow) {
        infoWindow.close();
      }

      var contentString = '<div class="<portlet:namespace/>map-info">'
        + '<p class="<portlet:namespace/>map-type">' + panLoc.type + '</p>'
        + '<p class="<portlet:namespace/>map-message">' + panLoc.message + '</p>'
        + '</div>';

      infoWindow = new google.maps.InfoWindow({
        content: contentString,
        position: latLng,
        pixelOffset: new google.maps.Size(15, -30)
      });

      infoWindow.open(map);

      intervalCounter++;
    }, 10000);
  });
</script>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=<%= apiKey %>"></script>
