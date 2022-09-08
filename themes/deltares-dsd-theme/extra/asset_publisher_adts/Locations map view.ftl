<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign propsUtils = staticUtil["com.liferay.portal.kernel.util.PropsUtil"] />
<#assign mapId = themeDisplay.getPortletDisplay().getId() />
<#assign googleMapsApiKey = propsUtils.get("google.maps.api.key") />
<#if entries?has_content>

    <style type="text/css">
        #${mapId}map {
            height: 400px;
            width: 100%;
        }
    </style>
    <script>
        let map;
        let icons;
        let markers = [];

        function initMap() {
            map = new google.maps.Map(document.getElementById('${mapId}map'), {
                zoom: 12,
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

            // Add controls to the map, allowing users to hide/show features.
            var styleControl = document.getElementById("style-selector-control");
            map.controls[google.maps.ControlPosition.TOP_LEFT].push(styleControl);

            // // Apply new JSON when the user chooses to hide/show features.
            // document.getElementById("hide-poi").addEventListener("click", function () {
            //     map.setOptions({styles: styles["hide"]});
            // });
            // document.getElementById("show-poi").addEventListener("click", function () {
            //     map.setOptions({styles: styles["default"]});
            // });

            markers.forEach(function (jsonMarker) {
                addMarker(jsonMarker);
            });

        }

        function addMarkerToArray(lat, lon, title, address, website, type, imgUrl){
            let jsonMarker = {"latitude": lat, "longitude": lon, "title": title, "address": address, "website": website, "imgUrl": imgUrl, "type": type}
            markers.push(jsonMarker);
        }

        function addMarker(jsonMarker) {

            var contentString = '<div class="blog-page"><div class="blog-page__item clearfix">' +
                '<div class="row" >   ' +
                '<div class="col-4">' +
                '    <img height="100%" width="100%" src="' + jsonMarker.imgUrl + '" />' +
                '</div>'+
                '<div class="col-8">' +
                '    <div class="expert-data__content">' +
                '        <h4 class="h1 clear-margin">' + jsonMarker.title + '</h4>'+
                '        <p>' + jsonMarker.address + '<br>'

            if (jsonMarker.website){
                contentString += '<a target="_blank" href="'+ jsonMarker.website + '" >' + jsonMarker.website + '</a><br>';
            }

            contentString   += ('<a target="_blank" href="https://www.google.com/maps/search/?api=1&query=' + jsonMarker.latitude + ',' + jsonMarker.longitude + '">'+
                '${languageUtil.get(locale, "dsd.theme.locations.direction")}</a> ' +
                '</p></div></div></div></div></div>')

            var infowindow = new google.maps.InfoWindow({
                content: contentString
            });

            let latLng = new google.maps.LatLng(jsonMarker.latitude, jsonMarker.longitude);
            let marker = new google.maps.Marker({
                position: latLng,
                title: jsonMarker.title,
                icon: icons[jsonMarker.type].icon,
            });
            marker.addListener('click', function() {
                infowindow.open(map, marker);
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

    <!--<div id="style-selector-control" class="map-control">-->
    <!--    <input-->
    <!--            type="radio"-->
    <!--            name="show-hide"-->
    <!--            id="hide-poi"-->
    <!--            class="selector-control"-->
    <!--            checked="checked"-->
    <!--    />-->
    <!--    <label for="hide-poi">Hide</label>-->
    <!--    <input-->
    <!--            type="radio"-->
    <!--            name="show-hide"-->
    <!--            id="show-poi"-->
    <!--            class="selector-control"-->
    <!--    />-->
    <!--    <label for="show-poi">Show - POI</label>-->
    <!--</div>-->
    <div id="${mapId}map" >
        <script>
            <#list entries as curentry>
            <#assign entry = curentry />
            <#assign assetRenderer = entry.getAssetRenderer()/>
            <#assign journalArticle = assetRenderer.getArticle()/>
            <#assign location = dsdParserUtils.getLocation(journalArticle) />
            <#if location.hasCoordinates() >
            addMarkerToArray(${location.getLatitude()},
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

            addMarkerToArray(${building.getLatitude()},
                ${building.getLongitude()},
                '${building.getTitle()}',
                '${location.getAddress()}, ${location.getCity()}',
                '${location.getWebsite()}',
                'building',
                '${building_img}')
            </#list>
            </#if>
            </#if>
            </#list>
        </script>
    </div>
</#if>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}&callback=initMap"></script>