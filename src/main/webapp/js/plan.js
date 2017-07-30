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
        Loader.show();
		$.get('./plan/daily.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#dailyList').html(data);
			PlansListCollapse.init(ViewType.DAILY);
		});
    };

    this.loadWeeklyPlans = function() {
        var date = $('#datepicker-weekly').data('date');
        Loader.show();
        $.get('./plan/weekly.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#weeklyList').html(data);
            PlansListCollapse.init(ViewType.WEEKLY);
		});
    };

    this.loadOverview = function() {
        Loader.show();
        $.get('./overview.html?year=2017&month=6', function (data) {
            Loader.hide();
            $('#overview').html(data);
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

	this.done = function(id, viewType, day) {
	    var container = PlansContainer.getContainer(viewType);

		$.post('./plan/' + id + '/done.html').done(function() {
            if (!UserSettings.showDone()) {
                animation.remove(container.find('#plan-' + id), 450);
                setTimeout(function() {
                    PlansListCollapse.cleanup(viewType, day);
                }, 460);
            }
		});
	};

    this.cancel = function(id, viewType, day) {
        var container = PlansContainer.getContainer(viewType);

        $.post('./plan/' + id + '/cancel.html').done(function() {
            if (!UserSettings.showCanceled()) {
                animation.remove(container.find('#plan-' + id), 450);
                setTimeout(function() {
                    PlansListCollapse.cleanup(viewType, day);
                }, 460);
            }
        });
    };
}

function PlansContainerResolver() {
    this.getContainer = function(viewType) {
        switch (viewType) {
            case ViewType.DAILY: return $('#dailyList');
            case ViewType.WEEKLY: return $('#weeklyList');
        }
        throw new Error('Failed to resolve view type: ' + viewType);
    }
}

var PlansContainer = new PlansContainerResolver();
var Plan = new PlanClass();