package by.internship.jdbc.service.db;

import by.internship.jdbc.model.db.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    public void save(Employee employee);
    public Employee getEmployeeById(UUID id);
    public List<Employee> getAllEmployees();
    public void delete(UUID id);

}
