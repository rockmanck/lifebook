import {UserSettingsClass} from "./user-settings.js";
import {LoaderClass} from "./loader.js";
import {PlansListCollapseClass} from "./plansListCollapse.js";
import {PlanClass} from "./plan.js";
import {MomentClass} from "./moment.js";
import {ListsClass} from "./lists.js";

window.Plan = new PlanClass();
window.Moment = new MomentClass();
window.Lists = new ListsClass();
let UserSettings;
let Loader;
const PlansListCollapse = new PlansListCollapseClass();

$(document).ready(function(){
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
        var tab = $(event.target);
        var activeTab = tab.attr("href");
		switch (activeTab) {
			case '#daily':
				Plan.loadDailyPlans();
				break;
			case '#weekly':
				Plan.loadWeeklyPlans();
				break;
            case '#overview':
                Plan.loadOverview();
                break;
            case '#lists':
                Lists.loadLists();
		}
        UserSettings.updateSelectedTab(tab.data('type'));
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

	$('#monthPicker').datetimepicker({
        format: 'MM/YYYY',
        viewMode: 'months',
        defaultDate: new Date()
    })
        .data('date', getCurrentMonthAndYear())
        .on('dp.change', function () {
            Plan.loadOverview();
        });

	function getCurrentMonthAndYear() {
	    var date = new Date();
	    return (date.getMonth() + 1) + "/" + date.getFullYear();
    }

    UserSettings = new UserSettingsClass();
    Loader = new LoaderClass();
	PlansListCollapse.init('ViewType.DAILY');
	PlansListCollapse.init('ViewType.WEEKLY');
});

var ViewType = Object.freeze({
	DAILY: 1,
	WEEKLY: 2
});