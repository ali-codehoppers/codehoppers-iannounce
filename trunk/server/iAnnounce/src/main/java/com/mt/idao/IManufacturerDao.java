package com.mt.idao;

import com.mt.hibernate.entities.Manufacturer;
import java.util.List;

public interface IManufacturerDao extends IDaoGeneric<Manufacturer, Integer> {
	List<Manufacturer> findByName(String name);
}
