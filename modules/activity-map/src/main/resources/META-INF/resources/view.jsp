<%@ include file="/init.jsp" %>

<div id="<portlet:namespace/>map"></div>

<style>
    #<portlet:namespace/>map {
        height: 300px;
        width: 100%;
    }
</style>

<script>
  function initMap() {
      const map = new google.maps.Map(document.getElementById('<portlet:namespace/>map'), {
          center: {lat: 52.370045, lng: 4.896097},
          zoom: 2
      });

      const infoWindow = new google.maps.InfoWindow({
          content: "",
          disableAutoPan: true,
      });

      const locations = JSON.parse('<%=downloadsJson %>');
      const markers = locations.map((location, i) => {
          const label = location.city;
          const position = location.position;
          let contentString = '<div>'
              + '<strong>City: ' + label + '</strong>'
          + '<table>'
          +     '<tr><th>Download</th><th>Count</th></tr>';

          let products = location.products;
          products.forEach(function (product) {
              contentString += '<tr><td>' + product.downloadName + '</td><td>' + product.downloadCount + '</td></tr>';
          });

          contentString += '</table></div>';

          const marker = new google.maps.Marker({
              position
          });

          // markers can only be keyboard focusable when they have click listeners
          // open info window when marker is clicked
          marker.addListener("click", () => {
              infoWindow.setContent(contentString);
              infoWindow.open(map, marker);
          });
          return marker;

      });

      let options = {
          imagePath: '<%=request.getContextPath()%>/images/m'
      };
      // Add a marker clusterer to manage the markers.
      let markerClusterer = new MarkerClusterer(map, markers, options);
  }

  window.initMap = initMap;
</script>

<script async defer src="https://maps.googleapis.com/maps/api/js?key=<%=googleMapsApiKey %>&callback=initMap"></script>
