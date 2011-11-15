/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions;

import com.mt.hibernate.entities.Image;
import com.mt.services.ImageService;
import com.mt.util.ImageUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author CodeHopper
 */
public class GetImage extends ActionSupport {

    private int imageId;
    private Image imageObj;
    private byte[] image;
    private ImageService imageService;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public String execute() throws Exception {
        imageObj = imageService.getById(imageId);
        image = new ImageUtil().GetImageBytes(imageObj);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("image/jpg");
        OutputStream out = response.getOutputStream();
        out.write(image);
        return null;
    }

    public ImageService getImageService() {
        return imageService;
    }

    public byte[] getImage() {
        return image;
    }
}
