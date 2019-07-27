package pp.ua.lifebook.user.parameters;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Language {
    EN("en", new Locale("en", "US")),
    UA("ua", new Locale("ukr", "UA")),
    RU("ru", new Locale("rus", "RU"))
    ;

    private final String code;
    private final Locale locale;
    private static final Map<String, Language> byCode = new HashMap<>();
    static {
        for (Language language : Language.values()) {
            byCode.put(language.code, language);
        }
    }

    Language(String code, Locale locale) {
        this.code = code;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Language byCode(String code) {
        return byCode.get(code.toLowerCase());
    }
}
