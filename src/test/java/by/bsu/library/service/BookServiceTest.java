package by.bsu.library.service;

import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;
import by.bsu.library.service.exception.ServiceException;
import by.bsu.library.service.impl.BookServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class BookServiceTest {

    @Mock
    BookDao bookDao;

    @InjectMocks
    BookServiceImpl bookServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertBook() throws ServiceException, DaoException {
        Book book = new Book(1L, "author1", "title1");
        Mockito.when(bookDao.insert(book)).thenReturn(1L);
        long id = bookServiceImpl.insert(book);
        Assert.assertEquals(1L, id);
        Mockito.verify(bookDao).insert(book);
    }

    @Test
    public void testUpdateBook() throws ServiceException, DaoException {
        Book book = new Book(1L, "author1_upd", "title1_upd");
        bookServiceImpl.update(book);
        Mockito.verify(bookDao).update(book);
    }

    @Test
    public void testDeleteBook() throws ServiceException, DaoException {
        bookServiceImpl.delete(1L);
        Mockito.verify(bookDao).delete(1L);
    }

    @Test
    public void testGetBookById() throws ServiceException, DaoException {
        Long id = 1L;
        Book actual = new Book(id, "author1", "title1");
        Mockito.when(bookDao.getElementById(id)).thenReturn(actual);
        Book expected = bookServiceImpl.getElementById(id);
        Assert.assertEquals(actual, expected);
        Mockito.verify(bookDao).getElementById(id);
    }

}
