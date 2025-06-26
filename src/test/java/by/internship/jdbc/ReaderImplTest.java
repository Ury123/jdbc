package by.internship.jdbc;

import by.internship.jdbc.exception.XmlOperationException;
import by.internship.jdbc.model.xml.wrapper.ProjectsXmlWrapper;
import by.internship.jdbc.xml.Impl.ReaderImpl;
import by.internship.jdbc.xml.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderImplTest {

    private static final String XML_PATH = "xml/valid.xml";
    private static final String INVALID_XML_PATH = "xml/invalid.xml";
    private static final String XSD_PATH = "xml/valid.xsd";
    private static final String MISSING_XSD_PATH = "xml/missing.xsd";

    private Reader reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    void test_readFromXml_shouldReturnResponse() {

        ProjectsXmlWrapper result = reader.readFromXml(ProjectsXmlWrapper.class, XML_PATH, XSD_PATH);

        assertNotNull(result);
        assertFalse(result.getProjects().isEmpty());

    }

    @Test
    void test_readFromXml_shouldThrowXmlOperationExceptionForInvalidXml() {

        assertThrows(XmlOperationException.class,
                () -> reader.readFromXml(ProjectsXmlWrapper.class, INVALID_XML_PATH, XSD_PATH));
    }

    @Test
    void test_readFromXml_shouldThrowXmlOperationExceptionWhenXsdMissing() {

        assertThrows(XmlOperationException.class,
                () -> reader.readFromXml(ProjectsXmlWrapper.class, XML_PATH, MISSING_XSD_PATH));
    }

}
