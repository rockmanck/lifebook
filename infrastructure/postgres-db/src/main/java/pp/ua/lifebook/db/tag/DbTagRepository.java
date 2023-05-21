package pp.ua.lifebook.db.tag;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import pp.ua.lifebook.storage.db.scheme.Tables;
import pp.ua.lifebook.storage.db.scheme.tables.records.TagRecord;
import pp.ua.lifebook.tag.Tag;
import pp.ua.lifebook.tag.port.TagRepositoryPort;

import java.util.Comparator;
import java.util.List;

public class DbTagRepository implements TagRepositoryPort {

    private final DSLContext dsl;

    public DbTagRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<Tag> search(int userId, String term) {
        return dsl.select()
            .from(Tables.TAG)
            .where(DSL.lower(Tables.TAG.NAME).like("%" + term + "%"))
            .fetchInto(TagRecord.class)
            .stream()
            .map(TagMapper::toDomainTag)
            .sorted(startWithFirstAndAlphabetically(term))
            .toList();
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
