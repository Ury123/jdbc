package by.internship.jdbc.service.db;

import by.internship.jdbc.model.db.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    public void save(Project project);
    public Project getProjectById(UUID id);
    public List<Project> getAllProjects();
    public void delete(UUID id);

}
