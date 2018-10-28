/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.aspirant.performanceModsAdminTool.service.TicketService;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ritesh
 */
@Controller
public class MarketplaceOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/order/marketplaceOrderList.htm")
    public String showMarketplaceOrdersList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String go = ServletRequestUtils.getStringParameter(request, "btnSubmit", null);
        int pageNo = 0;
        int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
        String marketplaceOrderId = ServletRequestUtils.getStringParameter(request, "marketplaceOrderId", null);
        String poNumber = ServletRequestUtils.getStringParameter(request, "poNumber", null);
        String customerName = ServletRequestUtils.getStringParameter(request, "customerName", null);
        String marketplaceSku = ServletRequestUtils.getStringParameter(request, "marketplaceSku", null);
        String marketplaceListingId = ServletRequestUtils.getStringParameter(request, "marketplaceListingId", null);
        String orderStatus = ServletRequestUtils.getStringParameter(request, "orderStatus", null);
        String fulfillmentChannel = ServletRequestUtils.getStringParameter(request, "fulfillmentChannel", null);
        String startDate = null;
        String stopDate = null;

        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDate = ServletRequestUtils.getStringParameter(request, "startDate", df.format(date.getTime()));
        stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

        if (go != null) {
            pageNo = 1;
        }

        int rowCount = this.orderService.getOrderCount(marketplaceId, marketplaceOrderId, poNumber, customerName, marketplaceSku, marketplaceListingId, orderStatus, fulfillmentChannel, startDate, stopDate);

        model.addAttribute("rowCount", rowCount);

        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);

        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);

        model.addAttribute("marketplaceOrderList", this.orderService.getOrderList(marketplaceId, marketplaceOrderId, poNumber, pageNo, customerName, marketplaceSku, marketplaceListingId, orderStatus, fulfillmentChannel, startDate, stopDate));
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        model.addAttribute("marketplaceId", marketplaceId);
        model.addAttribute("marketplaceOrderId", marketplaceOrderId);
        model.addAttribute("poNumber", poNumber);
        model.addAttribute("customerName", customerName);
        model.addAttribute("marketplaceSku", marketplaceSku);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("fulfillmentChannel", fulfillmentChannel);
        model.addAttribute("startDate", startDate);
        model.addAttribute("stopDate", stopDate);
        return "/order/marketplaceOrderList";
    }

    @RequestMapping(value = "/order/orderDetails.htm")
    public String showOrderDetailsForm(HttpServletRequest request, ModelMap model) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../order/marketplaceOrderList.htm?msg=msg.actionCancelled";
        } else {
            String marketplaceOrderId = ServletRequestUtils.getStringParameter(request, "marketplaceOrderId", null);
            String marketplaceSku = ServletRequestUtils.getStringParameter(request, "marketplaceSku", null);
            String startDate = ServletRequestUtils.getStringParameter(request, "start", null);
            String stopDate = ServletRequestUtils.getStringParameter(request, "stop", null);

            String poNumber = this.orderService.getPoNumberBymarketplaceOrderID(marketplaceOrderId);
            model.addAttribute("poNumber", poNumber);

            Order order = this.orderService.getorderBymarketplaceOrderID(marketplaceOrderId);
            model.addAttribute("order", order);

            List<Warehouse> warehouseList = this.warehouseService.getWarehouseList(true);
            model.addAttribute("warehouseList", warehouseList);

            String orderStatus = this.orderService.getOrderStatusOnMarketplaceOrderId(marketplaceOrderId);
            if (!"Canceled".equals(orderStatus) && !"Shipped".equals(orderStatus)) {
                List<ProductDetails> productList = this.orderService.getProductListOnMarketplaceSku(marketplaceSku);
                //System.out.println("Product List------->" + productList);
                model.addAttribute("productList", productList);
            }

            List<Order> orderTrans = this.orderService.getorderTransBymarketplaceOrderID(marketplaceOrderId);
            model.addAttribute("orderTrans", orderTrans);

            List<Ticket> ticketList = this.ticketService.getTicketListByOrderId(marketplaceOrderId);
            int tktCount = this.orderService.getCountOfTicketAssignedToOrder(marketplaceOrderId);
            model.addAttribute("ticketList", ticketList);
            model.addAttribute("tktCount", tktCount);
            model.addAttribute("startDate", startDate);
            model.addAttribute("stopDate", stopDate);

            return "/order/orderDetails";
        }
    }

    @RequestMapping(value = "/order/exportProcessingSheet.htm")
    public String showExportProcessingSheetForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String go = ServletRequestUtils.getStringParameter(request, "btnSubmit", null);
        int pageNo = 0;

        int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);

        String currentDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        if (go != null) {
            pageNo = 1;
        }
        int rowCount = this.orderService.getProcessOrderCount(warehouseId, currentDate);

        model.addAttribute("rowCount", rowCount);

        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);

        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);

        List<Warehouse> warehouseList = this.warehouseService.getWarehouseList(true);
        model.addAttribute("warehouseList", warehouseList);
        model.addAttribute("listingResult", this.orderService.getProcessOrderList(warehouseId, currentDate));
        return "/order/exportProcessingSheet";
    }

    @RequestMapping(value = "order/processingSheetExcel.htm")
    public void getProcessingSheetExcelReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
            List<ProcessingSheet> processingSheetList = this.orderService.getProcessOrderList(warehouseId, curDate);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=Order_Processing_Sheet-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "OrderProcessingSheet");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("PO Number");
            headerRow.addCell("Warehouse Name");
            headerRow.addCell("Manufacturer Name");
            headerRow.addCell("Model Number(MPN)");
            headerRow.addCell("Quantity");
            headerRow.addCell("Price");
            headerRow.addCell("Ship To Name");
            headerRow.addCell("Shipping Address");
            headerRow.addCell("City");
            headerRow.addCell("State");
            headerRow.addCell("Zip");
            headerRow.addCell("Phone");
            mySheet.addRow(headerRow);
            for (ProcessingSheet data : processingSheetList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getWarehouseId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getWarehouseName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getMPN(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getQuantity(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getPrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getShipToName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShippingAddressLine1(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShippingAddressLine2(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShippingAddressLine3(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCity(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getState(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getZip(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getPhoneNumber(), POICell.TYPE_TEXT);
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

    @RequestMapping(value = "/order/orderTracking.htm", method = RequestMethod.GET)
    public String showOrderTrackingForm(ModelMap model) {
        Order order = new Order();
        List<Warehouse> warehouseList = this.warehouseService.getWarehouseList(true);
        model.addAttribute("warehouseList", warehouseList);
        model.addAttribute("order", order);

        return "/order/orderTracking";
    }

    @RequestMapping(value = "order/orderTracking.htm", method = RequestMethod.POST)
    public String onAddmanufacturerSubmit(@ModelAttribute("order") Order order, HttpServletRequest request) throws UnsupportedEncodingException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
            int trackingId = this.orderService.addOrderTracking(order, warehouseId);

            if (trackingId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../order/orderTracking.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "Tracking added successfully.";
                return "redirect:../order/orderTracking.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }

    }
    //controller for bulk order tracking in bulk, get method 

    @RequestMapping(value = "order/bulkOrderTracking.htm", method = RequestMethod.GET)
    public String bulkOrderTrackingUploadFeed(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);
        return "/order/bulkOrderTracking";
    }
    //controller for bulk order tracking in bulk, post method

    @RequestMapping(value = "order/bulkOrderTracking.htm", method = RequestMethod.POST)
    public String bulkOrderTrackingUploadFeedSubmit(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("bulk order feed data validation started...", GlobalConstants.TAG_SYSTEMLOG));
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            int result = this.orderService.saveMultipartFileData(uploadFeed, marketplaceId);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../order/bulkOrderTracking.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Bulk Tracking uploaded successfully.";
                return "redirect:../order/bulkOrderTracking.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "exportProcessingSheet/excelDownload.htm", method = RequestMethod.POST)
    public void excelDownloadProseccingSheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
            String currentDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            List<ProcessingSheet> processOrderList = this.orderService.getProcessOrderList(warehouseId, currentDate);
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=ExportProcessingSheet.xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Export Processing Sheet");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("PO Number");
            headerRow.addCell("Warehouse Name");
            headerRow.addCell("Manufacturer Name");
            headerRow.addCell("Model Number(MPN)");
            headerRow.addCell("Quantity");
            headerRow.addCell("Price");
            headerRow.addCell("Ship To Name");
            headerRow.addCell("Ship To Address");
            headerRow.addCell("City");
            headerRow.addCell("State");
            headerRow.addCell("Zip Code");
            headerRow.addCell("Phone Number");
            mySheet.addRow(headerRow);
            for (ProcessingSheet processingSheet : processOrderList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell("", POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getWarehouseName(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getMPN(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getQuantity(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getPrice(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getShipToName(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getShippingAddressLine1(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getShippingAddressLine2(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getShippingAddressLine3(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getCity(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getState(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getZip(), POICell.TYPE_TEXT);
                dataRow.addCell(processingSheet.getPhoneNumber(), POICell.TYPE_TEXT);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            User user = this.userService.getUserByUserId(curUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "order/showUnshippedOrderList.htm", method = RequestMethod.GET)
    public String getUnshippedList(ModelMap map) {
        map.addAttribute("orderListOfUnshippedOrder", this.orderService.getOrderListOfUnshippedOrderForDashboard(0));
        return "/order/showUnshippedOrderList";
    }

    @RequestMapping(value = "order/marketPlaceOrderListExcelDownload.htm")
    public void getMarplaceOrderListExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            String marketplaceOrderId = ServletRequestUtils.getStringParameter(request, "marketplaceOrderId", null);
            String poNumber = ServletRequestUtils.getStringParameter(request, "poNumber", null);
            String customerName = ServletRequestUtils.getStringParameter(request, "customerName", null);
            String marketplaceSku = ServletRequestUtils.getStringParameter(request, "marketplaceSku", null);
            String marketplaceListingId = ServletRequestUtils.getStringParameter(request, "marketplaceListingId", null);
            String orderStatus = ServletRequestUtils.getStringParameter(request, "orderStatus", null);
            String fulfillmentChannel = ServletRequestUtils.getStringParameter(request, "fulfillmentChannel", null);
            String startDate = ServletRequestUtils.getStringParameter(request, "start", null);
            String stopDate = ServletRequestUtils.getStringParameter(request, "stop", null);

            List<Order> marketPlaceOrderList = this.orderService.getOrderList(marketplaceId, marketplaceOrderId, poNumber, -1, customerName, marketplaceSku, marketplaceListingId, orderStatus, fulfillmentChannel, startDate, stopDate);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=MarketPlaceOrderList.xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "MarketPlace Order List");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("PO Number");
            headerRow.addCell("MarketPlace");
            headerRow.addCell("MaarketPlace Order Id");
            headerRow.addCell("Marketplace SKU");
            headerRow.addCell("MarketPlace Listing ID");
            headerRow.addCell("Order Date");
            headerRow.addCell("Customer Name");
            headerRow.addCell("Customer Phone");
            headerRow.addCell("Quantity Unshipped");
            headerRow.addCell("Quantity Shipped");
            headerRow.addCell("Price");
            headerRow.addCell("Fulfillment Channel");
            headerRow.addCell("MarketPlace order Status");
            mySheet.addRow(headerRow);
            for (Order item : marketPlaceOrderList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(item.getPoNumber(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getMarketplace().getMarketplaceName(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getMarketplaceOrderId(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getMarketplaceSku(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getMarketplaceListingId(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getOrderDate(), POICell.TYPE_DATE);
                dataRow.addCell(item.getCustomerName(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getCustomerPhoneNo(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getQuantityUnshipped(), POICell.TYPE_INTEGER);
                dataRow.addCell(item.getQuantityShipped(), POICell.TYPE_INTEGER);
                dataRow.addCell(item.getPrice(), POICell.TYPE_DECIMAL);
                dataRow.addCell(item.getFulfillmentChannel(), POICell.TYPE_TEXT);
                dataRow.addCell(item.getOrderStatus(), POICell.TYPE_TEXT);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "order/marketplaceOrderUpload.htm", method = RequestMethod.GET)
    public String uploadMarketPlaceOrder(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);

        UploadFeed uploadFeed = new UploadFeed();
        model.addAttribute("uploadFeed", uploadFeed);
        return "order/marketplaceOrderUpload";
    }

    @RequestMapping(value = "order/marketplaceOrderUpload.htm", method = RequestMethod.POST)
    public String uploadFeesSubmit(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("marketPlace Order data upload started...", GlobalConstants.TAG_SYSTEMLOG));
            int marketplaceId = ServletRequestUtils.getIntParameter(request, "marketplaceId", 0);
            int result = this.orderService.saveMultipartFileForMarketPlaceOrderUpload(uploadFeed, marketplaceId);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../order/marketplaceOrderUpload.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Orders uploaded successfully.";
                return "redirect:../order/marketplaceOrderUpload.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }
}
