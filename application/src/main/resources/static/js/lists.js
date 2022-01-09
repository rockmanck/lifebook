function ListsClass() {
    var _self = this;

    this.edit = function(id) {
        loadListById(id);
    };

    this.new = function() {
        loadListById(-1);
    };

    this.save = function() {
        var listModal = $('#listModal');
        var form = listModal.find('form');
        var data = form.serialize();
        $.post(form.attr('action'), data).done(function () {
            listModal.modal('hide');
            _self.loadLists();
        });
    };

    this.loadLists = function() {
        Loader.show();
        $.get('./lists', function (data) {
            Loader.hide();
            $('#lists').html(data);
        });
    };

    this.delete = function(id) {
        $.ajax({
            url: '/lists/' + id,
            type: 'DELETE',
            success: function() {
                _self.loadLists();
            }
        });
    }

    function loadListById(id) {
        $.get('./lists/' + id, function (data) {
            var form = $('#listModal');
            form.html(data);
            form.modal({backdrop: 'static'});
        });
    }
}

var Lists = new ListsClass();