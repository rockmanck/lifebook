package pp.ua.lifebook.web.moment;

import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.web.tag.TagDtoMapper;

public class MomentDtoMapper {
    public static Moment toDomain(MomentDto dto, int userId) {
        final Moment result = new Moment();
        result.setId(dto.getId());
        result.setDate(dto.getDate());
        result.setDescription(dto.getDescription());
        result.setTags(TagDtoMapper.toDomain(dto.getTags(), userId));
        return result;
    }
}
