/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.ReportDao;
import com.aspirant.performanceModsAdminTool.model.DTO.AccessLogReportDTO;
import com.aspirant.performanceModsAdminTool.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gaurao
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success) {
        return this.reportDao.getAccessLogReport(startDate, stopDate, userId, success);
    }

}
