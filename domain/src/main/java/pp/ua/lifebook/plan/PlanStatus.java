package pp.ua.lifebook.plan;

import ck.rockman.utils.CollectionUtils;

import java.util.Map;

public enum PlanStatus {
    SCHEDULED("SCHDL", "Scheduled"),
    DONE("DN", "Done"),
    CANCELED("CNCL", "Canceled");

    private static final Map<String, PlanStatus> byCode = CollectionUtils.arrayToMap(PlanStatus.values(), PlanStatus::getCode);

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

    public static PlanStatus byCode(String code) {
        return byCode.get(code);
    }
}
