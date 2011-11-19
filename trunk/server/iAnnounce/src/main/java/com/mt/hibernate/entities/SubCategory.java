package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@Entity
@javax.persistence.Table(name = "subcategories")
@NamedQueries(
  {
    @NamedQuery(name = "SubCategory.findByName", query = "select c from SubCategory c where c.name like ?")
  })
public class SubCategory implements Serializable
{

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int categoryId;
    private Category category;
    private List<Product> products;

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public SubCategory()
    {
    }

    public SubCategory(String name, int categoryId)
    {
        this.name = name;
        this.categoryId = categoryId;
    }

    @Id
    @GeneratedValue
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Column(insertable = false, updatable = false, name="categoryId")
    public int getCategoryId()
    {
        return categoryId;
    }

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    public Category getCategory()
    {
        return category;
    }

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "subcategoryId")
    public List<Product> getProducts()
    {
        return products;
    }
}
