/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.OrderDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.service.OrderApiService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import com.amazonservices.mws.orders.amazon.AmazonWebService;
import com.amazonservices.mws.orders.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders.model.ListOrdersResponse;
import com.amazonservices.mws.orders.model.Order;
import com.amazonservices.mws.orders.model.OrderItem;
import com.amazonservices.mws.orders.model.ResponseHeaderMetadata;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import java.util.Optional;
import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class OrderApiServiceImpl implements OrderApiService {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    private OrderDao orderDao;

    @Override
    public ListOrdersResponse getOrderList(XMLGregorianCalendar lastUpdatedAfterDate) {
        ListOrdersResponse response = null;
        ListOrderItemsResponse response1 = null;
        try {
            AmazonWebService a = new AmazonWebService("/home/altius/performanceMods/amazon/amazon.properties");

            if (a.isPropsLoaded()) {
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("Properties file loaded, Going to do get order list", GlobalConstants.TAG_SYSTEMLOG));
                try {

                    NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
                    int x = 0;
                    int customerId = 0;
                    String sqlInsert = "";
                    String contact = "";
//                   String PONumber = "";
                    String phone = "";
//                    String nxtPONumber = "";
                    KeyHolder keyHolder1 = new GeneratedKeyHolder();
                    response = a.getOrderList(lastUpdatedAfterDate);
                    String curDate = DateUtils.getCurrentDateString(DateUtils.PST, DateUtils.YMDHMS);
                    System.out.println("size :" + response.getListOrdersResult().getOrders().size());
                    MapSqlParameterSource[] batchParams1 = new MapSqlParameterSource[response.getListOrdersResult().getOrders().size()];
                    MapSqlParameterSource[] batchParams2 = new MapSqlParameterSource[response.getListOrdersResult().getOrders().size()];
                    ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
                    Map<String, Object> params = new HashMap<String, Object>();
                    Map<String, Object> params2 = new HashMap<String, Object>();
                    MapSqlParameterSource params1 = new MapSqlParameterSource();
                    String sqlInsertCustomer = "INSERT IGNORE INTO customers (CUSTOMER_NAME,SHIPPING_ADDRESS_LINE1,SHIPPING_ADDRESS_LINE2,SHIPPING_ADDRESS_LINE3,CITY,STATE,ZIP_CODE,COUNTRY,PHONE_NO,EMAIL)"
                            + " VALUES(:customerName,:shippingAddressLine1,:shippingAddressLine2,:shippingAddressLine3,:city,:state,:postalCode,:country,:customerPhone,:email)";

                    String sql = "INSERT IGNORE INTO tesy_order (PO_NUMBER,ORDER_ID,MARKETPLACE_ID,MARKETPLACE_ORDER_ID,MARKETPLACE_SKU,MARKETPLACE_LISTING_ID,ORDER_DATE,SHIP_BY_DATE,DELIVERY_BY_DATE,"
                            + " PAYMENT_DATE,LAST_UPDATED_DATE,CUSTOMER_NAME,CUSTOMER_PHONE_NO,QUANTITY_UNSHIPPED,QUANTITY_SHIPPED,ORDER_ITEM_ID,PRICE,TAX,SHIPPING,SHIP_TO_NAME,SHIPPING_ADDRESS_LINE1,SHIPPING_ADDRESS_LINE2,SHIPPING_ADDRESS_LINE3,"
                            + " CITY,STATE,POSTAL_CODE,COUNTRY,SHIPPING_PHONE_NO,FULFILLMENT_CHANNEL,ORDER_STATUS,CUSTOMER_ID,UPDATED_BY)"
                            + " VALUES (:poNumber,NULL,:marketplaceId,:marketplace_order_id,:marketplaceSku,:marketplaceListingId,:orderDate,:latestShipDate,:latestDeliveryDate,:paymentDate,:lastUpdatedDate,:customerName,"
                            + " :customerPhone,:quantityUnshipped,:quantityShipped,:orderItemId,:price,:tax,:shipping,:shipToName,:shippingAddressLine1,:shippingAddressLine2,:shippingAddressLine3,:city,:state,:postalCode,:country,:shipPhoneNo,:fulfillmentChannel,:orderStatus,:customerId,:updatedBy) ON DUPLICATE KEY "
                            + " UPDATE  SHIP_BY_DATE=:latestShipDate,DELIVERY_BY_DATE=:latestDeliveryDate,LAST_UPDATED_DATE=:lastUpdatedDate,CUSTOMER_NAME=:customerName,CUSTOMER_PHONE_NO=:customerPhone,QUANTITY_UNSHIPPED=:quantityUnshipped,QUANTITY_SHIPPED=:quantityShipped,PRICE=:price,TAX=:tax,"
                            + " SHIPPING=:shipping,SHIP_TO_NAME=:shipToName,SHIPPING_ADDRESS_LINE1=:shippingAddressLine1,SHIPPING_ADDRESS_LINE2=:shippingAddressLine2,SHIPPING_ADDRESS_LINE3=:shippingAddressLine3,CITY=:city,STATE=:state,"
                            + " POSTAL_CODE=:postalCode,COUNTRY=:country,SHIPPING_PHONE_NO=:shipPhoneNo,FULFILLMENT_CHANNEL=:fulfillmentChannel,ORDER_STATUS=:orderStatus,CUSTOMER_ID=:customerId,UPDATED_BY=:updatedBy";

//                    PONumber = this.orderDao.getMaxPoNumberForAutogeneration();
//                    String nxtNumber = PONumber.substring(3);
                    for (Order o : response.getListOrdersResult().getOrders()) {

                        response1 = a.getOrderItemAsyncList(o.getAmazonOrderId());

                        for (OrderItem ot : response1.getListOrderItemsResult().getOrderItems()) {

//                            nxtNumber = "" + (Integer.parseInt(nxtNumber) + 1);
//                            nxtPONumber = "ING" + nxtNumber;

                            params1.addValue("customerName", o.getBuyerName());
                            if (o.getShippingAddress() == null) {
                                params1.addValue("shipToName", "");
                                params1.addValue("shippingAddressLine1", "");
                                params1.addValue("shippingAddressLine2", "");
                                params1.addValue("shippingAddressLine3", "");
                                params1.addValue("city", "");
                                params1.addValue("state", "");
                                params1.addValue("postalCode", "");
                                params1.addValue("country", "");
                                params1.addValue("shipPhoneNo", "");
                            } else {
                                params1.addValue("shipToName", o.getShippingAddress().getName());
                                params1.addValue("shippingAddressLine1", o.getShippingAddress().getAddressLine1());

                                if (o.getShippingAddress().getAddressLine2() != null) {
                                    params1.addValue("shippingAddressLine2", o.getShippingAddress().getAddressLine2());
                                } else {
                                    params1.addValue("shippingAddressLine2", "");
                                }
                                if (o.getShippingAddress().getAddressLine3() != null) {
                                    params1.addValue("shippingAddressLine3", o.getShippingAddress().getAddressLine3());
                                } else {
                                    params1.addValue("shippingAddressLine3", "");
                                }
                                params1.addValue("city", o.getShippingAddress().getCity());
                                params1.addValue("state", o.getShippingAddress().getStateOrRegion());
                                params1.addValue("postalCode", o.getShippingAddress().getPostalCode());
                                params1.addValue("country", o.getShippingAddress().getCountryCode());
                                params1.addValue("updatedBy", "Api");
                                if (o.getShippingAddress().getPhone() != null) {
                                     phone = o.getShippingAddress().getPhone();
                                    String number = phone.replaceAll("[^,0-9]", "");
//                                    int i=number.indexOf("X");
//                                    String result="";
//                                    if(i== -1){
//                                        result = number.substring(number.length() - 10);
//                                    }else{
//                                        result= number.substring(0,i);
//                                    }
                                    
//                                    contact = result.substring(result.length() - 10);
                                    contact =  number;
                                } else {
                                    contact = "";
                                }

                                params1.addValue("shipPhoneNo", contact);
                                params1.addValue("customerPhone", contact);
                                
                            }
                            params1.addValue("email", o.getBuyerEmail());

                            if ("Pending".equals(o.getOrderStatus()) || "Canceled".equals(o.getOrderStatus())) {
                                customerId = 0;

                            } else {
                                nm.update(sqlInsertCustomer, params1, keyHolder1);
                                if (keyHolder1.getKey() != null) {
                                    customerId = keyHolder1.getKey().intValue();
                                } else {
                                    String sqlSelect = "SELECT c.`CUSTOMER_ID` FROM customers c WHERE c.`CUSTOMER_NAME`=? AND c.`PHONE_NO`=? AND c.`ZIP_CODE`=?";
                                    customerId = this.jdbcTemplate.queryForInt(sqlSelect, o.getBuyerName(), contact, o.getShippingAddress().getPostalCode());
                                }
                            }
                            params.put("customerId", customerId);
                            //tesy_order table insert

                            params.put("poNumber", null);
                            params.put("marketplaceId", "1");
                            params.put("marketplace_order_id", o.getAmazonOrderId());
                            params.put("marketplaceSku", ot.getSellerSKU());
                            params.put("marketplaceListingId", ot.getASIN());
                            params.put("orderDate", o.getPurchaseDate().toGregorianCalendar().getTime());
                            if (o.getLatestShipDate() != null) {
                                params.put("latestShipDate", o.getLatestShipDate().toGregorianCalendar().getTime());
                            } else {
                                params.put("latestShipDate", "");
                            }
                            if (o.getLatestDeliveryDate() != null) {

                                params.put("latestDeliveryDate", o.getLatestDeliveryDate().toGregorianCalendar().getTime());
                            } else {

                                params.put("latestDeliveryDate", curDate);
                            }
                            params.put("paymentDate", o.getPurchaseDate().toGregorianCalendar().getTime());
                            params.put("lastUpdatedDate", o.getLastUpdateDate().toGregorianCalendar().getTime());
                            params.put("customerName", o.getBuyerName());
                            params.put("orderItemId", ot.getOrderItemId());
                            params.put("quantityUnshipped", o.getNumberOfItemsUnshipped());
                            params.put("quantityShipped", o.getNumberOfItemsShipped());
                            if (o.getOrderTotal() == null) {
                                params.put("price", "0.0");
                            } else {
                                params.put("price", Double.parseDouble(o.getOrderTotal().getAmount()));
                            }
                            if (ot.getItemTax() == null) {
                                params.put("tax", "0.0");
                            } else {
                                params.put("tax", ot.getItemTax().getAmount());
                            }
                            if (ot.getShippingPrice() == null) {
                                params.put("shipping", "0.0");
                            } else {
                                params.put("shipping", ot.getShippingPrice().getAmount());
                            }
                            if (o.getShippingAddress() == null) {
                                params.put("shipToName", "");
                                params.put("shippingAddressLine1", "");
                                params.put("shippingAddressLine2", "");
                                params.put("shippingAddressLine3", "");
                                params.put("city", "");
                                params.put("state", "");
                                params.put("postalCode", "");
                                params.put("country", "");
                                params.put("shipPhoneNo", "");
                                params.put("customerPhone", "");

                            } else {
                                params.put("shipToName", o.getShippingAddress().getName());
                                params.put("shippingAddressLine1", o.getShippingAddress().getAddressLine1());
                                if (o.getShippingAddress().getAddressLine2() != null) {
                                    params.put("shippingAddressLine2", o.getShippingAddress().getAddressLine2());
                                } else {
                                    params.put("shippingAddressLine2", "");
                                }
                                if (o.getShippingAddress().getAddressLine3() != null) {
                                    params.put("shippingAddressLine3", o.getShippingAddress().getAddressLine3());
                                } else {
                                    params.put("shippingAddressLine3", "");
                                }
                                params.put("city", o.getShippingAddress().getCity());
                                params.put("state", o.getShippingAddress().getStateOrRegion());
                                params.put("postalCode", o.getShippingAddress().getPostalCode());
                                params.put("country", o.getShippingAddress().getCountryCode());
                                if (o.getShippingAddress().getPhone() != null) {
                                    phone = o.getShippingAddress().getPhone();
                                    String number = phone.replaceAll("[^,0-9]", "");
//                                    int i=number.indexOf("X");
//                                    String result="";
//                                    if(i== -1){
//                                        result = number.substring(number.length() - 10);
//                                    }else{
//                                        result= number.substring(0,i);
//                                    }
                                    
//                                    contact = result.substring(result.length() - 10);
                                    contact = number;

                                } else {
                                    contact = "";
                                }
                                params.put("shipPhoneNo", contact);
                                params.put("customerPhone", contact);
                            }
                            params.put("updatedBy", "Api");
                            params2.put("updatedBy", "Api");
                            params.put("orderStatus", o.getOrderStatus());
                            String fulfillmentChannel = o.getFulfillmentChannel();
                            if ("MFN".equals(fulfillmentChannel)) {
                                fulfillmentChannel = "Seller";
                            }
                            if ("AFN".equals(fulfillmentChannel)) {
                                fulfillmentChannel = "Amazon";
                            }
                            params.put("fulfillmentChannel", fulfillmentChannel);
                            batchParams1[x] = new MapSqlParameterSource(params);

                            params.clear();
                            params2.put("marketplace_order_id", o.getAmazonOrderId());
                            //tesy_order_trans table insert
                            params.put("marketplace_order_id", o.getAmazonOrderId());
                            batchParams2[x] = new MapSqlParameterSource(params2);


                            sqlInsert = "INSERT INTO tesy_order_trans"
                                    + " SELECT tor.PO_NUMBER,tor.ORDER_ID,tor.MARKETPLACE_ID,tor.MARKETPLACE_ORDER_ID,tor.MARKETPLACE_SKU,tor.MARKETPLACE_LISTING_ID,tor.ORDER_DATE,tor.SHIP_BY_DATE,tor.DELIVERY_BY_DATE,"
                                    + " tor.PAYMENT_DATE,tor.LAST_UPDATED_DATE,tor.CUSTOMER_NAME,tor.CUSTOMER_PHONE_NO,tor.QUANTITY_UNSHIPPED,"
                                    + " tor.QUANTITY_SHIPPED,tor.ORDER_ITEM_ID,tor.PRICE,tor.TAX,tor.SHIPPING,tor.SHIP_TO_NAME,tor.SHIPPING_ADDRESS_LINE1,tor.SHIPPING_ADDRESS_LINE2,tor.SHIPPING_ADDRESS_LINE3,"
                                    + " tor.CITY,tor.STATE,tor.POSTAL_CODE,tor.COUNTRY,tor.SHIPPING_PHONE_NO,tor.FULFILLMENT_CHANNEL,tor.ORDER_STATUS,"
                                    + " tor.CUSTOMER_ID,tor.WAREHOUSE_ID,tor.PROCESSED_BY,tor.PROCESSED_DATE, tor.`TRACKING_BY`,tor.`TRACKING_CARRIER`,"
                                    + " tor.`TRACKING_DATE`,tor.`TRACKING_ID`,tor.`UPDATED_BY` FROM tesy_order tor WHERE "
                                    + " tor. MARKETPLACE_ORDER_ID=:marketplace_order_id";
                            x++;
                        }
                    }
                    LogUtils.systemLogger.info(LogUtils.buildStringForLog("Response-RequestId:" + rhmd.getRequestId() + "Timestamp:" + rhmd.getTimestamp(), GlobalConstants.TAG_SYSTEMLOG));
                    params.clear();
                    int[] resultList = nm.batchUpdate(sql, batchParams1);
                    int[] batchUpdate = nm.batchUpdate(sqlInsert, batchParams2);
                    String sqlpo = "UPDATE tesy_order tor SET tor.`PO_NUMBER`=CONCAT('TEL-',tor.`ORDER_ID`) WHERE tor.`PO_NUMBER` IS NULL";
                    this.jdbcTemplate.update(sqlpo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                }
            } else {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("Properties file not loaded so cannot proceed", GlobalConstants.TAG_SYSTEMLOG));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
        }
        return response;
    }
}
