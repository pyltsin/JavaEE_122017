package ru.otus.controller;

import ru.otus.dao.DaoCrud;
import ru.otus.dao.DaoEmployee;
import ru.otus.dao.DaoReference;
import ru.otus.exception.BisunessException;
import ru.otus.model.City;
import ru.otus.model.Department;
import ru.otus.model.Employee;
import ru.otus.model.Position;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@WebServlet("/JDBCServlet")
public class JDBCServlet extends HttpServlet {

    private DaoCrud<Employee> daoEmployee;
    private DaoCrud<City> daoCity;
    private DaoCrud<Department> daoDepartment;

    @Resource(name = "jdbc/OracleDS")
    private DataSource ds;

    @Override
    public void init() {
        daoEmployee = new DaoEmployee(ds);
        daoCity = new DaoReference<>(ds, "CITY", resultSet -> {
            City city = null;
            try {
                resultSet.next();
                city = new City();
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return city;
        });

        daoDepartment = new DaoReference<>(ds,"DEPARTMENT", resultSet -> {
            Department department = null;
            try {
                resultSet.next();
                department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return department;
        });
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("All rows  \n");
        List<Employee> employeeList = daoEmployee.getAll();
        employeeList.forEach(employee -> {
            stringBuilder.append("\nEmployee: ").append(employee.toString());
            stringBuilder.append("\nPosition: ").append(employee.getPosition().toString());
            stringBuilder.append("\nDepartment: ").append(daoDepartment.get(employee.getDepartmentId()).toString());
            stringBuilder.append("\nCity: ").append(daoCity.get(employee.getCityId()).toString());
            stringBuilder.append("\n");
        });

        stringBuilder.append("\n");
        stringBuilder.append("update:  \n");

        Employee employee1 = employeeList.get(1);
        employee1.setName("TEST \n");
        employee1.setPosition(Position.CHIEF);
        daoEmployee.update(employee1);

        Employee out = daoEmployee.get(employee1.getId());
        stringBuilder.append(out.toString());
        stringBuilder.append("\n");
        stringBuilder.append("delete:  \n");
        daoEmployee.delete(employee1.getId());
        stringBuilder.append(daoEmployee.getAll().size());

        stringBuilder.append("\n");
        stringBuilder.append("procedure:  \n");
        try (Connection connection = ds.getConnection()) {
            CallableStatement callableStatement =
                    connection.prepareCall("CALL TESTOUT(?)");
            callableStatement.registerOutParameter(1, Types.NVARCHAR);
            callableStatement.executeUpdate();
            String dt = callableStatement.getString(1);
            stringBuilder.append(dt).append("\n");
        } catch (SQLException e) {
            throw new BisunessException(e);
        }

        writer.append(stringBuilder.toString());

    }
}
