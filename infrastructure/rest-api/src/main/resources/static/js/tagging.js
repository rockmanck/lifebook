export class Tagging {
    #tagIndex = -1;

    constructor(form, suggestInputId) {
        const self = this;
        form.find(`#${suggestInputId}`).autocomplete({
            source: "./tags/suggest",
            minLength: 2,
            select: function(event, ui) {
                self.#addTag(form, ui.item);
            },
            close: function (event, ui) {
                document.getElementById(suggestInputId).value = '';
            }
        });

        this.#tagIndex = -1;
        form.find('.tag-id')
            .each(function (i) {
                const index = $(this).data('index');
                if (index > self.#tagIndex) {
                    self.#tagIndex = index;
                }
            });

        form.find('.tag-remove')
            .on('click', function(e) {
                const target = $(this);
                const index = target.data('index');
                const input = $(
                    '<input type="hidden" name="tags[' + index + '].removed" value="true">'
                );
                input.insertAfter(target.parent());
                target.parent().remove();
            });
    }

    #addTag(form, item) {
        let container = form.find('.tags');

        this.#tagIndex += 1;
        let tagVisual = document.createElement('span');
        tagVisual.setAttribute('class', 'tag default-tag');
        tagVisual.innerText = (item.newTag ? "Create: " + item.label : item.label) + ' ';
        container.append(tagVisual);

        const tagRemove = document.createElement('span');
        tagRemove.setAttribute('class', 'tag-remove');
        tagRemove.innerHTML = '&times;';
        tagRemove.setAttribute('onclick', 'Plan.removeTag(this);');
        tagRemove.setAttribute('data-index', this.#tagIndex);
        tagVisual.append(tagRemove);

        let tagIdHidden = document.createElement('input');
        tagIdHidden.setAttribute('name', `tags[${this.#tagIndex}].value`);
        tagIdHidden.setAttribute('type', 'hidden');
        tagIdHidden.setAttribute('value', item.newTag ? '' : `${item.value}`);
        container.append(tagIdHidden);

        let tagLabelHidden = document.createElement('input');
        tagLabelHidden.setAttribute('name', `tags[${this.#tagIndex}].label`);
        tagLabelHidden.setAttribute('type', 'hidden');
        tagLabelHidden.setAttribute('value', item.label);
        container.append(tagLabelHidden);
    }
}