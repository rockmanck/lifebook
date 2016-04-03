package ua.lifebook.moments;

import ua.lifebook.db.repository.Serial;
import ua.lifebook.db.repository.Table;

import java.time.LocalDate;

@Table("Moments")
public class Moments {
    @Serial private Integer id;
    private LocalDate date;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
