package by.bsu.library.dao.impl;

import by.bsu.library.dao.ItemDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;
import by.bsu.library.entity.ItemInfo;
import by.bsu.library.entity.Reader;
import by.bsu.library.entity.TakenBookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    private static final String SQL_ITEM_ID = "ITEM_ID";
    private static final String SQL_SELECT_ITEM_BY_ID = "SELECT BOOK_ITEMS.ITEM_ID, BOOKS.BOOK_ID, BOOKS.AUTHOR, BOOKS.TITLE " +
            "FROM BOOK_ITEMS JOIN BOOKS ON BOOK_ITEMS.BOOK_ID = BOOKS.BOOK_ID WHERE ITEM_ID=?";
    private static final String SQL_SELECT_ITEMS_BY_BOOK_ID = "SELECT BOOK_ITEMS.ITEM_ID, BOOKS.BOOK_ID, BOOKS.AUTHOR, BOOKS.TITLE " +
            "FROM BOOK_ITEMS JOIN BOOKS ON BOOK_ITEMS.BOOK_ID = BOOKS.BOOK_ID WHERE BOOK_ID=?";

    private static final String SQL_SELECT_TAKEN_BOOKS_BY_READER_ID = "SELECT " +
            "ID, BOOK_ITEMS.ITEM_ID, BOOKS.BOOK_ID, BOOKS.AUTHOR, BOOKS.TITLE, " +
            "READERS.READER_ID, READERS.NAME, READERS.PASSWORD, TAKEN_BOOKS.DATE_RETURN FROM " +
            "TAKEN_BOOKS JOIN BOOK_ITEMS ON TAKEN_BOOKS.ITEM_ID = BOOK_ITEMS.ITEM_ID JOIN BOOKS ON BOOK_ITEMS.BOOK_ID = BOOKS.BOOK_ID " +
            "JOIN READERS ON TAKEN_BOOKS.READER_ID = READERS.READER_ID WHERE READER_ID=?";

    private static final String SQL_SELECT_EXPIRED_BOOKS_BY_READER_ID = "SELECT " +
            "ID, BOOK_ITEMS.ITEM_ID, BOOKS.BOOK_ID, BOOKS.AUTHOR, BOOKS.TITLE, " +
            "READERS.READER_ID, READERS.NAME, READERS.PASSWORD, TAKEN_BOOKS.DATE_RETURN FROM " +
            "TAKEN_BOOKS JOIN BOOK_ITEMS ON TAKEN_BOOKS.ITEM_ID = BOOK_ITEMS.ITEM_ID JOIN BOOKS ON BOOK_ITEMS.BOOK_ID = BOOKS.BOOK_ID " +
            "JOIN READERS ON TAKEN_BOOKS.READER_ID = READERS.READER_ID WHERE READER_ID=? AND DATE_RETURN >= TRUNC(SYSDATE)";

    private static final String SQL_SELECT_FREE_ITEMS_COUNT_BY_BOOK_ID = "SELECT COUNT(*) FROM BOOK_ITEMS WHERE BOOK_ID=? AND " +
            "ITEM_ID NOT IN (SELECT ITEM_ID FROM TAKEN_BOOKS WHERE BOOK_ID=?)";
    private static final String SQL_INSERT_ITEM = "INSERT INTO BOOK_ITEMS (BOOK_ID) VALUES (?)";
    private static final String SQL_UPDATE_ITEM = "UPDATE BOOK_ITEMS SET BOOK_ID=? WHERE ITEM_ID=?";
    private static final String SQL_DELETE_ITEM = "DELETE FROM BOOK_ITEMS WHERE ITEM_ID=?";
    private static final String SQL_TAKE_ITEM_BY_BOOK_ID = "INSERT INTO TAKEN_BOOKS (ITEM_ID, READER_ID, DATE_RETURN) " +
            "VALUES ((SELECT ITEM_ID FROM BOOK_ITEMS WHERE (ITEM_ID NOT IN (SELECT ITEM_ID FROM TAKEN_BOOKS WHERE BOOK_ID=?)) " +
            "AND (ROWNUM=1)) ,?,?)";
    private static final String SQL_RETURN_ITEM = "DELETE FROM TAKEN_BOOKS WHERE ITEM_ID=?";

    @Autowired
    private DataSource dataSource;

    @Override
    public Long insert(ItemInfo object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ITEM, new String[]{SQL_ITEM_ID})) {
            ps.setLong(1, object.getBook().getBookID());
            if (ps.executeUpdate() == 0) {
                return null;
            }
            resultSet = ps.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Exception while inserting item", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean update(ItemInfo object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ITEM)) {
            ps.setLong(1, object.getBook().getBookID());
            if (ps.executeUpdate() == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating item", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_ITEM)) {
            ps.setLong(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting item", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public ItemInfo getElementById(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ITEM_BY_ID)) {
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            return initItem(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getElementById", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int getItemsCountByBookId(Long bookID) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_FREE_ITEMS_COUNT_BY_BOOK_ID)) {
            ps.setLong(1, bookID);
            resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getItemsCountByBookId", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<ItemInfo> getAllItemsByBookId(Long bookID) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<ItemInfo> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ITEMS_BY_BOOK_ID)) {
            ps.setLong(1, bookID);
            resultSet = ps.executeQuery();
            ItemInfo itemInfo;
            while (resultSet.next()) {
                itemInfo = initItem(resultSet);
                items.add(itemInfo);
            }
            return items;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getAllItemsByBookId", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<TakenBookInfo> getTakenBooksByReaderId(Long readerID) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<TakenBookInfo> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_TAKEN_BOOKS_BY_READER_ID)) {
            ps.setLong(1, readerID);
            resultSet = ps.executeQuery();
            TakenBookInfo takenBookInfo;
            while (resultSet.next()) {
                takenBookInfo = initTakenBookInfo(resultSet);
                items.add(takenBookInfo);
            }
            return items;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getTakenBooksByReaderId", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<TakenBookInfo> getExpiredBooksByReaderId(Long readerID) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<TakenBookInfo> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_EXPIRED_BOOKS_BY_READER_ID)) {
            ps.setLong(1, readerID);
            resultSet = ps.executeQuery();
            TakenBookInfo takenBookInfo;
            while (resultSet.next()) {
                takenBookInfo = initTakenBookInfo(resultSet);
                items.add(takenBookInfo);
            }
            return items;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getExpiredBooksByReaderId", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean takeBook(Long bookID, Long readerID, Date dateReturn) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_TAKE_ITEM_BY_BOOK_ID)) {
            ps.setLong(1, bookID);
            ps.setLong(2, readerID);
            ps.setDate(3, dateReturn);
            if (ps.executeUpdate() == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Exception in method takeBook", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean returnBook(Long itemID) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_RETURN_ITEM)) {
            ps.setLong(1, itemID);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Exception in method returnBook", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private ItemInfo initItem(ResultSet resultSet) throws SQLException {
        ItemInfo item = new ItemInfo();
        item.setItemID(resultSet.getLong(1));
        Book book = new Book(resultSet.getLong(2), resultSet.getString(3), resultSet.getString(4));
        item.setBook(book);
        return item;
    }

    private TakenBookInfo initTakenBookInfo (ResultSet resultSet) throws SQLException {
        TakenBookInfo takenBookInfo= new TakenBookInfo();
        takenBookInfo.setId(resultSet.getLong(1));
        ItemInfo itemInfo = new ItemInfo(resultSet.getLong(2), resultSet.getLong(3), resultSet.getString(4), resultSet.getString(5));
        takenBookInfo.setItemInfo(itemInfo);
        Reader reader = new Reader(resultSet.getLong(6), resultSet.getString(7), resultSet.getString(8));
        takenBookInfo.setReader(reader);
        takenBookInfo.setReturnDate(resultSet.getDate(9));
        return takenBookInfo;
    }
}
