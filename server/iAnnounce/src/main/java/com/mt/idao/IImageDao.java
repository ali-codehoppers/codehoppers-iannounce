package com.mt.idao;

import com.mt.hibernate.entities.Category;
import com.mt.hibernate.entities.Image;
import java.util.List;

public interface IImageDao extends IDaoGeneric<Image, Integer> {
	List<Image> findByName(String name);
}
