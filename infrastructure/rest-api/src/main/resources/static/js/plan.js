function PlanClass() {
	let _self = this;
	let sevenDays = 7 * 24 * 60 * 60 * 1000;
    let tagIndex = -1;

	this.loadDailyPlans = function() {
		var date = $('#datepicker-daily').data('date');
        Loader.show();
		$.get('./items/daily.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#dailyList').html(data);
			PlansListCollapse.init('ViewType.DAILY');
		});
    };

    this.loadWeeklyPlans = function() {
        var date = $('#datepicker-weekly').data('date');
        Loader.show();
        $.get('./items/weekly.html?date=' + encodeURI(date), function (data) {
            Loader.hide();
			$('#weeklyList').html(data);
            PlansListCollapse.init('ViewType.WEEKLY');
		});
    };

    this.loadOverview = function() {
        Loader.show();
        var monthAndYear = $('#monthPicker').data('date').split("/");
        var year = monthAndYear[1];
        var month = monthAndYear[0];
        $.get('./overview.html?year=' + year + '&month=' + month, function (data) {
            Loader.hide();
            $('#overviewContent').html(data);
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
		var viewType = form.find('#viewType').val();
		var data = form.serialize();
		$.post(form.attr('action'), data).done(function () {
			planModal.modal('hide');
			switch (viewType) {
				case 'ViewType.DAILY': _self.loadDailyPlans(); break;
				case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
			}
		});
	};

	this.done = function(id, viewType, day) {
	    var container = PlansContainer.getContainer(viewType);

		$.post('./plan/' + id + '/done.html').done(function() {
            switch (viewType) {
                case 'ViewType.DAILY': _self.loadDailyPlans(); break;
                case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
            }
		});
	};

    this.cancel = function(id, viewType, day) {
        var container = PlansContainer.getContainer(viewType);

        $.post('./plan/' + id + '/cancel.html').done(function() {
            switch (viewType) {
                case 'ViewType.DAILY': _self.loadDailyPlans(); break;
                case 'ViewType.WEEKLY': _self.loadWeeklyPlans(); break;
            }
        });
    };

    function loadPlanById(id, defaultDate, viewType) {
        $.get('./plan/' + id + '/edit.html', function (data) {
            let form = $('#planModal');
            form.html(data);
            updateDueDate(form, defaultDate);
            form.modal({backdrop: 'static'});
            form.find('#viewType').val(viewType);
            form.find('#tagSuggest').autocomplete({
                source: "./tags/suggest",
                minLength: 2,
                select: function(event, ui) {
                    addTag(form, ui.item);
                },
                close: function (event, ui) {
                    document.getElementById('tagSuggest').value = '';
                }
            });
            form.find('#tagSuggest').on( "autocompleteselect", function( event, ui ) {} );

            tagIndex = -1;
            form.find('.tag-id')
                .each(function (i) {
                    const index = $(this).data('index');
                    if (index > tagIndex) {
                        tagIndex = index;
                    }
                });
        });
    }

    function addTag(form, item) {
        let container = form.find('.tags');

        tagIndex += 1;
        let tagVisual = document.createElement('span');
        tagVisual.setAttribute('class', 'tag default-tag');
        tagVisual.innerText = item.newTag ? "Create: " + item.label : item.label;
        container.append(tagVisual);

        let tagIdHidden = document.createElement('input');
        tagIdHidden.setAttribute('name', `tags[${tagIndex}].value`);
        tagIdHidden.setAttribute('type', 'hidden');
        tagIdHidden.setAttribute('value', item.newTag ? '' : `${item.value}`);
        container.append(tagIdHidden);

        let tagLabelHidden = document.createElement('input');
        tagLabelHidden.setAttribute('name', `tags[${tagIndex}].label`);
        tagLabelHidden.setAttribute('type', 'hidden');
        tagLabelHidden.setAttribute('value', item.label);
        container.append(tagLabelHidden);
    }

    function updateDueDate(form, defaultDate) {
        var dueDate = form.find('#planTimeRaw').val();
        var date = dueDate !== '' ? new Date(dueDate) : defaultDate;
        form.find('#planTime').datetimepicker({
            minDate: new Date() - sevenDays,
            sideBySide: true,
            format: 'MM/DD/YYYY HH:mm'
        }).data("DateTimePicker").date(date);
    }
}

function PlansContainerResolver() {
    this.getContainer = function(viewType) {
        switch (viewType) {
            case 'ViewType.DAILY': return $('#dailyList');
            case 'ViewType.WEEKLY': return $('#weeklyList');
        }
        throw new Error('Failed to resolve view type: ' + viewType);
    }
}

var PlansContainer = new PlansContainerResolver();
var Plan = new PlanClass();