package idao;

import hibernate.entities.Announcement;
import java.util.List;

public interface IAnnouncementDao extends IDaoGeneric<Announcement, Integer> {
	List<Announcement> findByName(String name);
}