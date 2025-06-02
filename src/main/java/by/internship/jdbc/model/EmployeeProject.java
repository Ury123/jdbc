package by.internship.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProject {

    private UUID id;

    private Date startDate;

    private Date endDate;

    private Employee employee;

    private Project project;
}
