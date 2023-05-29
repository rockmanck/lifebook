export function AnimationClass() {
	const defaultAnimationDelay = 250; // milliseconds

	/**
	 * Hides slowly element and removes from DOM
	 * @param $element jquery element to process
	 * @param userDelay optional - animation time
	 */
	this.remove = function($element, userDelay) {
		const delay = typeof userDelay !== undefined ? userDelay : defaultAnimationDelay;
		$element.fadeOut(delay, function() {
			$(this).remove();
		});
	};

	this.show = function($elem, userDelay) {
        const delay = typeof userDelay !== undefined ? userDelay : defaultAnimationDelay;
		$elem.fadeIn(delay, function() {$(this).show();})
	};

    this.hide = function($elem, userDelay) {
        const delay = typeof userDelay !== undefined ? userDelay : defaultAnimationDelay;
        $elem.fadeOut(delay, function() {$(this).hide();})
    };

    /**
	 * Shows alert with text 'Implement me'
     */
	this.notImplemented = function() {
        const implementMe = $('#implement-me');
        this.show(implementMe);
	    const me = this;
		setTimeout(function() {
		    me.hide(implementMe)
        }, 5000);
	};
}
