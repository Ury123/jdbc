package by.internship.jdbc.dao.Impl;

import by.internship.jdbc.dao.ProjectDao;
import by.internship.jdbc.model.ProjectDomain;
import by.internship.jdbc.model.db.Project;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectDaoImpl implements ProjectDao {

    private final DataSource dataSource;

    public ProjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Project project) throws SQLException {
        String sql = "INSERT INTO project (id, name, description, domain) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, project.getId());
            ps.setObject(2, project.getName());
            ps.setObject(3, project.getDescription());
            ps.setObject(4, project.getDomain());

            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Project> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM project WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

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
        }

        return Optional.empty();
    }

    @Override
    public List<Project> findAll() throws SQLException {
        String sql = "SELECT * FROM project";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

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
        }

        return projects;
    }

    @Override
    public void delete(UUID id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM project WHERE id = ?")) {

            ps.setObject(1, id);
            ps.executeUpdate();
        }
    }
}
