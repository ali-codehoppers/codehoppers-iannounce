/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.util;

import com.mt.hibernate.entities.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

enum ImageType {

    Thumbnail,
    Small,
    Medium,
    Large
}

public class ImageUtil {

    Properties prop = new Properties();
    private static final Logger logger = Logger.getLogger(ImageUtil.class);

    public ImageUtil() throws Exception {
        InputStream is = ImageUtil.class.getResourceAsStream("image.properties");
        prop.load(is);
    }

    public boolean SaveImage(File image, String fileName, String extension) throws Exception {
        String path = "D:\\Projects\\Java\\Mexam-BuynSell\\UploadedImages";
        File fileToCreate = new File(path, fileName+"."+extension);
        FileUtils.copyFile(image, fileToCreate);
        return true;
    }

    public byte[] GetImageBytes(Image image, ImageType imageType) throws Exception {
//        String path = prop.getProperty("imagePath");
        String path = "D:\\Projects\\Java\\Mexam-BuynSell\\UploadedImages";
        switch (imageType) {
            case Thumbnail:
                path += "/" + image.getFileName() + "_thumb";
                break;
            case Small:
                path += "/" + image.getFileName() + "_small";
                break;
            case Medium:
                path += "/" + image.getFileName() + "_medium";
                break;
            case Large:
                path += "/" + image.getFileName();

        }
        File file = new File(path);
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream fin = new FileInputStream(file);
        fin.read(fileContent);
        return fileContent;

    }

    public byte[] GetImageBytes(Image image) throws Exception {
        String path = "D:\\Projects\\Java\\Mexam-BuynSell\\UploadedImages";
        path += "\\" + image.getFileName()+"."+image.getExtension();
        File file = new File(path);
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream fin = new FileInputStream(file);
        fin.read(fileContent);
        return fileContent;
    }
}
