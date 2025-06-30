package by.internship.jdbc.service.sync;

import by.internship.jdbc.mapper.ProjectMapper;
import by.internship.jdbc.model.db.Project;
import by.internship.jdbc.model.xml.XmlProject;
import by.internship.jdbc.service.db.ProjectService;
import by.internship.jdbc.service.xml.XmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncProjectService {

    private static final String XML_PROJECT_PATH = "data/projects.xml";
    private static final String XSD_PROJECT_PATH = "schemas/projects.xsd";

    private final ProjectService projectService;
    private final XmlService xmlService;
    private final ProjectMapper projectMapper;

    public void syncProjects(LocalDateTime lastSync) {
        List<XmlProject> xmlProjects = xmlService.readProjects(XML_PROJECT_PATH, XSD_PROJECT_PATH);
        List<Project> dbProjects = projectService.getAllProjects();

        Map<UUID, XmlProject> xmlMap = xmlProjects.stream().collect(Collectors.toMap(XmlProject::getId, p -> p));
        Map<UUID, Project> dbMap = dbProjects.stream().collect(Collectors.toMap(Project::getId, p -> p));

        Iterator<XmlProject> xmlIterator = xmlProjects.iterator();
        while (xmlIterator.hasNext()) {
            XmlProject xmlProject = xmlIterator.next();
            Project dbProject = dbMap.get(xmlProject.getId());
            if (dbProject == null) {
                if (xmlProject.getCreatedAt().isBefore(lastSync)) {
                    xmlIterator.remove();
                    continue;
                }
                projectService.save(projectMapper.toDb(xmlProject));
            } else if (!Objects.equals(xmlProject.getUpdatedAt(), dbProject.getUpdatedAt())) {
                projectService.save(projectMapper.toDb(xmlProject));
            }
        }

        for (Project dbProject : dbProjects) {
            XmlProject xmlProject = xmlMap.get(dbProject.getId());
            if (xmlProject == null) {
                if (dbProject.getCreatedAt().isBefore(lastSync)) {
                    projectService.delete(dbProject.getId());
                    continue;
                }
                xmlProjects.add(projectMapper.toXml(dbProject));
            }
        }

        xmlService.writeProjects(XML_PROJECT_PATH, xmlProjects);
    }

}
