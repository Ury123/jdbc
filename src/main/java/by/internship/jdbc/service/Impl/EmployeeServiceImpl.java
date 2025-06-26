package by.internship.jdbc.service.Impl;

import by.internship.jdbc.dao.EmployeeDao;
import by.internship.jdbc.exception.EntityNotFoundException;
import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String SAVE_EMPLOYEE = "Saving employee with id ";
    private static final String GET_EMPLOYEE_BY_ID = "Retrieving employee by id ";
    private static final String EMPLOYEE_NOT_FOUND_ERR_MSG = "Employee not found with id ";
    private static final String GET_ALL_EMPLOYEES = "Retrieving all employees";
    private static final String DELETE_EMPLOYEE = "Deleting employee with id ";

    private final EmployeeDao employeeDao;

    @Override
    public void save(Employee employee) {
        log.info(SAVE_EMPLOYEE + employee.getId());
        employeeDao.save(employee);
    }

    @Override
    public Employee getEmployeeById(UUID id) {
        log.info(GET_EMPLOYEE_BY_ID + id);
        return employeeDao.findById(id).orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND_ERR_MSG + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info(GET_ALL_EMPLOYEES);
        return employeeDao.findAll();
    }

    @Override
    public void delete(UUID id) {
        log.info(DELETE_EMPLOYEE + id);
        employeeDao.delete(id);
    }
}
