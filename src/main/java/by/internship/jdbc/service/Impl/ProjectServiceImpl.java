package by.internship.jdbc.service.Impl;

import by.internship.jdbc.dao.ProjectDao;
import by.internship.jdbc.exception.EntityNotFoundException;
import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.db.Project;
import by.internship.jdbc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private static final String SAVE_PROJECT = "Saving project with id ";
    private static final String GET_PROJECT_BY_ID = "Retrieving project by id ";
    private static final String PROJECT_NOT_FOUND_ERR_MSG = "Project not found with id ";
    private static final String GET_ALL_PROJECTS = "Retrieving all projects";
    private static final String DELETE_PROJECT = "Deleting project with id ";

    private final ProjectDao projectDao;

    @Override
    public void save(Project project) {
        log.info(SAVE_PROJECT + project.getId());
        projectDao.save(project);
    }

    @Override
    public Project getProjectById(UUID id) {
        log.info(GET_PROJECT_BY_ID + id);
        return projectDao.findById(id).orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND_ERR_MSG + id));
    }

    @Override
    public List<Project> getAllProjects() {
        log.info(GET_ALL_PROJECTS);
        return projectDao.findAll();
    }

    @Override
    public void delete(UUID id) {
        log.info(DELETE_PROJECT + id);
        projectDao.delete(id);
    }
}
