package ua.lifebook.db.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private final JdbcTemplate jdbc;
    private final Map<Class<?>, Entity> resolvedEntitiesCache = new HashMap<>();

    public Repository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Object obj) {
        checkEntity(obj);
        final Class<?> clazz = obj.getClass();
        if (!resolvedEntitiesCache.containsKey(clazz)) {

        }
        final Entity entity = resolvedEntitiesCache.get(clazz);
        final List<Field> fields = entity.getFields();
        if (((Identifiable) obj).getId() != null) {
            update(obj, fields);
        } else {
            insert(obj, fields);
        }
    }

    public <T> T load(int id, Class<T> clazz) {
        return null;
    }

    private void insert(Object obj, List<Field> fields) {

    }

    private void update(Object obj, List<Field> fields) {

    }

    private void checkEntity(Object obj) {
        if (!(obj instanceof Identifiable)) throw new IllegalArgumentException("Repository could process only Identifiable objects");
    }
}
