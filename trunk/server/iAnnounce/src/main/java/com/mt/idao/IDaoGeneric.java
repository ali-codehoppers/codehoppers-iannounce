package com.mt.idao;

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
}
