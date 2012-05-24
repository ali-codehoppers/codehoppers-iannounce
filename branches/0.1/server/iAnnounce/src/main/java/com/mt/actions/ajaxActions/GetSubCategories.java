/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.ajaxActions;

import com.google.gson.GsonBuilder;
import com.mt.hibernate.entities.SubCategory;
import com.mt.services.CategoryService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

/**
 *
 * @author CodeHopper
 */
public class GetSubCategories extends ActionSupport
{
    private int categoryId;
    private List<SubCategory> subCategorys;
    private CategoryService categoryService;
    private String jsonString="";    

    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    @Override
    public String execute() throws Exception
    {
        subCategorys = categoryService.getById(categoryId).getSubCategorys();
        jsonString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(subCategorys);
        return SUCCESS;
    }

    public String getJsonString()
    {
        return jsonString;
    }
}
