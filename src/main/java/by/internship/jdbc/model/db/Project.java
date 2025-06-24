package by.internship.jdbc.model.db;

import by.internship.jdbc.model.ProjectDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<EmployeeProject> employeeProjects;
}
