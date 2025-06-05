package by.internship.jdbc.model.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeProject {

    @XmlElement(name = "id")
    private UUID id;

    @XmlElement(name = "start_date")
    private Date startDate;

    @XmlElement(name = "end_date")
    private Date endDate;

    @XmlElement(name = "project")
    private Project project;

    @XmlTransient
    private Employee employee;
}

