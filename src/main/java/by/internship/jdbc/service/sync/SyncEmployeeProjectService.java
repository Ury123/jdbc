package by.internship.jdbc.service.sync;

import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.db.EmployeeProject;
import by.internship.jdbc.service.db.EmployeeProjectService;
import by.internship.jdbc.service.db.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncEmployeeProjectService {

    private final EmployeeService employeeService;
    private final EmployeeProjectService employeeProjectService;

    public void syncEmployeeProject(LocalDateTime lastSync) {
        List<Employee> dbEmployees = employeeService.getAllEmployees();
        List<EmployeeProject> dbLinks = employeeProjectService.getAllEmployeeProjects();

        List<EmployeeProject> xmlLinks = dbEmployees.stream()
                .flatMap(e -> Optional.ofNullable(e.getEmployeeProjects()).orElse(List.of()).stream())
                .toList();

        Map<UUID, EmployeeProject> xmlMap = xmlLinks.stream().collect(Collectors.toMap(EmployeeProject::getId, ep -> ep));
        Map<UUID, EmployeeProject> dbMap = dbLinks.stream().collect(Collectors.toMap(EmployeeProject::getId, ep -> ep));

        for (EmployeeProject xml : dbLinks) {
            EmployeeProject db = xmlMap.get(xml.getId());
            if (db == null) {
                if (xml.getCreatedAt().isBefore(lastSync)) continue;
                employeeProjectService.save(xml);
            } else if (!Objects.equals(xml.getUpdatedAt(), db.getUpdatedAt())) {
                employeeProjectService.save(xml);
            }
        }

        for (EmployeeProject db : dbLinks) {
            if (!xmlMap.containsKey(db.getId())) {
                if (db.getCreatedAt().isBefore(lastSync)) {
                    employeeProjectService.delete(db.getId());
                }
            }
        }
    }

}
