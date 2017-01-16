function AnimationClass() {
	var defaultAnimationDelay = 250; // milliseconds

	/**
	 * Hides slowly element and removes from DOM
	 * @param $element jquery element to process
	 * @param delay optional - animation time
	 */
	this.remove = function($element, delay) {
		var delay = typeof delay !== undefined ? delay : defaultAnimationDelay;
		$element.fadeOut(delay, function() {
			$(this).remove();
		});
	}
}

var animation = new AnimationClass();
