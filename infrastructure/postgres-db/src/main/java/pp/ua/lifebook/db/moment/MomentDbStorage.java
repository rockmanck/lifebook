package pp.ua.lifebook.db.moment;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import pp.ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import pp.ua.lifebook.db.tag.DbTagUtil;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.tag.Tag;
import pp.ua.lifebook.tag.port.TagRepositoryPort;
import pp.ua.lifebook.utils.DateUtils;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pp.ua.lifebook.storage.db.scheme.Tables.MOMENTS;
import static pp.ua.lifebook.storage.db.scheme.Tables.TAG_MOMENT_RELATION;

public class MomentDbStorage implements MomentStorage {
    private static final String UPDATE_ONE_SQL = "UPDATE moments SET date = ?, description = ? WHERE id = ?";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final JdbcTemplate jdbc;
    private final DSLContext dslContext;
    private final TagRepositoryPort tagRepositoryPort;

    public MomentDbStorage(
        DataSource dataSource,
        DSLContext dslContext,
        TagRepositoryPort tagRepositoryPort
    ) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.dslContext = dslContext;
        this.tagRepositoryPort = tagRepositoryPort;
    }

    @Override
    public void save(Moment moment) {
        final Date date = DateUtils.localDateToDate(moment.getDate());
        if (moment.getId() == null) {
            final int momentId = dslContext.insertInto(
                    MOMENTS,
                    MOMENTS.DATE,
                    MOMENTS.DESCRIPTION,
                    MOMENTS.USER_ID
                )
                .values(
                    moment.getDate(),
                    moment.getDescription(),
                    moment.getUserId()
                )
                .returningResult(MOMENTS.ID)
                .fetchOne()
                .getValue(MOMENTS.ID);
            moment.setId(momentId);
        } else {
            jdbc.update(UPDATE_ONE_SQL, date, moment.getDescription(), moment.getId());
        }
        saveTags(new HashSet<>(moment.getTags()), moment.getUserId(), moment.getId());
    }

    @Override
    public List<Moment> getByDateRange(int userId, LocalDate start, LocalDate end) {
        final List<Moment> result = new ArrayList<>();
        final String sql = sqlBuilder.sql("GetMoments")
            .param("startDate", DateUtils.format(start))
            .param("endDate", DateUtils.format(end))
            .param("userId", userId)
            .build();
        try {
            jdbc.query(sql, rs -> {
                final Moment moment = moment(rs, userId);
                result.add(moment);
            });
        } catch (EmptyResultDataAccessException e) {
            log.debug("No moments found for {} user between {} and {} dates", userId, start, end);
        }
        return result;
    }

    @Override
    public Moment getById(int id, int userId) {
        final String sql = sqlBuilder.sql("GetMoment")
            .param("momentId", id)
            .build();
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> moment(rs, userId));
        } catch (EmptyResultDataAccessException e) {
            return Moment.builder().createMoment();
        }
    }

    public Moment moment(ResultSet rs, int userId) throws SQLException {
        final Moment moment = Moment.builder().createMoment();
        moment.setId(rs.getInt("id"));
        moment.setDate(DateUtils.dateToLocalDate(rs.getDate("date")));
        moment.setDescription(rs.getString("description"));
        moment.setUserId(rs.getInt("user_id"));
        Array rsArray = rs.getArray("tags");
        moment.setTags(DbTagUtil.toTags(rsArray, userId));
        return moment;
    }

    private void saveTags(Set<Tag> tags, Integer userId, Integer planId) {
        Collection<Tag> updatedTags = tagRepositoryPort.create(tags, userId);

        // firstly remove to make sure re-added tags during edit will be saved
        updatedTags.stream()
            .filter(Tag::removed)
            .forEach(tag -> dslContext.delete(TAG_MOMENT_RELATION)
                .where(TAG_MOMENT_RELATION.MOMENT_ID.eq(planId).and(TAG_MOMENT_RELATION.TAG_ID.eq(tag.id())))
                .execute());

        updatedTags.stream()
            .filter(t -> !t.removed())
            .forEach(tag -> dslContext.insertInto(
                    TAG_MOMENT_RELATION,
                    TAG_MOMENT_RELATION.MOMENT_ID,
                    TAG_MOMENT_RELATION.TAG_ID
                )
                .values(planId, tag.id())
                .onConflictDoNothing()
                .execute());
    }
}
