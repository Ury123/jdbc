package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.EmployeeProjectDao;
import by.internship.jdbc.exception.DaoException;
import by.internship.jdbc.model.db.EmployeeProject;
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
public class EmployeeProjectDaoImpl implements EmployeeProjectDao {

    @Value("${sql.employeeProject.save}")
    private String saveEmployeeProjectQuery;

    @Value("${sql.employeeProject.findById}")
    private String findByIdEmployeeProjectQuery;

    @Value("${sql.employeeProject.findAll}")
    private String findAllEmployeeProjectQuery;

    @Value("${sql.employeeProject.delete}")
    private String deleteEmployeeProjectQuery;

    private final DataSource dataSource;

    public EmployeeProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(EmployeeProject employeeProject) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(saveEmployeeProjectQuery)) {

            ps.setObject(1, employeeProject.getId());
            ps.setObject(2, employeeProject.getStartDate());
            ps.setObject(3, employeeProject.getEndDate());
            ps.setObject(4, employeeProject.getEmployee().getId());
            ps.setObject(5, employeeProject.getProject().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("Не удалось сохранить проект сотрудника с id {}", employeeProject.getId());
            throw new DaoException("Не удалось сохранить проект сотрудника с id "+ employeeProject.getId(), e);
        }
    }

    @Override
    public Optional<EmployeeProject> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findByIdEmployeeProjectQuery)) {

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
            log.error("Не удалось найти проект сотрудника с id {}", id);
            throw new DaoException("Не удалось найти проект сотрудника с id "+ id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeProject> findAll() {

        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findAllEmployeeProjectQuery)) {

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
            log.error("Не удалось найти проекты сотрудников");
            throw new DaoException("Не удалось найти проекты сотрудников", e);
        }

        return employeeProjects;
    }

    @Override
    public void delete(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(deleteEmployeeProjectQuery)) {
            ps.setObject(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("Не удалось удалить проект сотрудника с id {}", id);
            throw new DaoException("Не удалось удалить проект сотрудника с id "+ id, e);
        }
    }
}
