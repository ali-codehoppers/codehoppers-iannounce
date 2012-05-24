/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.ajaxActions;

import com.google.gson.GsonBuilder;
import com.mt.hibernate.entities.Product;
import com.mt.services.SubCategoryService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

/**
 *
 * @author CodeHopper
 */
public class GetSubcategoryProduct extends ActionSupport
{
    private int subcategoryId;
    private List<Product> products;
    private SubCategoryService subCategoryService;
    private String jsonString="";        

    public void setSubCategoryService(SubCategoryService subCategoryService)
    {
        this.subCategoryService = subCategoryService;
    }

    public void setSubcategoryId(int subcategoryId)
    {
        this.subcategoryId = subcategoryId;
    }

    @Override
    public String execute() throws Exception
    {
        products = subCategoryService.getById(subcategoryId).getProducts();
        jsonString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(products);
        return SUCCESS;
    }

    public String getJsonString()
    {
        return jsonString;
    }
}
