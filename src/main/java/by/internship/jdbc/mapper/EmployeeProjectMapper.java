package by.internship.jdbc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeProjectMapper {

    @Mapping(source = "projectId", target = "project.id")
    by.internship.jdbc.model.db.EmployeeProject toDb(by.internship.jdbc.model.xml.EmployeeProject employeeProject);

    @Mapping(source = "project.id", target = "projectId")
    by.internship.jdbc.model.xml.EmployeeProject toXml(by.internship.jdbc.model.db.EmployeeProject employeeProject);

}
