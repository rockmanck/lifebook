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
import java.util.List;

public class MomentDbStorage extends JdbcTemplate implements MomentStorage {
    private static final String INSERT_ONE_SQL = "INSERT INTO moments(date, description, user_id) VALUES(?, ?, ?)";
    private static final String UPDATE_ONE_SQL = "UPDATE moments SET date = ?, description = ? WHERE id = ?";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DynamicSqlBuilder sqlBuilder = new DynamicSqlBuilder();

    public MomentDbStorage(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void save(Moment moment) {
        if (moment.getId() == null) {
            update(INSERT_ONE_SQL, moment.getDate(), moment.getDescription(), moment.getUserId());
        } else {
            update(UPDATE_ONE_SQL,  moment.getDate(), moment.getDescription(), moment.getId());
        }
    }

    @Override
    public List<Moment> getByDate(int userId, LocalDate start, LocalDate end) {
        final List<Moment> result = new ArrayList<>();
        final String sql = sqlBuilder.sql("GetMoments")
            .param("startDate", DateUtils.format(start))
            .param("endDate", DateUtils.format(end))
            .build();
        try {
            query(sql, rs -> {
                final Moment moment = new Moment();
                moment.setId(rs.getInt("id"));
                moment.setDate(DateUtils.dateToLocalDate(rs.getDate("date")));
                moment.setDescription(rs.getString("description"));
                moment.setUserId(rs.getInt("user_id"));
                result.add(moment);
            });
        } catch (EmptyResultDataAccessException e) {
            log.debug("No moments found for {} user between {} and {} dates", userId, start, end);
        }
        return result;
    }
}
