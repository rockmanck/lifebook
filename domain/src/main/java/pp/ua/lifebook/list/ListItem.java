package pp.ua.lifebook.list;

public class ListItem {
    private Integer id;
    private Integer listId;
    private String name;
    private String comment;
    private Boolean completed;

    public ListItem() {
    }

    public ListItem(Integer id, Integer listId, String name, String comment, Boolean completed) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.comment = comment;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public ListItem setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getListId() {
        return listId;
    }

    public ListItem setListId(Integer listId) {
        this.listId = listId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ListItem setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public ListItem setCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }
}
