package by.bsu.library.service;

import by.bsu.library.service.exception.ServiceException;

public interface Service<T> {

    Long insert(T object) throws ServiceException;

    boolean update(T object) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    T getElementById(Long id) throws ServiceException;
}
