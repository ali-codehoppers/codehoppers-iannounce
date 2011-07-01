package idao;

import hibernate.entities.Rangee;
import java.util.List;

public interface IRangeDao extends IDaoGeneric<Rangee, Integer> {
	List<Rangee> findByName(String name);
}
