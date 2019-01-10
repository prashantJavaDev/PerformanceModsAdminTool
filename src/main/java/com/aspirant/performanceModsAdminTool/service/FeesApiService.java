/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.amazonservices.mws.products.model.GetMyFeesEstimateResponse;

/**
 *
 * @author Ritesh
 */
public interface FeesApiService {

    /**
     * get the value of GetMyFeesEstimateResponse
     * @return value of GetMyFeesEstimateResponse
     */
    public GetMyFeesEstimateResponse getMyFeesEstimate();
}
