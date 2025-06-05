package by.internship.jdbc.model.xml;

import by.internship.jdbc.model.adapter.LocalDateAdapter;
import by.internship.jdbc.model.db.EmployeeProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

    @XmlElement(name = "id")
    private UUID id;

    @XmlElement(name = "fio")
    private String fio;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "start_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElementWrapper(name = "employee_projects")
    @XmlElement(name = "employee_project")
    private List<EmployeeProject> employeeProjects;
}

