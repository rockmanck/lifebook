package ua.lifebook.plans;

import ua.lifebook.db.sync.Support;

@Support("PlanStatus")
public enum PlanStatus {
    SCHEDULED("SCHDL", "Scheduled"), DONE("DN", "Done"), CANCELED("CNCL", "Canceled");

    private final String code;
    private final String name;

    PlanStatus(String code, String name) {
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
