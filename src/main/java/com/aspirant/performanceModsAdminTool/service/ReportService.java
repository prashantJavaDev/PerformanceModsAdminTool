/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.DTO.AccessLogReportDTO;
import java.util.List;

/**
 *
 * @author gaurao
 */
public interface ReportService {

    /**
     * Method to get the report of access log from specified date, userId,
     * success
     *
     * @param startDate statrtDate is a date from when you want to get the
     * report
     * @param stopDate stopDate is a date till when you want to get the report
     * @param userId userId is used to get the report from a particular user
     * @param success success is used to get the report as per the requirement
     * i.e. succeed/failed
     * @return Returns the list of access log report
     */
    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success);

}
