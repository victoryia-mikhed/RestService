package by.bsu.library.dao;


import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Reader;


public interface ReaderDao extends Dao<Reader> {

    Reader getReaderByName(String name) throws DaoException;

    Reader getReaderByNamePassword(String name, String password) throws DaoException;

}