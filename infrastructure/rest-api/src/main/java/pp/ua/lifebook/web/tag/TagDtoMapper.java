package pp.ua.lifebook.web.tag;

import pp.ua.lifebook.tag.Tag;

public class TagDtoMapper {
    public static TagDto toDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setValue(tag.id());
        dto.setLabel(tag.name());
        return dto;
    }
}
