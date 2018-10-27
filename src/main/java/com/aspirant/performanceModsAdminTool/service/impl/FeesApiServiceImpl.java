/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.OrderDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.service.FeesApiService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders.amazon.AmazonWebService;
import com.amazonservices.mws.orders.model.ResponseHeaderMetadata;
import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.model.FeesEstimateIdentifier;
import com.amazonservices.mws.products.model.FeesEstimateRequest;
import com.amazonservices.mws.products.model.FeesEstimateRequestList;
import com.amazonservices.mws.products.model.FeesEstimateResult;
import com.amazonservices.mws.products.model.GetMyFeesEstimateRequest;
import com.amazonservices.mws.products.model.GetMyFeesEstimateResponse;
import com.amazonservices.mws.products.model.MoneyType;
import com.amazonservices.mws.products.model.PriceToEstimateFees;
import com.amazonservices.mws.products.samples.MarketplaceWebServiceProductsSampleConfig;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class FeesApiServiceImpl implements FeesApiService {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    @Autowired
    private OrderDao orderDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public GetMyFeesEstimateResponse getMyFeesEstimate() {
        GetMyFeesEstimateResponse response = null;
        try {
            AmazonWebService a = new AmazonWebService("/home/altius/performanceMods/amazon/amazon.properties");
            if (a.isPropsLoaded()) {
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("Properties file loaded, Going to do get order list", GlobalConstants.TAG_SYSTEMLOG));
                try {
                    MarketplaceWebServiceProductsAsyncClient client = MarketplaceWebServiceProductsSampleConfig.getAsyncClient();
                    List<GetMyFeesEstimateRequest> requestList = new ArrayList<GetMyFeesEstimateRequest>();
                    GetMyFeesEstimateRequest feesRequest = new GetMyFeesEstimateRequest();
                    feesRequest.setSellerId(a.getSellerId());
                    feesRequest.setMWSAuthToken(a.getMwsAuthToken());
                    int size = 0;
                    do {

                        List<Listing> skuList = this.orderDao.getMarketplaceSkuList();
                        size = skuList.size();
                        List<FeesEstimateRequest> listOfFeesEstimatedRequest = new ArrayList<FeesEstimateRequest>();
                        for (Listing listing : skuList) {
                            FeesEstimateRequest feesEstimateRequest = new FeesEstimateRequest();
                            feesEstimateRequest.setIdType("SellerSKU");
                            feesEstimateRequest.setIdValue(listing.getSku());
                            feesEstimateRequest.setIdentifier("ABC124");
                            feesEstimateRequest.setIsAmazonFulfilled(false);
                            feesEstimateRequest.setMarketplaceId("ATVPDKIKX0DER");
                            MoneyType mtype = new MoneyType();
                            mtype.setAmount(new BigDecimal(listing.getLastListedPrice()));
                            mtype.setCurrencyCode("USD");
                            PriceToEstimateFees ps = new PriceToEstimateFees();
                            ps.setListingPrice(mtype);
                            feesEstimateRequest.setPriceToEstimateFees(ps);
                            listOfFeesEstimatedRequest.add(feesEstimateRequest);
                        }
                        FeesEstimateRequestList feesEstimateRequestList = new FeesEstimateRequestList();
                        feesEstimateRequestList.setFeesEstimateRequest(listOfFeesEstimatedRequest);
                        feesRequest.setFeesEstimateRequestList(feesEstimateRequestList);
                        requestList.add(feesRequest);
                        response = a.invokeGetMyFeesEstimate(client, feesRequest);
                        System.out.println("size :" + response.getGetMyFeesEstimateResult().getFeesEstimateResultList().getFeesEstimateResult().size());
                        
                        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
                        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[response.getGetMyFeesEstimateResult().getFeesEstimateResultList().getFeesEstimateResult().size()];
                        com.amazonservices.mws.products.model.ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
                        MapSqlParameterSource params = new MapSqlParameterSource();

                        String sql = "UPDATE tesy_available_listing tal SET tal.`CURRENT_COMMISSION`=:estimatedFees,tal.`FEED_STATUS`=:feesStatus WHERE tal.`SKU`=:sellerSKU";
                        for (FeesEstimateResult f : response.getGetMyFeesEstimateResult().getFeesEstimateResultList().getFeesEstimateResult()) {
                            if (f.getFeesEstimate() != null) {
                                params.addValue("estimatedFees", f.getFeesEstimate().getTotalFeesEstimate().getAmount());
                                params.addValue("sellerSKU", f.getFeesEstimateIdentifier().getIdValue());
                                params.addValue("feesStatus", 1);
                                nm.update(sql, params);
                            } else {
                                String query = "UPDATE tesy_available_listing tal SET tal.`FEED_STATUS`=? WHERE tal.`SKU`=?";
                                jdbcTemplate.update(query, "No ASIN found for SKU :" + f.getFeesEstimateIdentifier().getIdValue(), f.getFeesEstimateIdentifier().getIdValue());
                            }
                        }
                        Thread.sleep(1000);
                    } while (size > 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
        }
        return response;
    }
}
