package by.internship.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private UUID id;

    private String name;

    private String description;

    private ProjectDomain domain;

    private List<EmployeeProject> employeeProjects;
}
