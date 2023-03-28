package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.file.TestDataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {

    private static Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();

    public static Connection getInstance() {
        if (connection == null) {
            TestDataManager data = new TestDataManager();
            try {
                connection = DriverManager.getConnection(data.getProperty("/url"), data.getProperty("/username"),
                        data.getProperty("/password"));
            } catch (SQLException e) {
                LOGGER.fatal("Connector instance was not initialized");
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.fatal("Connector was not closed");
            throw new RuntimeException(e);
        }
    }
}
