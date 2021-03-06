/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.TurnDao;
import com.aspirant.performanceModsAdminTool.model.DTO.InventoryApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.InventoryAttributes;
import com.aspirant.performanceModsAdminTool.model.DTO.InventoryResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemAttributes;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.LocationApiResponce;
import com.aspirant.performanceModsAdminTool.model.DTO.PriceApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PriceAttributes;
import com.aspirant.performanceModsAdminTool.model.DTO.PriceList;
import com.aspirant.performanceModsAdminTool.model.DTO.PriceResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.ShippingAPIResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.aspirant.performanceModsAdminTool.service.TurnService;
import com.aspirant.performanceModsAdminTool.web.controller.ShippingTurnAPIResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pk
 */
@Service
public class TurnServiceImpl implements TurnService {
//   private  int  pageNo=0;

//    private final String ITEM_FILE_PATH = "/home/pk/performanceMods/turn14/APIResponce.csv";
    private final String ITEM_FILE_PATH = "/home/ubuntu/performanceMods/turn14/APIResponce.csv";
//    private final String PRICE_FILE_PATH = "/home/pk/performanceMods/turn14/APIResponsePrice.csv";
    private final String PRICE_FILE_PATH = "/home/ubuntu/performanceMods/turn14/APIResponsePrice.csv";
//    private final String INVENTORY_FILE_PATH = "/home/pk/performanceMods/turn14/APIResponseInventory.csv";
    private final String INVENTORY_FILE_PATH = "/home/ubuntu/performanceMods/turn14/APIResponseInventory.csv";
//    private final String ITEM_FILE_PATH2 = "/home/pk/performanceMods/turn14/APIResponce2.csv";
    private final String ITEM_FILE_PATH2 = "/home/ubuntu/performanceMods/turn14/APIResponce2.csv";
//    private final String PRICE_FILE_PATH2 = "/home/pk/performanceMods/turn14/APIResponsePrice2.csv";
    private final String PRICE_FILE_PATH2 = "/home/ubuntu/performanceMods/turn14/APIResponsePrice2.csv";
//    private final String INVENTORY_FILE_PATH2 = "/home/pk/performanceMods/turn14/APIResponseInventory2.csv";
    private final String INVENTORY_FILE_PATH2 = "/home/ubuntu/performanceMods/turn14/APIResponseInventory2.csv";

    @Autowired
    TurnDao turnDao;

    @Override
    public int updateTokenEntry(TokenResponse tokenResponse, int warehouseId) {
        return turnDao.updateTokenEntry(tokenResponse, warehouseId);
    }

    @Override
    public TokenResponse getToken(String apiType) {
        return turnDao.getToken(apiType);
    }

    @Override
    public void getApiTokenOfTurn(int warehouseId) {
        HttpPost post = new HttpPost("https://api.turn14.com/v1/token");
//        post.setHeader("Content-type", "application/json");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
        if (warehouseId == 1) {
            pairs.add(new BasicNameValuePair("client_id", "4f4cb12a9ff2d0433f4304809cad2e956fb6900f"));
            pairs.add(new BasicNameValuePair("client_secret", "589a422699942aad13ccc844f6155ec5489c8ff3"));
        } else if (warehouseId == 8) {
            pairs.add(new BasicNameValuePair("client_id", "32f6f5938f142ff216081b367ce42b9eb217ab84"));
            pairs.add(new BasicNameValuePair("client_secret", "11ff12352b7d682316ce677708f6e268b1cad241"));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(post);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            Type typeList = new TypeToken<TokenResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                TokenResponse t = new Gson().fromJson(json, typeList);

                updateTokenEntry(t, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLocation() {
//        getApiTokenOfTurn();
        HttpGet get = new HttpGet("https://api.turn14.com/v1/locations");
        TokenResponse token = getToken("T");
        get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<LocationApiResponce>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                LocationApiResponce resp = new Gson().fromJson(json, typeList);
                System.out.println("Resp-===" + resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getItems(int warehouseId) {
        getApiTokenOfTurn(warehouseId);
        HttpGet get = new HttpGet("https://api.turn14.com/v1/items?page=1");
        TokenResponse token = null;
        if (warehouseId == 1) {
            token = getToken("T");
        } else if (warehouseId == 8) {
            token = getToken("T2");
        }
        get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
        try {
            File f = null;
            if (warehouseId == 1) {
                f = new File(ITEM_FILE_PATH);
            } else {
                f = new File(ITEM_FILE_PATH2);
            }
            FileOutputStream fout = new FileOutputStream(f);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
//            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<ItemApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                ItemApiResponse resp = new Gson().fromJson(json, typeList);
//                List<ItemResponse> data = resp.getData();
                System.out.println("resp.getData().size()" + resp.getData().size());
//                System.out.println("Resp-===" + resp);
//                addItem(data);
//                writeInFile(data,fout);
                int total_pages = resp.getMeta().getTotal_pages();
                System.out.println("toalpages==" + total_pages);
                int process = Math.round(total_pages / 100) + 1;
                System.out.println("process==" + process);
                executeParallel(process, fout, total_pages, warehouseId);
//                executeParallel(1, fout);
//                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addItem(List<ItemResponse> data) {
        this.turnDao.addItem(data);
        return 0;
    }

    public void executeParallel(int times, FileOutputStream fout, int total_Pages, int warehouseId) {
        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        for (int i = 0; i < times; i++) {
            final int cal = i;
            final FileOutputStream fout1 = fout;
            final int totalpages = total_Pages;
            final int wID = warehouseId;
            Runnable r;
            r = new Runnable() {
                int pageNo = cal * 100 + 1;

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        System.out.println("Page NO======" + pageNo);
                        if (pageNo <= totalpages) {
                            HttpGet get = new HttpGet("https://api.turn14.com/v1/items?page=" + pageNo);
                            callTurnAPI(get, fout1, wID);
                            pageNo++;
                        }
                    }
                }
            };
            exec.submit(r);
        }
        exec.shutdown();
        while (!exec.isTerminated()) {

        }
        try {
            fout.close();
            if (warehouseId == 1) {
                this.turnDao.addItemByFile(ITEM_FILE_PATH, warehouseId);
            } else {
                this.turnDao.addItemByFile(ITEM_FILE_PATH2, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("All thead Finished");
        System.out.println("Out Time" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
    }

    private void callTurnAPI(HttpGet get, FileOutputStream fout, int warehouseId) {
        try {
//            TokenResponse token = getToken("T");
            TokenResponse token = null;
            if (warehouseId == 1) {
                token = getToken("T");
            } else if (warehouseId == 8) {
                token = getToken("T2");
            }
            get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            Type typeList = new TypeToken<ItemApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                ItemApiResponse resp = new Gson().fromJson(json, typeList);
                List<ItemResponse> data = resp.getData();
//                System.out.println("resp.getData().size()" + resp.getData().size());
//                addItem(data);
                writeInFile(data, fout, warehouseId);
            } else if (res.getStatusLine().getStatusCode() == 401) {

                getApiTokenOfTurn(warehouseId);
                callTurnAPI(get, fout, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInFile(List<ItemResponse> data, FileOutputStream fout, int warehouseId) {
        try {

//            PrintWriter p=new PrintWriter(fout);
            System.out.println("size==" + data.size());
            int count = 0;
            for (ItemResponse i : data) {
                count++;
                ItemAttributes a = i.getAttributes();
                String str = i.getId() + "," + i.getType() + "," + a.getPart_number() + "," + a.getMfr_part_number() + "," + a.getBrand() + "," + warehouseId + "\n";
                byte[] bytes = str.getBytes();
                fout.write(bytes);
            }
            System.out.println("count===" + count);
//            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPrice(int warehouseId) {

        getApiTokenOfTurn(warehouseId);
        HttpGet get = new HttpGet("https://api.turn14.com/v1/pricing?page=1");
//        TokenResponse token = getToken("T");
        TokenResponse token = null;
        if (warehouseId == 1) {
            token = getToken("T");
        } else if (warehouseId == 8) {
            token = getToken("T2");
        }
        get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
        try {
//            File f = new File(PRICE_FILE_PATH);

            File f = null;
            if (warehouseId == 1) {
                f = new File(PRICE_FILE_PATH);
            } else {
                f = new File(PRICE_FILE_PATH2);
            }
            FileOutputStream fout = new FileOutputStream(f);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
//            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<PriceApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                PriceApiResponse resp = new Gson().fromJson(json, typeList);
//                List<PriceResponse> data = resp.getData();
                System.out.println("resp.getData().size()" + resp.getData().size());
//                System.out.println("Resp-===" + data);
//                addItem(data);
//                writeInFile(data,fout);
                int total_pages = resp.getMeta().getTotal_pages();
                System.out.println("toalpages==" + total_pages);
                int process = Math.round(total_pages / 100) + 1;
                System.out.println("process==" + process);
                executeParallelForPrice(process, fout, total_pages, warehouseId);
                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void executeParallelForPrice(int times, FileOutputStream fout, int total_pages, int warehouseId) {
        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        for (int i = 0; i < times; i++) {
            final int cal = i;
            final int totalpages = total_pages;
            final FileOutputStream fout1 = fout;
            final int wId = warehouseId;
            Runnable r;
            r = new Runnable() {
                int pageNo = cal * 100 + 1;

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
//                    for (int j = 0; j < 1; j++) {
                        if (pageNo <= totalpages) {
                            System.out.println("Page NO======" + pageNo);
                            HttpGet get = new HttpGet("https://api.turn14.com/v1/pricing?page=" + pageNo);
                            callTurnAPIPrice(get, fout1, wId);
                            pageNo++;
                        }
                    }
                }
            };
            exec.submit(r);
        }
        exec.shutdown();
        while (!exec.isTerminated()) {

        }
        try {
            fout.close();
            if (warehouseId == 1) {
                this.turnDao.addPriceFile(PRICE_FILE_PATH, warehouseId);
            } else {
                this.turnDao.addPriceFile(PRICE_FILE_PATH2, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("All thead Finished");
        System.out.println("Out Time" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
    }

    private void callTurnAPIPrice(HttpGet get, FileOutputStream fout, int warehouseId) {
        try {
//            TokenResponse token = getToken("T");
            TokenResponse token = null;
            if (warehouseId == 1) {
                token = getToken("T");
            } else if (warehouseId == 8) {
                token = getToken("T2");
            }
            get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            Type typeList = new TypeToken<PriceApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                PriceApiResponse resp = new Gson().fromJson(json, typeList);
                List<PriceResponse> data = resp.getData();
//                System.out.println("resp.getData().size()" + resp.getData().size());
//                addItem(data);
                writeInFilePrice(data, fout, warehouseId);
            } else if (res.getStatusLine().getStatusCode() == 401) {

                getApiTokenOfTurn(warehouseId);
                callTurnAPIPrice(get, fout, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInFilePrice(List<PriceResponse> data, FileOutputStream fout, int warehouseId) {
        try {

//            PrintWriter p=new PrintWriter(fout);
            System.out.println("size==" + data.size());
            for (PriceResponse i : data) {
                PriceAttributes a = i.getAttributes();
                String str = i.getId() + "," + i.getType() + "," + a.getPurchase_cost() + "," + a.isHas_map() + ",";
                if (a.isHas_map()) {
                    List<PriceList> pricelists = a.getPricelists();
                    if (pricelists.isEmpty()) {
                        str = str + "0,";
                    } else {
                        int count = 0;
                        for (PriceList p : pricelists) {
                            if (p.getName().equals("MAP")) {
                                count++;
                                str = str + p.getPrice() + ",";
                            }
                        }
                        if (count == 0) {
                            str = str + "0,";
                        }
                    }
                } else {
                    str = str + "0,";
                }
                str = str + warehouseId + "\n";
                byte[] bytes = str.getBytes();
                fout.write(bytes);
            }
//            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getInventory(int warehouseId) {

        getApiTokenOfTurn(warehouseId);
        HttpGet get = new HttpGet("https://api.turn14.com/v1/inventory?page=1");
//        TokenResponse token = getToken("T");
        TokenResponse token = null;
        if (warehouseId == 1) {
            token = getToken("T");
        } else if (warehouseId == 8) {
            token = getToken("T2");
        }
        get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
        try {
//            File f = new File(INVENTORY_FILE_PATH);
            File f = null;
            if (warehouseId == 1) {
                f = new File(INVENTORY_FILE_PATH);
            } else {
                f = new File(INVENTORY_FILE_PATH2);
            }
            FileOutputStream fout = new FileOutputStream(f);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
//            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<InventoryApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                InventoryApiResponse resp = new Gson().fromJson(json, typeList);
//                List<InventoryResponse> data = resp.getData();
                System.out.println("resp.getData().size()" + resp.getData().size());
//                System.out.println("Resp-===" + data);
//                addItem(data);
//                writeInFileInventory(data,fout);
                int total_pages = resp.getMeta().getTotal_pages();
                System.out.println("toalpages==" + total_pages);
                int process = Math.round(total_pages / 100) + 1;
                System.out.println("process==" + process);
                executeParallelForInventory(process, fout, total_pages, warehouseId);
                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void executeParallelForInventory(int times, FileOutputStream fout, int total_pages, int warehouseId) {
        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        for (int i = 0; i < times; i++) {
            final int cal = i;
            final int totalpages = i;
            final FileOutputStream fout1 = fout;
            final int wId = warehouseId;
            Runnable r;
            r = new Runnable() {
                int pageNo = cal * 100 + 1;

                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
//                    for (int j = 0; j < 1; j++) {
                        if (pageNo <= totalpages) {
                            System.out.println("Page NO======" + pageNo);
                            HttpGet get = new HttpGet("https://api.turn14.com/v1/inventory?page=" + pageNo);
                            callTurnAPIInventory(get, fout1, wId);
                            pageNo++;
                        }
                    }
                }
            };
            exec.submit(r);
        }
        exec.shutdown();
        while (!exec.isTerminated()) {

        }
        try {
            fout.close();
            if (warehouseId == 1) {
                this.turnDao.addInventoryFile(INVENTORY_FILE_PATH, warehouseId);
            } else {
                this.turnDao.addInventoryFile(INVENTORY_FILE_PATH2, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("All thead Finished");
        System.out.println("Out Time" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
    }

    private void callTurnAPIInventory(HttpGet get, FileOutputStream fout, int warehouseId) {
        try {
//            TokenResponse token = getToken("T");
            TokenResponse token = null;
            if (warehouseId == 1) {
                token = getToken("T");
            } else if (warehouseId == 8) {
                token = getToken("T2");
            }

            get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            Type typeList = new TypeToken<InventoryApiResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                InventoryApiResponse resp = new Gson().fromJson(json, typeList);
                List<InventoryResponse> data = resp.getData();
//                System.out.println("resp.getData().size()" + resp.getData().size());
//                addItem(data);
                writeInFileInventory(data, fout, warehouseId);
            } else if (res.getStatusLine().getStatusCode() == 401) {

                getApiTokenOfTurn(warehouseId);
                callTurnAPIInventory(get, fout, warehouseId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInFileInventory(List<InventoryResponse> data, FileOutputStream fout, int warehouseId) {
        try {

//            PrintWriter p=new PrintWriter(fout);
            System.out.println("size==" + data.size());
            for (InventoryResponse i : data) {
                InventoryAttributes a = i.getAttributes();
                String str = i.getId() + "," + i.getType() + ",";
                if (a.getInventory() != null) {
                    String invData = a.getInventory().toString().substring(1, 23);
//                    System.out.println("invaData===="+invData);
                    String[] splitlevel1Data = invData.split(",");
                    double total = 0;
                    for (String strg : splitlevel1Data) {
//                        System.out.println("Strg==="+strg);
                        total = total + Double.parseDouble(strg.split("=")[1]);
                    }
                    str = str + total + ",";
//                    System.out.println("Total"+total);
                } else {
                    str = str + 0 + ",";
                }
                if (a.getManufacturer() != null) {
                    str = str + a.getManufacturer().getStock() + ",";
                } else {
                    str = str + 0 + ",";
                }
                str = str + warehouseId + "\n";
                byte[] bytes = str.getBytes();
                fout.write(bytes);
            }
//            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addItemByFile() {
//        this.turnDao.addItemByFile("/home/pk/Videos/APIResponce.csv");
//        this.turnDao.addItemByFile("/home/pk/Videos/APIResponsePrice.csv");
        return 0;
    }

    @Override
    public int addPriceByFile() {
//        this.turnDao.addPriceFile("/home/pk/Videos/APIResponsePrice.csv");
        return 0;
    }

    @Override
    public int addInventoryByFile() {
//        this.turnDao.addInventoryFile("/home/pk/Videos/APIResponseInventory.csv");
        return 0;

    }

    @Override
    public void getShipping(int warehouseId) {
        getApiTokenOfTurn(warehouseId);
        HttpGet get = new HttpGet("https://api.turn14.com/v1/shipping");
        TokenResponse token = null;
        if (warehouseId == 1) {
            token = getToken("T");
        } else if (warehouseId == 8) {
            token = getToken("T2");
        }
        get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
//            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<ShippingTurnAPIResponse>() {
            }.getType();
            if (res.getStatusLine().getStatusCode() == 200) {
                ShippingTurnAPIResponse sp = new Gson().fromJson(json, typeList);
                this.turnDao.addShipping(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
