package by.bsu.library.service.exception;


public class ServiceException extends Exception {

    public ServiceException(Exception e, String message) {
        super(message, e);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }
}
