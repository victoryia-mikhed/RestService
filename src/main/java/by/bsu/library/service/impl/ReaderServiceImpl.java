package by.bsu.library.service.impl;

import by.bsu.library.dao.ReaderDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Reader;
import by.bsu.library.service.ReaderService;
import by.bsu.library.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

    private static Logger LOG = Logger.getLogger(ReaderServiceImpl.class);
    private static String NULL_ERROR_MESSAGE = "Parameter 'reader' is null";

    @Autowired
    private ReaderDao readerDao;

    public void setReaderDao(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }


    @Override
    public Long insert(Reader object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            if (readerDao.getReaderByNamePassword(object.getName(), object.getPassword())!=null) {
                return null;
            }
            return readerDao.insert(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'insert'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Reader object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            return readerDao.update(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'update'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return readerDao.delete(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'delete'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Reader getElementById(Long id) throws ServiceException {
        try {
            return readerDao.getElementById(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getElementById'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Reader getReaderByName(String name) throws ServiceException {
        try {
            return readerDao.getReaderByName(name);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getReaderByName'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Reader getReaderByNamePassword(String name, String password) throws ServiceException {
        try {
            return readerDao.getReaderByNamePassword(name, password);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getReaderByNamePassword'", e);
            throw new ServiceException(e);
        }
    }
}
