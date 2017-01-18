package by.bsu.library.dao;

import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.ItemInfo;
import by.bsu.library.entity.TakenBookInfo;

import java.sql.Date;
import java.util.List;

public interface ItemDao extends Dao<ItemInfo> {

    int getItemsCountByBookId(Long bookID) throws DaoException;

    List<ItemInfo> getAllItemsByBookId(Long bookID) throws DaoException;

    List<TakenBookInfo> getTakenBooksByReaderId(Long readerID) throws DaoException;

    List<TakenBookInfo> getExpiredBooksByReaderId(Long readerID) throws DaoException;

    boolean takeBook(Long bookID, Long readerID, Date dateReturn) throws DaoException;

    boolean returnBook(Long itemID) throws DaoException;

}
