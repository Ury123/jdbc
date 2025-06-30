package by.internship.jdbc.service.db;

import by.internship.jdbc.model.db.EmployeeProject;

import java.util.List;
import java.util.UUID;

public interface EmployeeProjectService {

    public void save(EmployeeProject employeeProject);
    public EmployeeProject getEmployeeProjectById(UUID id);
    public List<EmployeeProject> getAllEmployeeProjects();
    public void delete(UUID id);

}
