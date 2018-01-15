package ru.otus.runners;

import ru.otus.exception.BisunessException;
import ru.otus.utils.UtilsBD;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RunnerPopulateBD {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {
        Properties properties = new Properties();
        properties.load(RunnerPopulateBD.class.getResourceAsStream("/bd/bd.properties"));
        Connection connection = UtilsBD.getSimpleConnection(properties);
        List<String> listPath = Arrays.asList("/bd/deleteAll.txt","/bd/populateDBCity.txt", "/bd/populateDBDepartment.txt", "/bd/populateDBPosition.txt", "/bd/populateDBEmployee.txt");
        listPath.forEach(s -> {
            File file = new File(RunnerPopulateBD.class.getResource(s).getFile());
            try {
                UtilsBD.populateDB(connection, file.toPath());
            } catch (IOException | SQLException e) {
                throw new BisunessException(e);
            }
        });
        connection.close();
    }
}
