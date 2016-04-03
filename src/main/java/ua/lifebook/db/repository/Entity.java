package ua.lifebook.db.repository;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final List<Field> fields = new ArrayList<>();

    public void addField(Field field){
        fields.add(field);
    }

    public List<Field> getFields() {
        return fields;
    }
}
