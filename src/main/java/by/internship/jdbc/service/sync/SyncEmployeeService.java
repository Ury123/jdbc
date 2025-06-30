package by.internship.jdbc.service.sync;

import by.internship.jdbc.mapper.EmployeeMapper;
import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.xml.XmlEmployee;
import by.internship.jdbc.service.db.EmployeeService;
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
public class SyncEmployeeService {

    private static final String XML_EMPLOYEE_PATH = "data/employees.xml";
    private static final String XSD_EMPLOYEE_PATH = "schemas/employees.xsd";

    private final EmployeeService employeeService;
    private final XmlService xmlService;
    private final EmployeeMapper employeeMapper;

    public void syncEmployees(LocalDateTime lastSync) {

        List<XmlEmployee> xmlEmployees = xmlService.readEmployees(XML_EMPLOYEE_PATH, XSD_EMPLOYEE_PATH);
        List<Employee> dbEmployees = employeeService.getAllEmployees();

        Map<UUID, XmlEmployee> xmlMap = xmlEmployees.stream().collect(Collectors.toMap(XmlEmployee::getId, e -> e));
        Map<UUID, Employee> dbMap = dbEmployees.stream().collect(Collectors.toMap(Employee::getId, e -> e));

        Iterator<XmlEmployee> xmlIterator = xmlEmployees.iterator();
        while (xmlIterator.hasNext()) {
            XmlEmployee xmlEmployee = xmlIterator.next();
            Employee dbEmployee = dbMap.get(xmlEmployee.getId());
            if (dbEmployee == null) {
                if (xmlEmployee.getCreatedAt().isBefore(lastSync)) {
                    xmlIterator.remove();
                    continue;
                }
                employeeService.save(employeeMapper.toDb(xmlEmployee));
            } else if (!Objects.equals(xmlEmployee.getUpdatedAt(), dbEmployee.getUpdatedAt())) {
                employeeService.save(employeeMapper.toDb(xmlEmployee));
            }
        }

        for (Employee dbEmployee : dbEmployees) {
            XmlEmployee xmlEmployee = xmlMap.get(dbEmployee.getId());
            if (xmlEmployee == null) {
                if (dbEmployee.getCreatedAt().isBefore(lastSync)) {
                    employeeService.delete(dbEmployee.getId());
                    continue;
                }
                xmlEmployees.add(employeeMapper.toXml(dbEmployee));
            }
        }

        xmlService.writeEmployees(XML_EMPLOYEE_PATH, xmlEmployees);

    }

}
