package by.bsu.library.rest;

import by.bsu.library.entity.Book;
import by.bsu.library.service.BookService;
import by.bsu.library.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
public class BookController {
    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Autowired
    private BookService bookService;

    private static Logger LOG = Logger.getLogger(BookController.class);

    @GetMapping("/books")
    public List getBooks() {
        List<Book> list = null;
        try {
            list = bookService.getAllBooks();
        } catch (ServiceException e) {
            LOG.error("ServiceException in method getBooks");
        }
        return list;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBook(@PathVariable("id") Long id) {
        Book book = null;
        try {
            book = bookService.getElementById(id);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method getBook", e);
        }
        if (book == null) {
            return new ResponseEntity("No Book found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }

    @PostMapping(value = "/books")
    public ResponseEntity createBook(@RequestBody Book book) {
        try {
            bookService.insert(book);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method createBook", e);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        try {
            if (!bookService.delete(id)) {
                return new ResponseEntity("No Book found for ID " + id, HttpStatus.NOT_FOUND);
            }
        } catch (ServiceException e) {
            LOG.error("ServiceException in method deleteBook", e);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateBook(@PathVariable Long id, @RequestBody Book book) {
        boolean isUpdated = false;
        try {
            isUpdated = bookService.update(book);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method updateBook", e);
        }
        if (!isUpdated) {
            return new ResponseEntity("No Book found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }

}