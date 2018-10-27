/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author shrutika
 */
public class Product implements Serializable {

    private int productId;
    private String productName;
    private Manufacturer manufacturer;
    private String manufacturerMpn;
    private String productTitle;
    private String performanceModsMpn;
    private double productMap;
    private double productMsrp;
    private double productWeight;
    private double estShippingWt;
    private double productLength;
    private double productWidth;
    private double productHeight;
    private String upc;
    private String asin;
    private String neweggItemId;
    private String neweggB2BItemId;
    private MainCategory mainCategory;
    private SubCategory subCategory1;
    private ChildOfSubCategory childCategory;
    private ChildOfChildCategory childOfChildCategory;
    private String shortDesc;
    private String longDesc;
    private boolean returnable;
    private String keywords;
    private MultipartFile resizeImageFile;
    private String resizeImageUrl;
    private MultipartFile largeImageFile1;
    private MultipartFile largeImageFile2;
    private MultipartFile largeImageFile3;
    private MultipartFile largeImageFile4;
    private String largeImageUrl1;
    private String largeImageUrl2;
    private String largeImageUrl3;
    private String largeImageUrl4;
    private String notes;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;
    private ProductStatus productStatus;
    private String warehouseName;
    private String warehouseMpn;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerMpn() {
        return manufacturerMpn;
    }

    public void setManufacturerMpn(String manufacturerMpn) {
        this.manufacturerMpn = manufacturerMpn;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getperformanceModsMpn() {
        return performanceModsMpn;
    }

    public void setperformanceModsMpn(String performanceModsMpn) {
        this.performanceModsMpn = performanceModsMpn;
    }

    public double getProductMap() {
        return productMap;
    }

    public void setProductMap(double productMap) {
        this.productMap = productMap;
    }

    public double getProductMsrp() {
        return productMsrp;
    }

    public void setProductMsrp(double productMsrp) {
        this.productMsrp = productMsrp;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public double getProductLength() {
        return productLength;
    }

    public void setProductLength(double productLength) {
        this.productLength = productLength;
    }

    public double getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(double productWidth) {
        this.productWidth = productWidth;
    }

    public double getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(double productHeight) {
        this.productHeight = productHeight;
    }

    public double getEstShippingWt() {
        return estShippingWt;
    }

    public void setEstShippingWt(double estShippingWt) {
        this.estShippingWt = estShippingWt;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public SubCategory getSubCategory1() {
        return subCategory1;
    }

    public void setSubCategory1(SubCategory subCategory1) {
        this.subCategory1 = subCategory1;
    }


    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public boolean isReturnable() {
        return returnable;
    }

    public void setReturnable(boolean returnable) {
        this.returnable = returnable;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public MultipartFile getResizeImageFile() {
        return resizeImageFile;
    }

    public void setResizeImageFile(MultipartFile resizeImageFile) {
        this.resizeImageFile = resizeImageFile;
    }

    public String getResizeImageUrl() {
        return resizeImageUrl;
    }

    public void setResizeImageUrl(String resizeImageUrl) {
        this.resizeImageUrl = resizeImageUrl;
    }

    public MultipartFile getLargeImageFile1() {
        return largeImageFile1;
    }

    public void setLargeImageFile1(MultipartFile largeImageFile1) {
        this.largeImageFile1 = largeImageFile1;
    }

    public MultipartFile getLargeImageFile2() {
        return largeImageFile2;
    }

    public void setLargeImageFile2(MultipartFile largeImageFile2) {
        this.largeImageFile2 = largeImageFile2;
    }

    public MultipartFile getLargeImageFile3() {
        return largeImageFile3;
    }

    public void setLargeImageFile3(MultipartFile largeImageFile3) {
        this.largeImageFile3 = largeImageFile3;
    }

    public MultipartFile getLargeImageFile4() {
        return largeImageFile4;
    }

    public void setLargeImageFile4(MultipartFile largeImageFile4) {
        this.largeImageFile4 = largeImageFile4;
    }

    public String getLargeImageUrl1() {
        return largeImageUrl1;
    }

    public void setLargeImageUrl1(String largeImageUrl1) {
        this.largeImageUrl1 = largeImageUrl1;
    }

    public String getLargeImageUrl2() {
        return largeImageUrl2;
    }

    public void setLargeImageUrl2(String largeImageUrl2) {
        this.largeImageUrl2 = largeImageUrl2;
    }

    public String getLargeImageUrl3() {
        return largeImageUrl3;
    }

    public void setLargeImageUrl3(String largeImageUrl3) {
        this.largeImageUrl3 = largeImageUrl3;
    }

    public String getLargeImageUrl4() {
        return largeImageUrl4;
    }

    public void setLargeImageUrl4(String largeImageUrl4) {
        this.largeImageUrl4 = largeImageUrl4;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseMpn() {
        return warehouseMpn;
    }

    public void setWarehouseMpn(String warehouseMpn) {
        this.warehouseMpn = warehouseMpn;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getNeweggItemId() {
        return neweggItemId;
    }

    public void setNeweggItemId(String neweggItemId) {
        this.neweggItemId = neweggItemId;
    }

    public String getNeweggB2BItemId() {
        return neweggB2BItemId;
    }

    public void setNeweggB2BItemId(String neweggB2BItemId) {
        this.neweggB2BItemId = neweggB2BItemId;
    }  

    public ChildOfSubCategory getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(ChildOfSubCategory childCategory) {
        this.childCategory = childCategory;
    }

    public ChildOfChildCategory getChildOfChildCategory() {
        return childOfChildCategory;
    }

    public void setChildOfChildCategory(ChildOfChildCategory childOfChildCategory) {
        this.childOfChildCategory = childOfChildCategory;
    }

    
}
