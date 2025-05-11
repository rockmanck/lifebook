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

import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.select;
import static pp.ua.lifebook.storage.db.scheme.Tables.*;

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

    @Override
    public List<Tag> mostPopular(int userId, int limit) {
        /*
        with tmp as (select t.id, count(tp.tag_id) as c
                     from tag t
                              left join tag_plan_relation tp on tp.tag_id = t.id
                              left join plans p on p.id = tp.plan_id
                     where t.user_id = 2
                     group by t.id
                     limit 7)
        select *
        from tag t
                 join tmp tp on tp.id = t.id
        order by tp.c desc, t.name
        */

        return dsl.with("tmp")
                .as(
                        select(TAG.ID.as("t_tag_id"), count(TAG_PLAN_RELATION.TAG_ID).as("t_tag_count"))
                        .from(TAG)
                        .leftJoin(TAG_PLAN_RELATION).on(TAG_PLAN_RELATION.TAG_ID.eq(TAG.ID))
                        .leftJoin(PLANS).on(PLANS.ID.eq(TAG_PLAN_RELATION.PLAN_ID))
                        .where(TAG.USER_ID.eq(userId))
                        .groupBy(TAG.ID)
                        .limit(limit)
                )
                .select(TAG.ID, TAG.NAME, TAG.USER_ID)
                .from(TAG)
                .join(DSL.table("tmp")).on(DSL.field("t_tag_id").eq(TAG.ID))
                .orderBy(DSL.field("t_tag_count").desc(), TAG.NAME.asc())
                .fetchInto(TagRecord.class)
                .stream()
                .map(DbTagMapper::toDomainTag)
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
