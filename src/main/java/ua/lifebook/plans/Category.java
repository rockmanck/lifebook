package ua.lifebook.plans;

import ua.lifebook.db.Identifiable;

public class Category extends Identifiable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
