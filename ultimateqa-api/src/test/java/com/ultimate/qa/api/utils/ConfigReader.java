package com.ultimate.qa.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties configFile = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            configFile.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyName) {
        return configFile.getProperty(keyName);
    }
}
