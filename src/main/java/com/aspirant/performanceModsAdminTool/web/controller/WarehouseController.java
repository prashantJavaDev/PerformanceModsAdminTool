/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author shrutika
 */
@Controller
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    //upload Feeds
    @RequestMapping(value = "/warehouse/uploadFeed.htm", method = RequestMethod.GET)
    public String uploadFeed(ModelMap model) {
        List<Warehouse> warehouseList = this.warehouseService.getWarehouseList(true);
        model.addAttribute("warehouseList", warehouseList);

        UploadFeed uploadFeed = new UploadFeed();
        model.addAttribute("uploadFeed", uploadFeed);
        return "/warehouse/uploadFeed";
    }

    @RequestMapping(value = "/warehouse/uploadFeed.htm", method = RequestMethod.POST)
    public String postUploadFeed(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, ModelMap model) throws IOException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Feed data validation started...", GlobalConstants.TAG_SYSTEMLOG));
            int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
            String warehouseName = ServletRequestUtils.getStringParameter(request, "warehouseName", null);
            model.addAttribute("warehouseId", warehouseId);
            model.addAttribute("warehouseName", warehouseName);

            List<BadDataDTO> lst = this.warehouseService.saveMultipartFileData(uploadFeed, warehouseId);
            model.addAttribute("lst", lst); 
            return "warehouse/tempUploadResult";
            
        }
    }

    @RequestMapping(value = "/warehouse/saveFeed.htm")
    public String finalUploadFeed(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../warehouse/uploadFeed.htm?msg=msg.actionCancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Feed upload started...", GlobalConstants.TAG_SYSTEMLOG));
            int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
            int rows = this.warehouseService.saveAndUpdateFeed(warehouseId);
            String msg;
            if (rows == -1) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../product/listProduct.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else if (rows == 0) {
                msg = "Products uploaded successfully.";
                return "redirect:../product/listProduct.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            } else {
                msg = rows + " Products uploaded successfully.";
                return "redirect:../product/listProduct.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/warehouse/badProductExcel.htm")
    public void getExcelReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String warehouseName = ServletRequestUtils.getStringParameter(request, "warehouseName", null);
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            List<FeedUpload> productList = this.warehouseService.badFeedforExport();
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=BadFeedData-" + curDate + "-" + warehouseName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Bad Feed Data ");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Manufacturer Name");
            headerRow.addCell("Model Number (MPN)");
            headerRow.addCell("MAP");
            headerRow.addCell("MSRP");
            headerRow.addCell("Price");
            headerRow.addCell("Quantity");
            headerRow.addCell("Condition");
            headerRow.addCell("Warehouse Id No.");
            headerRow.addCell("Weight");
            headerRow.addCell("Est Ship Weight");
            headerRow.addCell("Length");
            headerRow.addCell("Width");
            headerRow.addCell("Height");
            headerRow.addCell("UPC");
            headerRow.addCell("Short Desc");
            headerRow.addCell("Long Desc");
            headerRow.addCell("Resize Image");
            headerRow.addCell("Large Image");
            headerRow.addCell("Shipping");
            headerRow.addCell("Reason");

            mySheet.addRow(headerRow);
            for (FeedUpload data : productList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductMap(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductMsrp(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductPrice(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductQuantity(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductCondition(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getWarehouseIdentificationNo(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductWeight(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getEstimatedShipWeight(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductLength(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductWidth(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductHeight(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getUpc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShortDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getLongDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getResizeImageUrl(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getLargeImageUrl(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShipping(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getReason(), POICell.TYPE_TEXT);
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

    @RequestMapping(value = "/admin/addNewWarehouse.htm", method = RequestMethod.GET)
    public String showAddUserForm(ModelMap model) {
        Warehouse warehouse = new Warehouse();
        warehouse.setActive(true);
        model.addAttribute("warehouse", warehouse);
        return "admin/addNewWarehouse";
    }

    @RequestMapping(value = "/admin/addNewWarehouse.htm", method = RequestMethod.POST)
    public String onAddWarehouseSubmit(@ModelAttribute("warehouse") Warehouse warehouse, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../admin/warehouseList.htm?msg=msg.actionCancelled";
        } else {
            int warehouseId = this.warehouseService.addWarehouse(warehouse);
            if (warehouseId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addNewWarehouse.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.warehouseAddedSuccessfully";
                return "redirect:warehouseList.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/admin/warehouseList.htm", method = RequestMethod.GET)
    public String showUserListPage(ModelMap model) {
        model.addAttribute("warehouseList", this.warehouseService.getWarehouseList(false));
        return "/admin/warehouseList";
    }

    @RequestMapping(value = "/admin/editWarehouse.htm", method = RequestMethod.GET)
    public String showEditWarehouseForm(@RequestParam(value = "warehouseId", required = true) int warehouseId, ModelMap model) {
        Warehouse warehouse = this.warehouseService.getWarehouseByWarehouseId(warehouseId);
        model.addAttribute("warehouse", warehouse);
        return "/admin/editWarehouse";
    }

    @RequestMapping(value = "/admin/editWarehouse.htm", method = RequestMethod.POST)
    public String onEditUserSubmit(@ModelAttribute("warehouse") Warehouse warehouse, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../admin/warehouseList.htm?msg=msg.actionCancelled";
        } else {
            int id = this.warehouseService.updateWarehouse(warehouse);
            if (id == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/editWarehouse.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.warehouseUpdatedSuccessfully";
                return "redirect:../admin/warehouseList.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }
}
