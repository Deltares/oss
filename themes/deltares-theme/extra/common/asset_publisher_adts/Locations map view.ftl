<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign mapId = themeDisplay.getPortletDisplay().getId() />


<#if entries?has_content>

    <script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet-src.js" integrity="sha512-WXoSHqw/t26DszhdMhOXOkI7qCiv5QWXhH9R7CgvgZMHz1ImlkVQ3uNsiQKu5wwbbxtPzFXd1hK4tzno2VqhpA==" crossorigin=""></script>
    <script src="https://leaflet.github.io/Leaflet.markercluster/dist/leaflet.markercluster-src.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" integrity="sha512-07I2e+7D8p6he1SIM+1twR5TIrhUQn9+I6yjqD53JQjFiMf8EtC93ty0/5vJTZGF8aAocvHYNEDJajGdNx1IsQ==" crossorigin="">
    <link rel="stylesheet" href="https://leaflet.github.io/Leaflet.markercluster/dist/MarkerCluster.Default.css">
    <link rel="stylesheet" href="https://leaflet.github.io/Leaflet.markercluster/dist/MarkerCluster.css">

    <div id="${mapId}map" >

        <style>
            #${mapId}map {
                height: 400px;
                width: 100%;
            }
        </style>


        <script>
            let icons;
            let markers;
            let map;
            let today = new Date();
            let todayString = today.getDate() + '-' + today.getMonth() + '-' + today.getFullYear()
            function addMarker(lat, lon, title, address, website, type, imgUrl) {

                let contentString = '<div class="blog-page"><div class="blog-page__item clearfix">' +
                    '<div class="row" >   ' +
                    '<div class="col-4">' +
                    '    <img height="100%" width="100%" src="' + imgUrl + '" />' +
                    '</div>'+
                    '<div class="col-8">' +
                    '    <div class="expert-data__content">' +
                    '        <h4 class="h1 clear-margin">' + title + '</h4>'+
                    '        <p>' + address + '<br>'

                if (website){
                    contentString += '<a target="_blank" href="'+ website + '" >' + website + '</a><br>';
                }

                contentString   += ('<a target="_blank" href="https://www.google.com/maps/search/?api=1&query=' + lat + ',' + lon + '">'+
                    '${languageUtil.get(locale, "dsd.theme.locations.direction")}</a> ' +
                    '</p></div></div></div></div></div>')

                let marker = L.marker(L.latLng(lat, lon), {
                    title: title,
                    icon: L.icon({iconUrl: icons[type].icon, iconSize: [25,25]})});

                marker.bindPopup(contentString);
                markers.addLayer(marker);

            }
            function initMap() {

                const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    maxZoom: 20,
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap.</a> Markers updated by <a href="https://www.deltares.nl/en/">Deltares</a> ' + todayString
                });
                let latlng = L.latLng(22.5, 20);
                const map = L.map('${mapId}map', {center: latlng, zoom: 12, layers: [tiles]});
                markers = L.markerClusterGroup({
                    chunkedLoading: true, maxClusterRadius: 20, singleMarkerMode: true , showCoverageOnHover: true,
                    iconCreateFunction: function (cluster) {
                        let m = cluster.getAllChildMarkers()
                        if ( m.length === 1) {
                            return m[0].options.icon;
                        } else {
                            var c = ' marker-cluster-small';
                            return new L.DivIcon({ html: '<div><span>' + m.length + '</span></div>', className: 'marker-cluster' + c, iconSize: new L.Point(40, 40) });
                        }
                    }
                });

                let iconBase = "${themeDisplay.getPathThemeImages()}/dsd/";

                icons = {
                    hotel: {
                        icon: iconBase + 'marker-hotel.png'
                    },
                    restaurant: {
                        icon: iconBase + 'marker-restaurant.png'
                    },
                    online: {
                        icon: iconBase + 'marker-deltares.png',
                    },
                    event: {
                        icon: iconBase + 'marker-deltares.png',
                    },
                    building: {
                        icon: iconBase + 'marker-building.png'
                    }
                };

                <#list entries as curentry>
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdParserUtils.getLocation(journalArticle) />
                <#if location.hasCoordinates() >
                addMarker(${location.getLatitude()},
                    ${location.getLongitude()},
                    '${location.getTitle()}',
                    '${location.getAddress()}, ${location.getCity()}',
                    '${location.getWebsite()}',
                    '${location.getLocationType()}',
                    '${location.getSmallImageURL(themeDisplay)}')
                </#if>

                <#if location.getLocationType() == "event" >
                <#assign buildings = location.getBuildings() />
                <#if buildings?? >
                <#list buildings as building>
                <#assign building_img = building.getSmallImageURL(themeDisplay) />
                <#if building_img == "" >
                <#assign building_img = themeDisplay.getPathThemeImages() + "/dsd/building.png" />
                </#if>

                addMarker(${building.getLatitude()},
                    ${building.getLongitude()},
                    '${building.getTitle()}',
                    '${location.getAddress()}, ${location.getCity()}',
                    '${location.getWebsite()}',
                    'building',
                    '${building_img}')
                </#list>
                </#if>

                map.panTo(L.latLng(${location.getLatitude()}, ${location.getLongitude()}));

                </#if>
                </#list>

                map.addLayer(markers);
            }

            initMap();
        </script>
</#if>