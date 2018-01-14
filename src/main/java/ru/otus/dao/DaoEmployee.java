package ru.otus.dao;

import ru.otus.exception.BisunessException;
import ru.otus.model.Employee;
import ru.otus.model.Position;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEmployee extends AbstractDaoCrud<Employee> {
    public DaoEmployee(DataSource ds) {
        super(ds);
    }

    @Override
    public Employee get(long id) {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return rowMap(resultSet);
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }

    private Employee rowMap(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setLogin(resultSet.getString("login"));
        employee.setEmail(resultSet.getString("email"));
        employee.setCityId(resultSet.getLong("city_id"));
        employee.setDepartmentId(resultSet.getLong("department_id"));
        employee.setName(resultSet.getString("name"));
        employee.setSalary(resultSet.getLong("salary"));
        employee.setPosition(Position.valueOf(resultSet.getInt("position_id")));
        employee.setPassword(resultSet.getString("password"));
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE ORDER BY ID DESC");
            return rowsMap(resultSet);
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }

    private List<Employee> rowsMap(ResultSet resultSet) throws SQLException {
        List<Employee> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(rowMap(resultSet));
        }
        return list;
    }

    @Override
    public void update(Employee entity) {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE EMPLOYEE SET " +
                            "LOGIN = ?, " +
                            "PASSWORD = ?, " +
                            "NAME = ?, " +
                            "DEPARTMENT_ID = ?, " +
                            "POSITION_ID = ?, " +
                            "CITY_ID = ?, " +
                            "EMAIL = ?," +
                            "SALARY = ? " +
                            "WHERE ID= ?");
            populateStatement(entity, preparedStatement);
            preparedStatement.setLong(9, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }

    private void populateStatement(Employee entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getLogin());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getName());
        preparedStatement.setLong(4, entity.getDepartmentId());
        preparedStatement.setInt(5, entity.getPosition().ordinal());
        preparedStatement.setLong(6, entity.getCityId());
        preparedStatement.setString(7, entity.getEmail());
        preparedStatement.setLong(8, entity.getSalary());
    }

    @Override
    public void insert(Employee entity) {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO EMPLOYEE" +
                            "(LOGIN, PASSWORD, NAME, DEPARTMENT_ID, POSITION_ID, CITY_ID, EMAIL," +
                            " SALARY) VALUES (?, ?,?,?,?,?,?,?)");
            populateStatement(entity, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }

    @Override
    public void delete(long id) {
        DataSource ds = getDs();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EMPLOYEE WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BisunessException(e);
        }
    }
}
