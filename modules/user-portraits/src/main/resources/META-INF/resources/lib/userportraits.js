UserPortraitUtil = {

    updateTitle: function (namespace, title){
        let userPortraits = document.getElementById(namespace + "title");
        userPortraits.innerHTML = title;
    },

    updatePortraits: function (namespace, jsonData){

        if (jsonData != null && jsonData !== "") {
            const urls = JSON.parse(jsonData);
            let contents = [];
            urls.map((url, i) => {

                if (i === 0){
                    contents.push('<div className="carousel-item col-12 col-sm-6 col-md-4 col-lg-3 active">');
                } else {
                    contents.push('<div className="carousel-item col-12 col-sm-6 col-md-4 col-lg-3">');
                }
                contents.push('<img src="' + url + '" alt="img'+ i + '" className="img-fluid mx-auto d-block">')
                contents.push('</div>')
            });
            let userPortraits = document.getElementById(namespace + "portraits");
            userPortraits.innerHTML = contents.join("");
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

    startUserPortraits :  function (resourceUrl, namespace){

        UserPortraitUtil.updateTitle(namespace, "Loading user portraits... Progress 0 %");

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=start', {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status === 200){
                        let responseData = this.get('responseData');
                        UserPortraitUtil.updatePortraits(namespace, responseData);
                        UserPortraitUtil.updateTitle("");
                    } else if (xhr.status === 204){
                        //Data not loaded yet so start download process
                        UserPortraitUtil.setRunningProcess(namespace, setInterval(function () {
                            UserPortraitUtil.statusUserPortraits(resourceUrl, namespace);
                        }, 1000));

                    }
                }
            }
        });
    },

    downloadUserPortraits :  function (resourceUrl, namespace){

        UserPortraitUtil.stopRunningProcess(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=download', {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status === 200){
                        let responseData = this.get('responseData');
                        UserPortraitUtil.updatePortraits(namespace, responseData);
                        UserPortraitUtil.updateTitle(namespace, "");
                    }
                }
            }
        });
    },

    statusUserPortraits : function (resourceUrl, namespace){

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
                            UserPortraitUtil.downloadUserPortraits(resourceUrl, namespace);
                        } else if (statusMsg.status === 'running') {
                            let percent = 100 * statusMsg.progress / statusMsg.total;
                            UserPortraitUtil.updateTitle(namespace, 'Loading user portraits... Progress ' + Math.round(percent) + ' %');
                        } else {
                            UserPortraitUtil.stopRunningProcess(namespace);
                        }

                    } else {
                         UserPortraitUtil.stopRunningProcess(namespace);
                    }
                },
                error : function (){
                    UserPortraitUtil.stopRunningProcess(namespace);
                },
                failure : function(response, status, xhr) {
                    UserPortraitUtil.stopRunningProcess(namespace);
                }
            }
        });

    }

}
