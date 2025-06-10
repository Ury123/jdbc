package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.ProjectDao;
import by.internship.jdbc.model.ProjectDomain;
import by.internship.jdbc.model.db.Project;
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
public class ProjectDaoImpl implements ProjectDao {

    @Value("${sql.project.save}")
    private String scripForSave;

    @Value("${sql.project.findById}")
    private String scriptForFindById;

    @Value("${sql.project.findAll}")
    private String scriptForFindAll;

    @Value("${sql.project.delete}")
    private String scriptForDelete;

    private final DataSource dataSource;

    public ProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Project project) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scripForSave)) {

            ps.setObject(1, project.getId());
            ps.setObject(2, project.getName());
            ps.setObject(3, project.getDescription());
            ps.setObject(4, project.getDomain());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> findById(UUID id) {

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindById)) {

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
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {

        List<Project> projects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(scriptForFindAll)) {

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
            throw new RuntimeException(e);
        }

        return projects;
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
