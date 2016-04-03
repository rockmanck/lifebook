package ua.lifebook.plans;


import ck.rockman.utils.CollectionUtils;

import java.util.Map;

public enum RepeatType {
    ;

    private static final Map<String, RepeatType> byCode = CollectionUtils.arrayToMap(RepeatType.values(), RepeatType::getCode);
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

    public static RepeatType byCode(String code) {
        return byCode.get(code);
    }
}
