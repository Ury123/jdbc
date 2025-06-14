package by.internship.jdbc.model.xml;

import by.internship.jdbc.model.db.EmployeeProject;
import by.internship.jdbc.model.ProjectDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

    @XmlElement(name = "id")
    private UUID id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "domain")
    private ProjectDomain domain;

    @XmlTransient
    private List<EmployeeProject> employeeProjects;
}

