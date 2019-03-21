package ua.lifebook.users.parameters;

import com.google.common.base.Charsets;
import ua.lifebook.i18n.EncodingControl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public enum Language {
    EN("en", new Locale("en", "US"), new EncodingControl(Charsets.UTF_8)),
    UA("ua", new Locale("ukr", "UA"), new EncodingControl(Charsets.UTF_8)),
    RU("ru", new Locale("rus", "RU"), new EncodingControl(Charsets.UTF_8))
    ;

    private final String code;
    private final Locale locale;
    private final ResourceBundle.Control encodingControl;
    private static final Map<String, Language> byCode = new HashMap<>();
    static {
        for (Language language : Language.values()) {
            byCode.put(language.code, language);
        }
    }

    Language(String code, Locale locale, ResourceBundle.Control control) {
        this.code = code;
        this.locale = locale;
        this.encodingControl = control;
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle.Control getEncodingControl() {
        return encodingControl;
    }

    public static Language byCode(String code) {
        return byCode.get(code.toLowerCase());
    }
}
