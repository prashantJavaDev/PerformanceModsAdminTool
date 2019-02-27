/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.EmailerBlock;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.AdminService;
import com.aspirant.performanceModsAdminTool.service.IngramService;
import com.aspirant.performanceModsAdminTool.service.ListingService;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.service.ManufacturerService;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.aspirant.performanceModsAdminTool.service.TicketService;
//import com.aspirantutils.PassPhrase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shrutika
 */
@Controller
public class AjaxController {

    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    ListingService listingService;
    @Autowired
    AdminService adminService;
    @Autowired
    TicketService ticketService;
    @Autowired
    OrderService orderService;
    @Autowired
    IngramService ingramService;

    @RequestMapping(value = "product/ajaxGetSubCategoryListForMainCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetSubCategoryListForMainCategory(@RequestParam(value = "mainCategoryId") int mainCategoryId) {
        String json;
        List<SubCategory> subCategoryList = this.productService.getSubCategoryListForMainCategory(mainCategoryId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(subCategoryList, typeList);
        return json;
    }

    @RequestMapping(value = "product/ajaxGetChildCategoryListForSubCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetchildCategoryListForSubCategory(@RequestParam(value = "subCategoryId") int subCategoryId) {
        String json;
        List<ChildOfSubCategory> childCategoryList = this.productService.getChildCategoryListForSubCategory(subCategoryId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(childCategoryList, typeList);
        return json;
    }

    @RequestMapping(value = "product/ajaxGetSubChildCategoryListForSubCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetSubchildCategoryListForSubCategory(@RequestParam(value = "childCategoryId") int childCategoryId) {
        String json;
        List<ChildOfChildCategory> subChildCategoryList = this.productService.getsubChildCategoryListForSubCategory(childCategoryId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(subChildCategoryList, typeList);
        return json;
    }

    
    
    @RequestMapping(value = "product/getMpnListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getMpnList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.productService.searchMpn(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "product/getWareHouseMpnListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getWareHouseMpnList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.productService.searchWarehouseMpn(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getProductMpnListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getProductMpnList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.productService.searchProductMpn(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "product/getProductNameListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getProductNameList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.productService.searchProductName(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "admin/ajaxGetSubCategoryListByMainCategoryId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetSubCategoryListByMainCategoryId(@RequestParam(value = "mainCategoryId") int mainCategoryId) {
        String json;
        List<Integer> assignedList = this.productService.getSubCategoryIdListByMainCategory(mainCategoryId);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(assignedList, typeList);
        return json;
    }

    

    @RequestMapping(value = "admin/ajaxGetChildCategoryListBySubCategoryId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetChildCategoryListBySubCategoryId(@RequestParam(value = "subCategoryId") int subCategoryId) {
        String json;
        List<Integer> assignedList = this.productService.getChildCategoryIdListBySubCategory(subCategoryId);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(assignedList, typeList);
        return json;

    }

    @RequestMapping(value = "admin/ajaxGetSubChildCategoryListByChildCategoryId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetSubChildCategoryListByChildCategoryId(@RequestParam(value = "childCategoryId") int childCategoryId) {
        String json;
        List<Integer> assignedList = this.productService.getSubChildCategoryIdListByChildCategory(childCategoryId);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(assignedList, typeList);
        return json;

    }

    @RequestMapping(value = "admin/ajaxAddSubCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxAddSubCategory(
            @RequestParam("subCategoryDesc") String subCategoryDesc,
            @RequestParam("active") boolean active) {
        String json = null;
        int subCategoryId = this.productService.addSubCategory(subCategoryDesc, active);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", subCategoryId);
        map.put("label", subCategoryDesc);
        map.put("active", active);

        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "admin/ajaxAddChildCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxAddChildCategory(
            @RequestParam("childCategoryDesc") String childCategoryDesc,
            @RequestParam("active") boolean active) {

        String json = null;
        int childCategoryId = this.productService.addChildCategory(childCategoryDesc, active);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", childCategoryId);
        map.put("label", childCategoryDesc);
        map.put("active", active);

        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "admin/ajaxAddSubChildCategory.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxAddSubChildCategory(
            @RequestParam("subChildCategoryDesc") String subChildCategoryDesc,
            @RequestParam("active") boolean active) {

        String json = null;
        int subChildCategoryId = this.productService.addSubChildCategory(subChildCategoryDesc, active);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", subChildCategoryId);
        map.put("label", subChildCategoryDesc);
        map.put("active", active);

        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "admin/getCodeListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getCodeList(HttpServletRequest request) {
        String manufacturerCode = request.getParameter("term");
        String codeStatus = "";
        String className = "";
        int codeLength;
        String json = null;
        if (manufacturerCode.length() >= 3 && manufacturerCode.length() <= 4) {
            List<String> matchingList = this.manufacturerService.GetListofMatchingManufacturerCode(manufacturerCode);
            codeLength = matchingList.size();

            if (codeLength == 0) {
                codeStatus = "This code is Available";
                className = "green";
            } else if (codeLength > 0) {
                codeStatus = "Please enter another Code";
                className = "red";
            }
        } else {
            codeStatus = "";
            className = "";
            codeLength = -1;
        }
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("label", codeStatus);
        map.put("length", codeLength);
        map.put("className", className);

        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "admin/ajaxGeneratePassword.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxGeneratePassword() {
        String json;
        String pass =RandomStringUtils.random(5);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pass", pass);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "/admin/getMappedManufacturer.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxGetMappedManufacturer(
            @RequestParam("manufacturerId") int manufacturerId) {
        String json = null;
        List<Manufacturer> manufacturerList = this.manufacturerService.mapManufacturerList(manufacturerId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(manufacturerList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/ajaxUpdatePriceQuantity.htm", method = RequestMethod.GET)
    public @ResponseBody
    String updatePriceQuantity(
            @RequestParam(value = "sku") String sku,
            @RequestParam(value = "price") double price,
            @RequestParam(value = "quantity") int quantity,
            @RequestParam(value = "profit") double profit,
            @RequestParam(value = "active") boolean active) {
        String json;
        int id = this.listingService.updatePriceQuantity(sku, price, quantity, profit, active);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "listing/getMarketplaceSkuListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getSkuList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.listingService.searchSku(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/ajaxGetSkuListOnMpn.htm", method = RequestMethod.GET)
    public @ResponseBody
    String checkSku(
            @RequestParam(value = "productMpn") String productMpn,
            @RequestParam(value = "manufacturerId") int manufacturerId) {
        String json = null;
        String sku = this.adminService.generateSku(productMpn, manufacturerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sku", sku);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/ajaxProcessListing.htm", method = RequestMethod.GET)
    public @ResponseBody
    String processListing(
            @RequestParam(value = "processingMarketplaceId") int marketplaceId,
            @RequestParam(value = "manufacturerId") int manufacturerId) {
        String json;
        int id = this.listingService.processListing(marketplaceId, manufacturerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getCustomerNameListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getCustomerNameList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.ticketService.searchCustomerName(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getTicketNoListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getTicketNoList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.ticketService.searchTicketNo(term);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/ajaxReopenTicket.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxReopenTicket(@RequestParam(value = "notes") String notes,
            @RequestParam(value = "ticketId") int ticketId,
            @RequestParam(value = "ticketTypeId") int ticketTypeId) {
        String json;
        int id = this.ticketService.reopenTicket(ticketId, ticketTypeId, notes);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getOrderIdListForAutocomplete.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getOrderIdList(HttpServletRequest request) {
        String term = request.getParameter("term");
        String json = null;
        List<String> matchingList = this.ticketService.searchOrderId(term);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(matchingList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getTicketListByOrderId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getTicketListByOrderId(@RequestParam(value = "orderId") String orderId) {
        String json;
        List<Ticket> ticketList = this.ticketService.getTicketListByOrderId(orderId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(ticketList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getTicketListByPhoneNumber.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getTicketListByPhoneNumber(@RequestParam(value = "custPhoneNumber") String custPhoneNumber) {
        String json;
        List<Ticket> ticketList = this.ticketService.getTicketListByPhoneNumber(custPhoneNumber);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(ticketList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/getAssignListOnTicketType.htm", method = RequestMethod.GET)
    public @ResponseBody
    String getAssignListOnTicketType(@RequestParam(value = "ticketTypeId") int ticketTypeId) {
        String json;
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        List<User> assignedToList = new LinkedList<User>();
        if (ticketTypeId == 5) {
            assignedToList = this.ticketService.getAssignedToSupervisorList(true);
        } else {
            assignedToList = this.ticketService.getAssignedToList(true);
        }
        json = gson.toJson(assignedToList, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/ajaxProcessWarehouseOrder.htm", method = RequestMethod.GET)
    public @ResponseBody
    String assignWarehouseToOrder(
            @RequestParam(value = "warehouseId") int warehouseId,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "poNumber") String poNumber) {
        String json;
        if (warehouseId == 20) {
            this.orderService.assignWarehouseToOrder(warehouseId, orderId);
            this.ingramService.getOrderRequestXml(poNumber);
        } else {
            this.orderService.assignWarehouseToOrder(warehouseId, orderId);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("id", id);
        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }

    @RequestMapping(value = "/ajax/deleteProductByID.htm", method = RequestMethod.GET)
    public @ResponseBody
    String deleteProductByProductIdPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        int productId = ServletRequestUtils.getIntParameter(request, "productId", 0);
        int result = 0;
        result = this.productService.deleteProductByProductID(productId);
        if (result > 0) {
            return new Gson().toJson("Success");
        } else {
            return new Gson().toJson("Fail");
        }
    }
    
    @RequestMapping(value = "/ajax/deleteListingBySku.htm", method = RequestMethod.GET)
    public @ResponseBody
    String deleteListingBySkuPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String sku = ServletRequestUtils.getStringParameter(request, "sku", null);
        int result = 0;
        result = this.productService.deleteListingBySku(sku);
        if (result > 0) {
            return new Gson().toJson("Success");
        } else {
            return new Gson().toJson("Fail");
        }
    }
    
    
//    @RequestMapping(value = "/ajax/getAjaxAutoFillPageByOrderId.htm", method = RequestMethod.GET)
//    public @ResponseBody
//    String getAutofillPageByOrderId(@RequestParam(value = "orderId") String orderId) {
//        String json;
//        Order order = this.ticketService.getOrderByOrderId(orderId);
//        Gson gson = new Gson();
//        Type typeObject = new TypeToken<Order>() {
//        }.getType();
//        json = gson.toJson(order, typeObject);
//        return json;
//    }
    
    
    @RequestMapping(value = "/ajax/addTrackingByOrderId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String addTrackingByOrderIdPost(@RequestParam(value = "warehouseId") int warehouseId,
                    @RequestParam(value = "trackingId") String trackingId,
                    @RequestParam(value = "trackingCarrier") String trackingCarrier,
                    @RequestParam(value = "marketplaceOrderId") String marketplaceOrderId) {
        int result = 0;
        Order order = new Order();
        order.setMarketplaceOrderId(marketplaceOrderId);
        order.setTrackingId(trackingId);
        order.setTrackingCarrier(trackingCarrier);
        result = this.orderService.addOrderTracking(order, warehouseId);
        if (result > 0) {
            return new Gson().toJson("Success");
        } else {
            return new Gson().toJson("Fail");
        }
    }

}
