package ua.lifebook.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ConfigurationHolder {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationHolder.class);
    public static final Path EXTERNAL_CONFIG_PATH = Paths.get("/etc/lifebook");
    public static final String APP_NAME_PARAM = "app.name";

    private final Config config = loadConfig();

    private Config loadConfig() {
        final Config defaultConfig = ConfigFactory.load();
        return tryLoadOverrides().withFallback(defaultConfig).resolve();
    }

    private Config tryLoadOverrides() {
        final Path configPath = getConfigFilePath();

        logger.info("Loading configuration from {}", configPath.toAbsolutePath().toString());

        return ConfigFactory.parseFileAnySyntax(configPath.toFile(), ConfigParseOptions.defaults().setAllowMissing(true));
    }

    /**
     * Returns path for config file (without extension)
     */
    public Path getConfigFilePath() {
        String appName;
        try {
            appName = System.getProperty(APP_NAME_PARAM, ConfigFactory.load().getString(APP_NAME_PARAM));
        } catch (ConfigException.Missing e) {
            logger.warn("Can't resolve " + APP_NAME_PARAM + " property");
            appName = "default";
        }

        return EXTERNAL_CONFIG_PATH.resolve(appName);
    }


    public Config getConfig() {
        return config;
    }
}
