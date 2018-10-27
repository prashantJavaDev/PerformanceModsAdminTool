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
public class Invoice {
    
    private String poNumber;
    private String mpn;
    private double invoiceDifferene;
    private double invoicePrice;
    private Warehouse warehouse;
    private CurrentWarehouseProduct cwp;

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public double getInvoiceDifferene() {
        return invoiceDifferene;
    }

    public void setInvoiceDifferene(double invoiceDifferene) {
        this.invoiceDifferene = invoiceDifferene;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public CurrentWarehouseProduct getCwp() {
        return cwp;
    }

    public void setCwp(CurrentWarehouseProduct cwp) {
        this.cwp = cwp;
    }

    @Override
    public String toString() {
        return "Invoice{" + "poNumber=" + poNumber + ", mpn=" + mpn + ", invoiceDifferene=" + invoiceDifferene + ", invoicePrice=" + invoicePrice + ", warehouse=" + warehouse + ", cwp=" + cwp + '}';
    }

     

    
    
        
}
