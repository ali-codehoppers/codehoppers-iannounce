/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.Product;

import com.mt.hibernate.entities.Category;
import com.mt.hibernate.entities.Manufacturer;
import com.mt.hibernate.entities.SubCategory;
import com.mt.services.CategoryService;
import com.mt.services.ManufacturerService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;

public class ShowAddProduct extends ActionSupport
{
    private List<Category> categorys;
    private List<Manufacturer> manufacturers;
    private ManufacturerService manufacturerService;
    private CategoryService categoryService;

    public void setManufacturerService(ManufacturerService manufacturerService)
    {
        this.manufacturerService = manufacturerService;
    }

    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @Override
    public String execute() throws Exception
    {
        manufacturers = manufacturerService.getAll();
        categorys = categoryService.getAll();
        return SUCCESS;
    }

    public List<Category> getCategorys()
    {
        return categorys;
    }

    public List<Manufacturer> getManufacturers()
    {
        return manufacturers;
    }

    public List<SubCategory> getSubcategorys()
    {
        return new ArrayList<SubCategory>();
    }

}
