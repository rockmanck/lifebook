package ua.lifebook.plans;


public enum RepeatType {
    ;
    private final String code;
    private final String name;

    RepeatType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
