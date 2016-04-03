package ua.lifebook.db.repository;

import java.lang.reflect.Method;

public class Field {
    private final String name;
    private final int type;
    private final Method getter;
    private final Method setter;

    public Field(String name, int type, Method getter, Method setter) {
        this.name = name;
        this.type = type;
        this.getter = getter;
        this.setter = setter;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }
}
