package by.internship.jdbc.service;

import by.internship.jdbc.model.xml.Employee;
import by.internship.jdbc.model.xml.Project;

import java.util.List;

public interface XmlService {

    List<Employee> readEmployees (String xmlPath, String xsdPath);
    List<Project> readProjects (String xmlPath, String xsdPath);

    void writeEmployees (String xmlPath, List<Employee> employees);
    void writeProjects (String xmlPath, List<Project> projects);

}
