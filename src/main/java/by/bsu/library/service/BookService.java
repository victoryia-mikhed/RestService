package by.bsu.library.service;

import by.bsu.library.entity.Book;
import by.bsu.library.service.exception.ServiceException;

import java.util.List;

public interface BookService extends Service<Book> {

    List<Book> getBooksByTitle(String title) throws ServiceException;

    List<Book> getBooksByAuthor(String author) throws ServiceException;

    List<Book> getAllBooks() throws ServiceException;

}
