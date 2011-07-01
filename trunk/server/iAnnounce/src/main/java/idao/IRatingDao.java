package idao;

import hibernate.entities.Rating;
import java.util.List;

public interface IRatingDao extends IDaoGeneric<Rating, Integer> {
	List<Rating> findByName(int name);
}
