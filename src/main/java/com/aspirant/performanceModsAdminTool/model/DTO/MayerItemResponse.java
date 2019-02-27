/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

/**
 *
 * @author pk
 */
public class MayerItemResponse {

    private String ItemNumber;
    private String ManufacturerID;
    private String ItemDescription;
    private String PartStatus;
    private String Kit;
    private String Kit_Only;
    private String UP;
    private String LTL_Required;
    private double Length;
    private double Width;
    private double Height;
    private double Weight;
    private String ManufacturerName;
    private double QtyAvailable;
    private double SuggestedRetailPrice;
    private double JobberPrice;
    private Double MinAdvertisedPrice;
    private double CustomerPrice;
    private String Oversize;
    private String Additional_andling_Charge;

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String ItemNumber) {
        this.ItemNumber = ItemNumber;
    }

    public String getManufacturerID() {
        return ManufacturerID;
    }

    public void setManufacturerID(String ManufacturerID) {
        this.ManufacturerID = ManufacturerID;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String ItemDescription) {
        this.ItemDescription = ItemDescription;
    }

    public String getPartStatus() {
        return PartStatus;
    }

    public void setPartStatus(String PartStatus) {
        this.PartStatus = PartStatus;
    }

    public String getKit() {
        return Kit;
    }

    public void setKit(String Kit) {
        this.Kit = Kit;
    }

    public String getKit_Only() {
        return Kit_Only;
    }

    public void setKit_Only(String Kit_Only) {
        this.Kit_Only = Kit_Only;
    }

    public String getUP() {
        return UP;
    }

    public void setUP(String UP) {
        this.UP = UP;
    }

    public String getLTL_Required() {
        return LTL_Required;
    }

    public void setLTL_Required(String LTL_Required) {
        this.LTL_Required = LTL_Required;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double Length) {
        this.Length = Length;
    }

    public double getWidth() {
        return Width;
    }

    public void setWidth(double Width) {
        this.Width = Width;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double Height) {
        this.Height = Height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String ManufacturerName) {
        this.ManufacturerName = ManufacturerName;
    }

    public double getQtyAvailable() {
        return QtyAvailable;
    }

    public void setQtyAvailable(double QtyAvailable) {
        this.QtyAvailable = QtyAvailable;
    }

    public double getSuggestedRetailPrice() {
        return SuggestedRetailPrice;
    }

    public void setSuggestedRetailPrice(double SuggestedRetailPrice) {
        this.SuggestedRetailPrice = SuggestedRetailPrice;
    }

    public double getJobberPrice() {
        return JobberPrice;
    }

    public void setJobberPrice(double JobberPrice) {
        this.JobberPrice = JobberPrice;
    }

    public Double getMinAdvertisedPrice() {
        return MinAdvertisedPrice;
    }

    public void setMinAdvertisedPrice(Double MinAdvertisedPrice) {
        this.MinAdvertisedPrice = MinAdvertisedPrice;
    }

    public double getCustomerPrice() {
        return CustomerPrice;
    }

    public void setCustomerPrice(double CustomerPrice) {
        this.CustomerPrice = CustomerPrice;
    }

    public String getOversize() {
        return Oversize;
    }

    public void setOversize(String Oversize) {
        this.Oversize = Oversize;
    }

    public String getAdditional_andling_Charge() {
        return Additional_andling_Charge;
    }

    public void setAdditional_andling_Charge(String Additional_andling_Charge) {
        this.Additional_andling_Charge = Additional_andling_Charge;
    }

    @Override
    public String toString() {
        return "MayerItemResponse{" + "ItemNumber=" + ItemNumber + ", ManufacturerID=" + ManufacturerID + ", ItemDescription=" + ItemDescription + ", PartStatus=" + PartStatus + ", Kit=" + Kit + ", Kit_Only=" + Kit_Only + ", UP=" + UP + ", LTL_Required=" + LTL_Required + ", Length=" + Length + ", Width=" + Width + ", Height=" + Height + ", Weight=" + Weight + ", ManufacturerName=" + ManufacturerName + ", QtyAvailable=" + QtyAvailable + ", SuggestedRetailPrice=" + SuggestedRetailPrice + ", JobberPrice=" + JobberPrice + ", MinAdvertisedPrice=" + MinAdvertisedPrice + ", CustomerPrice=" + CustomerPrice + ", Oversize=" + Oversize + ", Additional_andling_Charge=" + Additional_andling_Charge + '}';
    }
    

}
