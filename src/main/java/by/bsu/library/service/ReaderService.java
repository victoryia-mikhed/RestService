package by.bsu.library.service;

import by.bsu.library.entity.Reader;
import by.bsu.library.service.exception.ServiceException;

public interface ReaderService extends Service<Reader> {

    Reader getReaderByName(String name) throws ServiceException;

    Reader getReaderByNamePassword(String name, String password) throws ServiceException;

}
