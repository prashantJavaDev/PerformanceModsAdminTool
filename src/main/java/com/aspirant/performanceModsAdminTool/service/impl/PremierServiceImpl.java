/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.PremierDao;
import com.aspirant.performanceModsAdminTool.dao.TurnDao;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.LocationApiResponce;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierCountryPrice;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierInventory;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierInventoryApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierInventoryResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierPriceApiResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierPriceResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierTokenResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.aspirant.performanceModsAdminTool.service.PremierService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pk
 */
@Service
public class PremierServiceImpl implements PremierService {

    private final String INVENTORY_FILE_PATH = "/home/ubuntu/performanceMods/premier/InventoryAPIResponce.csv";
//    private final String INVENTORY_FILE_PATH = "/home/pk/performanceMods/premier/InventoryAPIResponce.csv";
    private final String INVENTORY_FILE_PATH2 = "/home/ubuntu/performanceMods/premier/InventoryAPIResponce2.csv";
//    private final String INVENTORY_FILE_PATH = "/home/pk/performanceMods/premier/InventoryAPIResponce2.csv";
    private final String INVENTORY_BASE_URL = "https://api.premierwd.com/api/v5/inventory?itemNumbers=";
    private final String PRICE_FILE_PATH = "/home/ubuntu/performanceMods/premier/PriceAPIResponce.csv";
//    private final String PRICE_FILE_PATH = "/home/pk/performanceMods/premier/PriceAPIResponce.csv";
    private final String PRICE_FILE_PATH2 = "/home/ubuntu/performanceMods/premier/PriceAPIResponce2.csv";
//    private final String PRICE_FILE_PATH = "/home/pk/performanceMods/premier/PriceAPIResponce.csv";
    private final String PRICE_BASE_URL = "https://api.premierwd.com/api/v5/pricing?itemNumbers=";
    private final String API_KEY = "4f99608a-7690-4a9e-8a3a-ed6b13c24130"; //Warehouse ID 3
    private final String API_KEY2 = "abf0ef0f-4c9d-4f87-b95b-c212fda120cc"; //warehouse id 7

    @Autowired
    PremierDao premierDao;
    @Autowired
    TurnDao turnDao;

    @Override
    public String getSessionToken(int warehouseId) {
        String sessionToken = null;
        try {
            String apiKey = "";
            if (warehouseId == 3) {
                apiKey = API_KEY;
            } else {
                apiKey = API_KEY2;
            }
            HttpGet get = new HttpGet("https://api.premierwd.com/api/v5/authenticate?apiKey=" + apiKey);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
//            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<PremierTokenResponse>() {
            }.getType();
            PremierTokenResponse resp = new Gson().fromJson(json, typeList);
//            System.out.println("Resp-===" + resp);
            premierDao.updateTokenOfPremier(resp.getSessionToken(), warehouseId);
            sessionToken = resp.getSessionToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionToken;
    }

    @Override
    public void getInventory(int warehouseId) {
        try {
            getSessionToken(warehouseId);

            File f = null;
            if (warehouseId == 3) {
                f = new File(INVENTORY_FILE_PATH);
            } else {
                f = new File(INVENTORY_FILE_PATH2);
            }
            FileOutputStream fout = new FileOutputStream(f);
            executeParallel(4, INVENTORY_BASE_URL, fout, "INV", warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPrice(int warehouseId) {
        try {
            getSessionToken(warehouseId);
            File f = null;
            if (warehouseId == 3) {
                f = new File(PRICE_FILE_PATH);
            } else {
                f = new File(PRICE_FILE_PATH2);
            }
            FileOutputStream fout = new FileOutputStream(f);
            executeParallel(4, PRICE_BASE_URL, fout, "PRI", warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeParallel(int times, final String url, FileOutputStream fout, final String apiName, int warehousId) {

        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        final List<String> feedEntries = this.premierDao.getFeedEntries();
        final int loopCount = Math.round(feedEntries.size() / times) == 0 ? 1 : Math.round(feedEntries.size() / times);
        System.out.println("loop Count==" + loopCount);
        for (int i = 0; i < times; i++) {
            System.out.println("In Count==" + i);
            final int start = (loopCount * i) + 1;
            final int cal = i;
            final FileOutputStream fout1 = fout;
            final int wId = warehousId;
            Runnable r = new Runnable() {
                int end = (loopCount * cal) + loopCount;

                @Override
                public void run() {
                    int count = 0;
                    StringBuilder sb = new StringBuilder();
                    System.out.println("start==" + start);
                    System.out.println("end==" + end);
                    if ((end - start) > 50) {
                        for (int j = start; j <= end; j++) {
//                        callTurnAPI(get, fout1);
                            sb.append(feedEntries.get(j));
                            sb.append(",");
                            count++;
                            if (count == 50) {
                                HttpGet get = new HttpGet(url + sb);
                                callPremierAPI(get, fout1, apiName, wId);
                                count = 0;
                                sb.setLength(0);
                            }
                        }
                    } else {
                        for (int j = start; j <= end; j++) {
                            sb.append(feedEntries.get(j));
                            sb.append(",");
                        }
                        HttpGet get = new HttpGet(url + sb.substring(0, sb.length() - 1));
                        callPremierAPI(get, fout1, apiName, wId);
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
            if (apiName.equals("INV")) {
                if (warehousId == 3) {
                    this.premierDao.addInventoryFile(INVENTORY_FILE_PATH, warehousId);
                } else {
                    this.premierDao.addInventoryFile(INVENTORY_FILE_PATH2, warehousId);
                }
            } else if (apiName.equals("PRI")) {
                if (warehousId == 3) {
                    this.premierDao.addPriceFile(PRICE_FILE_PATH, warehousId);
                } else {
                    this.premierDao.addPriceFile(PRICE_FILE_PATH2, warehousId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("All thead Finished");
        System.out.println("Out Time" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
    }

    private void callPremierAPI(HttpGet get, FileOutputStream fout, String apiName, int warehouseId) {
        try {
            TokenResponse token = null;
            if (warehouseId == 3) {
                token = this.turnDao.getToken("P");
            } else {
                token = this.turnDao.getToken("P2");
            }
            HttpClient client = new DefaultHttpClient();
            get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            System.out.println("res.getStatusLine().getStatusCode()====" + res.getStatusLine().getStatusCode());
            if (res.getStatusLine().getStatusCode() == 200) {
                if (apiName.equals("INV")) {
                    Type typeList = new TypeToken<List<PremierInventoryResponse>>() {
                    }.getType();
                    List<PremierInventoryResponse> resp = new Gson().fromJson(json, typeList);
                    writeInInventoryFile(resp, fout, warehouseId);
                } else if (apiName.equals("PRI")) {
                    Type typeList = new TypeToken<List<PremierPriceResponse>>() {
                    }.getType();
                    List<PremierPriceResponse> resp = new Gson().fromJson(json, typeList);
//                    System.out.println("daatatta====" + resp);
                    writeInPriceFile(resp, fout, warehouseId);
                }
            } else if (res.getStatusLine().getStatusCode() == 401) {
                getSessionToken(warehouseId);
                callPremierAPI(get, fout, apiName, warehouseId);
            } else {
                HttpResponse res1 = client.execute(get);
                InputStream content1 = res1.getEntity().getContent();
                String json1 = IOUtils.toString(content1);
                System.out.println("res.getStatusLine().getStatusCode()22222222222====" + res.getStatusLine().getStatusCode());
                if (res1.getStatusLine().getStatusCode() == 200) {
                    if (apiName.equals("INV")) {
                        Type typeList1 = new TypeToken<List<PremierInventoryResponse>>() {
                        }.getType();
                        List<PremierInventoryResponse> resp = new Gson().fromJson(json1, typeList1);
                        writeInInventoryFile(resp, fout, warehouseId);
                    } else if (apiName.equals("PRI")) {
                        Type typeList = new TypeToken<List<PremierPriceResponse>>() {
                        }.getType();

                        List<PremierPriceResponse> resp = new Gson().fromJson(json, typeList);
//                        System.out.println("daatatta====" + resp);
                        writeInPriceFile(resp, fout, warehouseId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInInventoryFile(List<PremierInventoryResponse> data, FileOutputStream fout, int warehouseId) {
        System.out.println("size===" + data.size());
        StringBuilder sb = new StringBuilder();
        for (PremierInventoryResponse response : data) {
            sb.append(response.getItemNumber());
            sb.append(";");
            List<PremierInventory> inventory = response.getInventory();
            int sumOfInventory = 0;
            for (PremierInventory premierInventory : inventory) {
                sumOfInventory = sumOfInventory + premierInventory.getQuantityAvailable();
            }
            sb.append(sumOfInventory);
            sb.append(";");
            sb.append(warehouseId);
            sb.append("\n");
        }
        byte[] bytes = sb.toString().getBytes();
        try {
            fout.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInPriceFile(List<PremierPriceResponse> data, FileOutputStream fout, int warehouseId) {
        System.out.println("size===" + data.size());
        StringBuilder sb = new StringBuilder();
        for (PremierPriceResponse response : data) {

            sb.append(response.getItemNumber());
            sb.append(";");
            List<PremierCountryPrice> pricing = response.getPricing();
            for (PremierCountryPrice pr : pricing) {
                if (pr.getCurrency().equals("USD")) {
                    sb.append(pr.getCost());
                    sb.append(";");
                    sb.append(pr.getMap());
                }
            }
            sb.append(";");
            sb.append(warehouseId);
            sb.append("\n");
        }
        byte[] bytes = sb.toString().getBytes();
        try {
            fout.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
