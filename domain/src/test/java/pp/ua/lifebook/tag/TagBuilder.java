package pp.ua.lifebook.tag;

public class TagBuilder {
    private static int id = 1;

    public static Tag aTag(int userId, String name) {
        return new Tag(id++, userId, name);
    }

    public static Tag aNewTag(int userId, String name) {
        return new Tag(null, userId, name, true, false);
    }
}
