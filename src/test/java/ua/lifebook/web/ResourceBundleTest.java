package ua.lifebook.web;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.Test;
import pp.ua.lifebook.user.parameters.Language;
import ua.lifebook.i18n.EncodingControl;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceBundleTest {
    @Test
    void checkCyrillic() {
        final String signIn = "signIn";
        assertEquals("Войти", getBundle(Language.RU).getString(signIn), "Check sign in text in russian");
        assertEquals("Увійти", getBundle(Language.UA).getString(signIn), "Check sign in text in ukrainian");
        assertEquals("Sign in", getBundle(Language.EN).getString(signIn), "Check sign in text in english");
    }

    private ResourceBundle getBundle(Language language) {
        return ResourceBundle.getBundle("MessagesBundle", language.getLocale(), new EncodingControl(Charsets.UTF_8));
    }
}