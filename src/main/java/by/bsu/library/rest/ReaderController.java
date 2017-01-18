package by.bsu.library.rest;

import by.bsu.library.entity.Reader;
import by.bsu.library.service.ReaderService;
import by.bsu.library.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@RestController
public class ReaderController {

    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Autowired
    private ReaderService readerService;

    private static Logger LOG = Logger.getLogger(ReaderController.class);

    @GetMapping("/readers/{id}")
    public ResponseEntity getReader(@PathVariable("id") Long id) {
        Reader reader = null;
        try {
            reader = readerService.getElementById(id);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method getReader", e);
        }
        if (reader == null) {
            return new ResponseEntity("No Reader found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reader, HttpStatus.OK);
    }

    @PostMapping(value = "/readers")
    public ResponseEntity createReader(@RequestBody Reader reader) {
        try {
            readerService.insert(reader);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method createReader", e);
        }
        return new ResponseEntity(reader, HttpStatus.OK);
    }

    @DeleteMapping("/readers/{id}")
    public ResponseEntity deleteReader(@PathVariable Long id) {
        try {
            if (!readerService.delete(id)) {
                return new ResponseEntity("No Reader found for ID " + id, HttpStatus.NOT_FOUND);
            }
        } catch (ServiceException e) {
            LOG.error("ServiceException in method deleteReader", e);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PutMapping("/readers/{id}")
    public ResponseEntity updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        boolean isUpdated = false;
        try {
            isUpdated = readerService.update(reader);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method updateReader", e);
        }
        if (!isUpdated) {
            return new ResponseEntity("No Reader found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reader, HttpStatus.OK);
    }

}
