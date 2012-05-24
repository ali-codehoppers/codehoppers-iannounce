
package idao;

import hibernate.entities.UserSession;
import java.util.List;

public interface IUserSessionDao extends IDaoGeneric<UserSession, Integer> {
	List<UserSession> findByName(String name);
}
