package by.internship.jdbc.dao;

import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.db.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDao {

    public void save(Project project);
    public Optional<Project> findById(UUID id);
    public List<Project> findAll();
    public void delete(UUID id);

}
