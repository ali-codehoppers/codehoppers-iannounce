package com.mt.idao;

import com.mt.hibernate.entities.State;
import java.util.List;

public interface IStateDao extends IDaoGeneric<State, Integer> {
	List<State> findByName(String name);
}
