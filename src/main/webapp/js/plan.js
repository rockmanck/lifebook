function PlanClass() {
	var _self = this;
	var sevenDays = 7 * 24 * 60 * 60 * 1000;

	function loadPlanById(id, defaultDate, viewType) {
		$.get('./plan/' + id + '/edit.html', function (data) {
			var form = $('#planModal');
			form.html(data);
			updateDueDate(form, defaultDate);
			form.modal({backdrop: 'static'});
			form.find('#viewType').val(viewType);
		});
	}

	function updateDueDate(form, defaultDate) {
		var dueDate = form.find('#planTimeRaw').val();
		var date = dueDate != '' ? new Date(dueDate) : defaultDate;
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

	this.edit = function(id, viewType) {
		loadPlanById(id, new Date(), viewType);
	};

	this.new = function(datePickerId, viewType) {
		var dateVal = $('#' + datePickerId).data('date');
		loadPlanById(-1, new Date(dateVal), viewType);
	};

	this.save = function() {
		var planModal = $('#planModal');
		var form = planModal.find('form');
		var viewType = parseInt(form.find('#viewType').val());
		var data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			planModal.modal('hide');
			switch (viewType) {
				case ViewType.DAILY: _self.loadDailyPlans(); break;
				case ViewType.WEEKLY: _self.loadWeeklyPlans(); break;
			}
		});
	};

	this.done = function(id) {
		$.post('./plan/' + id + '/done.html').done(function() {
			animation.remove($('#plan' + id), 450);
		});
	};
}

var Plan = new PlanClass();