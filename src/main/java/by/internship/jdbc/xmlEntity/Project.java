package by.internship.jdbc.xmlEntity;

import by.internship.jdbc.model.EmployeeProject;
import by.internship.jdbc.model.ProjectDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

    private UUID id;
    private String name;
    private String description;
    private ProjectDomain domain;

    @XmlTransient
    private List<EmployeeProject> employeeProjects;
}

