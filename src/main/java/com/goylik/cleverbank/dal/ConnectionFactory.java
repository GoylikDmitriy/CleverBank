package com.goylik.cleverbank.dal;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A factory class for creating database connections.
 */
public class ConnectionFactory {
    private static final String FILE_PROPERTIES_NAME = "db/postgresql/db.properties";
    private static final String URL_ATTRIBUTE_NAME = "db.url";
    private static final String DRIVER_ATTRIBUTE_NAME = "db.driver";

    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static String driverName;

    static {
        try {
            URL fileURL = ConnectionFactory.class.getClassLoader().getResource(FILE_PROPERTIES_NAME);
            if (fileURL == null) {
                throw new ExceptionInInitializerError("File can't be found for the specified name  = " + FILE_PROPERTIES_NAME);
            }

            properties.load(new FileReader(fileURL.toURI().getPath()));
            driverName = (String) properties.get(DRIVER_ATTRIBUTE_NAME);
            Class.forName(driverName);
        } catch (URISyntaxException e) {
            throw new ExceptionInInitializerError("URI syntax exception.");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver can't be set. Driver = " + driverName);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("File can't be read. File = " + driverName);
        }

        DATABASE_URL = (String) properties.get(URL_ATTRIBUTE_NAME);
        if (DATABASE_URL == null) {
            throw new ExceptionInInitializerError("Missing database url in the file = " + FILE_PROPERTIES_NAME);
        }
    }

    /**
     * Private constructor to prevent direct instantiation of the factory.
     */
    private ConnectionFactory() {

    }

    /**
     * Creates a new database connection using the properties configuration.
     *
     * @return A database connection.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(DATABASE_URL, properties));
    }
}
