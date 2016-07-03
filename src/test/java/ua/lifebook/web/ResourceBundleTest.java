package ua.lifebook.web;

import org.junit.Assert;
import org.junit.Test;
import ua.lifebook.users.Language;

import java.util.ResourceBundle;

public class ResourceBundleTest {
    @Test
    public void checkCyrillic() {
        final String signIn = "signIn";
        Assert.assertEquals("Check sign in text in russian", "Войти", getBundle(Language.RU).getString(signIn));
        Assert.assertEquals("Check sign in text in ukrainian", "Увійти", getBundle(Language.UA).getString(signIn));
        Assert.assertEquals("Check sign in text in english", "Sign in", getBundle(Language.EN).getString(signIn));
    }

    private ResourceBundle getBundle(Language language) {
        return ResourceBundle.getBundle("MessagesBundle", language.getLocale(), language.getEncodingControl());
    }
}