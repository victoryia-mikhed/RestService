package by.bsu.rest.dao;

import java.util.ArrayList;
import java.util.List;

import by.bsu.rest.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

	private static List<Person> persons;
	{
		persons = new ArrayList();
		persons.add(new Person(101, "Vika", "Mikhed", "vika.mikhed@gmail.com", "8029-877-13-87"));
		persons.add(new Person(201, "Veronika", "Mikhed", "veronika.mikhed@gmail.com", "8029-742-24-60"));
		persons.add(new Person(301, "Petr", "Petukh", "petukh@gmail.com", "8029-123-45-67"));
		persons.add(new Person(System.currentTimeMillis(), "Abcd", "Qwerty", "qwerty@gmail.com", "8029-999-99-99"));
	}

	/**
	 * Returns list of all persons in database.
	 * 
	 * @return list of persons
	 */
	public List list() {
		return persons;
	}

	/**
	 * Returns person object for given id from database. If person was not found for id, returns null.
	 * 
	 * @param id person id
	 * @return person object for given id
	 */
	public Person get(Long id) {
		for (Person c : persons) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Creates new person in database. Updates the id and inserts new person in list.
	 * 
	 * @param person Person object
	 * @return person object with updated id
	 */
	public Person create(Person person) {
		person.setId(System.currentTimeMillis());
		persons.add(person);
		return person;
	}

	/**
	 * Deletes the person object from database. If person was not found for given id, returns null.
	 * 
	 * @param id id of person
	 * @return id of deleted person object
	 */
	public Long delete(Long id) {
		for (Person c : persons) {
			if (c.getId().equals(id)) {
				persons.remove(c);
				return id;
			}
		}
		return null;
	}

	/**
	 * Updates the person object for given id in database. If person does not exists, returns null
	 * 
	 * @param id
	 * @param person
	 * @return person object with id
	 */
	public Person update(Long id, Person person) {
		for (Person c : persons) {
			if (c.getId().equals(id)) {
				person.setId(c.getId());
				persons.remove(c);
				persons.add(person);
				return person;
			}
		}
		return null;
	}

}