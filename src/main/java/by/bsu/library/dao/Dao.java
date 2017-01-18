package by.bsu.library.dao;

import by.bsu.library.dao.exception.DaoException;

public interface Dao<T> {

    Long insert(T object) throws DaoException;

    boolean update(T object) throws DaoException;

    boolean delete(Long id) throws DaoException;

    T getElementById(Long id) throws DaoException;

}