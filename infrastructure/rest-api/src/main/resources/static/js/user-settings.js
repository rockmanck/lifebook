export function UserSettingsClass() {
    const _weekly = $('#view-options-weekly');
    const _daily = $('#view-options-daily');
    const _weeklyCheckboxes = _weekly.find('input');
    const _dailyCheckboxes = _daily.find('input');

    const _syncHref = './updateUserSettings.html';
    const _checkedStates = {
        SHOW_OUTDATED: false,
        SHOW_DONE: false,
        SHOW_CANCELED: false
    };
    let _syncMode = false;
    let _selectedTab;

    init();

    function init() {
        parseCheckedOptions(_dailyCheckboxes);
        _weeklyCheckboxes.change(function() {
            if (_syncMode) return;

            var checkbox = $(this);
            checkedChanged(checkbox, _daily, Plan.loadWeeklyPlans);
        });

        _dailyCheckboxes.change(function() {
            if (_syncMode) return;
            
            var checkbox = $(this);
            checkedChanged(checkbox, _weekly, Plan.loadDailyPlans);
        });
        
        _selectedTab = $('.nav.nav-tabs>li.active>a').data('type');
    }

    function checkedChanged(checkbox, oppositeControl, plansLoader) {
        var checked = checkbox.prop('checked');
        _checkedStates[checkbox.val()] = checked;

        _syncMode = true;
        var name = checkbox.prop('name');
        oppositeControl.find('input[name="' + name + '"]').prop('checked', checked);
        $.ajax(_syncHref, {
            data: getSettings(),
            complete: plansLoader
        });
        _syncMode = false;
    }

    function parseCheckedOptions(checkboxes) {
        for (var i = 0; i < checkboxes.length; i += 1) {
            var checkbox = $(checkboxes[i]);
            updateCheckedState(checkbox);
        }
    }

    function updateCheckedState(checkbox) {
        _checkedStates[checkbox.val()] = checkbox.prop('checked');
    }

    function getSettings() {
        var result = {};
        var viewOptions = "";
        for (var property in _checkedStates) {
            if (_checkedStates.hasOwnProperty(property)) {
                var value = _checkedStates[property];
                if (value) {
                    if (viewOptions != '') viewOptions += ",";
                    viewOptions += property;
                }
            }
        }
        result['viewOptions'] = viewOptions;
        result['defaultTab'] = _selectedTab;
        return result;
    }

    this.updateSelectedTab = function(tab) {
        _selectedTab = tab;
        $.ajax(_syncHref, {data: getSettings()});
    };
    
    this.showDone = function() {
        return _checkedStates['SHOW_DONE'];
    };

    this.showCanceled = function() {
        return _checkedStates['SHOW_CANCELED'];
    };
}
