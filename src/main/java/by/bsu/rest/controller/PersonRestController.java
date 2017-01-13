package by.bsu.rest.controller;

import java.util.List;

import by.bsu.rest.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.rest.dao.PersonDAO;

@RestController
public class PersonRestController {
	
	@Autowired
	private PersonDAO personDAO;

	
	@GetMapping("/persons")
	public List getPersons() {
		return personDAO.list();
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity getPerson(@PathVariable("id") Long id) {
		Person person = personDAO.get(id);
		if (person == null) {
			return new ResponseEntity("No Person found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping(value = "/persons")
	public ResponseEntity createPerson(@RequestBody Person person) {
		personDAO.create(person);
		return new ResponseEntity(person, HttpStatus.OK);
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity deletePerson(@PathVariable Long id) {
		if (null == personDAO.delete(id)) {
			return new ResponseEntity("No Person found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(id, HttpStatus.OK);
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity updatePerson(@PathVariable Long id, @RequestBody Person person) {
		person = personDAO.update(id, person);
		if (null == person) {
			return new ResponseEntity("No Person found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(person, HttpStatus.OK);
	}

}