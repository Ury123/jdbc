package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeProjectDao;
import by.internship.jdbc.model.db.EmployeeProject;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${sql.employeeProject.save}")
    private String scripForSave;

    @Value("${sql.employeeProject.findById}")
    private String scriptForFindById;

    @Value("${sql.employeeProject.findAll}")
    private String scriptForFindAll;

    @Value("${sql.employeeProject.delete}")
    private String scriptForDelete;

    private final DataSource dataSource;

    public EmployeeProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(EmployeeProject employeeProject) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scripForSave)) {

            ps.setObject(1, employeeProject.getId());
            ps.setObject(2, employeeProject.getStartDate());
            ps.setObject(3, employeeProject.getEndDate());
            ps.setObject(4, employeeProject.getEmployee().getId());
            ps.setObject(5, employeeProject.getProject().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EmployeeProject> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindById)) {

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeProject> findAll() {

        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindAll)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeProject employeeProject = new EmployeeProject();
                    employeeProject.setId(UUID.fromString(rs.getString("id")));
                    employeeProject.setStartDate(rs.getDate("start_date").toLocalDate());
                    employeeProject.setEndDate(rs.getDate("end_date").toLocalDate());

                    employeeProjects.add(employeeProject);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeProjects;
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
