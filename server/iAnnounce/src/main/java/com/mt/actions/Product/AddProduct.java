/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.Product;

import com.mt.hibernate.entities.Image;
import com.mt.hibernate.entities.Manufacturer;
import com.mt.hibernate.entities.Product;
import com.mt.hibernate.entities.SubCategory;
import com.mt.services.ImageService;
import com.mt.services.ManufacturerService;
import com.mt.services.ProductService;
import com.mt.services.SubCategoryService;
import com.mt.util.ImageUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.util.UUID;

/**
 *
 * @author CodeHopper
 */
public class AddProduct extends ActionSupport {

    private int manufacturerId;
    private int subcategoryId;
    private String name;
    private String description;
    private String detailDescription;
    private String features;
    private String specifications;
    private String message;
    private File image;
    private String imageContentType;
    private String imageFilename;
    Product product;
    private ProductService productService;
    private SubCategoryService subCategoryService;
    private ManufacturerService manufacturerService;
    private ImageService imageService;

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public void setImageFileName(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSubCategoryService(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    public void setManufacturerService(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Override
    public String execute() throws Exception {

        SubCategory subCategory = subCategoryService.getById(subcategoryId);
        Manufacturer manufacturer = manufacturerService.getById(manufacturerId);

        product = new Product(name, description, detailDescription, manufacturer, subCategory, features, specifications);
        productService.addNew(product);

        String fileName = UUID.randomUUID().toString();
        new ImageUtil().SaveImage(image, fileName,getExtension(imageFilename));

        if (image != null) {
            Image tmpImage = new Image();
            tmpImage.setContentType(imageContentType);
            tmpImage.setFileName(fileName);
            tmpImage.setProduct(product);
            tmpImage.setOrgFileName(imageFilename);
            tmpImage.setExtension(getExtension(imageFilename));
            imageService.addNew(tmpImage);
        }

        message = "Product added sucessfully";
        return SUCCESS;
    }

    public Product getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }

    private String getExtension(String fileName) {
        if (fileName != null && fileName.length() > 0) {
            int index = fileName.lastIndexOf('.');
            if (index > -1) {
                return fileName.substring(index + 1, fileName.length());
            }
        }
        return null;
    }
}
