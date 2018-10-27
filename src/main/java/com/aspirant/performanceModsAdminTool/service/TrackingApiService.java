/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.amazonaws.mws.model.SubmitFeedRequest;
import com.amazonaws.mws.model.SubmitFeedResponse;

/**
 *
 * @author Ritesh
 */
public interface TrackingApiService {
    
    public SubmitFeedResponse submitFeed();
    
    public SubmitFeedResponse submitAcknowledgementFeed();
    
}
