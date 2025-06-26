package by.internship.jdbc.mapper;

import by.internship.jdbc.model.db.Project;
import by.internship.jdbc.model.xml.XmlProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toDb(XmlProject project);
    XmlProject toXml(Project project);

}
