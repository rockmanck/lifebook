package pp.ua.lifebook.tag;

import java.util.Objects;

public record Tag(
    Integer id,
    int userId,
    String name,
    boolean newTag,
    boolean removed
) {
    public Tag(Integer id, int userId, String name) {
        this(id, userId, name, false, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
