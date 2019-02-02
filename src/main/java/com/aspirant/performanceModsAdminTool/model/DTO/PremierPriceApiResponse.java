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
public class PremierPriceApiResponse {

    private List<PremierPriceResponse> data;

    public List<PremierPriceResponse> getData() {
        return data;
    }

    public void setData(List<PremierPriceResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PremierPriceApiResponse{" + "data=" + data + '}';
    }
    
}
