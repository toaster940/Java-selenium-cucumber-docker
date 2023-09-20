package DataProviders;

import Enums.DriverType;
import Enums.EnvironmentType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigFileReader {

    private final Properties properties;

    public ConfigFileReader() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        String propertyFilePath = "config/configuration.properties";

        try {
            fileReader = new FileReader(propertyFilePath);
            bufferedReader = new BufferedReader(fileReader);
            properties = new Properties();

            try {
                properties.load(bufferedReader);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getUrl() {
        String url = properties.getProperty("url");

        // Simply If...Else
        if (url != null)
            return url;
        else
            throw new RuntimeException("url not specified in the config file.");
    }

    public long getTime() {
        String timeout = properties.getProperty("timeout");

        // Common If...Else
        if (timeout != null) {
            return Long.parseLong(timeout);
        } else {
            throw new RuntimeException("timeout not specified in the config file.");
        }
    }

    public DriverType getBrowser() {
        String browserName;
        if (System.getProperty("browser") == null) {
            browserName = properties.getProperty("browser");
        } else {
            browserName = System.getProperty("browser");
        }
        switch (browserName) {
            case "chrome":
                return DriverType.CHROME;
            case "firefox":
                return DriverType.FIREFOX;
            case "edge":
                return DriverType.EDGE;
            case "safari":
                return DriverType.SAFARI;
            default:
                throw new RuntimeException(
                        "Browser name key value in configuration file is not matched: " + browserName);
        }
    }

    public EnvironmentType getEnvironment() {
        String environmentName;
        if (System.getProperty("env") == null) {
            environmentName = properties.getProperty("environment");
        } else {
            environmentName = System.getProperty("env");
        }

        switch (environmentName) {
            case "local":
                return EnvironmentType.LOCAL;
            case "remote":
                return EnvironmentType.REMOTE;
            default:
                throw new RuntimeException(
                        "Environment type key value in configuration file is not matched: " + environmentName);
        }
    }

    public String[] getCredentials() {

        String[] credential = { properties.getProperty("email"), properties.getProperty("password") };
        return credential;
    }

    public String getExcelPath() {

        return properties.getProperty("TestDataExcelPath");
    }

    public void setPropertyFileValue(String key, String value) {

        // Specify the path of the existing .properties file
        String propertyFilePath = "config/configuration.properties";

        try (InputStream inputStream = new FileInputStream(propertyFilePath)) {
            Properties properties = new Properties();

            // Load the existing properties from the file
            properties.load(inputStream);

            // Add or modify key-value pairs
            properties.setProperty(key, value);

            // Save the updated properties back to the file
            try (OutputStream outputStream = new FileOutputStream(propertyFilePath)) {
                properties.store(outputStream, "Updated Properties File");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
