AUI().ready(	'liferay-sign-in-modal', 	function(A) {
    var signIn = A.one('.sign-in > a');


    if (signIn && signIn.getData('redirect') !== 'true') {
        signIn.plug(Liferay.SignInModal);
    }
});

$(document).ready(function() {
    $("#all-events").hide();
    $(".view-toggle-featured").text("  back");
    $(".view-toggle-featured").hide();
    //add the button function
    $("#toggle-list-all").click(function() {
        //hide all open descriptions
        $("#all-events").find("div.expand-calendar-item div.event-description").hide();
        $("#all-events").find("div.expand-calendar-item p.description-toggle").html(unescape("&#9656"));
        //toggle the lists
        $("#featured-events").toggle();
        $("#all-events").toggle();
        //toggle the buttons
        $(".view-toggle-featured").toggle();
        $(".view-toggle-all").toggle();
    });
    $("#toggle-list-featured").click(function() {
        //hide all open descriptions
        $("#featured-events").find("div.expand-calendar-item div.event-description").hide();
        $("#featured-events").find("div.expand-calendar-item p.description-toggle").html(unescape("&#9656"));
        //toggle the lists
        $("#featured-events").toggle();
        $("#all-events").toggle();
        //toggle the buttons
        $(".view-toggle-featured").toggle();
        $(".view-toggle-all").toggle();
    });

    //event calendar expand
    $("#featured-events div.event-item, #all-events div.event-item").each(
        function() {
            $count = $(this).index() + 1;
            $currentElem = $(this);
            $(this).find("div.expand-calendar-item p.description-toggle").click(function() {
                //show the correct arrow
                var str1 = $(this).html();
                if (str1 == unescape("&#9662")) {
                    $(this).html(unescape("&#9656"));
                } else {
                    //▸ &#9656;  ▾ &#9662;
                    $(this).html(unescape("&#9662"));
                }
                //toggle description visibillity
                $(this).parent().find("div.event-description").toggle("fast");
            });

        });
});
