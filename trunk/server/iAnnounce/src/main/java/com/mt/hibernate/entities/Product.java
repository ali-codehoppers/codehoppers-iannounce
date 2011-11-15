package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Product.findByName", query = "select c from Product c where c.name like ?"),
    @NamedQuery(name = "Product.findByCategory", query = "select p from Product p inner join p.subcategory as sub where sub.categoryId = cast(? as string)")
})
public class Product implements Serializable {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private int manufacturerId;
    private Manufacturer manufacturer;
    @Expose
    private int subcategoryId;
    private SubCategory subcategory;
    @Expose
    private String features;
    @Expose
    private String specifications;
    @Expose
    private List<Image> images;
    @Expose
    private String detailDescription;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public void setSubcategory(SubCategory subCategory) {
        this.subcategory = subCategory;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }
    
    

    public Product() {
    }

    public Product(String name, String description, int manufacturerId, int subcategoryId, String features, String specifications) {
        this.name = name;
        this.description = description;
        this.manufacturerId = manufacturerId;
        this.subcategoryId = subcategoryId;
        this.features = features;
        this.specifications = specifications;
    }

    public Product(String name, String description,String detailDescription, Manufacturer manufacturer, SubCategory subcategory, String features, String specifications) {
        this.name = name;
        this.description = description;
        this.detailDescription = detailDescription;        
        this.manufacturer = manufacturer;
        this.subcategory = subcategory;
        this.features = features;
        this.specifications = specifications;
    }

    public Product(int id, String name, String description, String detailDescription, int manufacturerId, int subcategoryId, String features, String specifications) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.detailDescription = detailDescription;
        this.manufacturerId = manufacturerId;
        this.subcategoryId = subcategoryId;
        this.features = features;
        this.specifications = specifications;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Column(insertable = false, updatable = false, name = "subcategoryId")
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public String getDescription() {
        return description;
    }

    public String getFeatures() {
        return features;
    }

    @Column(insertable = false, updatable = false, name = "manufacturerId")
    public int getManufacturerId() {
        return manufacturerId;
    }

    @OneToMany(targetEntity = Image.class, fetch = FetchType.LAZY, mappedBy = "productId")
    public List<Image> getImages() {
        return images;
    }

    public String getSpecifications() {
        return specifications;
    }

    @ManyToOne(targetEntity = SubCategory.class, fetch = FetchType.LAZY)
    public SubCategory getSubcategory() {
        return subcategory;
    }

    @ManyToOne(targetEntity = Manufacturer.class, fetch = FetchType.LAZY)
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    @Column(name = "detailDescription")
    public String getDetailDescription() {
        return detailDescription;
    }
    
}
