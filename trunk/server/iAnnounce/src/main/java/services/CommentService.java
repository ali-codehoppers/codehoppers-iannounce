package services;

import hibernate.entities.Comment;
import idao.ICommentDao;
import java.util.List;

public class CommentService {

    private ICommentDao CDao;

    public CommentService(ICommentDao CDao) {
        this.CDao = CDao;
    }

    public List<Comment> findByName(int name) {
        return CDao.findByName(name);
    }

    public Integer addNew(Comment newInstance) {
        return CDao.addNew(newInstance);
    }

    public Comment getById(Integer id) {
        return CDao.getById(id);
    }

    public void update(Comment transientObject) {
        CDao.update(transientObject);
    }

    public void addOrUpdate(Comment obj) {
        CDao.addOrUpdate(obj);
    }

    public void delete(Comment persistentObject) {
        CDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        CDao.deleteById(id);
    }

    public List<Comment> getAll() {
        return CDao.getAll();
    }
}
