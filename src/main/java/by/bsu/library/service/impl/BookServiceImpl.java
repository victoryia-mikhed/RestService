package by.bsu.library.service.impl;

import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;
import by.bsu.library.service.BookService;
import by.bsu.library.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static Logger LOG = Logger.getLogger(ItemServiceImpl.class);
    private static String NULL_ERROR_MESSAGE = "Parameter 'book' is null";

    @Autowired
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Long insert(Book object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            return bookDao.insert(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'insert'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Book object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            return bookDao.update(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'update'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookDao.delete(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'delete'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getElementById(Long id) throws ServiceException {
        try {
            return bookDao.getElementById(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getElementById'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws ServiceException {
        try {
            return bookDao.getBooksByTitle(title);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getBookByTitle'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) throws ServiceException {
        try {
            return bookDao.getBooksByAuthor(author);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getBooksByAuthor'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getAllBooks() throws ServiceException {
        try {
             return  bookDao.getAllBooks();
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getAllBooks'", e);
            throw new ServiceException(e);
        }
    }
}
