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
public class PriceApiResponse {

    private MetaTurn meta;
    private List<PriceResponse> data;

    public MetaTurn getMeta() {
        return meta;
    }

    public void setMeta(MetaTurn meta) {
        this.meta = meta;
    }

    public List<PriceResponse> getData() {
        return data;
    }

    public void setData(List<PriceResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PriceApiResponse{" + "meta=" + meta + ", data=" + data + '}';
    }
    
}
