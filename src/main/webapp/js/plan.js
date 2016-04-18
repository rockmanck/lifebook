function PlanClass() {
	var _self = this;
	var sevenDays = 7 * 24 * 60 * 60 * 1000;

	function loadPlanById(id) {
		$.get('./plan/' + id + '/edit.html', function (data) {
			var form = $('#planModal');
			form.html(data);
			updateDueDate(form);
			form.modal('show')
		});
	}

	function updateDueDate(form) {
		var dueDate = form.find('#planTimeRaw').val();
		var date = dueDate != '' ? new Date(dueDate) : new Date();
		form.find('#planTime').datetimepicker({
			minDate: new Date() - sevenDays,
			sideBySide: true,
			format: 'MM/DD/YYYY HH:mm'
		}).data("DateTimePicker").date(date);
	}

	this.loadDailyPlans = function() {
		var date = $('#datepicker-daily').data('date');
		$.get('./plan/daily.html?date=' + encodeURI(date), function (data) {
			$('#dailyList').html(data);
		});
	};

	this.loadWeeklyPlans = function() {
		var date = $('#datepicker-weekly').data('date');
		$.get('./plan/weekly.html?date=' + encodeURI(date), function (data) {
			$('#weeklyList').html(data);
		});
	};

	this.edit = function(id) {
		loadPlanById(id);
	};

	this.new = function() {
		loadPlanById(-1);
	};

	this.save = function() {
		var planModal = $('#planModal');
		var form = planModal.find('form');
		var data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			planModal.modal('hide');
			_self.loadDailyPlans();
		});
	}
}

var Plan = new PlanClass();