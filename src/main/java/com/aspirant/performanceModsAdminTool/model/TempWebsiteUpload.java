/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model;

/**
 *
 * @author Pallavi
 */
public class TempWebsiteUpload {
    
    private String imageURL1;
    private String imageURL2;
    private String imageURL3;
    private String imageURL4;
    private int productID;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }

    public String getImageURL2() {
        return imageURL2;
    }

    public void setImageURL2(String imageURL2) {
        this.imageURL2 = imageURL2;
    }

    public String getImageURL3() {
        return imageURL3;
    }

    public void setImageURL3(String imageURL3) {
        this.imageURL3 = imageURL3;
    }

    public String getImageURL4() {
        return imageURL4;
    }

    public void setImageURL4(String imageURL4) {
        this.imageURL4 = imageURL4;
    }

    @Override
    public String toString() {
        return "TempWebsiteUpload{" + "imageURL1=" + imageURL1 + ", imageURL2=" + imageURL2 + ", imageURL3=" + imageURL3 + ", imageURL4=" + imageURL4 + ", productID=" + productID + '}';
    }
    
}
