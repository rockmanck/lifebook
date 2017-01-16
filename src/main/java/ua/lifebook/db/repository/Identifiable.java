package ua.lifebook.db.repository;

public abstract class Identifiable {
    @Serial("Id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
