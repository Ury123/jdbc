package by.internship.jdbc.exception;

public class XmlOperationException extends RuntimeException {

    public XmlOperationException(final String message) {
        super(message);
    }

    public XmlOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
