package idao;

import hibernate.entities.Comment;
import java.util.List;

public interface ICommentDao extends IDaoGeneric<Comment, Integer> {
	List<Comment> findByName(int name);
}
