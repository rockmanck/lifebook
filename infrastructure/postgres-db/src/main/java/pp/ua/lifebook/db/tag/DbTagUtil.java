package pp.ua.lifebook.db.tag;

import org.json.JSONArray;
import pp.ua.lifebook.tag.Tag;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class DbTagUtil {
    public static List<Tag> toTags(Array array, int userId) throws SQLException {
        if (array == null) {
            return List.of();
        }

        return Stream.of((String[]) array.getArray())
            .map(e -> {
                JSONArray values = new JSONArray(e);
                return new Tag(values.getInt(0), userId, values.getString(1));
            })
            .toList();
    }
}
