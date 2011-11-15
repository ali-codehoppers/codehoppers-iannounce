package com.mt.idao;

import com.mt.hibernate.entities.Country;
import java.util.List;

public interface ICountryDao extends IDaoGeneric<Country, Integer> {
	List<Country> findByName(String name);
}
