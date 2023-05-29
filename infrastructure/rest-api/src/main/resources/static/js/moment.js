import {Tagging} from "./tagging.js";

export function MomentClass() {
    let tagging;

    this.edit = function(id, viewType) {
        loadMomentById(id,  new Date(), viewType);
    };

    this.new = function(datePickerId, viewType) {
        const dateVal = $('#' + datePickerId).data('date');
        loadMomentById(-1, new Date(dateVal), viewType);
    };

    this.save = function() {
        const momentModal = $('#momentModal');
        const form = momentModal.find('form');
        const viewType = form.find('#viewType').val();
        const data = form.serialize();
        $.post(form.attr('action'), data).done(function () {
            momentModal.modal('hide');
            switch (viewType) {
                case 'ViewType.DAILY': Plan.loadDailyPlans(); break;
                case 'ViewType.WEEKLY': Plan.loadWeeklyPlans(); break;
            }
        });
    };

    function loadMomentById(id, defaultDate, viewType) {
        $.get('./moment/' + id + '/edit.html', function (data) {
            const form = $('#momentModal');
            form.html(data);
            updateDate(form, defaultDate);
            form.modal({backdrop: 'static'});
            form.find('#viewType').val(viewType);
            tagging = new Tagging(form, 'momentTagSuggest');
        });
    }

    function updateDate(form, defaultDate) {
        const dueDate = form.find('#momentDateRaw').val();
        const date = dueDate !== '' ? new Date(dueDate) : defaultDate;
        form.find('#momentDate').datetimepicker({
            format: 'MM/DD/YYYY'
        }).data("DateTimePicker").date(date);
    }
}
