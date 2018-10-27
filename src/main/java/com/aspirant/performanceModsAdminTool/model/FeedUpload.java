/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;

/**
 *
 * @author shrutika
 */
public class FeedUpload implements Serializable {

    private boolean status;
    private int productStatus;
    private boolean mappingRequired;
    private String reason;
    private int lineNo;
    private int warehouseId;
    private String manufacturerName;
    private String productMpn;
    private String productMap;
    private String productMsrp;
    private String productPrice;
    private String productQuantity;
    private String productCondition;
    private String warehouseIdentificationNo;
    private String productWeight;
    private String estimatedShipWeight;
    private String productLength;
    private String productWidth;
    private String productHeight;
    private String upc;
    private String shortDesc;
    private String longDesc;
    private String resizeImageUrl;
    private String largeImageUrl;
    private String shipping;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public boolean isMappingRequired() {
        return mappingRequired;
    }

    public void setMappingRequired(boolean mappingRequired) {
        this.mappingRequired = mappingRequired;
    }

    public String getReason() {
        return reason;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductMpn() {
        return productMpn;
    }

    public void setProductMpn(String productMpn) {
        this.productMpn = productMpn;
    }

    public String getProductMap() {
        return productMap;
    }

    public void setProductMap(String productMap) {
        this.productMap = productMap;
    }

    public String getProductMsrp() {
        return productMsrp;
    }

    public void setProductMsrp(String productMsrp) {
        this.productMsrp = productMsrp;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getWarehouseIdentificationNo() {
        return warehouseIdentificationNo;
    }

    public void setWarehouseIdentificationNo(String warehouseIdentificationNo) {
        this.warehouseIdentificationNo = warehouseIdentificationNo;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getEstimatedShipWeight() {
        return estimatedShipWeight;
    }

    public void setEstimatedShipWeight(String estimatedShipWeight) {
        this.estimatedShipWeight = estimatedShipWeight;
    }

    public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
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

    public String getResizeImageUrl() {
        return resizeImageUrl;
    }

    public void setResizeImageUrl(String resizeImageUrl) {
        this.resizeImageUrl = resizeImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    @Override
    public String toString() {
        return "FeedUpload{" + "status=" + status + ", productStatus=" + productStatus + ", mappingRequired=" + mappingRequired + ", reason=" + reason + ", lineNo=" + lineNo + ", warehouseId=" + warehouseId + ", manufacturerName=" + manufacturerName + ", productMpn=" + productMpn + ", productMap=" + productMap + ", productMsrp=" + productMsrp + ", productPrice=" + productPrice + ", productQuantity=" + productQuantity + ", productCondition=" + productCondition + ", warehouseIdentificationNo=" + warehouseIdentificationNo + ", productWeight=" + productWeight + ", estimatedShipWeight=" + estimatedShipWeight + ", productLength=" + productLength + ", productWidth=" + productWidth + ", productHeight=" + productHeight + ", upc=" + upc + ", shortDesc=" + shortDesc + ", longDesc=" + longDesc + ", resizeImageUrl=" + resizeImageUrl + ", largeImageUrl=" + largeImageUrl + ", shipping=" + shipping + '}';
    }
    
    

}
