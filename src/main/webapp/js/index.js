$(document).ready(function(){
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
		var activeTab = $(event.target).attr("href");
		switch (activeTab) {
			case '#daily':
				Plan.loadDailyPlans();
				break;
			case '#weekly':
				Plan.loadWeeklyPlans();
				break;
		}

		var y = $(event.relatedTarget).text();  // previous tab
	});

	$('#datepicker-daily').datetimepicker({
		inline: true,
		sideBySide: true,
		format: 'MM/DD/YYYY'
	}).on('dp.change', function() {
		Plan.loadDailyPlans();
	});

	$('#datepicker-weekly').datetimepicker({
		inline: true,
		sideBySide: true,
		format: 'MM/DD/YYYY'
	}).on('dp.change', function() {
		Plan.loadWeeklyPlans();
	});

	$('#planModal').on('shown.bs.modal', function() {
		var title = $(this).find('#title');
		title.focus();
		title.val(title.val());
	});
});

var ViewType = {
	DAILY: 1,
	WEEKLY: 2
};