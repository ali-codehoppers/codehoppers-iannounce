package idao;

import hibernate.entities.Person;
import java.util.List;

public interface IPersonDao extends IDaoGeneric<Person, Integer> {
	List<Person> findByName(String name);
}
