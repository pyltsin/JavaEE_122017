package ru.otus.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.otus.model.Employee;
import ru.otus.model.Employees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends TypeAdapter<Employees> {
    @Override
    public void write(JsonWriter jsonWriter, Employees employees) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employees read(JsonReader jsonReader) throws IOException {
        Employees employees = new Employees();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("employees")) {
                employees.setEmployees(readStringArray(jsonReader));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return employees;
    }

    public List<Employee> readStringArray(JsonReader reader) throws IOException {
        List<Employee> employeeList = new ArrayList<>();
        reader.beginObject();
        reader.nextName();
        reader.beginArray();
        while (reader.hasNext()) {
            Employee employee = new Employee();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "id":
                        long id = reader.nextLong();
                        employee.setId(id);
                        break;
                    case "name":
                        employee.setName(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            if (employee.getId() % 2 == 1) {
                employeeList.add(employee);
            }
            reader.endObject();
        }
        reader.endArray();
        reader.endObject();
        return employeeList;
    }
}
