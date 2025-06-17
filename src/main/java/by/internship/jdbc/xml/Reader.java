package by.internship.jdbc.xml;

public interface Reader {

    <T> T readFromXml (Class<T> clazz, String filePath);

}
