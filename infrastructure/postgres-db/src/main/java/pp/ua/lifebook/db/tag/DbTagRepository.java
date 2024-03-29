package pp.ua.lifebook.db.tag;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import pp.ua.lifebook.storage.db.scheme.tables.records.TagRecord;
import pp.ua.lifebook.tag.Tag;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pp.ua.lifebook.storage.db.scheme.Tables.TAG;

public class DbTagRepository implements TagRepositoryPort {

    private final DSLContext dsl;

    public DbTagRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<Tag> search(int userId, String term) {
        return dsl.select()
            .from(TAG)
            .where(DSL.lower(TAG.NAME).like("%" + term + "%"))
            .fetchInto(TagRecord.class)
            .stream()
            .map(DbTagMapper::toDomainTag)
            .sorted(startWithFirstAndAlphabetically(term))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Tag> create(Collection<Tag> tags, int userId) {
        return tags.stream()
            .map(t -> {
                if (t.id() == null && !t.removed()) {
                    Integer tagId = dsl.insertInto(TAG, TAG.NAME, TAG.USER_ID)
                        .values(t.name(), userId)
                        .returningResult(TAG.ID)
                        .fetchOne()
                        .getValue(TAG.ID);
                    return new Tag(tagId, userId, t.name());
                } else {
                    return t;
                }
            }).collect(Collectors.toSet());
    }

    private static Comparator<Tag> startWithFirstAndAlphabetically(String term) {
        return (o1, o2) -> {
            String lowerTerm = term.toLowerCase();
            boolean firstStartsWith = o1.name().toLowerCase().startsWith(lowerTerm);
            boolean secondStartsWith = o2.name().toLowerCase().startsWith(lowerTerm);
            if (firstStartsWith != secondStartsWith) {
                if (firstStartsWith) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return o1.name().compareTo(o2.name());
            }
        };
    }
}
