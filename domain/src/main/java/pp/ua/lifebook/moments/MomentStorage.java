package pp.ua.lifebook.moments;

import java.time.LocalDate;
import java.util.List;

public interface MomentStorage {

    void save(Moment moment);

    List<Moment> getByDateRange(int userId, LocalDate start, LocalDate end);

    Moment getById(int id);
}
