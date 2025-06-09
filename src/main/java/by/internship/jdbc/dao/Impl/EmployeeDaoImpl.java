package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeDao;
import by.internship.jdbc.model.db.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EmployeeDaoImpl implements EmployeeDao {

    private final DataSource dataSource;

    public EmployeeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        String sql = "insert into employee (id, fio, email, start_date) values(?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, employee.getId());
            ps.setObject(2, employee.getFio());
            ps.setObject(3, employee.getEmail());
            ps.setObject(4, employee.getStartDate());

            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Employee> findById(UUID id) throws SQLException {
        String sql = "select * from employee where id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
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
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        String sql = "select * from employee";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(UUID.fromString(rs.getString("id")));
                    employee.setFio(rs.getString("fio"));
                    employee.setEmail(rs.getString("email"));
                    employee.setStartDate(rs.getDate("start_date").toLocalDate());
                    employee.setEmployeeProjects(new ArrayList<>());
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    @Override
    public void delete(UUID id) throws SQLException {
        String sql = "delete from employee where id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        }
    }
}
