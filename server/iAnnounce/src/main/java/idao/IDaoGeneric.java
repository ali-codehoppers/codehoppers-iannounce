package idao;

import java.io.Serializable;
import java.util.List;

public interface IDaoGeneric<T, PK extends Serializable> {

    PK addNew(T newInstance);

    T getById(PK id);

    void update(T transientObject);

    void addOrUpdate(T obj);

    void delete(T persistentObject);

    void deleteById(PK id);

    List<T> getAll();
    
    List getNearbyObjects();
    
    List getNearbyMembers(double latitude,double longitude,int range,int neighbourId);
    
    List getNearbyLocations(double latitude,double longitude,int range,int neighbourId);
}
