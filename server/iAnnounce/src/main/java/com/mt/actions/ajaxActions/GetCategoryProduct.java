/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.ajaxActions;

import com.google.gson.GsonBuilder;
import com.mt.hibernate.entities.Product;
import com.mt.services.ProductService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

/**
 *
 * @author CodeHopper
 */
public class GetCategoryProduct extends ActionSupport
{
    private int categoryId;
    private List<Product> products;
    private ProductService productService;
    private String jsonString="";

    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    @Override
    public String execute() throws Exception
    {
        products = productService.findByCategory(categoryId);
        jsonString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(products);
        return SUCCESS;
    }

    public String getJsonString()
    {
        return jsonString;
    }
}
