package pp.ua.lifebook.web.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class EncodingControl extends ResourceBundle.Control {
    private final Charset encoding;

    public EncodingControl(Charset encoding) {
        this.encoding = encoding;
    }

    public Charset getEncoding() {
        return encoding;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        InputStream stream = getInputStream(loader, reload, resourceName);
        if (stream != null) {
            try (stream) {
                return new PropertyResourceBundle(new InputStreamReader(stream, encoding));
            }
        }
        return null;
    }

    private InputStream getInputStream(ClassLoader loader, boolean reload, String resourceName) throws IOException {
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        return stream;
    }
}
