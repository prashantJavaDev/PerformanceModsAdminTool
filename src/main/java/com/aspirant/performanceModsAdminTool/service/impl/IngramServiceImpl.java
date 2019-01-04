/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.IngramDao;
import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.service.IngramService;
import com.aspirant.performanceModsAdminTool.web.controller.IngramController;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ritesh
 */
@Service
public class IngramServiceImpl implements IngramService {

    @Autowired
    private IngramDao ingramDao;
    public static final String IMG_FILE_PATH = "/home/ubuntu/performanceMods/feeds/";
    Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);

    @Override
    public List<CurrentWarehouseProduct> getWarehouseIdentificationNo() {
        return this.ingramDao.getWarehouseIdentificationNo();
    }

    @Override
    public int updateCurrentWareHouseProductForIngram(String sku, int quantity, double price) {
        return this.ingramDao.updateCurrentWareHouseProductForIngram(sku, quantity, price);
    }

    @Override
    public String getIngramResponceXml(String request, int i) {
        try {

            URL url = new URL("http://newport.ingrammicro.com");
            URLConnection con = url.openConnection();
            // specify that we will send output and accept input
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(20000);  // long timeout, but not infinite
            con.setReadTimeout(20000);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            // tell the web server what we are sending
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(request.toString());
            writer.flush();
            writer.close();
            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[2048];
            int num;
            while (-1 != (num = reader.read(cbuf))) {
                buf.append(cbuf, 0, num);
            }
            String result = buf.toString();
            //System.err.println("\nResponse from server after POST:\n" + result);
            if (i == 2) {
                this.getOrderResponceData(result);
            } else if (i == 3) {
                this.getOrderDetailsResponce(result);
            } else if (i == 4) {
                this.getOrderStatusData(result);
            } else if (i == 5) {
                this.getOrderTrackingResponce(result);
            } else if (i == 7) {
                //this.getOrderShippingCostResponce(result);
            } else {
                this.getInvoiceResponce(result);
            }
            return null;

        } catch (Throwable t) {
            //t.printStackTrace(System.out);
            return null;
        }
    }

    public String getOrderResponceData(String responce) {
        try {
            String warehouseOrderNumber = null;
            String poNumber = null;

            //File file = new File("/home/ubuntu/Desktop/OrderResponce.xml");
            File file = new File("/home/ubuntu/performanceMods/ingram/OrderResponce.xml");
            FileWriter writer = new FileWriter(file);
            writer.write(responce);
            writer.close();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("OrderNumbers");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    warehouseOrderNumber = eElement
                            .getElementsByTagName("BranchOrderNumber")
                            .item(0)
                            .getTextContent();

                    poNumber = eElement
                            .getElementsByTagName("CustomerPO")
                            .item(0)
                            .getTextContent();
                }
            }
            file.delete();
            this.ingramDao.updateIngramOrderResponceData(warehouseOrderNumber, poNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOrderDetailsResponce(String responce) {
        return null;
    }

    public String getOrderStatusData(String responce) {
        try {
            String warehouseOrderStatus = null;
            String warehouseOrderNumber = null;
            String poNumber = "";
//            File file = new File("/home/ubuntu/Desktop/OrderStatusResponce.xml");
            File file = new File("/home/ubuntu/performanceMods/ingram/OrderStatusResponce.xml");
            FileWriter writer = new FileWriter(file);
            writer.write(responce);
            writer.close();
            //File inputFile = new File("/home/ubuntu/Desktop/PNAResponce.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("OrderStatusInformation");
            NodeList mList = doc.getElementsByTagName("CustomerPO");

            for (int temp = 0; temp < mList.getLength(); temp++) {
                Node nNode = mList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    poNumber = eElement.getAttribute("Number");
                }
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    warehouseOrderStatus = eElement
                            .getElementsByTagName("OrderStatus")
                            .item(0)
                            .getTextContent();
                }
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    warehouseOrderNumber = eElement
                            .getElementsByTagName("BranchOrderNumber")
                            .item(0)
                            .getTextContent();
                }
            }
            file.delete();
            if (warehouseOrderStatus != null) {
                this.ingramDao.updateWarehouseOrderStatus(warehouseOrderStatus, poNumber, warehouseOrderNumber);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOrderTrackingResponce(String responce) {
        try {
            String trackingCarrier = null;
            String trackingId = null;
            String poNumber = null;
//            File file = new File("/home/ubuntu/Desktop/OrderTrackingResponce.xml");
            File file = new File("/home/ubuntu/performanceMods/ingram/OrderTrackingResponce.xml");
            FileWriter writer = new FileWriter(file);
            writer.write(responce);
            writer.close();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("OrderTrackingResponse");
            NodeList mList = doc.getElementsByTagName("Package");

            for (int temp = 0; temp < mList.getLength(); temp++) {
                Node nNode = mList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    trackingId = eElement.getAttribute("ID");
                }
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    poNumber = eElement
                            .getElementsByTagName("CustomerPO")
                            .item(0)
                            .getTextContent();
                    trackingCarrier = eElement
                            .getElementsByTagName("Carrier")
                            .item(0)
                            .getTextContent();
                }
            }

            file.delete();
            if (trackingCarrier != null && trackingId != null && poNumber != null) {
                this.ingramDao.updateTrackingCarrierAndId(trackingCarrier, trackingId, poNumber);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInvoiceResponce(String responce) {
        return null;
    }

    public String getPNAResponcedata() {
        String server = "partnerreports.ingrammicro.com";
        int port = 21;
        String user = "885347";
        String pass = "fs8qKX";
        List<String> sku = new ArrayList<String>();
        List<Integer> qty = new ArrayList<Integer>();
        List<Double> price = new ArrayList<Double>();
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFile1 = "FUSION/US/AVAIL/TOTAL.ZIP";
            //Local Path
//            File downloadTotalFile = new File("/home/ubuntu/Desktop/TOTAL.ZIP");
//            File downloadTotalFile1 = new File("/home/ubuntu/Desktop/Ingram/" + curDate + "TOTAL.ZIP");

            //Live Path
            File downloadTotalFile = new File("/home/ubuntu/performanceMods/ingram/TOTAL.ZIP");
            File downloadTotalFile1 = new File("/home/ubuntu/performanceMods/feeds/ingram/" + curDate + "TOTAL.ZIP");

            OutputStream totaloutputStream1 = new BufferedOutputStream(new FileOutputStream(downloadTotalFile));
            OutputStream totaloutputStream2 = new BufferedOutputStream(new FileOutputStream(downloadTotalFile1));
            ftpClient.retrieveFile(remoteFile1, totaloutputStream1);
            ftpClient.retrieveFile(remoteFile1, totaloutputStream2);
            totaloutputStream1.close();
            totaloutputStream2.close();

            String remoteFile2 = "/FUSION/US/ELYK8N/PRICE.ZIP";
            //Local Path
//            File downloadPriceFile = new File("/home/ubuntu/Desktop/PRICE.ZIP");
//            File downloadPriceFile1 = new File("/home/ubuntu/Desktop/Ingram/" + curDate + "PRICE.ZIP");
            //Live Path
            File downloadPriceFile = new File("/home/ubuntu/performanceMods/ingram/PRICE.ZIP");
            File downloadPriceFile1 = new File("/home/ubuntu/performanceMods/feeds/ingram/" + curDate + "PRICE.ZIP");

            OutputStream priceOutputStream1 = new BufferedOutputStream(new FileOutputStream(downloadPriceFile));
            OutputStream priceOutputStream2 = new BufferedOutputStream(new FileOutputStream(downloadPriceFile1));
            ftpClient.retrieveFile(remoteFile2, priceOutputStream1);
            ftpClient.retrieveFile(remoteFile2, priceOutputStream2);
            priceOutputStream1.close();
            priceOutputStream2.close();

            String zipTotalFilePath = "/home/ubuntu/performanceMods/ingram/TOTAL.ZIP";
//            String zipTotalFilePath = "/home/ubuntu/Desktop/TOTAL.ZIP";
            String destDir = "/home/ubuntu/performanceMods/ingram";
//            String destDir = "/home/ubuntu/Desktop/Ingram";
            unzip(zipTotalFilePath, destDir);

            String zipPriceFilePath = "/home/ubuntu/performanceMods/ingram/PRICE.ZIP";
//            String zipPriceFilePath = "/home/ubuntu/Desktop/PRICE.ZIP";
            unzip(zipPriceFilePath, destDir);

            String quantityFile = "/home/ubuntu/performanceMods/ingram/TOTAL.TXT";
//            String quantityFile = "/home/ubuntu/Desktop/Ingram/TOTAL.TXT";
            String[] dataFields = new String[2];
            try {
                BufferedReader br;
                br = new BufferedReader(new FileReader(quantityFile));
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    sCurrentLine = sCurrentLine.replace("\"", "");
                    dataFields = sCurrentLine.split(",", -1);
                    String quantity = dataFields[1].trim();
                    qty.add(Integer.parseInt(quantity));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String priceFile = "/home/ubuntu/performanceMods/ingram/PRICE.TXT";
//            String priceFile = "/home/ubuntu/Desktop/Ingram/PRICE.TXT";
            String[] dataFields1 = new String[24];
            try {
                BufferedReader br;
                br = new BufferedReader(new FileReader(priceFile));
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    dataFields1 = sCurrentLine.split(",", -1);
                    String price1 = dataFields1[14].trim();
                    price.add(Double.parseDouble(price1));
                    sku.add(dataFields1[1].trim());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < sku.size(); i++) {
                this.ingramDao.updateCurrentWareHouseProductForIngram(sku.get(i), qty.get(i), price.get(i));
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Order getDataForOrderDetailRequest() {
        return this.ingramDao.getDataForOrderDetailRequest();
    }

//    @Override
//    public String getPNARequestXml() {
//
//        List<CurrentWarehouseProduct> warehouseIdNumList = this.ingramDao.getWarehouseIdentificationNo();
//        String wIdNum = null;
//        int i = 1;
//        for (CurrentWarehouseProduct item : warehouseIdNumList) {
//
//            wIdNum = item.getWarehouseIdentificationNum();
//        }
//        String PNARequestXml = "<PNARequest> \n"
//                + "<Version>2.0</Version> \n"
//                + "<TransactionHeader> \n"
//                + "<SenderID/>"
//                + "<ReceiverID/>"
//                + "<CountryCode>MD</CountryCode> \n"
//                + "<LoginID>QD74C5HJQ5</LoginID> \n"
//                + "<Password>6jYpEBcv2Z</Password> \n"
//                + "<TransactionID>123456</TransactionID> \n"
//                + "</TransactionHeader> \n"
//                + "<PNAInformation SKU = \'"
//                + wIdNum
//                + "\' Quantity=\'1\' /> \n"
//                + "<ShowDetail>2</ShowDetail> \n"
//                + "</PNARequest> ";
//        return this.getIngramResponceXml(PNARequestXml, i);
//
//    }
    @Override
    public String getOrderRequestXml(String poNumber) {
        Order order = this.ingramDao.getDataforOrderRequestXML(poNumber);
        int i = 2;
        String shipAdr1 = order.getShippingAddressLine1();
        String shipAdr2 = order.getShippingAddressLine2();
        String shipAdr3 = order.getShippingAddressLine3();
        if (shipAdr1 == null) {
            shipAdr1 = "";
        }
        if (shipAdr2 == null) {
            shipAdr2 = "";
        }
        if (shipAdr3 == null) {
            shipAdr3 = "";
        }
        String OrderRequestXml = "<OrderRequest> \n"
                + "<Version>2.0</Version> \n"
                + "<TransactionHeader> \n"
                + "<SenderID/>"
                + "<ReceiverID/>"
                + "<CountryCode>MD</CountryCode> \n"
                + "<LoginID>QD74C5HJQ5</LoginID> \n"
                + "<Password>6jYpEBcv2Z</Password> \n"
                + "<TransactionID>54321</TransactionID> \n"
                + "</TransactionHeader> \n"
                + "<OrderHeaderInformation> \n"
                + "  <BillToSuffix /> \n"
                + "<AddressingInformation> \n"
                + "  <CustomerPO>" + order.getPoNumber() + "</CustomerPO> \n"
                + "  <ShipToAttention>" + order.getCustomerName() + "</ShipToAttention> \n"
                + "  <EndUserPO>" + order.getPoNumber() + "</EndUserPO> \n"
                + "<ShipTo> \n"
                + " <Address> \n"
                + "  <ShipToAddress1>" + shipAdr1 + "</ShipToAddress1> \n"
                + "  <ShipToAddress2>" + shipAdr2 + "</ShipToAddress2> \n"
                + "  <ShipToAddress3>" + shipAdr3 + "</ShipToAddress3> \n"
                + "  <ShipToCity>" + order.getCity() + "</ShipToCity> \n"
                + "  <ShipToProvince>" + order.getState() + "</ShipToProvince> \n"
                + "  <ShipToPostalCode>" + order.getPostalCode() + "</ShipToPostalCode> \n"
                + " </Address> \n"
                + "</ShipTo> \n"
                + "</AddressingInformation> \n"
                + "<ProcessingOptions> \n"
                + "  <CarrierCode></CarrierCode> \n"
                + "  <AutoRelease>1</AutoRelease> \n"
                + "  <ThirdPartyFreightAccount></ThirdPartyFreightAccount> \n"
                + "  <KillOrderAfterLineError>N</KillOrderAfterLineError> \n"
                + "<ShipmentOptions> \n"
                + "  <BackOrderFlag>Y</BackOrderFlag> \n"
                + "  <SplitShipmentFlag>N</SplitShipmentFlag> \n"
                + "  <SplitLine>N</SplitLine> \n"
                + "  <ShipFromBranches>20</ShipFromBranches> \n"
                + "</ShipmentOptions> \n"
                + "</ProcessingOptions> \n"
                + "<DynamicMessage> \n"
                + "  <MessageLines>Please deliver to Mrs Jones</MessageLines> \n"
                + "</DynamicMessage> \n"
                + "</OrderHeaderInformation> \n"
                + "<OrderLineInformation> \n"
                + "<ProductLine> \n"
                + "  <SKU>" + order.getWarehouesIdeNum() + "</SKU> \n"
                + "  <Quantity>" + order.getQuantityUnshipped() + "</Quantity> \n"
                + "  <CustomerLineNumber /> \n"
                + "  <ShipFromBranchAtLine>10</ShipFromBranchAtLine> \n"
                + "  <RequestedPrice>" + order.getPriceCrntWHprct() + "</RequestedPrice> \n"
                + "</ProductLine> \n"
                + "<CommentLine> \n"
                + "  <CommentText>Handle with care</CommentText> \n"
                + "</CommentLine> \n"
                + "</OrderLineInformation> \n"
                + " <ShowDetail>2</ShowDetail> \n"
                + "</OrderRequest>";
        return this.getIngramResponceXml(OrderRequestXml, i);
    }

    @Override
    public String getOrderDetailRequestXml() {
        Order order = this.ingramDao.getDataForOrderDetailRequest();
        int i = 3;
        String OrderDetailRequestXml = "<OrderDetailRequest> \n"
                + "<Version>2.0</Version>\n"
                + "<TransactionHeader>\n"
                + "<SenderID/>\n"
                + "<ReceiverID/>\n"
                + "<CountryCode>MD</CountryCode>\n"
                + "<LoginID>QD74C5HJQ5</LoginID>\n"
                + "<Password>6jYpEBcv2Z</Password>\n"
                + "<TransactionID>54321</TransactionID>\n"
                + "</TransactionHeader> \n"
                + "<OrderHeaderInfo> \n"
                + "<BranchOrderNumber>" + order.getWarehouseOrderNumber() + "</BranchOrderNumber> \n"
                + "<OrderSuffix/> \n"
                + "<CustomerPO>" + order.getPoNumber() + "</CustomerPO> \n"
                + "</OrderHeaderInfo> \n"
                + "<ShowDetail>1</ShowDetail> \n"
                + "</OrderDetailRequest>";

        return this.getIngramResponceXml(OrderDetailRequestXml, i);

    }

    @Override
    public String getOrderStatusRequestXml() {
        String poNumber = this.ingramDao.getPoNumberForOrderStatus();
        int i = 4;
        String orderStatusRequestXml = "<OrderStatusRequest> \n"
                + "<Version>2.0</Version>\n"
                + "<TransactionHeader>\n"
                + "<SenderID/>\n"
                + "<ReceiverID/>\n"
                + "<CountryCode>MD</CountryCode>\n"
                + "<LoginID>QD74C5HJQ5</LoginID>\n"
                + "<Password>6jYpEBcv2Z</Password>\n"
                + "<TransactionID>54321</TransactionID> \n"
                + "</TransactionHeader> \n"
                + "<OrderHeaderInfo>\n"
                + "<CustomerPO>" + poNumber + "</CustomerPO> \n"
                + "</OrderHeaderInfo> \n"
                + "</OrderStatusRequest>";
        return this.getIngramResponceXml(orderStatusRequestXml, i);
    }

    @Override
    public String getOrderTrackingRequestXml() {
        Order order = this.ingramDao.getDataForOrderTrackingRequest();
        int i = 5;
        String orderTrackingRequestXml = "<OrderTrackingRequest> \n"
                + "<Version>2.0</Version>\n"
                + "<TransactionHeader>\n"
                + "<SenderID/>\n"
                + "<ReceiverID/>\n"
                + "<CountryCode>MD</CountryCode>\n"
                + "<LoginID>QD74C5HJQ5</LoginID>\n"
                + "<Password>6jYpEBcv2Z</Password>\n"
                + "<TransactionID>123847</TransactionID>\n"
                + "</TransactionHeader> \n"
                + "<TrackingRequestHeader> \n"
                + "<BranchOrderNumber>" + order.getWarehouseOrderNumber() + "</BranchOrderNumber>\n"
                + "<OrderSuffix>11</OrderSuffix>\n"
                + "<CustomerPO>" + order.getPoNumber() + "</CustomerPO> \n"
                + "</TrackingRequestHeader>\n"
                + "<ShowDetail>2</ShowDetail> \n"
                + "</OrderTrackingRequest>";
        return this.getIngramResponceXml(orderTrackingRequestXml, i);
    }

    @Override
    public String getInvoiceXml() {
        Order order = this.ingramDao.getDataForInvoiceXml();
        int i = 6;
        String invoiceXml = "<Invoice> \n"
                + "    <InvoiceHeader> \n"
                + "        <InvoiceType>Invoice</InvoiceType> \n"
                + "        <InvoiceNumber>202540088</InvoiceNumber> \n"
                + "        <InvoiceDate>20160217</InvoiceDate> \n"
                + "        <CustomerPO>CST400222</CustomerPO> \n"
                + "        <OriginalOrderNumber>205544811</OriginalOrderNumber> \n"
                + "    </InvoiceHeader> \n"
                + "    <IMAccountNumber>20451887000</IMAccountNumber> \n"
                + "    <Address Type=\"Customer\"> \n"
                + "        <Name>BIODIRECT</Name> \n"
                + "        <Attention>GORDON GREEN</Attention>= \n"
                + "        <AddressLine1>CELWAY INDUSTRIAL ESTATE</AddressLine1> \n"
                + "        <AddressLine2>Unit 34</AddressLine2> \n"
                + "        <City>Santa Anna</City> \n"
                + "        <PostalCode>34242</PostalCode> \n"
                + "        <CountryCode>US</CountryCode> \n"
                + "    </Address> \n"
                + "    <Address Type=\"ShipTo\"> \n"
                + "        <Name>LOWCOST IT</Name> \n"
                + "        <Attention>PURCHASING MANAGER</Attention> \n"
                + "        <AddressLine1>123 Main Street</AddressLine1> \n"
                + "        <AddressLine2>COMMON SQUARE</AddressLine2> \n"
                + "        <City>Santa Anna</City>\n"
                + "        <PostalCode>34224</PostalCode> \n"
                + "        <CountryCode>US</CountryCode> \n"
                + "    </Address> \n"
                + "    <Address Type=\"ShipFrom\"> \n"
                + "        <Name>INGRAM MICRO TLP MEMPHIS</Name> \n"
                + "        <AddressLine1>3820 MICRO DRIVE</AddressLine1> \n"
                + "        <City>Millington</City> \n"
                + "        <PostalCode>380530000</PostalCode> \n"
                + "    </Address> \n"
                + "    <VAT> \n"
                + "        <VATNumber>UK445522668</VATNumber> \n"
                + "    </VAT> \n"
                + "    <Currency> \n"
                + "        <CurrencyCode>GBP</CurrencyCode> \n"
                + "    </Currency> \n"
                + "    <PaymentTerms> \n"
                + "        <TermsCode>66</TermsCode> \n"
                + "    </PaymentTerms> \n"
                + "    <PaymentDue> \n"
                + "        <PaymentDueDate>20160231</PaymentDueDate> \n"
                + "    </PaymentDue> \n"
                + "    <LineItemHeader> \n"
                + "        <LineItem LineNumber=\"1\"> \n"
                + "            <Product SKU=\"P803551\" InvoicedQuantity=\"3\"> \n"
                + "            <CustomerSKU></CustomerSKU> \n"
                + "            <EANCode>1234567891230</EANCode> \n"
                + "            <ManufacturerSKU>VEHH887A6</ManufacturerSKU> \n"
                + "            <SKUDescription1>INTEGRATED CD ROM</SKUDescription1>\n"
                + "            <SKUDescription2>2X RW/4X DVD</SKUDescription2> \n"
                + "            </Product>\n"
                + "            <SerialNumberHeader> \n"
                + "                <SerialNumber>SE445262</SerialNumber> \n"
                + "                <SerialNumber>SE445263</SerialNumber> \n"
                + "                <SerialNumber>SE445264</SerialNumber> \n"
                + "            </SerialNumberHeader> \n"
                + "            <Tax Type=\"VAT\">20.02</Tax> \n"
                + "            <LineMonetaryInfo> \n"
                + "                <UnitPrice>32.11</UnitPrice> \n"
                + "                <LineItemAmount>96.33</LineItemAmount> \n"
                + "            </LineMonetaryInfo> \n"
                + "        </LineItem> \n"
                + "    </LineItemHeader> \n"
                + "    <TotalInformation Type=\"OrderLines\">1</TotalInformation> \n"
                + "    <TotalInformation Type=\"TotalQuantity\">3</TotalInformation> \n"
                + "    <TotalInformation Type=\"Terms\">100 NET 15 DAYS</TotalInformation> \n"
                + "    <TotalFinancial Type=\"TotalAmountDue\">136.35</TotalFinancial> \n"
                + "    <TotalFinancial Type=\"TotalLineItemAmount\">96.33</TotalFinancial> \n"
                + "    <TotalFinancial Type=\"TotalDiscountAmount\">0</TotalFinancial> \n"
                + "    <TotalFinancial Type=\"TotalTaxableAmount\">116.33</TotalFinancial> \n"
                + "    <Final Type=\"FinalTaxAmount\">20.02</Final> \n"
                + "    <Final Type=\"FinalTaxableAmount\">96.33</Final> \n"
                + "</Invoice>";
        return this.getIngramResponceXml(invoiceXml, i);
    }

    @Override
    public int updateWarehouseOrderStatus(String warehouseOrderStatus, String poNumber, String warehouseOrderNumber) {
        return this.ingramDao.updateWarehouseOrderStatus(warehouseOrderStatus, poNumber, warehouseOrderNumber);
    }

    @Override
    public int updateTrackingCarrierAndId(String trackingCarrier, String trackingId, String poNumber) {
        return this.ingramDao.updateTrackingCarrierAndId(trackingCarrier, trackingId, poNumber);
    }

    @Override
    public String getPoNumberForOrderStatus() {
        return this.ingramDao.getPoNumberForOrderStatus();
    }

    public void unzip(String zipFilePath, String destDir) {

        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void flushDataForIngramOrderTracking() {
        this.ingramDao.flushDataForIngramOrderTracking();
    }

    @Override
    public void flushDataForIngramPNA() {
        this.ingramDao.flushDataForIngramPNA();
    }

    @Override
    public int updateIngramOrderResponceData(String warehouseOrderNumber, String poNumber) {
        return this.ingramDao.updateIngramOrderResponceData(warehouseOrderNumber, poNumber);
    }

    @Override
    public String getOrderShippingCostXml() {

        Order order = this.ingramDao.getDataForIngramBaseRateRequest();
        int i = 7;
        String shippingCostXml = "<BaseRateRequest> \n"
                + "<Version1.0></Version1.0> \n"
                + "<TransactionHeader>\n"
                + "<SenderID/>\n"
                + "<ReceiverID/>\n"
                + "<CountryCode>MD</CountryCode> \n"
                + "<LoginID>QD74C5HJQ5</LoginID> \n"
                + "<Password>6jYpEBcv2Z</Password> \n"
                + "<TransactionID>123847</TransactionID> \n"
                + "</TransactionHeader>  \n"
                + "<TrackingRequestHeader> \n"
                + "<BaseRateInformation> \n"
                + "<BranchOrderNumber>" + order.getWarehouseOrderNumber() + "</BranchOrderNumber> \n"
                + "<PostalCode>" + order.getPostalCode() + "</PostalCode> \n"
                + "<Suffix /> \n"
                + "</BaseRateInformation> \n"
                + "</BaseRateRequest>";
        return this.getIngramResponceXml(shippingCostXml, i);
    }
}
