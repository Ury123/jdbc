package by.internship.jdbc.xml;

public interface Writer {

    <T> void writeToXml(T object, Class<T> clazz, String filePath);

}
