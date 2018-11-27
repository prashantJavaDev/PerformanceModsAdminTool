/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.OrderDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MarketplaceOrderDetailsRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MarketplaceOrderRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.OrderDetailRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProcessingSheetRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ritesh
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Order getorderBymarketplaceOrderID(String marketplaceOrderId) {
        String sqlString = "SELECT tor.*,tm.`MARKETPLACE_NAME`,tw.`WAREHOUSE_NAME`, u1.`USERNAME`as pby,u.`USERNAME` as tby, tal.`CURRENT_COMMISSION` FROM pm_order tor"
                + " LEFT JOIN pm_marketplace tm ON tor.`MARKETPLACE_ID`=tm.`MARKETPLACE_ID`"
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=tor.`WAREHOUSE_ID`"
                + " LEFT JOIN pm_available_listing tal ON tal.`SKU`=tor.`MARKETPLACE_SKU`"
                + " LEFT JOIN user u ON u.`USER_ID`=tor.`TRACKING_BY`"
                + " LEFT JOIN user u1 ON u1.`USER_ID`=tor.`PROCESSED_BY`"
                + " WHERE tor.`MARKETPLACE_ORDER_ID`=? ";
        Object params[] = new Object[]{marketplaceOrderId};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForObject(sqlString, params, new MarketplaceOrderDetailsRowMapper());
    }

    @Override
    public List<ProductDetails> getProductListOnMarketplaceSku(String marketplaceSku) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tw.`WAREHOUSE_ID`,tw.`WAREHOUSE_NAME`,tpi.`LARGE_IMAGE_URL`,tcwp.`WAREHOUSE_IDENTIFICATION_NO`,"
                + " DATE(tcwp.`CREATED_DATE`) `CURRENT_DATE`,tcwp.`PRICE` CURRENT_PRICE,tcwp.`QUANTITY` CURRENT_QUANTITY,tcwp.`SHIPPING`"
                + " FROM pm_current_warehouse_product tcwp "
                + " LEFT JOIN pm_mpn_sku_mapping tmsp ON tmsp.`MANUFACTURER_MPN`=tcwp.`MPN`"
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=tcwp.`WAREHOUSE_ID`"
                + " LEFT JOIN pm_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tcwp.`PRODUCT_ID`"
                + " LEFT JOIN pm_product_image tpi ON tpi.`PRODUCT_ID`=tcwp.`PRODUCT_ID`"
                + " WHERE tmsp.`SKU`=:marketplaceSku"
                + " GROUP BY tcwp.`WAREHOUSE_ID` ORDER BY(tcwp.`PRICE`+tcwp.`SHIPPING`)ASC");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("marketplaceSku", marketplaceSku);

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
        return nm.query(sql.toString(), params, new OrderDetailRowMapper());
    }

    @Override
    public List<Order> getorderTransBymarketplaceOrderID(String marketplaceOrderId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tor.*,tm.`MARKETPLACE_NAME`,tw.`WAREHOUSE_NAME`, u.`USERNAME` AS trackBy, u.`USERNAME` AS processBy FROM pm_order_trans tor"
                + " LEFT JOIN pm_marketplace tm ON tor.`MARKETPLACE_ID`=tm.`MARKETPLACE_ID`"
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=tor.`WAREHOUSE_ID`"
                + " LEFT JOIN user u ON u.`USER_ID`=tor.`TRACKING_BY`"
                + " WHERE tor.`MARKETPLACE_ORDER_ID`=:marketplaceOrderId GROUP BY tor.`ORDER_STATUS` ORDER BY tor.`LAST_UPDATED_DATE` DESC");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("marketplaceOrderId", marketplaceOrderId);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql.toString(), params, new MarketplaceOrderRowMapper());
    }

    @Override
    public void assignWarehouseToOrder(int warehouseId, String orderId) {
        String sqlString;
        Object params[];
        Object params1[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        sqlString = "UPDATE pm_order tso SET tso.`WAREHOUSE_ID`=?, tso.`PROCESSED_BY`=?, tso.`PROCESSED_DATE`=? WHERE tso.`MARKETPLACE_ORDER_ID`=?";
        params = new Object[]{warehouseId, curUser, curDate, orderId};
        this.jdbcTemplate.update(sqlString, params);

        String sqlString1;
        sqlString1 = "UPDATE pm_order_trans tot SET tot.`WAREHOUSE_ID`=?, tot.`PROCESSED_BY`=?,tot.`PROCESSED_DATE`=? WHERE tot.`MARKETPLACE_ORDER_ID`=?";
        params1 = new Object[]{warehouseId, curUser, curDate, orderId};
        this.jdbcTemplate.update(sqlString1, params1);
    }

    @Override
    public int getProcessOrderCount(int warehouseId, String currentDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM pm_order tor WHERE tor.`ORDER_ID`");
        Map<String, Object> params = new HashMap<String, Object>();
        if (warehouseId != 0) {
            sql.append(" AND tor.`WAREHOUSE_ID`=:warehouseId ");
            params.put("warehouseId", warehouseId);
        }
        sql.append(" AND DATE(tor.`PROCESSED_DATE`)=:currentDate");
        params.put("currentDate", currentDate);

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        Integer i = nm.queryForObject(sql.toString(), params, Integer.class);
        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    @Override
    public List<ProcessingSheet> getProcessOrderList(int warehouseId, String currentDate) {
        StringBuilder sql = new StringBuilder("SELECT tw.`WAREHOUSE_ID`,tw.`WAREHOUSE_NAME`,tm.`MANUFACTURER_NAME`,twpm.`WAREHOUSE_MPN`,tor.`QUANTITY_SHIPPED`,tcwp.`PRICE`,tor.`SHIP_TO_NAME`,tor.`SHIPPING_ADDRESS_LINE1`,tor.`SHIPPING_ADDRESS_LINE2`,tor.`SHIPPING_ADDRESS_LINE3`,tor.`CITY`,tor.`STATE`,tor.`POSTAL_CODE`,tor.`SHIPPING_PHONE_NO`"
                + " FROM pm_current_warehouse_product tcwp"
                + " LEFT JOIN pm_mpn_sku_mapping tmsp ON tmsp.`MANUFACTURER_MPN`=tcwp.`MPN`"
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=tcwp.`WAREHOUSE_ID`"
                + " LEFT JOIN pm_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tcwp.`PRODUCT_ID`"
                + " LEFT JOIN pm_order tor ON tor.`WAREHOUSE_ID`=tw.`WAREHOUSE_ID`"
                + " LEFT JOIN  pm_manufacturer tm ON tm.`MANUFACTURER_ID`=tmsp.`MANUFACTURER_ID`"
                + " WHERE tor.`WAREHOUSE_ID`");
        Map<String, Object> params = new HashMap<String, Object>();
        if (warehouseId != 0) {
            sql.append(" AND tor.`WAREHOUSE_ID`=:warehouseId");
            params.put("warehouseId", warehouseId);
        }
        sql.append(" AND DATE(tor.`PROCESSED_DATE`)=:currentDate GROUP BY tor.`SHIP_TO_NAME`");
        params.put("currentDate", currentDate);

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        List<ProcessingSheet> list = nm.query(sql.toString(), params, new ProcessingSheetRowMapper());
        return list;
    }

    @Override
    public int addOrderTracking(Order order, int warehouseId) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        sqlString = "UPDATE pm_order SET TRACKING_ID=?, TRACKING_CARRIER=?, TRACKING_BY=?, TRACKING_DATE=?, WAREHOUSE_TRACKING_STATUS =1, WAREHOUSE_ORDER_STATUS =1 WHERE MARKETPLACE_ORDER_ID=? AND WAREHOUSE_ID =?";
        params = new Object[]{order.getTrackingId(), order.getTrackingCarrier(), curUser, curDate, order.getMarketplaceOrderId(), warehouseId};
        return this.jdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Listing> getMarketplaceSkuList() {
        String sqlString = "SELECT 'ATVPDKIKX0DER' AS Marketplace_Id,'SellerSKU' AS ID_type,tal.`SKU`,tal.`LAST_LISTED_PRICE`,tal.`ISAMAZONFULLFILLED` FROM pm_available_listing tal WHERE tal.`FEED_STATUS`= '0' LIMIT 20 ";
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString.toString(), GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sqlString, new RowMapper<Listing>() {
            @Override
            public Listing mapRow(ResultSet rs, int i) throws SQLException {
                Listing list = new Listing();
                list.setSku(rs.getString("SKU"));
                list.setIsAmazonFulfilled(rs.getBoolean("ISAMAZONFULLFILLED"));
                list.setLastListedPrice(rs.getDouble("LAST_LISTED_PRICE"));
                return list;
            }
        });
    }

    //creating new bulk ordertracking loaddata locally, some changes are left here
    @Override
    @Transactional
    public void loadBulkOrderTrackingDataLocally(String path, int marketplaceId) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`pm_temp_bulk_tracking`";
            this.jdbcTemplate.update(sql);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate pm_temp_bulk_tracking done.", GlobalConstants.TAG_SYSTEMLOG));
            //query load data from bulk order tracking csv file into pm_temp_bulk_tracking
            sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `tel_easy_admin_tool`.`pm_temp_bulk_tracking` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`WAREHOUSE_NAME`,`ORDER_ID`, `TRACKING_ID`,`TRACKING_CARRIER`)";
            this.jdbcTemplate.execute(sql);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));
            //query update data into pm_order table

            String sql3 = "UPDATE pm_temp_bulk_tracking tb\n"
                    + "LEFT JOIN pm_warehouse tw ON tb.`WAREHOUSE_NAME`=tw.`WAREHOUSE_NAME`\n"
                    + "SET tb.`WAREHOUSE_ID` = tw.`WAREHOUSE_ID`\n"
                    + "WHERE tb.`WAREHOUSE_NAME`=tw.`WAREHOUSE_NAME`";
            int d = this.jdbcTemplate.update(sql3);

            String sql1 = "UPDATE pm_order tr \n"
                    + "LEFT JOIN pm_temp_bulk_tracking tb ON tr.`MARKETPLACE_ORDER_ID` = tb.`ORDER_ID`\n"
                    + "SET tr.`TRACKING_CARRIER`=tb.`TRACKING_CARRIER`,tr.`TRACKING_ID`=tb.`TRACKING_ID`, tr.`WAREHOUSE_ID` = tb.`WAREHOUSE_ID`, \n"
                    + "tr.`TRACKING_BY`=? ,tr.`TRACKING_DATE`=?, tr.`LAST_UPDATED_DATE`=?, tr.`WAREHOUSE_TRACKING_STATUS`=1\n"
                    + "WHERE tr.`MARKETPLACE_ORDER_ID` = tb.`ORDER_ID` AND tr.`MARKETPLACE_ID`=? ";
            int i = this.jdbcTemplate.update(sql1, curUser, curDate, curDate, marketplaceId);

            String sql2 = "INSERT INTO pm_order_trans SELECT   \n"
                    + "tr.`PO_NUMBER`, tr.`ORDER_ID` ,tr.`MARKETPLACE_ID` ,tr.`MARKETPLACE_ORDER_ID`,tr.`MARKETPLACE_SKU`,tr. `MARKETPLACE_LISTING_ID`,tr.`ORDER_DATE`,tr.\n"
                    + "`SHIP_BY_DATE`,tr.`DELIVERY_BY_DATE`,tr.`PAYMENT_DATE`,tr.`LAST_UPDATED_DATE`,tr.`CUSTOMER_NAME`,tr.`CUSTOMER_PHONE_NO`,tr.`QUANTITY_UNSHIPPED`,tr.\n"
                    + "`QUANTITY_SHIPPED`,tr.`ORDER_ITEM_ID`,tr.`PRICE`,tr.`TAX`,tr.`SHIPPING`,tr.`SHIP_TO_NAME`,tr.`SHIPPING_ADDRESS_LINE1`,tr.`SHIPPING_ADDRESS_LINE2`,tr.\n"
                    + "`SHIPPING_ADDRESS_LINE3`,tr.`CITY`,tr.`STATE`,tr.`POSTAL_CODE`,tr.`COUNTRY`,tr.`SHIPPING_PHONE_NO`,tr.`FULFILLMENT_CHANNEL`,tr.`ORDER_STATUS`,tr.\n"
                    + "`TRACKING_ID`,tr.`TRACKING_CARRIER`,tr.`CUSTOMER_ID`,tr.`WAREHOUSE_ID`,tr.`PROCESSED_BY`,tr.`PROCESSED_DATE`,tr.`TRACKING_BY`,tr.\n"
                    + "`TRACKING_DATE`,`UPDATED_BY` \n"
                    + "FROM pm_order tr LEFT JOIN  pm_temp_bulk_tracking tb ON tb.`ORDER_ID` = tr.`MARKETPLACE_ORDER_ID`\n"
                    + "WHERE tr.`MARKETPLACE_ORDER_ID` = tb.`ORDER_ID` AND tr.`MARKETPLACE_ID`=?;";
            int j = this.jdbcTemplate.update(sql2, marketplaceId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getMarketplaceSkuListForFees() {
        String sql = "SELECT tal.`SKU` FROM pm_available_listing tal LIMIT 5";
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<Order> getOrderListOfUnshippedOrderForDashboard(int count) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT t.`MARKETPLACE_ORDER_ID`,t.`ORDER_DATE`,DATE_ADD(t.`ORDER_DATE`, INTERVAL 2 DAY) shippingdate FROM pm_order t  \n"
                    + "WHERE DATEDIFF(NOW(),t.`ORDER_DATE`)>2 AND t.`ORDER_STATUS`='Unshipped'");
            if (count == 1) {
                sql.append(" LIMIT 10");
            }

//            String sql = "SELECT t.`MARKETPLACE_ORDER_ID`,t.`ORDER_DATE`,DATE_ADD(t.`ORDER_DATE`, INTERVAL 2 DAY) shippingdate FROM pm_order t  \n"
//                    + "WHERE DATEDIFF(NOW(),t.`ORDER_DATE`)>2 AND t.`ORDER_STATUS`='Unshipped' LIMIT 10;";
            return this.jdbcTemplate.query(sql.toString(), new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
                    order.setOrderDate(rs.getDate("ORDER_DATE"));
                    order.setShippingDate(rs.getDate("shippingdate"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getOrderListForTrackingUpdate() {
        try {
            String sql = "SELECT * FROM pm_temp_bulk_tracking";
            return this.jdbcTemplate.query(sql.toString(), new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
                    order.setTrackingId(rs.getString("TRACKING_ID"));
                    order.setTrackingCarrier(rs.getString("TRACKING_CARRIER"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getOderListForCreatingXml() {
        try {

            String sql = "SELECT tor.`QUANTITY_UNSHIPPED`,tor.`MARKETPLACE_ORDER_ID`,tor.`TRACKING_ID`,tor.`TRACKING_CARRIER`,tor.`ORDER_ITEM_ID` \n"
                    + "FROM pm_order tor \n"
                    + "WHERE tor.`TRACKING_STATUS`=0 AND TRACKING_ID IS NOT NULL AND tor.`MARKETPLACE_AKNOWLEDGE_STATUS`=1\n"
                    + "LIMIT 1";
            return this.jdbcTemplate.query(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
                    order.setTrackingId(rs.getString("TRACKING_ID"));
                    order.setTrackingCarrier(rs.getString("TRACKING_CARRIER"));
                    order.setOrderItemId(rs.getString("ORDER_ITEM_ID"));
                    order.setQuantityUnshipped(rs.getInt("QUANTITY_UNSHIPPED"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public void updateTrackingRecord(String trackingID) {
        String sql = "UPDATE pm_order tor SET tor.`TRACKING_STATUS`=1 WHERE tor.`TRACKING_ID`=?";
        this.jdbcTemplate.update(sql, trackingID);
    }

//    @Override
//    public Order getDataForOrderRequestXML() {
//        try {
//
//            String sql = "ELECT \n"
//                    + "tor.`CUSTOMER_NAME`,\n"
//                    + "tor.`SHIPPING_ADDRESS`,\n"
//                    + "tor.`CITY`,\n"
//                    + "tor.`STATE`,\n"
//                    + "tor.`POSTAL_CODE`,\n"
//                    + "tcp.`WAREHOUSE_IDENTIFICATION_NO`,\n"
//                    + "tcp.`PRICE`,\n"
//                    + "tor.`MARKETPLACE_SKU`,\n"
//                    + "tcp.`WAREHOUSE_IDENTIFICATION_NO`,\n"
//                    + "tcp.`PRICE`\n"
//                    + "FROM pm_order tor LEFT JOIN pm_mpn_sku_mapping msm ON msm.`SKU` = tor.`MARKETPLACE_SKU`\n"
//                    + "LEFT JOIN pm_current_warehouse_product tcp ON tcp.`WAREHOUSE_IDENTIFICATION_NO` = msm.`MANUFACTURER_MPN` LIMIT 1";
//
//            @Override
//            public Order mapRow
//            (ResultSet rs, int i) throws SQLException {
//                Order order = new Order();
//                 
//                return order;
//            }
//        });
//
//    }
//    catch (Exception e
//
//    
//        ) {
//            
//            e.printStackTrace();
//        return null;
//    }
    @Override
    public String getPoNumberBymarketplaceOrderID(String marketplaceOrderId) {
        String sql = "SELECT tor.`PO_NUMBER` FROM pm_order tor WHERE tor.`MARKETPLACE_ORDER_ID`=? LIMIT 1";
        return this.jdbcTemplate.queryForObject(sql, String.class, marketplaceOrderId);
    }

    @Override
    public String getMaxPoNumberForAutogeneration() {
        String sql = "SELECT MAX(tor.`PO_NUMBER`) FROM pm_order tor";
        return this.jdbcTemplate.queryForObject(sql, String.class, null);
    }

    @Override
    public String getOrderStatusOnMarketplaceOrderId(String marketplaceOrderId) {
        String sql = "SELECT tor.`ORDER_STATUS` FROM pm_order tor WHERE tor.`MARKETPLACE_ORDER_ID`=?";
        return this.jdbcTemplate.queryForObject(sql, String.class, marketplaceOrderId);
    }

    @Override
    public int getCountOfTicketAssignedToOrder(String marketplaceOrderId) {
        String sql = "SELECT COUNT(*) \n"
                + "FROM tkt_ticket tt \n"
                + "LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                + "LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                + "LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                + "LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                + "LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                + "LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                + "WHERE tt.`ORDER_ID`= ? AND tts.`STATUS_DESC` IN('New','Assigned','In Progress')";

        return this.jdbcTemplate.queryForInt(sql, marketplaceOrderId);
    }

    @Override
    public void updateOrderAcknowledgementRecord(String orderId) {
        String sql = "UPDATE pm_order tor SET tor.`MARKETPLACE_AKNOWLEDGE_STATUS`=1 WHERE tor.`MARKETPLACE_ORDER_ID`=?";
        this.jdbcTemplate.update(sql, orderId);
    }

    @Override
    public Order getOrderForAmazonAcknowledgement() {
        try {
            String sql = "SELECT tor.`MARKETPLACE_ORDER_ID`,tor.`ORDER_ITEM_ID` FROM pm_order tor \n"
                    + "WHERE tor.`WAREHOUSE_TRACKING_STATUS`=1 AND tor.`MARKETPLACE_AKNOWLEDGE_STATUS`=0 AND tor.TRACKING_ID IS NOT NULL \n"
                    + "AND tor.`WAREHOUSE_ID`!=0 LIMIT 1";
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
                    order.setOrderItemId(rs.getString("ORDER_ITEM_ID"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public void loadMarketPlaceOrderDataLocally(String path, int marketplaceId) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`temp_marketplace_order_upload`";
            this.jdbcTemplate.update(sql);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate temp_table done.", GlobalConstants.TAG_SYSTEMLOG));

            sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `tel_easy_admin_tool`.`temp_marketplace_order_upload` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MARKETPLACE_ORDER_ID`,`MARKETPLACE_SKU`,`MARKETPLACE_LISTING_ID`,`ORDER_DATE`,`SHIP_BY_DATE`,`DELIVERY_BY_DATE`,`CUSTOMER_NAME`,`CUSTOMER_PHONE_NO`,`QUANTITY`,`ORDER_ITEM_ID`,`PRICE`,`TAX`,`SHIPPING`,`SHIP_TO_NAME`,`SHIPPING_ADDRESS_LINE1`,`SHIPPING_ADDRESS_LINE2`,`SHIPPING_ADDRESS_LINE3`,`CITY`,`STATE`,`POSTAL_CODE`,`COUNTRY`,`SHIPPING_PHONE_NO`,`FULFILLMENT_CHANNEL`,`ORDER_STATUS`)";
            this.jdbcTemplate.execute(sql);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));

            sql = "INSERT IGNORE INTO pm_order(`MARKETPLACE_ID`,`LAST_UPDATED_DATE`,`MARKETPLACE_ORDER_ID`,`MARKETPLACE_SKU`,`MARKETPLACE_LISTING_ID`,`ORDER_DATE`,`SHIP_BY_DATE`,`DELIVERY_BY_DATE`,`CUSTOMER_NAME`,`CUSTOMER_PHONE_NO`,`QUANTITY_UNSHIPPED`,`ORDER_ITEM_ID`,`PRICE`,`TAX`,`SHIPPING`,`SHIP_TO_NAME`,`SHIPPING_ADDRESS_LINE1`,`SHIPPING_ADDRESS_LINE2`,`SHIPPING_ADDRESS_LINE3`,`CITY`,`STATE`,`POSTAL_CODE`,`COUNTRY`,`SHIPPING_PHONE_NO`,`FULFILLMENT_CHANNEL`,`ORDER_STATUS`,`UPDATED_BY`)\n"
                    + "SELECT  ?,?,`MARKETPLACE_ORDER_ID`,`MARKETPLACE_SKU`,`MARKETPLACE_LISTING_ID`,`ORDER_DATE`,`SHIP_BY_DATE`,`DELIVERY_BY_DATE`,`CUSTOMER_NAME`,`CUSTOMER_PHONE_NO`,`QUANTITY`,`ORDER_ITEM_ID`,`PRICE`,`TAX`,`SHIPPING`,`SHIP_TO_NAME`,`SHIPPING_ADDRESS_LINE1`,`SHIPPING_ADDRESS_LINE2`,`SHIPPING_ADDRESS_LINE3`,`CITY`,`STATE`,`POSTAL_CODE`,`COUNTRY`,`SHIPPING_PHONE_NO`,`FULFILLMENT_CHANNEL`,`ORDER_STATUS`,? FROM temp_marketplace_order_upload   ";

            jdbcTemplate.update(sql, marketplaceId, curDate, "Manually");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Order> getMarketplaceOrderList(int marketplaceId, String marketplaceOrderId, String poNumber, int pageNo, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT teo.`MARKETPLACE_ORDER_ID`,teo.`UPDATED_BY`,teo.`PROCESSED_BY`,teo.`PROCESSED_DATE`,teo.`DELIVERY_BY_DATE`,teo.`SHIP_BY_DATE`,teo.`WAREHOUSE_ID`,teo.`MARKETPLACE_SKU`,teo.`TAX`,teo.`PO_NUMBER`,teo.`MARKETPLACE_LISTING_ID`,teo.`ORDER_DATE`,teo.`LAST_UPDATED_DATE`,teo.`CUSTOMER_NAME`,teo.`CUSTOMER_PHONE_NO`,teo.`QUANTITY_UNSHIPPED`,teo.`QUANTITY_SHIPPED`,teo.`PRICE`,teo.`SHIP_TO_NAME`,"
                + " teo.`SHIPPING_ADDRESS_LINE1`,teo.`SHIPPING_ADDRESS_LINE2`,teo.`SHIPPING_ADDRESS_LINE3`,teo.`CITY`,teo.`STATE`,teo.`POSTAL_CODE`,teo.`COUNTRY`,teo.`SHIPPING_PHONE_NO`,teo.`ORDER_STATUS`,teo.`FULFILLMENT_CHANNEL`,tm.`MARKETPLACE_NAME`,tw.`WAREHOUSE_NAME`,teo.`TRACKING_ID`,teo.`TRACKING_CARRIER`, teo.`TRACKING_BY`,teo.`TRACKING_DATE`, u.`USERNAME` FROM pm_order teo "
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=teo.`WAREHOUSE_ID`"
                + " LEFT JOIN user u ON u.`USER_ID`=teo.`TRACKING_BY`"
                + " LEFT JOIN pm_marketplace tm ON teo.`MARKETPLACE_ID`=tm.`MARKETPLACE_ID` WHERE teo.`ORDER_ID` AND teo.`ORDER_STATUS`!='Pending'");
        Map<String, Object> params = new HashMap<String, Object>();

        if (marketplaceId != 0) {
            sql.append(" AND tm.MARKETPLACE_ID=:marketplaceId ");
            params.put("marketplaceId", marketplaceId);
        }
        
        if (marketplaceOrderId != null && marketplaceOrderId != "") {
            sql.append(" AND teo.`MARKETPLACE_ORDER_ID`=:marketplaceOrderId ");
            params.put("marketplaceOrderId", marketplaceOrderId);
        }
        
        if (poNumber != null && poNumber != "") {
            sql.append(" AND teo.`PO_NUMBER`=:poNumber ");
            params.put("poNumber", poNumber);
        }

        if (customerName != null && customerName != "") {
            sql.append(" AND teo.`CUSTOMER_NAME`=:customerName ");
            params.put("customerName", customerName);
        }

        if (marketplaceSku != null && marketplaceSku != "") {
            sql.append(" AND teo.`MARKETPLACE_SKU`=:marketplaceSku ");
            params.put("marketplaceSku", marketplaceSku);
        }

        if (marketplaceListingId != null && marketplaceListingId != "") {
            sql.append(" AND teo.`MARKETPLACE_LISTING_ID`=:marketplaceListingId ");
            params.put("marketplaceListingId", marketplaceListingId);
        }

        if (orderStatus != null && orderStatus != "") {
            sql.append(" AND teo.`ORDER_STATUS`=:orderStatus ");
            params.put("orderStatus", orderStatus);
        }

        if (fulfillmentChannel != null && fulfillmentChannel != "") {
            sql.append(" AND teo.`FULFILLMENT_CHANNEL`=:fulfillmentChannel ");
            params.put("fulfillmentChannel", fulfillmentChannel);
        }

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(stopDate)) {
            startDate += " 00:00:00";
            stopDate += " 23:59:59";
            sql.append(" AND teo.`ORDER_DATE` BETWEEN :startDate AND :stopDate ORDER BY teo.`ORDER_DATE` DESC");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }

        if (pageNo != -1) {
            sql.append(" LIMIT ").append((pageNo - 1) * GlobalConstants.PAGE_SIZE).append(",").append(GlobalConstants.PAGE_SIZE);
        }

        //System.out.println("Query is:" + sql);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        List<Order> list = nm.query(sql.toString(), params, new MarketplaceOrderRowMapper());
        return list;

    }

    @Override
    public int getOrderCount(int marketplaceId, String marketplaceOrderId, String poNumber, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM pm_order teo"
                + " LEFT JOIN pm_marketplace tm ON teo.`MARKETPLACE_ID`=tm.`MARKETPLACE_ID`"
                + " WHERE teo.`ORDER_ID` AND teo.`ORDER_STATUS`!='Pending'");
        Map<String, Object> params = new HashMap<String, Object>();

        if (marketplaceId != 0) {
            
            sql.append(" AND teo.`MARKETPLACE_ID`=:marketplaceId ");
            params.put("marketplaceId", marketplaceId);
        }

        if (marketplaceOrderId != null && marketplaceOrderId != "") {
            sql.append(" AND teo.`MARKETPLACE_ORDER_ID`=:marketplaceOrderId ");
            params.put("marketplaceOrderId", marketplaceOrderId);
        }
        
        if (poNumber != null && poNumber != "") {
            sql.append(" AND teo.`PO_NUMBER`=:poNumber ");
            params.put("poNumber", poNumber);
        }

        if (customerName != null && customerName != "") {
            sql.append(" AND teo.`CUSTOMER_NAME`=:customerName ");
            params.put("customerName", customerName);
        }

        if (marketplaceSku != null && marketplaceSku != "") {
            sql.append(" AND teo.`MARKETPLACE_SKU`=:marketplaceSku ");
            params.put("marketplaceSku", marketplaceSku);
        }

        if (marketplaceListingId != null && marketplaceListingId != "") {
            sql.append(" AND teo.`MARKETPLACE_LISTING_ID`=:marketplaceListingId ");
            params.put("marketplaceListingId", marketplaceListingId);
        }

        if (orderStatus != null && orderStatus != "") {
            sql.append(" AND teo.`ORDER_STATUS`=:orderStatus ");
            params.put("orderStatus", orderStatus);
        }

        if (fulfillmentChannel != null && fulfillmentChannel != "") {
            sql.append(" AND teo.`FULFILLMENT_CHANNEL`=:fulfillmentChannel ");
            params.put("fulfillmentChannel", fulfillmentChannel);
        }

        if ( StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(stopDate)) {
            startDate += " 00:00:00";
            stopDate += " 23:59:59";
            sql.append(" AND teo.`ORDER_DATE` BETWEEN :startDate AND :stopDate");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        Integer i = nm.queryForObject(sql.toString(), params, Integer.class);

        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

}
