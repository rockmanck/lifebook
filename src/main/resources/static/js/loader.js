function LoaderClass() {
    var _loader = $('#loading');
    var _inShowMode = false;
    var _timerId = null;

    /**
     * Shows loader in singleton mode after 300 milliseconds after call
     */
    this.show = function () {
        if (_inShowMode) return;

        _inShowMode = true;
        _timerId = setTimeout(function () {
            _loader.show();
            _timerId = null;
        }, 300);
    };

    /**
     * Hides loader.
     * If showing timer is active
     */
    this.hide = function () {
        if (_inShowMode && _timerId != null) {
            clearTimeout(_timerId);
            _timerId = null;
            _inShowMode = false;
            return;
        }
        _loader.hide();
        _inShowMode = false;
    };
}