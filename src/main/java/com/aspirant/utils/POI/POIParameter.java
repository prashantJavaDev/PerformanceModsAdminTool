/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

/**
 *
 * @author akil
 */
public class POIParameter {

    private String label;
    private Object value;
    private short valueType;

    public POIParameter() {
        this.valueType = POICell.TYPE_AUTO;
    }

    public POIParameter(String label, Object value) {
        this.label = label;
        this.value = value;
        this.valueType = POICell.TYPE_AUTO;
    }

    public POIParameter(String label, Object value, short valueType) {
        this.label = label;
        this.value = value;
        this.valueType = valueType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public short getValueType() {
        return valueType;
    }

    public void setValueType(short valueType) {
        this.valueType = valueType;
    }
    
}
