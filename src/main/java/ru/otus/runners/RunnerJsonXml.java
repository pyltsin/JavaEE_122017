package ru.otus.runners;

import com.google.gson.Gson;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.otus.dao.DaoEmployee;
import ru.otus.model.Employee;
import ru.otus.model.Employees;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

public class RunnerJsonXml {
    public static void main(String[] args) throws SQLException, JAXBException, ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        //part1
        String pathname = "C://java_my/file.xml";
        File file = new File(pathname);
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("javaee");
        dataSource.setPassword("javaee");
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");

        DaoEmployee daoEmployee = new DaoEmployee(dataSource);
        List<Employee> employeeAll = daoEmployee.getAll();
        Employees employees = new Employees();
        employees.setEmployees(employeeAll);

        JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(employees, file);
        marshaller.marshal(employees, System.out);
        //part2
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(pathname);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("//employee/salary/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        int summ = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            String value = nodes.item(i).getNodeValue();
            summ += Long.parseLong(value);
            System.out.println(value);
        }
        double avg = summ / nodes.getLength();
        System.out.println("AVG:" + avg);
        XPathExpression exprAvg = xpath.compile("//employee[salary>" + avg + "]/login/text()");
        result = exprAvg.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            String value = nodes.item(i).getNodeValue();
            System.out.println("Login:" + value);
        }
        //part3
        JSONObject jsonObject = XML.toJSONObject(FileUtils.readFileToString(file, Charset.forName("UTF-8")));
        String json = jsonObject.toString(4);
        String pathJson = "C://java_my/file.json";
        FileUtils.writeStringToFile(new File(pathJson),
                json, "UTF-8");

        //part4
        Gson gson = new Gson();
        Employees employeesFromJson = gson.fromJson(new FileReader(pathJson), Employees.class);
        employeesFromJson.getEmployees().forEach(employee -> System.out.println(employee.getId() + ' ' + employee.getName()));
    }
}
