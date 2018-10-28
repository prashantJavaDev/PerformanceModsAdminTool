/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.aspirant.performanceModsAdminTool.service.ListingService;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ritesh
 */
@Controller
public class ListingController {

    @Autowired
    private ListingService listingService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "listing/marketplaceFeesUpload.htm", method = RequestMethod.GET)
    public String upleadFees(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        return "listing/marketplaceFeesUpload";
    }

    @RequestMapping(value = "listing/marketplaceFeesUpload.htm", method = RequestMethod.POST)
    public String uploadFeesSubmit(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Fees data validation started...", GlobalConstants.TAG_SYSTEMLOG));
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            int result = this.listingService.saveMultipartFileData(uploadFeed, marketplaceId);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../listing/marketplaceFeesUpload.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Fees uploaded successfully.";
                return "redirect:../listing/marketplaceFeesUpload.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "listing/selectMarketplace.htm", method = RequestMethod.GET)
    public String selectMarketplace(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        return "listing/selectMarketplace";
    }

    @RequestMapping(value = "listing/marketplaceListing.htm", method = RequestMethod.GET)
    public String createListing(HttpServletRequest request, ModelMap model) {
        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        String marketplaceName = ServletRequestUtils.getStringParameter(request, "marketplaceName", null);
        //List<Integer> profitPercentageList = this.listingService.getProfitPercentageList();
        List<Manufacturer> manufacturerList = this.productService.getListOfManufacturer();
        model.addAttribute("warehouseList", this.warehouseService.getWarehouseList(true));
        //model.addAttribute("profitPercentageList", profitPercentageList);
        model.addAttribute("manufacturerList", manufacturerList);
        model.addAttribute("marketplaceId", marketplaceId);
        model.addAttribute("marketplaceName", marketplaceName);
        model.addAttribute("rowCount", 0);
        model.addAttribute("pageCount", 0);
        model.addAttribute("pageNo", 0);
        return "listing/marketplaceListing";
    }

    @RequestMapping(value = "listing/marketplaceListing.htm", method = RequestMethod.POST)
    public String getUpdatedListings(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String go = ServletRequestUtils.getStringParameter(request, "go", null);
        int pageNo = 0;

        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        String marketplaceName = ServletRequestUtils.getStringParameter(request, "marketplaceName", null);
        modelMap.addAttribute("marketplaceId", marketplaceId);
        modelMap.addAttribute("marketplaceName", marketplaceName);
        int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
        String marketplaceSku = ServletRequestUtils.getStringParameter(request, "marketplaceSku", null);
        int[] manufacturerId = ServletRequestUtils.getIntParameters(request, "manufacturerIds");
        int manufacturerIds[] = ServletRequestUtils.getIntParameters(request, "manufacturerIds");

        List<Manufacturer> selectedManufacturersList = new LinkedList<Manufacturer>();

        for (int manufacturerId1 : manufacturerIds) {
            Manufacturer m = new Manufacturer();
            m.setManufacturerId(manufacturerId1);
            selectedManufacturersList.add(m);
        }
        modelMap.addAttribute("selectedManufacturersList", selectedManufacturersList);
        modelMap.addAttribute("manufacturerIds", manufacturerIds);
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        if (go != null) {
            pageNo = 1;
        }
        int rowCount = this.listingService.getListingCount(marketplaceId, warehouseId, marketplaceSku, false, manufacturerId);
        modelMap.addAttribute("rowCount", rowCount);

        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        modelMap.addAttribute("pageCount", pageCount);

        if (pageNo > rowCount) {
            pageNo = 1;
        }
        modelMap.addAttribute("pageNo", pageNo);

        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        modelMap.addAttribute("marketplaceId", marketplaceId);
        modelMap.addAttribute("warehouseId", warehouseId);
        modelMap.addAttribute("marketplaceSku", marketplaceSku);
//        modelMap.addAttribute("manufacturerId", manufacturerId);
        modelMap.addAttribute("marketplaceList", marketplaceList);
        modelMap.addAttribute("manufacturerList", this.productService.getListOfManufacturer());
        modelMap.addAttribute("warehouseList", this.warehouseService.getWarehouseList(false));
        modelMap.addAttribute("listingResult", this.listingService.getListingList(marketplaceId, warehouseId, marketplaceSku, pageNo, false, manufacturerId));
        return "listing/marketplaceListing";
    }

    @RequestMapping(value = "listing/marketplaceListing.htm", method = RequestMethod.POST, params = "btnSubmit")
    public String UpdateListings(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws UnsupportedEncodingException {
        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        String marketplaceName = ServletRequestUtils.getStringParameter(request, "marketplaceName", null);
        modelMap.addAttribute("marketplaceId", marketplaceId);
        modelMap.addAttribute("marketplaceName", marketplaceName);
        int result = this.listingService.UpdateListings(marketplaceId);
        if (result == 0) {
            String error = "Sorry! Your data not saved.";
            return "redirect:../listing/marketplaceListing.htm?error=" + URLEncoder.encode(error, "UTF-8");
        } else {
            String msg = "Listings confirmed successfully. You can now export data in excel.";
            return "redirect:../listing/exportMarketplaceListing.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
        }
    }

    @RequestMapping(value = "listing/exportMarketplaceListing.htm")
    public String getExportListings(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        int[] manufacturerId = ServletRequestUtils.getIntParameters(request, "manufacturerIds");
        int manufacturerIds[] = ServletRequestUtils.getIntParameters(request, "manufacturerIds");

        List<Manufacturer> selectedManufacturersList = new LinkedList<Manufacturer>();

        for (int manufacturerId1 : manufacturerIds) {
            Manufacturer m = new Manufacturer();
            m.setManufacturerId(manufacturerId1);
            selectedManufacturersList.add(m);
        }
        modelMap.addAttribute("selectedManufacturersList", selectedManufacturersList);
        modelMap.addAttribute("manufacturerIds", manufacturerIds);

        //reportHeader.setService(selectedServiceList);
        int pageNo = 0;
        String go = ServletRequestUtils.getStringParameter(request, "btnSubmit", null);
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        if (go != null) {
            pageNo = 1;
        }
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        List<Manufacturer> manufacturerList = this.productService.getListOfManufacturer();
        int rowCount = this.listingService.getListingCount(marketplaceId, 0, "", true, manufacturerId);
        modelMap.addAttribute("rowCount", rowCount);
        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        modelMap.addAttribute("pageCount", pageCount);
        modelMap.addAttribute("marketplaceId", marketplaceId);
        modelMap.addAttribute("marketplaceList", marketplaceList);
        modelMap.addAttribute("manufacturerList", manufacturerList);
        if (pageNo > rowCount) {
            pageNo = 1;
        }
        modelMap.addAttribute("pageNo", pageNo);
        modelMap.addAttribute("listingResult", this.listingService.getListingList(marketplaceId, 0, "", pageNo, true, manufacturerId));
        return "listing/exportMarketplaceListing";
    }

    @RequestMapping(value = "listing/listingExcel.htm")
    public void getListingExcelReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            int[] manufacturerId = ServletRequestUtils.getIntParameters(request, "manufacturerId");
            List<ProductListingDTO> listingData = this.listingService.getListingList(marketplaceId, 0, "", -1, true, manufacturerId);
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=Listings-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "ProductListings");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Manufacturer");
            headerRow.addCell("MPN");
            headerRow.addCell("Marketplace SKU");
            headerRow.addCell("Listing ID");
            headerRow.addCell("Listing Quantity");
            headerRow.addCell("Warehouse Quantity");
            headerRow.addCell("Listing price");
            headerRow.addCell("Warehouse Price");
            headerRow.addCell("Shipping");
            headerRow.addCell("Commission");
            headerRow.addCell("Profit");
            headerRow.addCell("Pack");
            headerRow.addCell("Warehouse");

            mySheet.addRow(headerRow);
            for (ProductListingDTO data : listingData) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturerMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getSku(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getListingId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCurrentQuantity(), POICell.TYPE_INTEGER);
                dataRow.addCell(data.getWarehouseQuantity(), POICell.TYPE_INTEGER);
                dataRow.addCell(data.getCurrentPrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getWarehousePrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getShipping(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getCommission(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getProfit(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getPack(), POICell.TYPE_INTEGER);
                dataRow.addCell(data.getWarehouse(), POICell.TYPE_TEXT);
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

    @RequestMapping(value = "/listing/exportMarketPlaceFees.htm")
    public String exportMarketPlaceFeesPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        int pageNo = 0;
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        int rowCount = this.listingService.getExportMarketplaceFeesCount(marketplaceId);
        model.addAttribute("rowCount", rowCount);
        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);
        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);
        List<Listing> feesList = this.listingService.exportMarketplaceFees(marketplaceId, pageNo);
        model.addAttribute("feesList", feesList);
        model.addAttribute("marketplaceId", marketplaceId);
        return "/listing/exportMarketPlaceFees";
    }

    @RequestMapping(value = "/listing/exportMarketPlaceFeesExel.htm")
    public void getExportMarketPlaceFeesExcelList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
 
            List<Listing> marketPlaceFeesList = this.listingService.exportMarketplaceFeesForExcel(marketplaceId);
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=ExportMarketPlace_FeesList-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "ExportMarketPlaceFeesList");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("MarketPlace_Listing_Id");
            headerRow.addCell("MarketPlace SKU");
            headerRow.addCell("Current Price");
            headerRow.addCell("Commission");
            mySheet.addRow(headerRow);
            for (Listing data : marketPlaceFeesList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getMarketplaceListingId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getSku(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCurrentPrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getCommission(), POICell.TYPE_DECIMAL);
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
    
    //controller for upload listing, get method 

    @RequestMapping(value = "listing/uploadMarketplaceListing.htm", method = RequestMethod.GET)
    public String bulkListingUploadFeed(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        return "/listing/uploadMarketplaceListing";
    }
    
    //controller for bulk order tracking in bulk, post method

    @RequestMapping(value = "listing/uploadMarketplaceListing.htm", method = RequestMethod.POST)
    public String bulkListingUploadFeedSubmit(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Listing upload data started...", GlobalConstants.TAG_SYSTEMLOG));
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            int result = this.listingService.saveMultipartFileData1(uploadFeed, marketplaceId);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../listing/uploadMarketplaceListing.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Listings uploaded successfully.";
                return "redirect:../listing/uploadMarketplaceListing.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }
}
