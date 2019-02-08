/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.List;

/**
 *
 * @author pk
 */
public class OrderDTO {

    private String environment;
    private String po_number;
    private String order_notes;
    private List<LocationsDTO> locations;
    private List<LogisticsDTO> expedited_logistics;
    private boolean acknowledge_prop_65;
    private RecipientDTO recipient;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getPo_number() {
        return po_number;
    }

    public void setPo_number(String po_number) {
        this.po_number = po_number;
    }

    public String getOrder_notes() {
        return order_notes;
    }

    public void setOrder_notes(String order_notes) {
        this.order_notes = order_notes;
    }

    public List<LocationsDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsDTO> locations) {
        this.locations = locations;
    }

    public List<LogisticsDTO> getExpedited_logistics() {
        return expedited_logistics;
    }

    public void setExpedited_logistics(List<LogisticsDTO> expedited_logistics) {
        this.expedited_logistics = expedited_logistics;
    }

    public boolean isAcknowledge_prop_65() {
        return acknowledge_prop_65;
    }

    public void setAcknowledge_prop_65(boolean acknowledge_prop_65) {
        this.acknowledge_prop_65 = acknowledge_prop_65;
    }

    public RecipientDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientDTO recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "OrderDTO{" + "environment=" + environment + ", po_number=" + po_number + ", order_notes=" + order_notes + ", locations=" + locations + ", expedited_logistics=" + expedited_logistics + ", acknowledge_prop_65=" + acknowledge_prop_65 + ", recipient=" + recipient + '}';
    }

}
