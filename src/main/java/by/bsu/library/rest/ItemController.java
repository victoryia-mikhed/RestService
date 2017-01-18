package by.bsu.library.rest;

import by.bsu.library.entity.ItemInfo;
import by.bsu.library.service.ItemService;
import by.bsu.library.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class ItemController {
    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Autowired
    private ItemService itemService;

    private static Logger LOG = Logger.getLogger(ItemController.class);

    @GetMapping("/items/{id}")
    public ResponseEntity getItem(@PathVariable("id") Long id) {
        ItemInfo itemInfo = null;
        try {
            itemInfo = itemService.getElementById(id);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method getItem", e);
        }
        if (itemInfo == null) {
            return new ResponseEntity("No Item found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(itemInfo, HttpStatus.OK);
    }

    @PostMapping(value = "/ietms")
    public ResponseEntity createItem(@RequestBody ItemInfo itemInfo) {
        try {
            itemService.insert(itemInfo);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method createItem", e);
        }
        return new ResponseEntity(itemInfo, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        try {
            if (!itemService.delete(id)) {
                return new ResponseEntity("No Item found for ID " + id, HttpStatus.NOT_FOUND);
            }
        } catch (ServiceException e) {
            LOG.error("ServiceException in method deleteItem", e);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity updateItem(@PathVariable Long id, @RequestBody ItemInfo itemInfo) {
        boolean isUpdated = false;
        try {
            isUpdated = itemService.update(itemInfo);
        } catch (ServiceException e) {
            LOG.error("ServiceException in method updateItem", e);
        }
        if (!isUpdated) {
            return new ResponseEntity("No Item found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(itemInfo, HttpStatus.OK);
    }

}