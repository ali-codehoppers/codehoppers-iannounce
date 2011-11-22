package com.mt.idao;

import com.mt.hibernate.entities.LookUp;
import java.util.List;

public interface ILookUpDao extends IDaoGeneric<LookUp, Integer> {
	List<LookUp> findByName(String name);
}
