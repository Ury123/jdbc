package by.internship.jdbc.dao;

import by.internship.jdbc.model.db.EmployeeProject;
import by.internship.jdbc.model.db.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeProjectDao {

    public void save(EmployeeProject employeeProject);
    public Optional<EmployeeProject> findById(UUID id);
    public List<EmployeeProject> findAll();
    public void delete(UUID id);

}
