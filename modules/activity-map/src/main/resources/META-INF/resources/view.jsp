<%@ include file="/init.jsp" %>

<div id="<portlet:namespace/>map"></div>

<style type="text/css">
    #<portlet:namespace/>map {
        height: 300px;
        width: 100%;
    }
</style>

<script>
  var map;

  function initMap() {
    map = new google.maps.Map(document.getElementById('<portlet:namespace/>map'), {
      center: {lat: 52.370045, lng: 4.896097},
      zoom: 5
    });
  }
</script>

<script>
  $(function() {
    var setMarkers = function(locations) {
      locations.forEach(function(item) {
        var latLng = new google.maps.LatLng(item.latitude, item.longitude);

        var marker = new google.maps.Marker({
          position: latLng,
          title: item.repository
        });

        marker.setMap(map);
      });
    };

    var geoJSON = JSON.parse('<%=logsJson %>').objects;

    setMarkers(geoJSON);

    var intervalCounter = 0;
    var infoWindow;

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

<script async defer src="https://maps.googleapis.com/maps/api/js?key=<%=googleMapsApiKey %>&callback=initMap"></script>
