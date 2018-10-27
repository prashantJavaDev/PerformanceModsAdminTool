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
public class TicketType implements Serializable {

    private int ticketTypeId;
    private String ticketTypeDesc;
    private boolean active;
    private String ticketCode;
    private TicketPriority priority;

    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketTypeDesc() {
        return ticketTypeDesc;
    }

    public void setTicketTypeDesc(String ticketTypeDesc) {
        this.ticketTypeDesc = ticketTypeDesc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.ticketTypeId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TicketType other = (TicketType) obj;
        if (this.ticketTypeId != other.ticketTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketType{" + "ticketTypeId=" + ticketTypeId + ", ticketTypeDesc=" + ticketTypeDesc + ", active=" + active + ", ticketCode=" + ticketCode + ", priority=" + priority + '}';
    }

}
