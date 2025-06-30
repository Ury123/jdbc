package by.internship.jdbc.service.xml.Impl;

import by.internship.jdbc.model.xml.XmlEmployee;
import by.internship.jdbc.model.xml.XmlProject;
import by.internship.jdbc.model.xml.wrapper.EmployeesXmlWrapper;
import by.internship.jdbc.model.xml.wrapper.ProjectsXmlWrapper;
import by.internship.jdbc.service.xml.XmlService;
import by.internship.jdbc.xml.Reader;
import by.internship.jdbc.xml.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class XmlServiceImpl implements XmlService {

    private final Reader reader;
    private final Writer writer;

    @Override
    public List<XmlEmployee> readEmployees(String xmlPath, String xsdPath) {

        EmployeesXmlWrapper wrapper = reader.readFromXml(EmployeesXmlWrapper.class, xmlPath, xsdPath);
        return wrapper.getEmployees();

    }

    @Override
    public List<XmlProject> readProjects(String xmlPath, String xsdPath) {

        ProjectsXmlWrapper wrapper = reader.readFromXml(ProjectsXmlWrapper.class, xmlPath, xsdPath);
        return wrapper.getProjects();

    }

    @Override
    public void writeEmployees(String xmlPath, List<XmlEmployee> employees) {

        EmployeesXmlWrapper wrapper = new EmployeesXmlWrapper(employees);
        writer.writeToXml(wrapper, EmployeesXmlWrapper.class, xmlPath);

    }

    @Override
    public void writeProjects(String xmlPath, List<XmlProject> projects) {

        ProjectsXmlWrapper wrapper = new ProjectsXmlWrapper(projects);
        writer.writeToXml(wrapper, ProjectsXmlWrapper.class, xmlPath);

    }
}
