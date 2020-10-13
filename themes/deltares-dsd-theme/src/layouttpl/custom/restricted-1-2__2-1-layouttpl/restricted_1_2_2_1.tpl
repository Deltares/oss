<div class="restricted-layout restricted-1-2__2-1 container-fluid-1280" id="main-content" role="main">
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>
	<div class="portlet-layout row">
		<div class="col-md-8 portlet-column portlet-column-first" id="column-2">
			${processor.processColumn("column-2", "portlet-column-content portlet-column-content-first")}
		</div>
		<div class="col-md-4 portlet-column portlet-column-last position-sticky" id="column-3">
			${processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")}
		</div>
	</div>
</div>
