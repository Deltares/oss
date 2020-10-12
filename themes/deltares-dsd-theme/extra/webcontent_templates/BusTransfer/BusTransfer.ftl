<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign busTransfer = dsdParserUtils.getRegistration(groupId , articleId) />
<#assign busRoute = busTransfer.getBusRoute() />
<#assign busStops = busRoute.getStops() />
<#assign firstStop = true />
<strong>${busTransfer.getTitle()}</strong>
<p>
    <#list busStops as stop>
        <#if !firstStop >
            ->
        </#if>
        <#assign time = busRoute.getTime(stop) />
        ${time} ${stop.getTitle()}
        <#assign firstStop = false />
    </#list>
</p>

<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign propsUtils = staticUtil["com.liferay.portal.kernel.util.PropsUtil"] />
<#assign mapId = themeDisplay.getPortletDisplay().getId() />
<#assign googleMapsApiKey = propsUtils.get("google.maps.api.key") />
<#if busStops?has_content>

    <style type="text/css">
        #${mapId}map {
            height: 300px;
            width: 50%;
        }
    </style>
    <script>
        let map;
        let icons;
        let markers = [];

        function initMap() {
            map = new google.maps.Map(document.getElementById('${mapId}map'), {
                zoom: 13,
                disableDefaultUI: true,
                zoomControl: true,
                fullscreenControl: true,
                styles: styles["hide"]
            });

            var iconBase = "${themeDisplay.getPathThemeImages()}/dsd/";

            icons = {
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
            map.setOptions({styles: styles["hide"]});

            markers.forEach(function (jsonMarker) {
                addMarker(jsonMarker);
            });

        }

        function addMarkerToArray(lat, lon, title, type, labelTxt){
            let jsonMarker = {
                "latitude": lat,
                "longitude": lon,
                "title": title,
                "type": type,
                "label": {
                    "text": labelTxt,
                }
            }
            markers.push(jsonMarker);
        }

        function addMarker(jsonMarker) {


            let latLng = new google.maps.LatLng(jsonMarker.latitude, jsonMarker.longitude);
            let marker = new google.maps.Marker({
                position: latLng,
                title: jsonMarker.title,
                icon: {
                    labelOrigin: new google.maps.Point(11, 40),
                    url:  icons[jsonMarker.type].icon,
                },
                label : jsonMarker.label
            });

            marker.setMap(map);

            if (jsonMarker.type === 'event'){
                map.setCenter(marker.position);
            }
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

    <div id="${mapId}map" >
        <script>
            <#list busStops as curentry>
            <#assign location = curentry />
            <#assign time = busRoute.getTime(location) />

            <#if location.hasCoordinates() >
            addMarkerToArray(${location.getLatitude()},
                ${location.getLongitude()},
                '${location.getTitle()}',
                '${location.getLocationType()}',
                '${time}'
            )
            </#if>


            </#list>
        </script>
    </div>

</#if>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}&callback=initMap"></script>