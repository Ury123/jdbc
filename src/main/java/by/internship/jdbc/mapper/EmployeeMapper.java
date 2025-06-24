package by.internship.jdbc.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeProjectMapper.class})
public interface EmployeeMapper {

    by.internship.jdbc.model.db.Employee toDb(by.internship.jdbc.model.xml.Employee employee);
    by.internship.jdbc.model.xml.Employee toXml(by.internship.jdbc.model.db.Employee employee);

}
