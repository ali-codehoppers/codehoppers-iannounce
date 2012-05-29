package dao;

import idao.IDaoGeneric;
import idao.IFinderExecutor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
        //return sessionFactory.getCurrentSession();
        return SessionFactoryUtils.getSession(sessionFactory, true);
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

    public List getNearbyObjects() {
        String query = "SELECT l.* ,(((ACOS( SIN( (31.4743 * PI() /180 ) ) * SIN( (l.latitude * PI() /180 )) + COS( (31.4743 * PI( ) /180 )) * COS( ( l.latitude * PI() /180 )) * COS( (( 74.4023 -  l.longitude ) * PI() /180 )))) *180 / PI()) *60 * 1.1515 * 1.609344 ) AS  `distance`FROM  announcement l HAVING  `distance` <= 2 ORDER BY  `distance` ASC";
        SQLQuery sQLQuery = getSession().createSQLQuery(query);
        return sQLQuery.list();
    }

    public List getNearbyMembers(double latitude, double longitude, int range, int neighbourId) {
        String query = "SELECT l.* ,(((ACOS( SIN( (" + latitude + " * PI() /180 ) ) * SIN( (l.latitude * PI() /180 )) + COS( (" + latitude + " * PI( ) /180 )) * COS( ( l.latitude * PI() /180 )) * COS( (( " + longitude + " -  l.longitude ) * PI() /180 )))) *180 / PI()) *60 * 1.1515 * 1.609344 ) AS  `distance`FROM  person l WHERE l.id IN(SELECT personId FROM neighbour WHERE neighbour.neighbourhoodId=" + neighbourId + " ) HAVING  `distance` <= " + range + " ORDER BY  `distance` ASC";
        SQLQuery sQLQuery = getSession().createSQLQuery(query);
        return sQLQuery.list();
    }

    public List getNearbyLocations(double latitude, double longitude, int range, int neighbourId) {
        String query = "SELECT l.* ,(((ACOS( SIN( (" + latitude + " * PI() /180 ) ) * SIN( (l.latitude * PI() /180 )) + COS( (" + latitude + " * PI( ) /180 )) * COS( ( l.latitude * PI() /180 )) * COS( (( " + longitude + " -  l.longitude ) * PI() /180 )))) *180 / PI()) *60 * 1.1515 * 1.609344 ) AS  `distance`FROM  location l WHERE l.neighbourhoodId=" + neighbourId + " HAVING  `distance` <= " + range + " ORDER BY  `distance` ASC";
        SQLQuery sQLQuery = getSession().createSQLQuery(query);
        return sQLQuery.list();
    }
}
