<%@ include file="/init-leaflet.jsp" %>

<div id="<portlet:namespace/>map"></div>

<style>
    #<portlet:namespace/>map {
        height: 400px;
        width: 100%;
    }
    th, td { padding: 10px; }
</style>

<aui:script>
  function initMap() {

      const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
              maxZoom: 20,
              attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap.</a> Markers updated by <a href="https://www.deltares.nl/en/">Deltares</a> <%=today%> .'
          }),
          latlng = L.latLng(22.5, 20);


      const map = L.map('<portlet:namespace/>map', {center: latlng, zoom: 2, layers: [tiles]});

      const markers = L.markerClusterGroup({ chunkedLoading: true, maxClusterRadius: 20, singleMarkerMode: true , showCoverageOnHover: true });

      const locations = JSON.parse('<%=downloadsJson %>');
      locations.map((location, i) => {
          const label = location.city;
          const position = location.position;
          let contentString = '<div>'
              + '<strong>City: ' + label + '</strong>';

          let products = location.products;
          if (products.length > 0){
              contentString += '<table><tr><th>Download</th><th>Count</th></tr>';
              products.forEach(function (product) {
                  contentString += '<tr><td>' + product.downloadName + '</td><td>' + product.downloadCount + '</td></tr>';
              });
              contentString += '</table>';
          } else {
              contentString += '<p>User login</p>'
          }
          contentString += '</div>';

          const marker = L.marker(L.latLng(position.lat, position.lng, { title: label }));
          marker.bindPopup(contentString);
          markers.addLayer(marker);

      });

      map.addLayer(markers);
  }

  initMap();

</aui:script>
