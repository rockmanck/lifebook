package pp.ua.lifebook.db.moment;

import org.springframework.jdbc.core.RowMapper;
import pp.ua.lifebook.moments.Moment;
import pp.ua.lifebook.utils.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MomentMapper implements RowMapper<Moment> {
    @Override
    public Moment mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Moment moment = Moment.builder().createMoment();
        moment.setId(rs.getInt("id"));
        moment.setDate(DateUtils.dateToLocalDate(rs.getDate("date")));
        moment.setDescription(rs.getString("description"));
        moment.setUserId(rs.getInt("user_id"));
        return moment;
    }
}
