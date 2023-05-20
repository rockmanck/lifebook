package pp.ua.lifebook.list;

public class Lists {
    private Integer id;
    private Integer userId;
    private String name;
    private Boolean deleted;

    public Lists() {
    }

    public Lists(Integer id, Integer userId, String name, Boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public Lists setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Lists setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lists setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Lists setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
