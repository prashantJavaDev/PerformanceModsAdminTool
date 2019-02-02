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
public class PremierInventoryApiResponse {

    private List<PremierInventoryResponse> data;

    public List<PremierInventoryResponse> getData() {
        return data;
    }

    public void setData(List<PremierInventoryResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PremierInventoryApiResponse{" + "data=" + data + '}';
    }

}
