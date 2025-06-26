package by.internship.jdbc.mapper;

import by.internship.jdbc.model.db.Employee;
import by.internship.jdbc.model.xml.XmlEmployee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeProjectMapper.class})
public interface EmployeeMapper {

    Employee toDb(XmlEmployee employee);
    XmlEmployee toXml(Employee employee);

}
