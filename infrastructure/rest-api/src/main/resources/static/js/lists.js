import {LoaderClass} from "./loader.js";

export function ListsClass() {
    const _self = this;
    const Loader = new LoaderClass();

    this.edit = function(id) {
        loadListById(id);
    };

    this.new = function() {
        loadListById(-1);
    };

    this.save = function() {
        const listModal = $('#listModal');
        const form = listModal.find('form');
        const data = form.serialize();
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

    this.deleteItem = function(listId, itemId) {
        $.ajax({
            url: '/lists/' + listId + '/items/' + itemId,
            type: 'DELETE',
            success: function() {
                loadListById(listId);
            }
        });
    }

    this.newItem = function(button) {
        let origin = document.querySelector('#new-list-item');
        let newItemDiv = origin.cloneNode(true);
        newItemDiv.removeAttribute('id');

        let nameInput = newItemDiv.getElementsByTagName('input')[0];
        nameInput.setAttribute('type', 'text');

        let index = getCurrentIndexAndIncrement();
        nameInput.name = 'listItems[' + index + '].name';

        let idInput = document.createElement('input');
        idInput.type = 'hidden';
        idInput.name = 'listItems[' + index + '].listId';
        idInput.value = document.getElementById('list-id').value;
        newItemDiv.insertBefore(idInput, nameInput);

        origin.parentNode.insertBefore(newItemDiv, button);
        nameInput.focus();
    }

    function loadListById(id) {
        $.get('./lists/' + id, function (data) {
            const form = $('#listModal');
            form.html(data);
            form.modal({backdrop: 'static'});
        });
    }

    function getCurrentIndexAndIncrement() {
        let nextItemIndexInput = document.getElementById('next-item-index');
        let index = parseInt(nextItemIndexInput.getAttribute('value'));
        nextItemIndexInput.value = index + 1;
        return index;
    }
}
