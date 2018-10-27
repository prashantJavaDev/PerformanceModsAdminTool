/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

/**
 *
 * @author Ritesh
 */
public class MoneyType {
    
    public double amount;
    public String currencyCode;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "MoneyType{" + "amount=" + amount + ", currencyCode=" + currencyCode + '}';
    }
    
}
