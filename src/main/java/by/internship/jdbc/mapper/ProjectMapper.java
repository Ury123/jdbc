package by.internship.jdbc.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    by.internship.jdbc.model.db.Project toDb(by.internship.jdbc.model.xml.Project project);
    by.internship.jdbc.model.xml.Project toXml(by.internship.jdbc.model.db.Project project);

}
