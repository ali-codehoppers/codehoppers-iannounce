package com.mt.idao;

import com.mt.hibernate.entities.Category;
import java.util.List;

public interface ICategoryDao extends IDaoGeneric<Category, Integer> {
	List<Category> findByName(String name);
        List<Category> findEagerCategoryList();
}
