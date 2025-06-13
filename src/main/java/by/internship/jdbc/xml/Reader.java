package by.internship.jdbc.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Reader {

    public static <T> T readFromXml (Class<T> clazz, String filePath) throws Exception {

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new File(filePath));

    }
}
