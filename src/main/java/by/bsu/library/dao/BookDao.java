package by.bsu.library.dao;

import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;

import java.util.List;

public interface BookDao extends Dao<Book> {

    List<Book> getBooksByTitle(String title) throws DaoException;

    List<Book> getBooksByAuthor(String author) throws DaoException;

    List<Book> getAllBooks() throws DaoException;

}