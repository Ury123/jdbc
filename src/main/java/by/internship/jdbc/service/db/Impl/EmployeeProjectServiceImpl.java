package by.internship.jdbc.service.db.Impl;

import by.internship.jdbc.dao.EmployeeProjectDao;
import by.internship.jdbc.exception.EntityNotFoundException;
import by.internship.jdbc.model.db.EmployeeProject;
import by.internship.jdbc.service.db.EmployeeProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    private static final String SAVE_EMPLOYEE_PROJECT = "Saving employeeProject with id ";
    private static final String GET_EMPLOYEE_PROJECT_BY_ID = "Retrieving employeeProject by id ";
    private static final String EMPLOYEE_PROJECT_NOT_FOUND_ERR_MSG = "EmployeeProject not found with id ";
    private static final String GET_ALL_EMPLOYEE_PROJECTS = "Retrieving all employeeProjects";
    private static final String DELETE_EMPLOYEE_PROJECT = "Deleting employeeProject with id ";

    private final EmployeeProjectDao employeeProjectDao;

    @Override
    public void save(EmployeeProject employeeProject) {
        log.info(SAVE_EMPLOYEE_PROJECT + employeeProject.getId());
        employeeProjectDao.save(employeeProject);
    }

    @Override
    public EmployeeProject getEmployeeProjectById(UUID id) {
        log.info(GET_EMPLOYEE_PROJECT_BY_ID + id);
        return employeeProjectDao.findById(id).orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_PROJECT_NOT_FOUND_ERR_MSG + id));
    }

    @Override
    public List<EmployeeProject> getAllEmployeeProjects() {
        log.info(GET_ALL_EMPLOYEE_PROJECTS);
        return employeeProjectDao.findAll();
    }

    @Override
    public void delete(UUID id) {
        log.info(DELETE_EMPLOYEE_PROJECT + id);
        employeeProjectDao.delete(id);
    }
}
