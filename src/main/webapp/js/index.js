$(document).ready(function(){
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		var x = $(event.target).text();         // active tab
		var y = $(event.relatedTarget).text();  // previous tab
	});

	$('#datepicker-daily').datetimepicker({
		inline: true,
		sideBySide: true,
		format: 'MM/dd/YYYY'
	});

	//$('#datepicker-monthly').datepicker({
	//	inline: true,
	//	sideBySide: true
	//});
});