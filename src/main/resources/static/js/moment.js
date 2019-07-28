function MomentClass() {
	this.edit = function(id, viewType) {
		loadMomentById(id,  new Date(), viewType);
	};

	this.new = function(datePickerId, viewType) {
        var dateVal = $('#' + datePickerId).data('date');
        loadMomentById(-1, new Date(dateVal), viewType);
	};

	this.save = function() {
		var momentModal = $('#momentModal');
		var form = momentModal.find('form');
		var viewType = parseInt(form.find('#viewType').val());
		var data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			momentModal.modal('hide');
			switch (viewType) {
				case ViewType.DAILY: Plan.loadDailyPlans(); break;
				case ViewType.WEEKLY: Plan.loadWeeklyPlans(); break;
			}
		});
	};

    function loadMomentById(id, defaultDate, viewType) {
        $.get('./moment/' + id + '/edit.html', function (data) {
            var form = $('#momentModal');
            form.html(data);
            updateDate(form, defaultDate);
            form.modal({backdrop: 'static'});
            form.find('#viewType').val(viewType);
        });
    }

    function updateDate(form, defaultDate) {
        var dueDate = form.find('#momentDateRaw').val();
        var date = dueDate !== '' ? new Date(dueDate) : defaultDate;
        form.find('#momentDate').datetimepicker({
            format: 'MM/DD/YYYY'
        }).data("DateTimePicker").date(date);
    }
}

var Moment = new MomentClass();