/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.DashBoardDao;
import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TicketRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ritesh
 */
@Repository
public class DashBoardDaoImpl implements DashBoardDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> getMonthlyTicketDetails() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getMonthlyTatDetails() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getTicketTypeTat() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getPercentageFcr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getSummaryForGraph() {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YMD);
        String startDate, stopDate;
        Map<String, Object> map = new HashMap<String, Object>();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        StringBuilder sql = new StringBuilder("SELECT 'Total',");
        sql.append(" IFNULL(COUNT(tt.`TICKET_ID`),0) AS totalTickets,");
        sql.append(" IFNULL(SUM(IF(tt.`TICKET_STATUS_ID` IN (1,2,3),1,0)),0) openTickets,");
        sql.append(" IFNULL(SUM(IF(tt.`TICKET_STATUS_ID` IN (4),1,0)),0) closedTickets,");
        sql.append(" IFNULL(SUM(IF(tt.`TICKET_STATUS_ID` IN (5),1,0)),0) canceledTickets");
        sql.append(" FROM tkt_ticket tt");
        sql.append(" WHERE tt.`CREATED_DATE` BETWEEN :startDate AND :stopDate");

        Map<String, Object> params = new HashMap<String, Object>();

        if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_AGENT_OPEN_TICKETS"))) {
            sql.append(" AND tt.ASSIGNED_TO=:requestedBy");
            params.put("requestedBy", curUser.getUserId());
        }

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, 1);
        startDate = sdf.format(cal.getTime()) + " 00:00:00";
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        stopDate = sdf.format(cal.getTime()) + " 23:59:59";
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        map = new HashMap<String, Object>();
        map.put("Last_month", nm.queryForMap(sql.toString(), params));

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        startDate = sdf.format(cal1.getTime()) + " 00:00:00";
        stopDate = curDate;
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        map.put("This_month", nm.queryForMap(sql.toString(), params));

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -1);
        startDate = sdf.format(cal2.getTime()) + " 00:00:00";
        stopDate = sdf.format(cal2.getTime()) + " 23:59:59";
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        map.put("Yesterday", nm.queryForMap(sql.toString(), params));

        String today = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        startDate = today + " 00:00:00";
        stopDate = curDate;
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        map.put("Today", nm.queryForMap(sql.toString(), params));

        return map;
    }

    @Override
    public Map<String, Object> getOpenTicketDetails() {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String sql = "";
        if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_AGENT_OPEN_TICKETS"))) {
            sql = "SELECT IFNULL(SUM(IF((tt.`ASSIGNED_TO`=:curUser OR tt.`CREATED_BY`=:curUser),1,0)),0) AS openTickets,"
                    + " IFNULL(SUM(IF(tt.`ASSIGNED_TO`=:curUser AND tt.`TICKET_TYPE_ID` IN (2),1,0)),0) orderReturn, "
                    + " IFNULL(SUM(IF(tt.`ASSIGNED_TO`=:curUser AND tt.`TICKET_TYPE_ID` IN (8),1,0)),0) claims,"
                    + " IFNULL(SUM(IF(tt.`ASSIGNED_TO`=:curUser AND tt.`TICKET_TYPE_ID` IN (5),1,0)),0) refund,"
                    + " IFNULL(SUM(IF(tt.`ASSIGNED_TO`=:curUser AND tt.`TICKET_TYPE_ID` IN (7),1,0)),0) cancellation,"
                    + " IFNULL(SUM(IF(tt.`ASSIGNED_TO`=:curUser AND tt.`TICKET_TYPE_ID` IN (1,3,4,6,9,10),1,0)),0) other"
                    + " FROM tkt_ticket tt "
                    + " WHERE tt.`TICKET_STATUS_ID` IN (1,2,3) AND tt.ASSIGNED_TO=:requestedBy";

        } else {
            sql = "SELECT IFNULL(COUNT(*),0) AS openTickets,"
                    + " IFNULL(SUM(IF(tt.`TICKET_TYPE_ID` IN (2),1,0)),0) orderReturn, "
                    + " IFNULL(SUM(IF(tt.`TICKET_TYPE_ID` IN (8),1,0)),0) claims,"
                    + " IFNULL(SUM(IF(tt.`TICKET_TYPE_ID` IN (5),1,0)),0) refund,"
                    + " IFNULL(SUM(IF(tt.`TICKET_TYPE_ID` IN (7),1,0)),0) cancellation,"
                    + " IFNULL(SUM(IF(tt.`TICKET_TYPE_ID` IN (1,3,4,6,9,10),1,0)),0) other"
                    + " FROM tkt_ticket tt "
                    + " WHERE tt.`TICKET_STATUS_ID` IN (1,2,3)";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("curUser", curUser.getUserId());
        params.put("requestedBy", curUser.getUserId());
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
        return nm.queryForMap(sql, params);
    }

    @Override
    public List<Map<String, Object>> getLast7daysUnshippedOrderData() {
        try {
            String sql = "SELECT \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 6 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 6 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount6', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 5 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 5 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount5', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 4 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 4 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount4', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 3 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 3 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount3', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 2 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 2 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount2', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 1 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW()- INTERVAL 1 DAY,'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCount1', \n"
                    + "IFNULL(SUM(IF(t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(),'%Y-%m-%d 23:59:59') AND t.`ORDER_DATE` IS NOT NULL,1,0)),0) AS 'TotalCountToday' \n"
                    + "FROM `tesy_order` t \n"
                    + "WHERE t.`ORDER_DATE` BETWEEN DATE_FORMAT(NOW() - INTERVAL 7 DAY,'%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(),'%Y-%m-%d 23:59:59') AND \n"
                    + "DATEDIFF(NOW(),t.`ORDER_DATE`)>2 AND t.`ORDER_STATUS`='Unshipped'\n"
                    + "GROUP BY t.`ORDER_STATUS`\n"
                    + "ORDER BY t.`ORDER_DATE` DESC";
            return this.jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getOrderShippmentDetails() {
        try {

            Map<String, Object> shipmentDetail = new HashMap<String, Object>();
            String sql = null;

            sql = "SELECT COUNT(*) FROM tesy_order tor";
            int totalOrders = this.jdbcTemplate.queryForInt(sql);
            shipmentDetail.put("totalOrders", totalOrders);

            sql = "SELECT COUNT(tor.`ORDER_STATUS`) FROM tesy_order tor WHERE tor.`ORDER_STATUS`='Shipped'";
            int totalShippedOrders = this.jdbcTemplate.queryForInt(sql);
            shipmentDetail.put("totalShippedOrders", totalShippedOrders);

            sql = "SELECT COUNT(tor.`ORDER_STATUS`) FROM tesy_order tor WHERE tor.`ORDER_STATUS`='Unshipped'";
            int totalUnshippedOrders = this.jdbcTemplate.queryForInt(sql);
            shipmentDetail.put("totalUnshippedOrders", totalUnshippedOrders);

            sql = "SELECT COUNT(*) FROM tesy_order t WHERE DATEDIFF(NOW(),t.`ORDER_DATE`)>2 AND t.`ORDER_STATUS`='Shipped'";
            int lateShipped = this.jdbcTemplate.queryForInt(sql);
            shipmentDetail.put("lateShipped", lateShipped);
            
            sql = "SELECT COUNT(*) FROM tesy_order tor WHERE TRIM(tor.`WAREHOUSE_ID`) IS NULL;";
            int  orderProcessed = this.jdbcTemplate.queryForInt(sql);
            shipmentDetail.put("orderProcessed", orderProcessed);

            return shipmentDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map<String, Object> getPricesDifferenceAndLowCountProduct() {
        try {
            String sql = null;
            Map<String, Object> priceAndProductCount = new HashMap<String, Object>();

            sql = "SELECT COUNT(*) AS underpriced FROM tesy_available_listing tal WHERE tal.`LAST_LISTED_PRICE`> tal.`CURRENT_PRICE`";
            int underPriced = this.jdbcTemplate.queryForInt(sql);
            priceAndProductCount.put("underPriced", underPriced);

            sql = "SELECT COUNT(*) AS overpriced FROM tesy_available_listing tal WHERE tal.`CURRENT_PRICE` > tal.`LAST_LISTED_PRICE`";
            int overPriced = this.jdbcTemplate.queryForInt(sql);
            priceAndProductCount.put("overPriced", overPriced);

            sql = "SELECT COUNT(*) AS lowCount FROM tesy_order tso\n"
                    + "LEFT JOIN tesy_available_listing tal ON tso.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID` \n"
                    + "LEFT JOIN tesy_mpn_sku_mapping tmsm ON tmsm.`SKU`=tal.`SKU`\n"
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`\n"
                    + "WHERE tal.`CURRENT_QUANTITY`=2";
            int lowCount = this.jdbcTemplate.queryForInt(sql);
            priceAndProductCount.put("lowCount", lowCount);

            return priceAndProductCount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getProductDetailsForWebsite() {

        try {

            Map<String, Object> productDetails = new HashMap<String, Object>();
            String sql = null;

            sql = "SELECT COUNT(tp.`PRODUCT_ID`) FROM tesy_product tp";
            int totalProducts = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("totalProducts", totalProducts);

//            sql = "";
//            int prodListedOnWebsite = this.jdbcTemplate.queryForInt(sql);
//            productDetails.put("totalShippedOrders", prodListedOnWebsite);
            
            sql = "SELECT  COUNT(*) FROM tesy_product tsp   \n"
                    + "WHERE TRIM(tsp.`PRODUCT_NAME`) IS NULL   \n"
                    + "OR TRIM(tsp.`MANUFACTURER_MPN`) IS NULL  \n"
                    + "OR TRIM(tsp.`TITLE`) IS NULL  \n"
                    + "OR TRIM(tsp.`WEIGHT`) IS NULL  \n"
                    + "OR TRIM(tsp.`UPC`) IS NULL   \n"
                    + "OR TRIM(tsp.`MAIN_CATEGORY_ID`)IS NULL  \n"
                    + "OR TRIM(tsp.`SUB_CATEGORY1`) IS NULL   \n"
                    + "OR TRIM(tsp.`SUB_CATEGORY2`) IS NULL   \n"
                    + "OR TRIM(tsp.`SHORT_DESC`) IS NULL  \n"
                    + "OR TRIM(tsp.`LONG_DESC`) IS NULL   \n"
                    + "OR TRIM(tsp.`RESIZE_IMAGE_URL`) IS NULL";
            int productWithMissigData = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("productWithMissigData", productWithMissigData);

            sql = "SELECT COUNT(DISTINCT(tm.`MAIN_CATEGORY_ID`)) FROM tesy_main_category tm";
            int  mainCategories = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("mainCategories", mainCategories);
            
            sql = "SELECT COUNT(DISTINCT(tm.`SUB_CATEGORY_DESC`)) FROM tesy_sub_category tm";
            int  subCategories = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("subCategories", subCategories);
            
            sql = "SELECT COUNT(*) FROM tesy_product tp WHERE tp.`PRODUCT_STATUS_ID`=1;";
            int   manuallyCreated = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("manuallyCreated", manuallyCreated);
            
            sql = "SELECT SUM(tor.`QUANTITY_SHIPPED`) FROM tesy_order tor";
            int    productProcessed = this.jdbcTemplate.queryForInt(sql);
            productDetails.put("productProcessed", productProcessed);

            return productDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
