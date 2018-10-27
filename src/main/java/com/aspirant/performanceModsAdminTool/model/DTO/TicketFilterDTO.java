/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.io.Serializable;

/**
 *
 * @author Ritesh
 */
public class TicketFilterDTO implements Serializable {

    private int statusMatrixId;
    private int marketplaceId;
    private int ticketTypeId;
    private int companyId;
    private int assignedToId;
    private int ticketStatusId;
    private String orderId;
    private String customerName;
    private String ticketNo;
    private int read;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    
    public int getStatusMatrixId() {
        return statusMatrixId;
    }

    public void setStatusMatrixId(int statusMatrixId) {
        this.statusMatrixId = statusMatrixId;
    }

    public int getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(int marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    public int getTicketStatusId() {
        return ticketStatusId;
    }

    public void setTicketStatusId(int ticketStatusId) {
        this.ticketStatusId = ticketStatusId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "TicketFilterDTO{" + "statusMatrixId=" + statusMatrixId + ", marketplaceId=" + marketplaceId + ", ticketTypeId=" + ticketTypeId + ", companyId=" + companyId + ", assignedToId=" + assignedToId + ", ticketStatusId=" + ticketStatusId + ", customerName=" + customerName + ", ticketNo=" + ticketNo + '}';
    }
}
