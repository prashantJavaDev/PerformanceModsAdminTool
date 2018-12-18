/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.OrderDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.aspirant.performanceModsAdminTool.service.TrackingApiService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedRequest;
import com.amazonaws.mws.model.SubmitFeedResponse;
import com.amazonservices.mws.orders.amazon.AmazonWebService;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.samples.MarketplaceWebServiceProductsSampleConfig;
import com.aspirant.performanceModsAdminTool.dao.ConfigDao;
import com.aspirant.performanceModsAdminTool.model.AmazonProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class TrackingApiServiceImpl implements TrackingApiService {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private ConfigDao configDao;

    @Override
    public SubmitFeedResponse submitFeed() {
        List<Future<SubmitFeedResponse>> responses = new ArrayList<Future<SubmitFeedResponse>>();
        SubmitFeedResponse response = null;
        try {
            String FeedContentData = null;
            AmazonProperties ap = configDao.getAmazonProperties();
            AmazonWebService a = new AmazonWebService(true, ap.getAccessKey(), ap.getSecretKey(), ap.getSellerId(), ap.getMwsAuthToken(), ap.getMarketplaceId());
//            AmazonWebService a = new AmazonWebService("/home/pk/performanceMods/amazon.properties");
            if (a.isPropsLoaded()) {
                // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Properties file loaded, Going to do get order list", GlobalConstants.TAG_SYSTEMLOG));
                try {
                    String trackId = this.orderService.createAndSaveXMLFile();
//                    String trackId = "040985983325648";
                    MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
                    config.setServiceURL("https://mws.amazonservices.com/");
                    final String appName = "performanceMods";
                    final String appVersion = "1.0";
                    MarketplaceWebService service = new MarketplaceWebServiceClient(a.getAccessKey(), a.getSecretKey(), appName, appVersion, config);
                    final IdList marketplaces = new IdList(Arrays.asList("ATVPDKIKX0DER"));

                    List<SubmitFeedRequest> requestList = new ArrayList<SubmitFeedRequest>();
                    SubmitFeedRequest feedRequest = new SubmitFeedRequest();
                    feedRequest.setMarketplaceIdList(marketplaces);
                    feedRequest.setFeedType("_POST_ORDER_FULFILLMENT_DATA_");
                    feedRequest.setMerchant("A26YLYFFSVKSNX");
                    FileInputStream fim = null;
                    try {
                        File f = new File("/home/altius/performanceMods/ingram/amazon_tracking.xml");
//                        File f = new File("/home/altius/xmlDocs/amazon_tracking.xml");
                        fim = new FileInputStream(f);
                        FeedContentData = computeContentMD5Value(fim);
                    } finally {
                        fim.close();
                    }
                    feedRequest.setFeedContent(new FileInputStream("/home/altius/performanceMods/ingram/amazon_tracking.xml"));
//                    feedRequest.setFeedContent(new FileInputStream("/home/altius/xmlDocs/amazon_tracking.xml"));
                    feedRequest.setContentMD5(FeedContentData);
                    requestList.add(feedRequest);
                    response = a.invokeSubmitFeed(service, requestList);
//                    System.out.println("Rsponce Context=-------" + response.getResponseHeaderMetadata().getResponseContext().toString());
//                    System.out.println("Response rgetFeedSubmissionInfo: " + response.getSubmitFeedResult().getFeedSubmissionInfo());
                    if ("_SUBMITTED_".equals(response.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus())) {
                        this.orderService.updateTrackingRecord(trackId);
                        File f = new File("/home/altius/performanceMods/ingram/amazon_tracking.xml");
//                        File f = new File("/home/altius/xmlDocs/amazon_tracking.xml");
//                        f.delete();
                    }
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

    public static String computeContentMD5Value(FileInputStream fis)
            throws IOException, NoSuchAlgorithmException {

        DigestInputStream dis = new DigestInputStream(fis,
                MessageDigest.getInstance("MD5"));

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) > 0);

        String md5Content = new String(
                org.apache.commons.codec.binary.Base64.encodeBase64(
                        dis.getMessageDigest().digest()));

        // Effectively resets the stream to be beginning of the file
        // via a FileChannel.
        fis.getChannel().position(0);

        return md5Content;
    }

    @Override
    public SubmitFeedResponse submitAcknowledgementFeed() {
        List<Future<SubmitFeedResponse>> responses = new ArrayList<Future<SubmitFeedResponse>>();
        SubmitFeedResponse response = null;
        try {
            String FeedContentData = null;
//            AmazonWebService a = new AmazonWebService("/home/pk/performanceMods/amazon.properties");
            AmazonProperties ap = configDao.getAmazonProperties();
            AmazonWebService a = new AmazonWebService(true, ap.getAccessKey(), ap.getSecretKey(), ap.getSellerId(), ap.getMwsAuthToken(), ap.getMarketplaceId());
            if (a.isPropsLoaded()) {
                // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Properties file loaded, Going to do get order list", GlobalConstants.TAG_SYSTEMLOG));
                try {
                    String orderId = this.orderService.amazonAcknowledgementFile();
                    System.out.println("Order ID ---------------->" + orderId);
//                    String orderId = "112-5489929-7493868";

                    MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
                    config.setServiceURL("https://mws.amazonservices.com/");
                    final String appName = "performanceMods";
                    final String appVersion = "1.0";
                    MarketplaceWebService service = new MarketplaceWebServiceClient(a.getAccessKey(), a.getSecretKey(), appName, appVersion, config);
                    final IdList marketplaces = new IdList(Arrays.asList("ATVPDKIKX0DER"));

                    List<SubmitFeedRequest> requestList = new ArrayList<SubmitFeedRequest>();
                    SubmitFeedRequest feedRequest = new SubmitFeedRequest();
                    feedRequest.setMarketplaceIdList(marketplaces);
                    feedRequest.setFeedType("_POST_ORDER_ACKNOWLEDGEMENT_DATA_");
                    feedRequest.setMerchant("A26YLYFFSVKSNX");
                    feedRequest.setMWSAuthToken("amzn.mws.ccab02a3-e705-4005-2081-34e54350f847");
                    FileInputStream fim = null;
                    try {
//                        File f = new File("/home/altius/performanceMods/ingram/amazon_acknowledgement.xml");
                        File f = new File("/home/altius/xmlDocs/amazon_acknowledgement.xml");
                        fim = new FileInputStream(f);
                        FeedContentData = computeContentMD5Value(fim);
                    } finally {
                        fim.close();
                    }
//                    feedRequest.setFeedContent(new FileInputStream("/home/altius/performanceMods/ingram/amazon_acknowledgement.xml"));
                    feedRequest.setFeedContent(new FileInputStream("/home/altius/xmlDocs/amazon_acknowledgement.xml"));
                    feedRequest.setContentMD5(FeedContentData);
                    requestList.add(feedRequest);
                    response = a.invokeSubmitFeed(service, requestList);
                    System.out.println("Rsponce Context=-------" + response);
//                    System.out.println("Rsponce Context=-------" + response.getResponseHeaderMetadata().getResponseContext().toString());
//                    System.out.println("Response rgetFeedSubmissionInfo: " + response.getSubmitFeedResult().getFeedSubmissionInfo());
                    if ("_SUBMITTED_".equals(response.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus())) {
                        this.orderService.updateOrderAcknowledgementRecord(orderId);
//                        File f = new File("/home/altius/performanceMods/ingram/amazon_acknowledgement.xml");
                        File f = new File("/home/altius/xmlDocs/amazon_acknowledgement.xml");
                        //f.delete();
                    }
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
