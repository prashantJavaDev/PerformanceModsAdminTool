/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.OrderDao;
import com.aspirant.performanceModsAdminTool.dao.WarehouseDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import static com.aspirant.performanceModsAdminTool.service.impl.ListingServiceImpl.FEES_FILE_PATH;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
//import static com.aspirant.performanceModsAdminTool.test.PNARequestXml.XMLFilePath;

/**
 *
 * @author Ritesh
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private WarehouseDao warehouseDao;
    public static final String BULK_ORDER_TRACKING_FILE_PATH = "/home/altius/performanceMods/tracking/";
    public static final String XMLFilePath = "/home/altius/performanceMods/ingram/";
//    public static final String XMLFilePath = "/home/altius/xmlDocs/";
    public static final String XMLFilePathForAknowledge = "/home/altius/performanceMods/ingram/";
//    public static final String XMLFilePathForAknowledge = "/home/altius/xmlDocs/";
    public static final String MARKETPLACE_ORDER_FILE_PATH = "/home/altius/performanceMods/marketPlaceOrder/";

    @Override
    public Order getorderBymarketplaceOrderID(String marketplaceOrderId) {
        return this.orderDao.getorderBymarketplaceOrderID(marketplaceOrderId);
    }

    @Override
    public int getOrderCount(int marketplaceId, String marketplaceOrderId, String poNumber, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate) {
        return this.orderDao.getOrderCount(marketplaceId, marketplaceOrderId, poNumber, customerName, marketplaceSku, marketplaceListingId, orderStatus, fulfillmentChannel, startDate, stopDate);
    }

    @Override
    public List<Order> getOrderList(int marketplaceId, String marketplaceOrderId, String poNumber, int pageNo, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate) {
        return this.orderDao.getMarketplaceOrderList(marketplaceId, marketplaceOrderId, poNumber, pageNo, customerName, marketplaceSku, marketplaceListingId, orderStatus, fulfillmentChannel, startDate, stopDate);
    }

    @Override
    public List<ProductDetails> getProductListOnMarketplaceSku(String marketplaceSku) {
        return this.orderDao.getProductListOnMarketplaceSku(marketplaceSku);
    }

    @Override
    public List<Order> getorderTransBymarketplaceOrderID(String marketplaceOrderId) {
        return this.orderDao.getorderTransBymarketplaceOrderID(marketplaceOrderId);
    }

    @Override
    public void assignWarehouseToOrder(int warehouseId, String orderId) {
        this.orderDao.assignWarehouseToOrder(warehouseId, orderId);
    }

    @Override
    public int getProcessOrderCount(int warehouseId, String currentDate) {
        return this.orderDao.getProcessOrderCount(warehouseId, currentDate);
    }

    @Override
    public List<ProcessingSheet> getProcessOrderList(int warehouseId, String currentDate) {
        return this.orderDao.getProcessOrderList(warehouseId, currentDate);
    }

    @Override
    public int addOrderTracking(Order order, int warehouseId) {
        return this.orderDao.addOrderTracking(order, warehouseId);
    }

    @Override
    public List<Listing> getMarketplaceSkuList() {
        return this.orderDao.getMarketplaceSkuList();
    }

    @Override
    public int saveMultipartFileData(UploadFeed uploadFeed, int marketplaceId) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = BULK_ORDER_TRACKING_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(BULK_ORDER_TRACKING_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(ListingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                this.orderDao.loadBulkOrderTrackingDataLocally(path, marketplaceId);
                return 1;
            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            }
        }
        return 0;
    }

    @Override
    public List<Order> getOrderListOfUnshippedOrderForDashboard(int count) {
        return this.orderDao.getOrderListOfUnshippedOrderForDashboard(count);
    }

    @Override
    public List<Order> getOrderListForTrackingUpdate() {
        return this.orderDao.getOrderListForTrackingUpdate();
    }

    @Override
    public List<Order> getOderListForCreatingXml() {
        return this.orderDao.getOderListForCreatingXml();
    }

    @Override
    public String createAndSaveXMLFile() {

        List<Order> listXml = this.orderDao.getOderListForCreatingXml();
        String trackingIds = null;
        for (Order order : listXml) {
            trackingIds = order.getTrackingId();
        }

        String marketPlaceOrderId;
        String trackingId;
        String trackingCarrier;
        String orderItemId;
        int quantity;
        for (Order item : listXml) {

            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                Element rootElement = doc.createElement("AmazonEnvelope");

                Attr attr2 = doc.createAttribute("xmlns:xsi");
                attr2.setValue("http://www.w3.org/2001/XMLSchema-instance");
                rootElement.setAttributeNode(attr2);

                Attr attr = doc.createAttribute("xsi:noNamespaceSchemaLocation");
                attr.setValue("amzn-envelope.xsd");
                rootElement.setAttributeNode(attr);

                doc.appendChild(rootElement);

                Element Header = doc.createElement("Header");
                rootElement.appendChild(Header);

                //DocumentVersion 1.01,MerchantIdentifier A1PYB0QXU6SOEO 
                Element DocumentVersion = doc.createElement("DocumentVersion");
                DocumentVersion.appendChild(doc.createTextNode("1.01"));
                Header.appendChild(DocumentVersion);

                Element MerchantIdentifier = doc.createElement("MerchantIdentifier");
                MerchantIdentifier.appendChild(doc.createTextNode("A1PYB0QXU6SOEO"));
                Header.appendChild(MerchantIdentifier);
                //<-------------------->

                Element MessageType = doc.createElement("MessageType");
                MessageType.appendChild(doc.createTextNode("OrderFulfillment"));
                rootElement.appendChild(MessageType);
                //<----------------------->

                Element Message = doc.createElement("Message");
                rootElement.appendChild(Message);

                //meassage elements
                //MessageID 1,OperationType Update,OrderFulfillment 
                Element MessageID = doc.createElement("MessageID");
                MessageID.appendChild(doc.createTextNode("1"));
                Message.appendChild(MessageID);

                Element OperationType = doc.createElement("OperationType");
                OperationType.appendChild(doc.createTextNode("Update"));
                Message.appendChild(OperationType);

                Element OrderFulfillment = doc.createElement("OrderFulfillment");
                Message.appendChild(OrderFulfillment);

                // oderfulfilment element AmazonOrderID 113-2808834-9236227,FulfillmentDate,
                //FulfillmentData-->CarrierName,ShippingMethod,ShipperTrackingNumber,Item-->AmazonOrderItemCode,Quantity
                if (item.getMarketplaceOrderId() == null) {
                    marketPlaceOrderId = "";
                } else {
                    marketPlaceOrderId = item.getMarketplaceOrderId();
                }
                Element AmazonOrderID = doc.createElement("AmazonOrderID");
                AmazonOrderID.appendChild(doc.createTextNode(marketPlaceOrderId));
                OrderFulfillment.appendChild(AmazonOrderID);

                Element FulfillmentDate = doc.createElement("FulfillmentDate");
                FulfillmentDate.appendChild(doc.createTextNode("2018-06-20T03:44:46+00:00"));
                OrderFulfillment.appendChild(FulfillmentDate);

                Element FulfillmentData = doc.createElement("FulfillmentData");
                OrderFulfillment.appendChild(FulfillmentData);
                //<----fulfilmentdata elements
                if (item.getTrackingCarrier() == null) {
                    trackingCarrier = "";
                } else {
                    trackingCarrier = item.getTrackingCarrier();
                }
                Element CarrierName = doc.createElement("CarrierName");
                CarrierName.appendChild(doc.createTextNode(trackingCarrier));
                FulfillmentData.appendChild(CarrierName);

                Element ShippingMethod = doc.createElement("ShippingMethod");
                ShippingMethod.appendChild(doc.createTextNode("Standard"));
                FulfillmentData.appendChild(ShippingMethod);

                if (item.getTrackingId() == null) {
                    trackingId = "";
                } else {
                    trackingId = item.getTrackingId();
                }
                Element ShipperTrackingNumber = doc.createElement("ShipperTrackingNumber");
                ShipperTrackingNumber.appendChild(doc.createTextNode(trackingId));
                FulfillmentData.appendChild(ShipperTrackingNumber);

                //Item-->AmazonOrderItemCode,Quantity
                Element Item = doc.createElement("Item");
                OrderFulfillment.appendChild(Item);

                if (item.getOrderItemId() == null) {
                    orderItemId = "";
                } else {
                    orderItemId = item.getOrderItemId();
                }
                Element AmazonOrderItemCode = doc.createElement("AmazonOrderItemCode");
                AmazonOrderItemCode.appendChild(doc.createTextNode(orderItemId));
                Item.appendChild(AmazonOrderItemCode);

                quantity = item.getQuantityUnshipped();
                Element Quantity = doc.createElement("Quantity");
                Quantity.appendChild(doc.createTextNode(Integer.toString(quantity)));
                Item.appendChild(Quantity);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(XMLFilePath, "amazon_tracking.xml").getPath());
                transformer.transform(source, result);
            } catch (Exception pce) {
                pce.printStackTrace();
            }
        }
        return trackingIds;
    }

    @Override
    public void updateTrackingRecord(String trackingID) {
        this.orderDao.updateTrackingRecord(trackingID);
    }

    @Override
    public String getPoNumberBymarketplaceOrderID(String marketplaceOrderId) {
        return this.orderDao.getPoNumberBymarketplaceOrderID(marketplaceOrderId);
    }

    @Override
    public String getOrderStatusOnMarketplaceOrderId(String marketplaceOrderId) {
        return this.orderDao.getOrderStatusOnMarketplaceOrderId(marketplaceOrderId);
    }

    @Override
    public int getCountOfTicketAssignedToOrder(String marketplaceOrderId) {
        return this.orderDao.getCountOfTicketAssignedToOrder(marketplaceOrderId);
    }

    @Override
    public void updateOrderAcknowledgementRecord(String orderId) {
        this.orderDao.updateOrderAcknowledgementRecord(orderId);
    }

    @Override
    public String amazonAcknowledgementFile() {

        Order order = this.orderDao.getOrderForAmazonAcknowledgement();
        String marketPlaceOrderId = "";
        String orderItemId = "";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("AmazonEnvelope");

            Attr attr2 = doc.createAttribute("xmlns:xsi");
            attr2.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(attr2);

            Attr attr1 = doc.createAttribute("xsi:noNamespaceSchemaLocation");
            attr1.setValue("amzn-envelope.xsd");
            rootElement.setAttributeNode(attr1);

            doc.appendChild(rootElement);

            Element Header = doc.createElement("Header");
            rootElement.appendChild(Header);

            //DocumentVersion 1.01,MerchantIdentifier A1PYB0QXU6SOEO 
            Element DocumentVersion = doc.createElement("DocumentVersion");
            DocumentVersion.appendChild(doc.createTextNode("1.01"));
            Header.appendChild(DocumentVersion);

            Element MerchantIdentifier = doc.createElement("MerchantIdentifier");
            MerchantIdentifier.appendChild(doc.createTextNode("A1PYB0QXU6SOEO"));
            Header.appendChild(MerchantIdentifier);
            //<-------------------->

            Element MessageType = doc.createElement("MessageType");
            MessageType.appendChild(doc.createTextNode("OrderAcknowledgement"));
            rootElement.appendChild(MessageType);
            //<----------------------->

            Element Message = doc.createElement("Message");
            rootElement.appendChild(Message);

            Element MessageID = doc.createElement("MessageID");
            MessageID.appendChild(doc.createTextNode("1"));
            Message.appendChild(MessageID);

            Element OrderAcknowledgement = doc.createElement("OrderAcknowledgement");
            Message.appendChild(OrderAcknowledgement);

            if (order.getMarketplaceOrderId() == null) {
                marketPlaceOrderId = "";
            } else {
                marketPlaceOrderId = order.getMarketplaceOrderId();
            }
            Element AmazonOrderID = doc.createElement("AmazonOrderID");
            AmazonOrderID.appendChild(doc.createTextNode(marketPlaceOrderId));
            OrderAcknowledgement.appendChild(AmazonOrderID);

            Element MerchantOrderID = doc.createElement("MerchantOrderID");
            MerchantOrderID.appendChild(doc.createTextNode(marketPlaceOrderId));
            OrderAcknowledgement.appendChild(MerchantOrderID);

            Element StatusCode = doc.createElement("StatusCode");
            StatusCode.appendChild(doc.createTextNode("Success"));
            OrderAcknowledgement.appendChild(StatusCode);

            //Item-->AmazonOrderItemCode,Quantity
            Element Item = doc.createElement("Item");
            OrderAcknowledgement.appendChild(Item);

            if (order.getOrderItemId() == null) {
                orderItemId = "";
            } else {
                orderItemId = order.getOrderItemId();
            }
            Element AmazonOrderItemCode = doc.createElement("AmazonOrderItemCode");
            AmazonOrderItemCode.appendChild(doc.createTextNode(orderItemId));
            Item.appendChild(AmazonOrderItemCode);

            Element MerchantOrderItemID = doc.createElement("MerchantOrderItemID");
            MerchantOrderItemID.appendChild(doc.createTextNode(orderItemId));
            Item.appendChild(MerchantOrderItemID);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XMLFilePathForAknowledge, "amazon_acknowledgement.xml").getPath());
            transformer.transform(source, result);
        } catch (Exception pce) {
            pce.printStackTrace();
        }

        return marketPlaceOrderId;
    }

    @Override
    public int saveMultipartFileForMarketPlaceOrderUpload(UploadFeed uploadFeed, int marketplaceId) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = MARKETPLACE_ORDER_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(MARKETPLACE_ORDER_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(ListingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                this.orderDao.loadMarketPlaceOrderDataLocally(path, marketplaceId);
                return 1;
            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            }
        }
        return 0;
    }
}
