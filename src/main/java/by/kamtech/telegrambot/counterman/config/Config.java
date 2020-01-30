package by.kamtech.telegrambot.counterman.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Config {

    private Properties appProperties = new Properties();

    public Config() {
    }

    public Config(String configFile) throws IOException {
        try {
            try (InputStream in = new FileInputStream(configFile)) {
                appProperties.load(in);
            }

        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException("Configuration file is not a valid XML document", e);
        }
    }

    public boolean hasKey(ConfigKey key) {
        return appProperties.containsKey(key.getKey());
    }

    public String getString(ConfigKey key) {
        return appProperties.getProperty(key.getKey());
    }

    public int getInteger(ConfigKey key) {
        return hasKey(key) ? Integer.parseInt(getString(key)) : 0;
    }

    public boolean getBoolean(ConfigKey key) {
        return Boolean.parseBoolean(getString(key));
    }

}
