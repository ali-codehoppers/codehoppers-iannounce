/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.Product;

import com.mt.hibernate.entities.Product;
import com.mt.services.ProductService;
import com.opensymphony.xwork2.ActionSupport;

public class ViewProduct extends ActionSupport
{

    private int productId;
    private String message;
    private Product product;
    
    private ProductService productService;

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setProductService(ProductService productService)
    {
        this.productService = productService;
    }
    
    @Override
    public String execute() throws Exception
    {
        product = productService.getById(productId);
        return SUCCESS;
    }

    public Product getProduct()
    {
        return product;
    }

    public String getMessage()
    {
        return message;
    }
    
    
}
