package by.internship.jdbc.xml.Impl;

import by.internship.jdbc.exception.XmlOperationException;
import by.internship.jdbc.xml.Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Slf4j
@Component
public class ReaderImpl implements Reader {

    private static final String READER_ERR_MSG = "Failed to read XML file";

    @Override
    public <T> T readFromXml (Class<T> clazz, String filePath) {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new File(filePath));

        } catch (JAXBException e) {
            log.error(READER_ERR_MSG, e);
            throw new XmlOperationException(READER_ERR_MSG, e);
        }
    }
}
