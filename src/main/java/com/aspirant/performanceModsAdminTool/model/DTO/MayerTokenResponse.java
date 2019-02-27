/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

/**
 *
 * @author pk
 */
public class MayerTokenResponse {

    private String apikey;
    private String expiration;
    private String lastLoginTs;
    private String lastLoginIP;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getLastLoginTs() {
        return lastLoginTs;
    }

    public void setLastLoginTs(String lastLoginTs) {
        this.lastLoginTs = lastLoginTs;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    @Override
    public String toString() {
        return "MayerTokenResponse{" + "apikey=" + apikey + ", expiration=" + expiration + ", lastLoginTs=" + lastLoginTs + ", lastLoginIP=" + lastLoginIP + '}';
    }
}
