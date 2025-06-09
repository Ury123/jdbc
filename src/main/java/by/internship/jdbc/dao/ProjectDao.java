package by.internship.jdbc.dao;

import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.db.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDao {

    public void save(Project project) throws SQLException;
    public Optional<Project> findById(UUID id) throws SQLException;
    public List<Project> findAll() throws SQLException;
    public void delete(UUID id) throws SQLException;

}
