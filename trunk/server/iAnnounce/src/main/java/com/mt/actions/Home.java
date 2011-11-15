package com.mt.actions;

import com.mt.hibernate.entities.Category;
import com.mt.hibernate.entities.SubCategory;
import com.mt.services.CategoryService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

public class Home extends ActionSupport
{
    
    private String treeJsonString;
    private CategoryService categoryService;
    
        
    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
 
    @Override
    public String execute() throws Exception
    {
        treeJsonString = getCategoryTreeData();
        return SUCCESS;
    }
    
    private String getCategoryTreeData()
    {
        String treeJson = "\"[";
        //List<Category> categorys = categoryService.findEagerCategoryList();
        List<Category> categorys = categoryService.getAll();
        for (int i = 0; i < categorys.size(); i++)
          {
            treeJson += getCategoryJsonString(categorys.get(i), i == categorys.size() - 1);
          }
        treeJson += "]\"";
        return treeJson;
    }
    
    private String getSubCategoryJsonString(SubCategory subCategory, boolean isLast)
    {
        String treeJson = "{'data' : {";
        treeJson += " 'title' : '" + subCategory.getName() + "'}";
        treeJson += " ,'attr' : {";
        treeJson += " 'id' : '" + subCategory.getId() + "'";
        treeJson += " ,'type' : 'subCategory'}";        
//        treeJson += "}";
        if (isLast)          
          {
            treeJson += "}";
          }
        else
          {
            treeJson += "},";
          }
        return treeJson;
    }
    
    private String getCategoryJsonString(Category category, boolean isLast)
    {
        List<SubCategory> subCategorys = category.getSubCategorys();
        String treeJson = "{'data' : {";
        treeJson += " 'title' : '" + category.getName() + "'}";
        treeJson += " ,'attr' : {";
        treeJson += " 'id' : '" + category.getId() + "'";
        treeJson += " ,'type' : 'category'}";                
        treeJson += ",'metadata':{";
        treeJson += " 'title' : '" + category.getName() + "',";
        treeJson += " 'attr' : {";
        treeJson += " 'id' : '" + category.getId() + "'}";
        treeJson += "},";
        treeJson += " 'children' : [";
        for (int i = 0; i < subCategorys.size(); i++)
          {
            treeJson += getSubCategoryJsonString(subCategorys.get(i), i == subCategorys.size() - 1);
          }
        treeJson += "]";
        
        if (isLast)
          {
            treeJson += "}";
          }
        else
          {
            treeJson += "},";
          }
        //treeJson=treeJson.replaceAll("'","\"");
        return treeJson;
    }
    
    public String getTreeJsonString()
    {
        return treeJsonString;
    }
    
}
