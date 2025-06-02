package by.internship.jdbc.xmlEntity;

import by.internship.jdbc.model.EmployeeProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

    private UUID id;
    private String fio;
    private String email;

    @XmlElement(name = "start_date")
    private Date startDate;

    @XmlElementWrapper(name = "employee_projects")
    @XmlElement(name = "employee_project")
    private List<EmployeeProject> employeeProjects;
}

