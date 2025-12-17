var DeltaresFacetUtil = {

    validateDateField: function(namespace) {
        let start = document.querySelector('input[name$="' + namespace + 'startDate"]');
        let startTime = 0;
        if (start.value){
            startTime = this.parseDate(start.value, "dd-MM-yyyy", "-").valueOf();
        }
        let end = document.querySelector('input[name$="' + namespace + 'endDate"]');
        let endTime = Number.MAX_SAFE_INTEGER
        if ( end.value ){
            endTime = this.parseDate(end.value,"dd-MM-yyyy", "-").valueOf()
        }
        return startTime < endTime;

    },
    parseDate: function (date, format, delimiter){
          var formatLower = format.toLowerCase();
          var formatItems = formatLower.split(delimiter);
          var dateItems = date.split(delimiter);
          var monthIndex = formatItems.indexOf("mm");
          var dayIndex = formatItems.indexOf("dd");
          var yearIndex = formatItems.indexOf("yyyy");
          var month = parseInt(dateItems[monthIndex]);
          month -= 1;
          return  new Date(dateItems[yearIndex], month, dateItems[dayIndex]);
    },
    clearError : function(namespace, name){
        let errorBlock = document.getElementById(namespace + name + '-message-block');
        errorBlock.innerHTML = '';
    },
    writeError: function(namespace, name, message){
        let errorBlock = document.getElementById(namespace + name + "-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-error");
        messageNode.innerHTML = message;
        errorBlock.appendChild(messageNode);
    },

};
