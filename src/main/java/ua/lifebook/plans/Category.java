package ua.lifebook.plans;

import ua.lifebook.db.repository.Identifiable;
import ua.lifebook.db.repository.Table;

@Table("Category")
public class Category extends Identifiable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
