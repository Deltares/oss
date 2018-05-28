<%-- 
    - Author(s): Pier-Angelo Gaetani @ Worth Systems
--%>

<%@ include file="/init.jsp" %>

<div id="userPortraits"></div>

<script>
    $(function() {
        function buildPortraitHTML(urls) {
            var resultHtml = '<div class="container-fluid">';
            resultHtml += '    <div class="row">';
            var j = 0

            for (var i = 0; i < urls.length / 2; i++) {
                resultHtml += '        <div class="col-md-2">';
                resultHtml += '            <img class="portrait" src="' + urls[i] + '">';
                resultHtml += '        </div>';

                j = i + 1;
            }

            resultHtml += '    </div>';
            resultHtml += '    <hr>';
            resultHtml += '    <div class="row">';

            for (j; j < urls.length; j++) {
                resultHtml += '        <div class="col-md-2">';
                resultHtml += '            <img class="portrait" src="' + urls[j] + '">';
                resultHtml += '        </div>';
            }

            resultHtml += '    </div>';
            resultHtml += '</div>';

            $("#userPortraits").html(resultHtml);
        }

        function buildPortraits() {
            var portraitsPromise = $.ajax({
                url: "<%=randomPortraitsURL %>",
                method: "GET",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.statusText);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });

            portraitsPromise.done(function(data) {
                buildPortraitHTML(data.objects);
            });
        }

        buildPortraits();

        setInterval(function() {
            buildPortraits();
        }, 10000);
    });
</script>

<style>
    #userPortraits {
        display: block;
        height: 256px;
    }

    .portrait {
        height: 100px;
        width: 76px;
    }
</style>
