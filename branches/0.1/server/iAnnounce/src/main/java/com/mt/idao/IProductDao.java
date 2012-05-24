package com.mt.idao;

import com.mt.hibernate.entities.Product;
import java.util.List;

public interface IProductDao extends IDaoGeneric<Product, Integer> {
	List<Product> findByName(String name);
        List<Product> findByCategory(String id);
}
