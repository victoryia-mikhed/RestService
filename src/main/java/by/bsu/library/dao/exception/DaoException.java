package by.bsu.library.dao.exception;

public class DaoException extends Exception{

    public DaoException() {
    }

    public DaoException(String message, Throwable exception) {
        super(message, exception);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable exception) {
        super(exception);
    }
}
