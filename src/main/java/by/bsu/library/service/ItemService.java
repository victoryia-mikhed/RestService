package by.bsu.library.service;

import by.bsu.library.entity.ItemInfo;
import by.bsu.library.entity.TakenBookInfo;
import by.bsu.library.service.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface ItemService extends Service<ItemInfo> {

    int getItemsCountByBookId(Long bookID) throws ServiceException;

    List<ItemInfo> getAllItemsByBookId(Long bookID) throws ServiceException;

    List<TakenBookInfo> getTakenBooksByReaderId(Long readerID) throws ServiceException;

    List<TakenBookInfo> getExpiredBooksByReaderId(Long readerID) throws ServiceException;

    boolean takeBook(Long bookID, Long readerID) throws ServiceException;

    boolean returnBook(Long itemID) throws ServiceException;

}
