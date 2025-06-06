package by.internship.jdbc.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private UUID id;

    private String fio;

    private String email;

    private Date startDate;

    private List<EmployeeProject> employeeProjects;
}
