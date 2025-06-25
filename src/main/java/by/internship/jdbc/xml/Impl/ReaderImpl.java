package by.internship.jdbc.xml.Impl;

import by.internship.jdbc.exception.XmlOperationException;
import by.internship.jdbc.xml.Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;

@Slf4j
@Component
public class ReaderImpl implements Reader {

    private static final String READER_ERR_MSG = "Failed to read XML file";
    private static final String XSD_ERR_MSG = "XSD Validation Error: ";
    private static final String LOAD_XSD_ERR_MSG = "Failed to load XSD schema:";

    @Override
    public <T> T readFromXml (Class<T> clazz, String filePath,  String xsdPath) {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            InputStream xsdStream = getClass().getClassLoader().getResourceAsStream(xsdPath);

            Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));

            unmarshaller.setSchema(schema);

            unmarshaller.setEventHandler(event -> {
                log.error(XSD_ERR_MSG + event.getMessage());
                return false;
            });

            InputStream xmlStream = getClass().getClassLoader().getResourceAsStream(filePath);

            return (T) unmarshaller.unmarshal(xmlStream);

        } catch (JAXBException e) {
            log.error(READER_ERR_MSG, e);
            throw new XmlOperationException(READER_ERR_MSG, e);
        } catch (SAXException e) {
            log.error(LOAD_XSD_ERR_MSG + xsdPath, e);
            throw new XmlOperationException(LOAD_XSD_ERR_MSG, e);
        }
    }
}
