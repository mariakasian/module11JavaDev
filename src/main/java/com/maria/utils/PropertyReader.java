package com.maria.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class PropertyReader {

    public static String getConnectionUrl() {
        return Objects.requireNonNull(getProperties())
                .map(properties -> properties.getProperty("hibernate.connection.url")).orElse(null);
    }

    private static Optional<Properties> getProperties() {
        try (InputStream input = PropertyReader.class.getClassLoader()
                .getResourceAsStream("hibernate.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return Optional.empty();
            }

            prop.load(input);

            return Optional.of(prop);
        } catch (IOException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
