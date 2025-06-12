package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.ProjectDao;
import by.internship.jdbc.exception.JdbcOperationException;
import by.internship.jdbc.model.ProjectDomain;
import by.internship.jdbc.model.db.Project;
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
public class ProjectDaoImpl implements ProjectDao {

    private static final String FAILED_TO_SAVE_PROJECT = "Failed to save project with id ";
    private static final String FAILED_TO_FIND_PROJECT_BY_ID = "Failed to retrieve project with id ";
    private static final String FAILED_TO_FIND_ALL_PROJECTS = "Failed to retrieve all projects";
    private static final String FAILED_TO_DELETE_PROJECT = "Failed to delete project with id ";

    @Value("${sql.project.save}")
    private String saveProjectQuery;

    @Value("${sql.project.findById}")
    private String findByIdProjectQuery;

    @Value("${sql.project.findAll}")
    private String findAllProjectQuery;

    @Value("${sql.project.delete}")
    private String deleteProjectQuery;

    private final DataSource dataSource;

    public ProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Project project) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(saveProjectQuery)) {

            ps.setObject(1, project.getId());
            ps.setObject(2, project.getName());
            ps.setObject(3, project.getDescription());
            ps.setObject(4, project.getDomain());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(FAILED_TO_SAVE_PROJECT + project.getId());
            throw new JdbcOperationException(FAILED_TO_SAVE_PROJECT + project.getId(), e);
        }
    }

    @Override
    public Optional<Project> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findByIdProjectQuery)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Project project = new Project();
                    project.setId(id);
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setDomain(ProjectDomain.valueOf(rs.getString("domain")));
                    project.setEmployeeProjects(new ArrayList<>());

                    return Optional.of(project);
                }
            }

        } catch (SQLException e) {
            log.error(FAILED_TO_FIND_PROJECT_BY_ID + id);
            throw new JdbcOperationException(FAILED_TO_FIND_PROJECT_BY_ID + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {

        List<Project> projects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(findAllProjectQuery)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project();
                    project.setId(UUID.fromString(rs.getString("id")));
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setDomain(ProjectDomain.valueOf(rs.getString("domain")));
                    project.setEmployeeProjects(new ArrayList<>());
                    projects.add(project);
                }
            }

        } catch (SQLException e) {
            log.error(FAILED_TO_FIND_ALL_PROJECTS);
            throw new JdbcOperationException(FAILED_TO_FIND_ALL_PROJECTS, e);
        }

        return projects;
    }

    @Override
    public void delete(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(deleteProjectQuery)) {

            ps.setObject(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(FAILED_TO_DELETE_PROJECT + id);
            throw new JdbcOperationException(FAILED_TO_DELETE_PROJECT + id, e);
        }
    }
}
