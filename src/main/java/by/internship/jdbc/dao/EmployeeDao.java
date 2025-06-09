package by.internship.jdbc.dao;

import by.internship.jdbc.model.db.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeDao {

    public void save(Employee employee) throws SQLException;
    public Optional<Employee> findById(UUID id) throws SQLException;
    public List<Employee> findAll() throws SQLException;
    public void delete(UUID id) throws SQLException;

}
