package by.internship.jdbc.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProject {

    private UUID id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Employee employee;

    private Project project;
}
