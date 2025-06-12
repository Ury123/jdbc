package by.internship.jdbc.exception;

public class JdbcOperationException extends RuntimeException {

    public JdbcOperationException(final String message) {
        super(message);
    }

    public JdbcOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
