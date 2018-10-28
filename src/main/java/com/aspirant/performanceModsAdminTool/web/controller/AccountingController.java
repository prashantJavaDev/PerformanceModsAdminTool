/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Invoice;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.InvoiceService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.impl.bootstrap.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Pallavi
 */
@Controller
public class AccountingController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "/admin/uploadInvoice.htm", method = RequestMethod.GET)
    public String uploadInvoiceFeedGet(ModelMap modelMap) {
        List<Warehouse> warehouseList = this.warehouseService.getWarehouseList(true);
        modelMap.addAttribute("warehouseList", warehouseList);

        UploadFeed uploadFeed = new UploadFeed();
        modelMap.addAttribute("uploadFeed", uploadFeed);
        return "/admin/uploadInvoice";
    }

    @RequestMapping(value = "/admin/uploadInvoice.htm", method = RequestMethod.POST)
    public String uploadInvoiceFeedPost(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
        String fileName = uploadFeed.getMultipartFile().getOriginalFilename();
        fileName=fileName.replace(".", ",");
        String[] fileExtension  =fileName.split(","); 
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            int result = this.invoiceService.saveMultipartFileData(uploadFeed, warehouseId,fileExtension[1]);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/uploadInvoice.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Invoice feed uploaded successfully.";
                return "redirect:../admin/invoiceDifferenceDetail.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/admin/invoiceDifferenceDetail")
    public String invoiceDifferenceListGet(ModelMap modelMap) {
        List<Invoice> invoice = this.invoiceService.invoiceDifference();
        modelMap.addAttribute("invoice", invoice);
        return "/admin/invoiceDifferenceDetail";
    }

    @RequestMapping(value = "/admin/invoiceDownload.htm")
    public void getPriceDifferenceDetailReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            List<Invoice> invoice = this.invoiceService.invoiceDifference();

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=Invoice_Difference_Detail-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Invoice_DifferenceDetail");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("PO Number");
            headerRow.addCell("Warehouse Name");
            headerRow.addCell("Model Number(MPN)");
            headerRow.addCell("Feed Price");
            headerRow.addCell("Shipping");
            headerRow.addCell("Invoice Price");
            headerRow.addCell("Difference");
            mySheet.addRow(headerRow);
            for (Invoice data : invoice) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getPoNumber(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getWarehouse().getWarehouseName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCwp().getPrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getCwp().getShipping(),POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getInvoicePrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getInvoiceDifferene(), POICell.TYPE_DECIMAL);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(io, GlobalConstants.TAG_SYSTEMLOG));
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

}
