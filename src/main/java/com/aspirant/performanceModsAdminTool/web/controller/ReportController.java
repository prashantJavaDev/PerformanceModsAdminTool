/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.exception.CouldNotBuildExcelException;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.AccessLogReportDTO;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.service.ReportService;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ritesh
 */
@Controller
public class ReportController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService WarehouseService;

    @RequestMapping(value = "/report/reportAccessLogExcel.htm")
    public void getAccessLogExcelReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws CouldNotBuildExcelException {
        try {
            String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
            int success = ServletRequestUtils.getIntParameter(request, "success", -1);
            List<AccessLogReportDTO> reportList = this.reportService.getAccessLogReport(startDate, stopDate, userId, success);
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=AccessLog-" + startDate + "_to_" + stopDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Access Log report");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Access dt");
            headerRow.addCell("IP");
            headerRow.addCell("Username");
            headerRow.addCell("Outcome");

            mySheet.addRow(headerRow);

            for (AccessLogReportDTO data : reportList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getAccessDate(), POICell.TYPE_DATETIME);
                dataRow.addCell(data.getIpAddress(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getUsername(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getOutcome(), POICell.TYPE_TEXT);

                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog(io, GlobalConstants.TAG_SYSTEMLOG));
            throw new CouldNotBuildExcelException(io.getMessage());
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            throw new CouldNotBuildExcelException(e.getMessage());
        }
    }

    @RequestMapping(value = "/report/reportAccessLog.htm")
    public String showAccessLogReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        int success = ServletRequestUtils.getIntParameter(request, "success", -1);
        List<AccessLogReportDTO> reportList = this.reportService.getAccessLogReport(startDate, stopDate, userId, success);
        modelMap.addAttribute("reportList", reportList);

        List<User> userList = this.userService.getUserList(false, "");
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("stopDate", stopDate);
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("success", success);
        return "report/reportAccessLog";
    }

    @RequestMapping(value = "/report/agentLoginAndBreakLog.htm")
    public String showAgentLoginAndBreakLog(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        int success = ServletRequestUtils.getIntParameter(request, "success", -1);
        List<AccessLogReportDTO> reportList = this.reportService.getAccessLogReport(startDate, stopDate, userId, success);
        modelMap.addAttribute("reportList", reportList);

        List<User> userList = this.userService.getUserList(false, "");
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("stopDate", stopDate);
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("success", success);
        return "report/agentLoginAndBreakLog";
    }
}
