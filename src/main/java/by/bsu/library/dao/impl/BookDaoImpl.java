package by.bsu.library.dao.impl;

import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{

    private static final String SQL_BOOK_ID = "BOOK_ID";
    private static final String SQL_SELECT_BOOK_BY_ID = "SELECT BOOK_ID, AUTHOR, TITLE FROM BOOKS WHERE BOOK_ID=?";
    private static final String SQL_SELECT_BOOKS_BY_TITLE = "SELECT BOOK_ID, AUTHOR, TITLE FROM BOOKS WHERE TITLE  LIKE '%'+?+'%'";
    private static final String SQL_SELECT_BOOKS_BY_AUTHOR = "SELECT BOOK_ID, AUTHOR, TITLE FROM BOOKS WHERE AUTHOR=?";
    private static final String SQL_SELECT_ALL_BOOKS = "SELECT BOOK_ID, AUTHOR, TITLE FROM BOOKS ORDER BY TITLE";
    private static final String SQL_INSERT_BOOK = "INSERT INTO BOOKS (AUTHOR, TITLE) VALUES (?,?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE BOOKS SET AUTHOR=?, TITLE=? WHERE BOOK_ID=?";
    private static final String SQL_DELETE_BOOK = "DELETE FROM BOOKS WHERE BOOK_ID=?";

    @Autowired
    private DataSource dataSource;

    @Override
    public Long insert(Book object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT_BOOK, new String[]{SQL_BOOK_ID})) {
            ps.setString(1, object.getAuthor());
            ps.setString(2, object.getTitle());
            if (ps.executeUpdate() == 0) {
                return null;
            }
            resultSet = ps.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Exception while inserting book", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean update(Book object) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            ps.setString(1, object.getAuthor());
            ps.setString(2, object.getTitle());
            ps.setLong(2, object.getBookID());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while updating book", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BOOK)) {
            ps.setLong(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting book", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Book getElementById(Long id) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID)) {
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            return initBook(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in method getElementById", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BOOKS_BY_TITLE)) {
            ps.setString(1, title);
            resultSet = ps.executeQuery();
            Book book;
            while (resultSet.next()) {
                book = initBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getBooksByTitle", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BOOKS_BY_AUTHOR)) {
            ps.setString(1, author);
            resultSet = ps.executeQuery();
            Book book;
            while (resultSet.next()) {
                book = initBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getBooksByAuthor", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Book> getAllBooks() throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ResultSet resultSet;
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL_BOOKS)) {
            resultSet = ps.executeQuery();
            Book book;
            while (resultSet.next()) {
                book = initBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DaoException("Exception in method getAllBooks", e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private Book initBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setBookID(resultSet.getLong(1));
        book.setAuthor(resultSet.getString(2));
        book.setTitle(resultSet.getString(3));
        return book;
    }
}
