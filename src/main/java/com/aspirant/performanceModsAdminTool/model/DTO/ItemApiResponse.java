/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author pk
 */
public class ItemApiResponse {
    private MetaTurn meta;
    private List<ItemResponse> data;
//    private TurnLinks links;
//    private ItemResponse data;

    public MetaTurn getMeta() {
        return meta;
    }

    public void setMeta(MetaTurn meta) {
        this.meta = meta;
    }

    public List<ItemResponse> getData() {
        return data;
    }

    public void setData(List<ItemResponse> data) {
        this.data = data;
    }

  
//    public TurnLinks getLinks() {
//        return links;
//    }
//
//    public void setLinks(TurnLinks links) {
//        this.links = links;
//    }
    @Override
    public String toString() {
        return "ItemApiResponse{" + "meta=" + meta + ", data=" + data + ", links="  + '}';
    }
//        public ItemResponse getData() {
//        return data;
//    }
//
//    public void setData(ItemResponse data) {
//        this.data = data;
//    }
    
//    @Override
//    public String toString() {
//        return "ItemApiResponse{" + "data=" + data + '}';
//    }
    
}
