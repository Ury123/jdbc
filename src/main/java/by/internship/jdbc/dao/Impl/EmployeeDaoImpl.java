package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeDao;
import by.internship.jdbc.exception.JdbcOperationException;
import by.internship.jdbc.model.db.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@PropertySource("classpath:sql.properties")
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String FAILED_TO_SAVE_EMPLOYEE = "Failed to save employee with id ";
    private static final String FAILED_TO_FIND_EMPLOYEE_BY_ID = "Failed to retrieve employee with id ";
    private static final String FAILED_TO_FIND_ALL_EMPLOYEES = "Failed to retrieve all employees";
    private static final String FAILED_TO_DELETE_EMPLOYEE = "Failed to delete employee with id ";

    @Value("${sql.employee.save}")
    private String saveEmployeeQuery;

    @Value("${sql.employee.findById}")
    private String findByIdEmployeeQuery;

    @Value("${sql.employee.findAll}")
    private String findAllEmployeeQuery;

    @Value("${sql.employee.delete}")
    private String deleteEmployeeQuery;

    private final DataSource dataSource;

    public EmployeeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Employee employee) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(saveEmployeeQuery)) {

            ps.setObject(1, employee.getId());
            ps.setObject(2, employee.getFio());
            ps.setObject(3, employee.getEmail());
            ps.setObject(4, employee.getStartDate());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(FAILED_TO_SAVE_EMPLOYEE + employee.getId());
            throw new JdbcOperationException(FAILED_TO_SAVE_EMPLOYEE + employee.getId(), e);
        }
    }

    @Override
    public Optional<Employee> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findByIdEmployeeQuery)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setFio(rs.getString("fio"));
                    employee.setEmail(rs.getString("email"));
                    employee.setStartDate(rs.getDate("start_date").toLocalDate());
                    employee.setEmployeeProjects(new ArrayList<>());
                    return Optional.of(employee);
                }
            }

        } catch (SQLException e) {
            log.error(FAILED_TO_FIND_EMPLOYEE_BY_ID + id);
            throw new JdbcOperationException(FAILED_TO_FIND_EMPLOYEE_BY_ID + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {

        List<Employee> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findAllEmployeeQuery);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(UUID.fromString(rs.getString("id")));
                employee.setFio(rs.getString("fio"));
                employee.setEmail(rs.getString("email"));
                employee.setStartDate(rs.getDate("start_date").toLocalDate());
                employee.setEmployeeProjects(new ArrayList<>());
                employees.add(employee);
            }

        } catch (SQLException e) {
            log.error(FAILED_TO_FIND_ALL_EMPLOYEES);
            throw new JdbcOperationException(FAILED_TO_FIND_ALL_EMPLOYEES, e);
        }

        return employees;
    }

    @Override
    public void delete(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(deleteEmployeeQuery)) {

            ps.setObject(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(FAILED_TO_DELETE_EMPLOYEE + id);
            throw new JdbcOperationException(FAILED_TO_DELETE_EMPLOYEE + id, e);
        }
    }
}
