package pp.ua.lifebook.moments;

import java.time.LocalDate;
import java.util.List;

public interface MomentStorage {

    void save(Moment moment);

    List<Moment> getByDate(int userId, LocalDate start, LocalDate end);
}
