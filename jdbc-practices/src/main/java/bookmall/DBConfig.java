package bookmall;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = DBConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("읽기 실패" + e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}

