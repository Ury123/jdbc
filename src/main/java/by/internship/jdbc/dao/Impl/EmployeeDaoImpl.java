package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeDao;
import by.internship.jdbc.model.db.Employee;
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

@Repository
@PropertySource("classpath:sql.properties")
public class EmployeeDaoImpl implements EmployeeDao {

    @Value("${sql.employee.save}")
    private String scripForSave;

    @Value("${sql.employee.findById}")
    private String scriptForFindById;

    @Value("${sql.employee.findAll}")
    private String scriptForFindAll;

    @Value("${sql.employee.delete}")
    private String scriptForDelete;

    private final DataSource dataSource;

    public EmployeeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Employee employee) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(scripForSave)) {

            ps.setObject(1, employee.getId());
            ps.setObject(2, employee.getFio());
            ps.setObject(3, employee.getEmail());
            ps.setObject(4, employee.getStartDate());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindById)) {

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
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {

        List<Employee> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindAll);
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
            throw new RuntimeException(e);
        }

        return employees;
    }

    @Override
    public void delete(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForDelete)) {

            ps.setObject(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
