package by.internship.jdbc.service;

import by.internship.jdbc.model.xml.XmlEmployee;
import by.internship.jdbc.model.xml.XmlProject;

import java.util.List;

public interface XmlService {

    List<XmlEmployee> readEmployees (String xmlPath, String xsdPath);
    List<XmlProject> readProjects (String xmlPath, String xsdPath);

    void writeEmployees (String xmlPath, List<XmlEmployee> employees);
    void writeProjects (String xmlPath, List<XmlProject> projects);

}
