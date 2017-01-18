package by.bsu.library.service.impl;

import by.bsu.library.dao.ItemDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.ItemInfo;
import by.bsu.library.entity.TakenBookInfo;
import by.bsu.library.service.ItemService;
import by.bsu.library.service.exception.ServiceException;
import by.bsu.library.util.DateGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private static Logger LOG = Logger.getLogger(ItemServiceImpl.class);
    private static String NULL_ERROR_MESSAGE = "Parameter 'itemInfo' is null";

    @Autowired
    private ItemDao itemDao;

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Long insert(ItemInfo object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            return itemDao.insert(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'insert'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(ItemInfo object) throws ServiceException {
        if (object == null) {
            throw new ServiceException(NULL_ERROR_MESSAGE);
        }
        try {
            return itemDao.update(object);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'update'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return itemDao.delete(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'delete'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ItemInfo getElementById(Long id) throws ServiceException {
        try {
            return itemDao.getElementById(id);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getElementById'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int getItemsCountByBookId(Long bookID) throws ServiceException {
        try {
            return itemDao.getItemsCountByBookId(bookID);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getItemsCountByBookId'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ItemInfo> getAllItemsByBookId(Long bookID) throws ServiceException {
        try {
            return itemDao.getAllItemsByBookId(bookID);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getAllItemsByBookId'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TakenBookInfo> getTakenBooksByReaderId(Long readerID) throws ServiceException {
        try {
            return itemDao.getTakenBooksByReaderId(readerID);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getTakenBooksByReaderId'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TakenBookInfo> getExpiredBooksByReaderId(Long readerID) throws ServiceException {
        try {
            return itemDao.getExpiredBooksByReaderId(readerID);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'getExpiredBooksByReaderId'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean takeBook(Long bookID, Long readerID) throws ServiceException {
        try {
            if (itemDao.getItemsCountByBookId(bookID) > 0) {
                return itemDao.takeBook(bookID, readerID, DateGenerator.dateOfReturn());
            }
            return false;
        } catch (DaoException e) {
            LOG.error("DaoException in method 'takeBook'", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean returnBook(Long itemID) throws ServiceException {
        try {
            return itemDao.returnBook(itemID);
        } catch (DaoException e) {
            LOG.error("DaoException in method 'returnBook'", e);
            throw new ServiceException(e);
        }
    }
}
