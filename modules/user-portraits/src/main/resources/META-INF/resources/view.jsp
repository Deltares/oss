<%@ include file="/init.jsp" %>

<div class="top-content">
	<div class="container-fluid">
		<div id="user-portraits-carousel" class="carousel slide" data-ride="carousel">
			<div class="carousel-inner row w-100 mx-auto" role="listbox">
				<c:forEach items="${userPortraitsList}" var="portrait" varStatus="loop">
					<div class="carousel-item col-12 col-sm-6 col-md-4 col-lg-3 ${loop.first ? 'active' : '' }">
						<img src="${portrait}" alt="img${loop.index}" class="img-fluid mx-auto d-block">
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>