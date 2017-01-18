package by.bsu.library.dao;


import by.bsu.library.dao.exception.DaoException;
import by.bsu.library.entity.Book;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseTearDown(value = "classpath:database/daoTestData/teardown.xml", type = DatabaseOperation.DELETE)
public class BookDaoTest {

    private final static String DATABASE_SETUP = "/database/daoTestData/bookData/database-setup.xml";
    private final static String UPDATE_AUTHOR = "/database/daoTestData/bookData/update-book.xml";
    private final static String DELETE_AUTHOR = "/database/daoTestData/bookData/delete-book.xml";

    @Autowired
    private BookDao bookDao;

    @Test
    @DatabaseSetup(value = DATABASE_SETUP)
    public void testInsertBook() throws DaoException {
        Book book = new Book(1, "author_insert", "title_insert");
        Long res = bookDao.insert(book);
        Assert.assertTrue(res > 0);
    }

    @Test
    @DatabaseSetup(value = DATABASE_SETUP)
    @ExpectedDatabase(value = UPDATE_AUTHOR, assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testUpdateBook() throws DaoException {
        Book book = new Book(3L, "author3_updated", "title3_updated");
        boolean res = bookDao.update(book);
        Assert.assertTrue(res);
    }

    @Test
    @DatabaseSetup(value = DATABASE_SETUP)
    @ExpectedDatabase(value = DELETE_AUTHOR, assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testDeleteBook() throws DaoException {
        Book book = new Book(2L, "author2", "title2");
        boolean res = bookDao.delete(book.getBookID());
        Assert.assertTrue(res);
    }

    @Test
    @DatabaseSetup(value = DATABASE_SETUP)
    public void testGetBookById() throws DaoException {
        Book expected = new Book(1L, "author1", "title1");
        Book actual = bookDao.getElementById(expected.getBookID());
        Assert.assertEquals(expected, actual);
    }

}
