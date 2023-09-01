let titleControl;
let mapControl;
let markersLayer;
ActivityMapUtil = {

    initMap : function(namespace) {

        const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 20,
                attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap.</a> <br/ ><strong>Deltares software download locations</strong>'
            }),
            latlng = L.latLng(22.5, 20);

        mapControl = L.map(namespace + 'map', {center: latlng, zoom: 2, layers: [tiles]});
        markersLayer = L.markerClusterGroup({ chunkedLoading: true, maxClusterRadius: 20, singleMarkerMode: true , showCoverageOnHover: true });
        mapControl.addLayer(markersLayer);

        return mapControl;
    },

    initTitleControl : function(map){

        let TitleControl = L.Control.extend({
            options: {
                // Default control position
                position: 'topleft'
            },
            setPosition: function(position){
                this.options.position = position;
            },
            onAdd: function(map) {
                return L.DomUtil.create('div', 'map-title-control');
            },
            setTitle: function(title){
                this.getContainer().innerHTML = '<strong>' + title + '</strong>'
            }
        });
        titleControl = new TitleControl().addTo(map);
        return titleControl;
    },

    updateMarkers: function (map, jsonData){


        if (jsonData != null && jsonData !== "") {
            const locations = JSON.parse(jsonData);
            locations.map((location, i) => {
                const label = location.city;
                const position = location.position;
                let contentString = '<div>'
                    + '<strong>City: ' + label + '</strong>';

                let products = location.products;
                if (products.length > 0) {
                    contentString += '<table><tr><th>Download</th><th>Count</th></tr>';
                    products.forEach(function (product) {
                        contentString += '<tr><td>' + product.downloadName + '</td><td>' + product.downloadCount + '</td></tr>';
                    });
                    contentString += '</table>';
                } else {
                    contentString += '<p>User login</p>'
                }
                contentString += '</div>';

                const marker = L.marker(L.latLng(position.lat, position.lng, {title: label}));
                marker.bindPopup(contentString);
                markersLayer.addLayer(marker);

            });
        }
    },

    getRunningProcess: function (namespace){
        let runningProcess = document.getElementById(namespace + "runningProcess");
        return runningProcess.value;
    },

    setRunningProcess: function (namespace, processId){
        let runningProcess = document.getElementById(namespace + "runningProcess");
        runningProcess.value = processId;
    },

    stopRunningProcess : function (namespace){
        clearInterval(this.getRunningProcess(namespace));
        this.setRunningProcess(namespace, undefined);
    },

    startDownloadActivityMap :  function (resourceUrl, namespace){

        let map  = ActivityMapUtil.initMap(namespace);
        let titleControl = ActivityMapUtil.initTitleControl(map);
        titleControl.setTitle("Loading map data... Progress 0 %");

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=start', {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status === 200){
                        let responseData = this.get('responseData');
                        ActivityMapUtil.updateMarkers(namespace, responseData);
                        titleControl.setTitle("");
                    } else if (xhr.status === 204){
                        //Data not loaded yet so start download process
                        ActivityMapUtil.setRunningProcess(namespace, setInterval(function () {
                            ActivityMapUtil.statusDownloadActivityMap(resourceUrl, namespace);
                        }, 1000));

                    }
                }
            }
        });
    },

    downloadDownloadActivityMap :  function (resourceUrl, namespace){

        ActivityMapUtil.stopRunningProcess(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=download', {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status === 200){
                        let responseData = this.get('responseData');
                        ActivityMapUtil.updateMarkers(namespace, responseData);
                        titleControl.setTitle("");
                    }
                }
            }
        });
    },

    statusDownloadActivityMap : function (resourceUrl, namespace){

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=updateStatus', {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                     if (xhr.status === 200){
                        let responseData = this.get('responseData');

                        let statusMsg = JSON.parse(responseData);
                        if (statusMsg.status === 'available') {
                            ActivityMapUtil.downloadDownloadActivityMap(resourceUrl, namespace);
                        } else if (statusMsg.status === 'running') {
                            let percent = 100 * statusMsg.progress / statusMsg.total;
                            titleControl.setTitle('Loading map data... Progress ' + Math.round(percent) + ' %');
                        } else {
                            ActivityMapUtil.stopRunningProcess(namespace);
                        }

                    } else {
                         ActivityMapUtil.stopRunningProcess(namespace);
                    }
                },
                error : function (){
                    ActivityMapUtil.stopRunningProcess(namespace);
                },
                failure : function(response, status, xhr) {
                    ActivityMapUtil.stopRunningProcess(namespace);
                }
            }
        });

    }

}
