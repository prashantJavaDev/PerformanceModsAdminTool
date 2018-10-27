/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.service.DashBoardService;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author gaurao
 */
@Controller
public class IndexController {

    @Autowired
    private DashBoardService dashBoardService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/home/home.htm")
    public String showHome(ModelMap map, HttpServletRequest request) {
        Map<String, Object> openTicketCount = this.dashBoardService.getOpenTicketDetails();
        map.addAttribute("openTicketCount", openTicketCount);
        Map<String, Object> summaryForGraph = this.dashBoardService.getSummaryForGraph();
        int size = summaryForGraph.size();

        Map<String, Object> thisMonth = (Map<String, Object>) summaryForGraph.get("This_month");
        Map<String, Object> lastMonth = (Map<String, Object>) summaryForGraph.get("Last_month");
        Map<String, Object> yesterday = (Map<String, Object>) summaryForGraph.get("Yesterday");
        Map<String, Object> today = (Map<String, Object>) summaryForGraph.get("Today");

        Integer open1[] = {((BigDecimal) lastMonth.get("openTickets")).intValue(), ((BigDecimal) thisMonth.get("openTickets")).intValue()};
        Integer closed1[] = {((BigDecimal) lastMonth.get("closedTickets")).intValue(), ((BigDecimal) thisMonth.get("closedTickets")).intValue()};
        Integer canceled1[] = {((BigDecimal) lastMonth.get("canceledTickets")).intValue(), ((BigDecimal) thisMonth.get("canceledTickets")).intValue()};

        Integer open2[] = {((BigDecimal) yesterday.get("openTickets")).intValue(), ((BigDecimal) today.get("openTickets")).intValue()};
        Integer closed2[] = {((BigDecimal) yesterday.get("closedTickets")).intValue(), ((BigDecimal) today.get("closedTickets")).intValue()};
        Integer canceled2[] = {((BigDecimal) yesterday.get("canceledTickets")).intValue(), ((BigDecimal) today.get("canceledTickets")).intValue()};

        Gson gson = new Gson();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date date = cal.getTime();
        String[] days = new String[7];
        days[0] = sdf.format(date);

        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
            days[i] = sdf.format(date);
        }
        List<Map<String, Object>> last7daysUnshippedOrderData = this.dashBoardService.getLast7daysUnshippedOrderData();
        
        List<Integer> listInt = new ArrayList<Integer>();
        for (Map<String, Object> m : last7daysUnshippedOrderData) {
            listInt.add(Integer.parseInt(m.get("TotalCount5").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCount4").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCount3").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCount2").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCount1").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCountToday").toString()));
            listInt.add(Integer.parseInt(m.get("TotalCount6").toString()));
        }


        map.addAttribute("open1", gson.toJson(open1));
        map.addAttribute("closed1", gson.toJson(closed1));
        map.addAttribute("canceled1", gson.toJson(canceled1));

        map.addAttribute("open2", gson.toJson(open2));
        //map.addAttribute("orderListOfUnshippedOrder", this.orderService.getOrderListOfUnshippedOrderForDashboard(1));
        //map.addAttribute("lowCountProduct", this.productService.getLowCountProductForDashbored(1));
        map.addAttribute("totalCount7", listInt);

        map.addAttribute("days", gson.toJson(days));
        map.addAttribute("closed2", gson.toJson(closed2));
        map.addAttribute("canceled2", gson.toJson(canceled2));
        
        Map<String, Object> shippmentDetails = this.dashBoardService.getOrderShippmentDetails();
        map.addAttribute("totalOrders", shippmentDetails.get("totalOrders")); 
        map.addAttribute("totalShippedOrders",shippmentDetails.get("totalShippedOrders")); 
        map.addAttribute("totalUnshippedOrders", shippmentDetails.get("totalUnshippedOrders")); 
        map.addAttribute("orderProcessed", shippmentDetails.get("orderProcessed")); 
        map.addAttribute("lateShipped",shippmentDetails.get("lateShipped")+"/"+ shippmentDetails.get("totalOrders")); 
       
        Map<String, Object> product = this.dashBoardService.getPricesDifferenceAndLowCountProduct();
        map.addAttribute("underPriced",  product.get("underPriced")); 
        map.addAttribute("overPriced", product.get("overPriced")); 
        map.addAttribute("lowCount",  product.get("lowCount")); 
        
        Map<String, Object> productDetails = this.dashBoardService.getProductDetailsForWebsite();
        map.addAttribute("totalProducts", productDetails.get("totalProducts"));
        map.addAttribute("productWithMissigData", productDetails.get("productWithMissigData"));
        //map.addAttribute("totalShippedOrders", productDetails.get("totalShippedOrders"));
        map.addAttribute("mainCategories", productDetails.get("mainCategories"));
        map.addAttribute("subCategories", productDetails.get("subCategories"));
        map.addAttribute("manuallyCreated", productDetails.get("manuallyCreated"));
        map.addAttribute("productProcessed", productDetails.get("productProcessed"));
        return "/home/home";
    }
}
