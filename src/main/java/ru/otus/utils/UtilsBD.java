package ru.otus.utils;

import ru.otus.exception.BisunessException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class UtilsBD {
    public static void populateDB(Connection connection, Path path) throws IOException, SQLException {
        Statement statement = connection.createStatement();
        Files.lines(path, Charset.forName("cp1251")).forEach(s -> {
            try {
                statement.addBatch(s);
            } catch (SQLException e) {
                throw new BisunessException(e);
            }
        });
        statement.executeBatch();
    }

    public static Connection getSimpleConnection(Properties properties) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection((String) properties.get("url"), (String) properties.get("user"),
                (String) properties.get("password"));
    }
}
