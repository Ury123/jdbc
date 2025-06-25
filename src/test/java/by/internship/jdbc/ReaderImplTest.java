package by.internship.jdbc;

import by.internship.jdbc.exception.XmlOperationException;
import by.internship.jdbc.model.xml.wrapper.ProjectsXmlWrapper;
import by.internship.jdbc.xml.Impl.ReaderImpl;
import by.internship.jdbc.xml.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderImplTest {

    private Reader reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    void shouldReadValidXmlSuccessfully() {

        String xmlPath = "xml/valid.xml";
        String xsdPath = "xml/valid.xsd";

        ProjectsXmlWrapper result = reader.readFromXml(ProjectsXmlWrapper.class, xmlPath, xsdPath);

        assertNotNull(result);
        assertFalse(result.getProjects().isEmpty());

    }

    @Test
    void shouldThrowXmlOperationExceptionForInvalidXml() {

        String xmlPath = "xml/invalid.xml";
        String xsdPath = "xml/valid.xsd";

        assertThrows(XmlOperationException.class,
                () -> reader.readFromXml(ProjectsXmlWrapper.class, xmlPath, xsdPath));
    }

    @Test
    void shouldThrowXmlOperationExceptionWhenXsdMissing() {

        String xmlPath = "xml/valid.xml";
        String xsdPath = "xml/missing.xsd";

        assertThrows(XmlOperationException.class,
                () -> reader.readFromXml(ProjectsXmlWrapper.class, xmlPath, xsdPath));
    }

}
