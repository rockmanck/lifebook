function PlansListCollapseClass() {

    this.init = function(viewType) {
        var container = PlansContainer.getContainer(viewType);
        container.find('.plans-day-strip').click(function () {
            var dayBar = $(this);
            var plansList = $(dayBar).next('div');
            if (dayBar.hasClass('expanded')) {
                animation.hide(plansList, 150);
            } else {
                animation.show(plansList, 150);
            }
            dayBar.toggleClass('expanded');
            dayBar.toggleClass('collapsed');
        });
    };

    this.cleanup = function (viewType, day) {
        var container = PlansContainer.getContainer(viewType);
        var strip = container.find('div[data-forDay="' + day + '"]');
        var list = strip.next('div');
        if (list.has('li').length > 0) return;

        animation.remove(strip, 150);
    };
}

var PlansListCollapse = new PlansListCollapseClass();