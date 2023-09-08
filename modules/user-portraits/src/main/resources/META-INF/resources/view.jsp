<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	String userPortraitsData = (String) renderRequest.getAttribute("userportraitdata");
%>

<div class="top-content">
	<div class="h2">Meet the OSS community members!</div>
	<div class="container-fluid">
		<div id="user-portraits-carousel" class="carousel slide" data-ride="carousel">
			<div id="<portlet:namespace/>portraits"  class="carousel-inner row w-100 mx-auto" role="listbox">
			</div>
			<div id="<portlet:namespace/>title">
			</div>
		</div>
	</div>
</div>

<aui:input name="runningProcess" type="hidden" />
<aui:script use="event, io, aui-io-request, node, aui-base">

	<% if (userPortraitsData == null ) {%>
	UserPortraitUtil.startUserPortraits("<portlet:resourceURL/>", "<portlet:namespace/>");
	<% } else { %>
	UserPortraitUtil.updatePortraits("<portlet:namespace/>", '<%=userPortraitsData%>');
	<% }%>


	$('#user-portraits-carousel').on('slide.bs.carousel', function (e) {
	/*
	CC 2.0 License Iatek LLC 2018 - Attribution required
	*/
	var $e = $(e.relatedTarget);
	var idx = $e.index();
	var itemsPerSlide = 5;
	var totalItems = $('.carousel-item').length;

	if (idx >= totalItems-(itemsPerSlide-1)) {
	var it = itemsPerSlide - (totalItems - idx);
	for (var i=0; i<it; i++) {
	// append slides to end
	if (e.direction === "left") {
	$('.carousel-item').eq(i).appendTo('.carousel-inner');
	}
	else {
	$('.carousel-item').eq(0).appendTo('.carousel-inner');
	}
	}
	}
	});

</aui:script>