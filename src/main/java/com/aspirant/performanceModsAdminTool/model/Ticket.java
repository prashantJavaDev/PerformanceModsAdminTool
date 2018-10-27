/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ritesh
 */
public class Ticket implements Serializable {

    private int ticketId;
    private String ticketNo;
    private Marketplace marketplace;
    private TicketType ticketType;
    private TicketPriority ticketPriority;
    private String orderId;
    private String trackingId;
    private String trackingCarrierName;
    private String description;
    private String details;
    private Date assignedOn;
    private User assignedTo;
    private Company company;
    private String customerName;
    private String custPhoneNumber;
    private String custEmailId;
    private TicketStatus ticketStatus;
    private Date completedOn;
    private String notes;
    private Date createDate;
    private String createdDateStr;
    private Date lastModifiedDate;
    private User createdBy;
    private User createdById;
    private User lastModifiedBy;
    private boolean read;
    private int tatInHours;

    public User getCreatedById() {
        return createdById;
    }

    public void setCreatedById(User createdById) {
        this.createdById = createdById;
    }
    
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingCarrierName() {
        return trackingCarrierName;
    }

    public void setTrackingCarrierName(String trackingCarrierName) {
        this.trackingCarrierName = trackingCarrierName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(Date assignedOn) {
        this.assignedOn = assignedOn;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustPhoneNumber() {
        return custPhoneNumber;
    }

    public void setCustPhoneNumber(String custPhoneNumber) {
        this.custPhoneNumber = custPhoneNumber;
    }

    public String getCustEmailId() {
        return custEmailId;
    }

    public void setCustEmailId(String custEmailId) {
        this.custEmailId = custEmailId;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getTatInHours() {
        return tatInHours;
    }

    public void setTatInHours(int tatInHours) {
        this.tatInHours = tatInHours;
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", ticketNo=" + ticketNo + ", marketplace=" + marketplace + ", ticketType=" + ticketType + ", ticketPriority=" + ticketPriority + ", orderId=" + orderId + ", trackingId=" + trackingId + ", trackingCarrierName=" + trackingCarrierName + ", description=" + description + ", details=" + details + ", assignedOn=" + assignedOn + ", assignedTo=" + assignedTo + ", company=" + company + ", customerName=" + customerName + ", custPhoneNumber=" + custPhoneNumber + ", custEmailId=" + custEmailId + ", ticketStatus=" + ticketStatus + ", completedOn=" + completedOn + ", notes=" + notes + ", createDate=" + createDate + ", createdDateStr=" + createdDateStr + ", lastModifiedDate=" + lastModifiedDate + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", read=" + read + ", tatInHours=" + tatInHours + '}';
    }

}
