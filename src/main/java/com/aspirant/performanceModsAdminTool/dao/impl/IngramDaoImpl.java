/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.IngramDao;
import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.rowmapper.OrderRequestXMLRowMapper;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ritesh
 */
@Repository
public class IngramDaoImpl implements IngramDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CurrentWarehouseProduct> getWarehouseIdentificationNo() {
        try {
            String sql = "SELECT tp.`WAREHOUSE_IDENTIFICATION_NO` FROM pm_current_warehouse_product tp \n"
                    + "WHERE tp.`WAREHOUSE_ID` = 20 AND tp.`FEED_STATUS`=0 LIMIT 1";

            return this.jdbcTemplate.query(sql, new RowMapper<CurrentWarehouseProduct>() {
                @Override
                public CurrentWarehouseProduct mapRow(ResultSet rs, int i) throws SQLException {
                    CurrentWarehouseProduct cwp = new CurrentWarehouseProduct();
                    cwp.setWarehouseIdentificationNum(rs.getString("WAREHOUSE_IDENTIFICATION_NO"));
                    return cwp;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateCurrentWareHouseProductForIngram(String sku, int quantity, double price) {
        try {
            String sql = "UPDATE pm_current_warehouse_product tp SET  tp.`QUANTITY`=? , tp.`PRICE`= ?,tp.`FEED_STATUS`=1\n"
                    + "WHERE tp.`WAREHOUSE_ID`=20 AND tp.`WAREHOUSE_IDENTIFICATION_NO`=?";
            return this.jdbcTemplate.update(sql, quantity, price, sku);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void flushDataForIngramPNA() {
        String sql = "UPDATE pm_current_warehouse_product tcwp SET tcwp.`FEED_STATUS`=0 WHERE tcwp.`WAREHOUSE_ID`=20";
        this.jdbcTemplate.update(sql);
    }

    @Override   
    public Order getDataforOrderRequestXML(String poNumber) {
        try {
            String sql = "SELECT tor.`PO_NUMBER`,tor.`SHIP_TO_NAME`,tor.`SHIPPING_ADDRESS_LINE1`,tor.`SHIPPING_ADDRESS_LINE2`,\n"
                    + "tor.`SHIPPING_ADDRESS_LINE3`,tor.`CITY`,tor.`STATE`,tor.`POSTAL_CODE`,tcwp.`PRICE`,tor.`WAREHOUSE_ID`,\n"
                    + "tor.`QUANTITY_UNSHIPPED`,tcwp.`WAREHOUSE_IDENTIFICATION_NO` FROM pm_order tor \n"
                    + "LEFT JOIN pm_mpn_sku_mapping tmsm ON tmsm.`SKU`=tor.`MARKETPLACE_SKU`\n"
                    + "LEFT JOIN pm_current_warehouse_product tcwp ON tcwp.`MPN`=tmsm.`MANUFACTURER_MPN`\n"
                    + "WHERE  tor.`WAREHOUSE_ID`=20 AND tcwp.`WAREHOUSE_ID`=20 AND tor.`PO_NUMBER`= ?";

            return this.jdbcTemplate.queryForObject(sql, new OrderRequestXMLRowMapper(), poNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Order getDataForOrderDetailRequest() {

        try {
            // 
            String sql = "SELECT tor.`PO_NUMBER`,tor.`WAREHOUSE_ORDER_NUMBER` FROM pm_order tor WHERE tor.`WAREHOUSE_ID`=20 AND tor.`PO_NUMBER`=?";
            // here problem is we are getting data frm getDataForOrderDetailRequestXml, ?<-- frm where we get PO_Number
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setPoNumber(rs.getString("PO_NUMBER"));
                    order.setWarehouseOrderNumber(rs.getString("WAREHOUSE_ORDER_NUMBER"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getDataForInvoiceXml() {

        try {
            // 
            String sql = "";

            return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();

                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int updateWarehouseOrderStatus(String warehouseOrderStatus, String poNumber, String warehouseOrderNumber) {
        try {
            String sql = "UPDATE pm_order tor SET tor.`WAREHOUSE_ORDER_STATUS`=?, tor.`WAREHOUSE_ORDER_NUMBER`=? WHERE tor.`PO_NUMBER`=?";
            return this.jdbcTemplate.update(sql, warehouseOrderStatus, warehouseOrderNumber, poNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateTrackingCarrierAndId(String trackingCarrier, String trackingId, String poNumber) {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            String sql = "UPDATE pm_order tor SET tor.`TRACKING_CARRIER`=?,tor.`TRACKING_ID`=?,tor.`TRACKING_BY`=52,\n"
                    + "tor.`WAREHOUSE_TRACKING_STATUS`=1,tor.`TRACKING_DATE`=? WHERE tor.`PO_NUMBER`=?";
            return this.jdbcTemplate.update(sql, trackingCarrier, trackingId,curDate,poNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getPoNumberForOrderStatus() {
        try {
            String sql = "SELECT tr.`PO_NUMBER` FROM pm_order tr WHERE tr.`WAREHOUSE_ID`=20 AND tr.`ORDER_STATUS`='Unshipped' AND tr.`WAREHOUSE_ORDER_STATUS` !='BILLED' LIMIT 1";
            String result = this.jdbcTemplate.queryForObject(sql, String.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getDataForOrderTrackingRequest() {
        try {
            String sql = "SELECT tor.`PO_NUMBER`,tor.`WAREHOUSE_ORDER_NUMBER` FROM pm_order tor WHERE tor.`WAREHOUSE_ID`=20 AND tor.`WAREHOUSE_TRACKING_STATUS`=0 AND tor.`WAREHOUSE_ORDER_STATUS` ='BILLED' OR tor.`WAREHOUSE_ORDER_STATUS` ='INVOICED' AND tor.`ORDER_STATUS`='Unshipped' LIMIT 1";
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setPoNumber(rs.getString("PO_NUMBER"));
                    order.setWarehouseOrderNumber(rs.getString("WAREHOUSE_ORDER_NUMBER"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void flushDataForIngramOrderTracking() {
        String sql = "UPDATE pm_order tor SET tor.`TRACKING_STATUS`=0 WHERE tcwp.`WAREHOUSE_ID`=20";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public int updateIngramOrderResponceData(String warehouseOrderNumber, String poNumber) {
        String sql = "UPDATE pm_order tor SET tor.`WAREHOUSE_ORDER_NUMBER`=? AND WAREHOUSE_ORDER_STATUS='PENDING' WHERE tor.`PO_NUMBER`= ?";
        return this.jdbcTemplate.update(sql, warehouseOrderNumber, poNumber);
    }

    @Override
    public Order getDataForIngramBaseRateRequest() {
        try {
            // 
            String sql = "SELECT tor.`WAREHOUSE_ORDER_NUMBER`,tor.`POSTAL_CODE` FROM pm_order tor WHERE tor.`WAREHOUSE_ORDER_NUMBER` IS NOT NULL AND tor.`WAREHOUSE_ID`=20 LIMIT 1";
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setPoNumber(rs.getString("WAREHOUSE_ORDER_NUMBER"));
                    order.setWarehouseOrderNumber(rs.getString("POSTAL_CODE"));
                    return order;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
