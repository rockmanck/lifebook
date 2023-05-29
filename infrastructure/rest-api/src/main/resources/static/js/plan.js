import {Tagging} from "./tagging.js";
import {LoaderClass} from "./loader.js";
import {PlansListCollapseClass} from "./plansListCollapse.js";

export function PlanClass() {
    const Loader = new LoaderClass();
    const PlansListCollapse = new PlansListCollapseClass();
	let _self = this;
	let sevenDays = 7 * 24 * 60 * 60 * 1000;
    let tagging;

	this.loadDailyPlans = function() {
		const date = $('#datepicker-daily').data('date');
        Loader.show();
		$.get('./items/daily.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#dailyList').html(data);
			PlansListCollapse.init('ViewType.DAILY');
		});
    };

    this.loadWeeklyPlans = function() {
        const date = $('#datepicker-weekly').data('date');
        Loader.show();
        $.get('./items/weekly.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#weeklyList').html(data);
            PlansListCollapse.init('ViewType.WEEKLY');
		});
    };

    this.loadOverview = function() {
        Loader.show();
        const monthAndYear = $('#monthPicker').data('date').split("/");
        const year = monthAndYear[1];
        const month = monthAndYear[0];
        $.get('./overview.html?year=' + year + '&month=' + month, function (data) {
            Loader.hide();
            $('#overviewContent').html(data);
        });
    };

	this.edit = function(id, viewType) {
		loadPlanById(id, new Date(), viewType);
	};

	this.new = function(datePickerId, viewType) {
		const dateVal = $('#' + datePickerId).data('date');
		loadPlanById(-1, new Date(dateVal), viewType);
	};

	this.save = function() {
		const planModal = $('#planModal');
		const form = planModal.find('form');
		const viewType = form.find('#viewType').val();
		const data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			planModal.modal('hide');
			switch (viewType) {
				case 'ViewType.DAILY': _self.loadDailyPlans(); break;
				case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
			}
		});
	};

	this.done = function(id, viewType, day) {
	    const container = PlansContainer.getContainer(viewType);

		$.post('./plan/' + id + '/done.html').done(function() {
            switch (viewType) {
                case 'ViewType.DAILY': _self.loadDailyPlans(); break;
                case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
            }
		});
	};

    this.cancel = function(id, viewType, day) {
        const container = PlansContainer.getContainer(viewType);

        $.post('./plan/' + id + '/cancel.html').done(function() {
            switch (viewType) {
                case 'ViewType.DAILY': _self.loadDailyPlans(); break;
                case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
            }
        });
    };

    this.removeTag = function(target) {
        const index = target.getAttribute('data-index');
        const input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', `tags[${index}].removed`);
        input.setAttribute('value', 'true');
        target.parentNode.after(input);
        target.parentNode.remove();
    };

    function loadPlanById(id, defaultDate, viewType) {
        $.get('./plan/' + id + '/edit.html', function (data) {
            let form = $('#planModal');
            form.html(data);
            updateDueDate(form, defaultDate);
            form.modal({backdrop: 'static'});
            form.find('#viewType').val(viewType);
            tagging = new Tagging(form, 'tagSuggest');
        });
    }

    function updateDueDate(form, defaultDate) {
        const dueDate = form.find('#planTimeRaw').val();
        const date = dueDate !== '' ? new Date(dueDate) : defaultDate;
        form.find('#planTime').datetimepicker({
            minDate: new Date() - sevenDays,
            sideBySide: true,
            format: 'MM/DD/YYYY HH:mm'
        }).data("DateTimePicker").date(date);
    }
}

export function PlansContainerResolver() {
    this.getContainer = function(viewType) {
        switch (viewType) {
            case 'ViewType.DAILY': return $('#dailyList');
            case 'ViewType.WEEKLY': return $('#weeklyList');
        }
        throw new Error('Failed to resolve view type: ' + viewType);
    }
}

const PlansContainer = new PlansContainerResolver();