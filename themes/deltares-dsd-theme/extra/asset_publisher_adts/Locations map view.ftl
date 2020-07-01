<#assign dsdUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdRegistrationUtils") />
<#assign propsUtils = staticUtil["com.liferay.portal.kernel.util.PropsUtil"] />
<#assign mapId = themeDisplay.getPortletDisplay().getId() />
<#assign googleMapsApiKey = propsUtils.get("google.maps.api.key") />
<#if entries?has_content>

    <style type="text/css">
        #${mapId}map {
            height: 500px;
            width: 100%;
        }
    </style>
    <script>
        let map;
        let markers = [];

        function initMap() {
            map = new google.maps.Map(document.getElementById('${mapId}map'), {
                zoom: 13,
                disableDefaultUI: true,
                zoomControl: true,
                fullscreenControl: true
            });

            var iconBase = "${themeDisplay.getPathThemeImages()}/dsd/";

            var icons = {
                hotel: {
                    icon: iconBase + 'marker-hotel.png'
                },
                restaurant: {
                    icon: iconBase + 'marker-restaurant.png'
                },
                event: {
                    icon: iconBase + 'marker-deltares.png',
                },
                building: {
                    icon: iconBase + 'marker-building.png'
                }
            };

            // Add controls to the map, allowing users to hide/show features.
            var styleControl = document.getElementById("style-selector-control");
            map.controls[google.maps.ControlPosition.TOP_LEFT].push(styleControl);

            // Apply new JSON when the user chooses to hide/show features.
            document.getElementById("hide-poi").addEventListener("click", function () {
                map.setOptions({styles: styles["hide"]});
            });
            document.getElementById("show-poi").addEventListener("click", function () {
                map.setOptions({styles: styles["default"]});
            });

            let marker;
            markers.forEach(function (jsonMarker) {
                let latLng = new google.maps.LatLng(jsonMarker.latitude, jsonMarker.longitude);
                marker = new google.maps.Marker({
                    position: latLng,
                    title: jsonMarker.title,
                    icon: icons[jsonMarker.type].icon,
                });

                marker.setMap(map);

            });
            if (marker) {
                map.setCenter(marker.position);
            }


        }

        function addMarker(lat, lon, title, type) {
            let jsonMarker = {"latitude": lat, "longitude": lon, "title": title, "type": type}
            markers.push(jsonMarker);
        }

        var styles = {
            default: null,
            hide: [
                {
                    featureType: "poi.business",
                    stylers: [{visibility: "off"}]
                },
                {
                    featureType: "transit",
                    elementType: "labels.icon",
                    stylers: [{visibility: "off"}]
                }
            ]
        };

    </script>
    <div id="style-selector-control" class="map-control">
        <input
                type="radio"
                name="show-hide"
                id="hide-poi"
                class="selector-control"
        />
        <label for="hide-poi">Hide</label>
        <input
                type="radio"
                name="show-hide"
                id="show-poi"
                class="selector-control"
                checked="checked"
        />
        <label for="show-poi">Show</label>
    </div>
    <div id="${mapId}map">
        <script>
            <#list entries as curentry>
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdUtils.getLocation(journalArticle) />
                <#if location.getLocationType() == "event" >
                    <#assign buildings = location.getBuildings() />
                    <#if buildings?? >
                        <#list buildings as building>
                            addMarker(${building.getLatitude()}, ${building.getLongitude()}, '${building.getTitle()}', 'building')
                        </#list>
                    </#if>
                </#if>
                <#if location.hasCoordinates() >
                    addMarker(${location.getLatitude()}, ${location.getLongitude()}, '${location.getTitle()}', '${location.getLocationType()}')
                </#if>
            </#list>

        </script>
    </div>

</#if>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}&callback=initMap"></script>