$(document).ready(function(){
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		var x = $(event.target).text();         // active tab
		var y = $(event.relatedTarget).text();  // previous tab
	});

	$('#datepicker-daily').datetimepicker({
		inline: true,
		sideBySide: true,
		format: 'MM/DD/YYYY'
	});

	//$('#datepicker-monthly').datepicker({
	//	inline: true,
	//	sideBySide: true
	//});

	$('li.facebook').on('click', function(){
		var container = $('#planModal');
		// get plan via ajax and init container
		container.find('#title').val('Some saved value!');
		// show the form
		container.modal('toggle');
	});

	$('#planModal').on('shown.bs.modal', function() {
		$('#planTime').datetimepicker({
			minDate: new Date(),
			sideBySide: true,
			format: 'MM/DD/YYYY HH:mm'
		});
	});

	$('#planEditSubmit').on('click', function() {
		var planModal = $('#planModal');
		var form = planModal.find('form');
		var data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			$('#planModal').modal('toggle');
		});
	});
});