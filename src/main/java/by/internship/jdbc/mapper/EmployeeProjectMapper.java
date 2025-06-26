package by.internship.jdbc.mapper;

import by.internship.jdbc.model.db.EmployeeProject;
import by.internship.jdbc.model.xml.XmlEmployeeProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeProjectMapper {

    @Mapping(source = "projectId", target = "project.id")
    EmployeeProject toDb(XmlEmployeeProject employeeProject);

    @Mapping(source = "project.id", target = "projectId")
    XmlEmployeeProject toXml(EmployeeProject employeeProject);

}
