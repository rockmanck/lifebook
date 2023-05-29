import {PlansContainerResolver} from "./plan.js";
import {AnimationClass} from "./animation.js";

export function PlansListCollapseClass() {
    const animation = new AnimationClass();

    this.init = function(viewType) {
        const container = new PlansContainerResolver().getContainer(viewType);
        container.find('.plans-day-strip').click(function () {
            const dayBar = $(this);
            const plansList = $(dayBar).next('div');
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
        const container = new PlansContainerResolver().getContainer(viewType);
        const strip = container.find('div[data-forDay="' + day + '"]');
        const list = strip.next('div');
        if (list.has('li').length > 0) return;

        animation.remove(strip, 150);
    };
}
