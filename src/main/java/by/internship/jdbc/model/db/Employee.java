package by.internship.jdbc.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDate startDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<EmployeeProject> employeeProjects;
}
