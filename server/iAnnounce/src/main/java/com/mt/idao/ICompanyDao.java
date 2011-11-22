package com.mt.idao;

import com.mt.hibernate.entities.Company;
import java.util.List;

public interface ICompanyDao extends IDaoGeneric<Company, Integer> {
	List<Company> findByName(String name);
}
