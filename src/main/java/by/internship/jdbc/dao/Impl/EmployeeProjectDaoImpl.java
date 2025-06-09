package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeProjectDao;
import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.db.EmployeeProject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EmployeeProjectDaoImpl implements EmployeeProjectDao {

    private final DataSource dataSource;

    public EmployeeProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(EmployeeProject employeeProject) throws SQLException {
        String sql = "INSERT INTO employee_project (id, start_date, end_date, employee_id, project_id) VALUES (?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, employeeProject.getId());
            ps.setObject(2, employeeProject.getStartDate());
            ps.setObject(3, employeeProject.getEndDate());
            ps.setObject(4, employeeProject.getEmployee().getId());
            ps.setObject(5, employeeProject.getProject().getId());

            ps.executeUpdate();
        }
    }

    @Override
    public Optional<EmployeeProject> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM employee_project WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EmployeeProject employeeProject = new EmployeeProject();
                    employeeProject.setId(id);
                    employeeProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    employeeProject.setEndDate(rs.getDate("end_date").toLocalDate());

                    return Optional.of(employeeProject);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeProject> findAll() throws SQLException {
        String sql = "SELECT * FROM employee_project";
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeProject employeeProject = new EmployeeProject();
                    employeeProject.setId(UUID.fromString(rs.getString("id")));
                    employeeProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    employeeProject.setEndDate(rs.getDate("end_date").toLocalDate());

                    employeeProjects.add(employeeProject);
                }
            }
        }

        return employeeProjects;
    }

    @Override
    public void delete(UUID id) throws SQLException {
        String sql = "DELETE FROM employee_project WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        }
    }
}
