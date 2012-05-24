package com.mt.idao;

import com.mt.hibernate.entities.SubCategory;
import java.util.List;

public interface ISubCategoryDao extends IDaoGeneric<SubCategory, Integer> {
	List<SubCategory> findByName(String name);
}
