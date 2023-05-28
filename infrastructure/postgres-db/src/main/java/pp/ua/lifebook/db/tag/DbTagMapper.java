package pp.ua.lifebook.db.tag;

import pp.ua.lifebook.storage.db.scheme.tables.records.TagRecord;
import pp.ua.lifebook.tag.Tag;

public class DbTagMapper {
    public static Tag toDomainTag(TagRecord tag) {
        return new Tag(
            tag.getId(),
            tag.getUserId(),
            tag.getName()
        );
    }
}
