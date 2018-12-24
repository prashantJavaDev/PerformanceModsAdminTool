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
public class TokenResponse {

//    {"access_token":"4845da400d6b0f343e3b525c7c7a71d1a4390fef5","expires_in":3600,"token_type":"Bearer","scope":null
    private String access_token;
    private int exprires_in;
    private String token_type;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExprires_in() {
        return exprires_in;
    }

    public void setExprires_in(int exprires_in) {
        this.exprires_in = exprires_in;
    }


    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "TokenResponse{" + "access_token=" + access_token + ", exprires_in=" + exprires_in + ", token_type=" + token_type + ", scope=" + scope + '}';
    }

   
    
}
