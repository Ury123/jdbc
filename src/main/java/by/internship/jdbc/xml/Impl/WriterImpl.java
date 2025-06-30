package by.internship.jdbc.xml.Impl;

import by.internship.jdbc.xml.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
@Component
public class WriterImpl implements Writer {

    private static final String WRITER_ERR_MSG = "Failed to write xml to file";

    @Override
    public <T> void writeToXml(T object, Class<T> clazz, String filePath) {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(object, new File(filePath));

        } catch (JAXBException e) {
            log.error(WRITER_ERR_MSG, e);
            throw new RuntimeException(WRITER_ERR_MSG, e);
        }
    }
}
