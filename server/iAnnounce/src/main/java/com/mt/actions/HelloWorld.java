package com.mt.actions;

import com.mt.hibernate.entities.Category;
import com.mt.services.CategoryService;
import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport
{
    CategoryService categoryService;

    public CategoryService getCategoryService()
    {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
    
    @Override
    public String execute() throws Exception
    {
        Category category = null;
    
 //       List<Category> categoryList =  categoryService.findByName("Access Control");
 //       if(categoryList==null|| categoryList.isEmpty())
          {
            category = new Category(1,"Motor Controls");
            categoryService.addNew(category);
            category = new Category(2,null);
            categoryService.addNew(category);            
          }
 //       else
 //           message+=". Records already exist. Size = " + categoryList.size() + "Name = "+ categoryList.get(0).getName();
        
        setMessage(getText(MESSAGE) + getMessage());
        return SUCCESS;
    }
    
    public static final String MESSAGE = "HelloWorld.message";
    private String message = "";

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
