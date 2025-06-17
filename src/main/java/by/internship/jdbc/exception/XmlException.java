package by.internship.jdbc.exception;

public class XmlException extends RuntimeException {

    public XmlException(final String message) {
        super(message);
    }

    public XmlException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
