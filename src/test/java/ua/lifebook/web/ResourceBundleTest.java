package ua.lifebook.web;

import com.google.common.base.Charsets;
import org.junit.Assert;
import org.junit.Test;
import pp.ua.lifebook.user.parameters.Language;
import ua.lifebook.i18n.EncodingControl;

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
        return ResourceBundle.getBundle("MessagesBundle", language.getLocale(), new EncodingControl(Charsets.UTF_8));
    }
}