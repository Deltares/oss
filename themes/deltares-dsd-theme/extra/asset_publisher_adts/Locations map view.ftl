<#assign dsdUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdRegistrationUtils") />
<#assign propsUtils = staticUtil["com.liferay.portal.kernel.util.PropsUtil"] />
<#assign mapId = themeDisplay.getPortletDisplay().getId() />
<#assign googleMapsApiKey = propsUtils.get("google.maps.api.key") />
<#if entries?has_content>

    <style type="text/css">
        #${mapId}map {
            height: 250px;
            width: 500px;
            float: right;
        }
    </style>
    <script>
        let map;
        let markers = [];
        function initMap() {
            map = new google.maps.Map(document.getElementById('${mapId}map'), {
                zoom: 15,
                disableDefaultUI: true,
                zoomControl: true,
                fullscreenControl: true

            });
            let marker;
            markers.forEach(function (jsonMarker) {

                let latLng = new google.maps.LatLng(jsonMarker.latitude, jsonMarker.longitude);
                marker = new google.maps.Marker({
                    position: latLng,
                    label: jsonMarker.title
                });

                marker.setMap(map);

            });
            if (marker){
                map.setCenter(marker.position);
            }


        }

        function addMarker(lat, lon, title){
            let jsonMarker = {"latitude": lat, "longitude": lon, "title": title}
            markers.push(jsonMarker);
        }

    </script>

    <div class="blog-page">


        <div id="${mapId}map">
            <script>
                initMap();
            </script>
        </div>
        <#list entries as curentry>
            <div class="blog-page__item clearfix">
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdUtils.getLocation(journalArticle) />
                <#assign website = location.getWebsite() />
                <#if location.hasCoordinates() >
                    <script>
                        addMarker(${location.getLatitude()}, ${location.getLongitude()}, '${journalArticle.getTitle()}')
                    </script>
                </#if>
                <div class="left-column">
                    <img src="${location.getSmallImageURL(themeDisplay)}" />
                </div>
                <div class="right-column">
                    <div class="expert-data__content">
                        <h4 class="h1 clear-margin">${journalArticle.getTitle()}</h4>
                        <p><strong>${location.getAddress()}, ${location.getCity()}</strong></p>
                        <#if website?? >
                            <p><a href="https://${website}" >${website}</a></p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>

    </div>

</#if>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}&callback=initMap"></script>