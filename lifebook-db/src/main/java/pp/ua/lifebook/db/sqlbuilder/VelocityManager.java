package pp.ua.lifebook.db.sqlbuilder;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

class VelocityManager {
    private VelocityEngine velocityEngine;

    VelocityManager() {
        Properties p = new Properties();
        p.setProperty("resource.loader", "classpath");
        p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        // This is for thread safety
        p.put("file.resource.loader.cache", "true");
        p.put("file.resource.loader.modificationCheckInterval", "-1");
        p.put("velocimacro.library.autoreload", "false");

        p.put("locale", "en_US");
        p.put("velocimacro.permissions.allow.inline.local.scope", "true");

        velocityEngine = new VelocityEngine();
        try {
            velocityEngine.init(p);
        } catch (Exception e) {
            throw new VelocityInitializationException(e);
        }
    }

    String renderTemplate(String template, Map<String, Object> context) {
        StringWriter wr = new StringWriter();
        try {
            Template vmTemplate = velocityEngine.getTemplate(template);
            vmTemplate.merge(new VelocityContext(context), wr);
        } catch (Exception e) {
            throw new TemplateRenderingException(e);
        }
        return wr.toString();
    }

    private static class VelocityInitializationException extends RuntimeException {
        VelocityInitializationException(Exception e) {
            super(e);
        }
    }

    private static class TemplateRenderingException extends RuntimeException {
        TemplateRenderingException(Exception e) {
            super(e);
        }
    }
}
