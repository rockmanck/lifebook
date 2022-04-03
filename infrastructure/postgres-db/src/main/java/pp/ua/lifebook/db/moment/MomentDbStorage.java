package pp.ua.lifebook.db.moment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import pp.ua.lifebook.db.sqlbuilder.DynamicSqlBuilder;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.moments.MomentStorage;
import pp.ua.lifebook.utils.DateUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MomentDbStorage implements MomentStorage {
    private static final String INSERT_ONE_SQL = "INSERT INTO moments(date, description, user_id) VALUES(?, ?, ?)";
    private static final String UPDATE_ONE_SQL = "UPDATE moments SET date = ?, description = ? WHERE id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT id, date, description, user_id FROM moments WHERE id = ?";
    private static final MomentMapper MAPPER = new MomentMapper();

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();
    private final JdbcTemplate jdbc;

    public MomentDbStorage(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Moment moment) {
        final Date date = DateUtils.localDateToDate(moment.getDate());
        if (moment.getId() == null) {
            jdbc.update(INSERT_ONE_SQL, date, moment.getDescription(), moment.getUserId());
        } else {
            jdbc.update(UPDATE_ONE_SQL, date, moment.getDescription(), moment.getId());
        }
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
                final Moment moment = MAPPER.mapRow(rs, 0);
                result.add(moment);
            });
        } catch (EmptyResultDataAccessException e) {
            log.debug("No moments found for {} user between {} and {} dates", userId, start, end);
        }
        return result;
    }

    @Override
    public Moment getById(int id) {
        try {
            return jdbc.queryForObject(FIND_BY_ID_SQL, MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return Moment.builder().createMoment();
        }
    }
}
