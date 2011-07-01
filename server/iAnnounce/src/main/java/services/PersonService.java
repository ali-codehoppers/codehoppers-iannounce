package services;

import hibernate.entities.Person;
import idao.IPersonDao;
import java.util.List;

public class PersonService {

    private IPersonDao personDao;

    public PersonService(IPersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> findByName(String name) {
        return personDao.findByName(name);
    }

    public Integer addNew(Person newInstance) {
        return personDao.addNew(newInstance);
    }

    public Person getById(Integer id) {
        return personDao.getById(id);
    }

    public void update(Person transientObject) {
        personDao.update(transientObject);
    }

    public void addOrUpdate(Person obj) {
        personDao.addOrUpdate(obj);
    }

    public void delete(Person persistentObject) {
        personDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        personDao.deleteById(id);
    }

    public List<Person> getAll() {
        return personDao.getAll();
    }
}
