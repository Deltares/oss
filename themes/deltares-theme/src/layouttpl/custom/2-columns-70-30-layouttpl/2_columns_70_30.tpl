<div class="two-columns-70-30 container-fluid-1280" id="main-content" role="main">
	<div class="portlet-layout row">
		<div class="col-md-9 portlet-column portlet-column-first" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")}
		</div>
		<div class="col-md-3 portlet-column portlet-column-last" id="column-2">
			${processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")}
		</div>
	</div>
</div>

<script type="text/javascript" data-senna-track="temporary">
	//creates the blue background from the left column to the left side of the page.
	var doit;
	function createBlueBg(){
        var section = document.querySelector('#wrapper > section#content');
        var sectionHeight = section ? section.offsetHeight : 0;
		document.querySelectorAll('.fake-background').forEach(function(el) {
          el.remove();
        });
        var style = document.createElement('style');
        style.className = 'fake-background';
        style.textContent = '.two-columns-70-30 .portlet-column-first::before { height: ' + sectionHeight + 'px!important; }';
        document.head.appendChild(style);
	}
	AUI().ready('aui-module', function(A){
		createBlueBg();
	});

	window.addEventListener('resize',function(e){
		clearTimeout(doit);
		doit = setTimeout(function() {
			createBlueBg();
		}, 100);
	});
</script>