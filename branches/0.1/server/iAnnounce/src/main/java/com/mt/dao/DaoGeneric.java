package com.mt.dao;

import com.mt.idao.IDaoGeneric;
import com.mt.idao.IFinderExecutor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DaoGeneric<T, PK extends Serializable> implements IDaoGeneric<T, PK>, IFinderExecutor<T> {

    private Class<T> type;
    private SessionFactory sessionFactory;

    public DaoGeneric(Class<T> type) {
        this.type = type;
    }

    public PK addNew(T newInstance) {

        return (PK) getSession().save(newInstance);
    }

    public T getById(PK id) {
        return (T) getSession().get(type, id);
    }

    public void update(T transientObject) {
        getSession().update(transientObject);
    }

    public void addOrUpdate(T obj) {
        getSession().saveOrUpdate(obj);
    }

    public void delete(T persistentObject) {
        getSession().delete(persistentObject);
    }

    public void deleteById(PK id) {
        delete(getById(id));
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
//        return SessionFactoryUtils.getSession(sessionFactory, true);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<T> getAll() {
        return getSession().createCriteria(type).list();
    }

    public List<T> executeFinder(Method method, Object[] args) {
        String queryName = type.getSimpleName();
        queryName += ".";
        queryName += method.getName();

        Query query = getSession().getNamedQuery(queryName);

        // Params are numbered from 0 in hibernate
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        return query.list();
    }
}