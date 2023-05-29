package pp.ua.lifebook.web.tag;

import pp.ua.lifebook.tag.Tag;

import java.util.List;

public class TagDtoMapper {

    private TagDtoMapper() {
        throw new UnsupportedOperationException("It is not allowed to create instance of this class");
    }

    public static TagDto toDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setValue(tag.id());
        dto.setLabel(tag.name());
        dto.setNewTag(tag.newTag());
        return dto;
    }

    public static List<Tag> toDomain(List<TagDto> dtos, int userId) {
        if (dtos == null) return List.of();

        return dtos.stream()
            .map(dto -> new Tag(
                dto.getValue(),
                userId,
                dto.getLabel(),
                dto.getValue() == null,
                dto.isRemoved()
            )).toList();
    }
}
