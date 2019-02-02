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

    private final String INVENTORY_FILE_PATH = "/home/ubuntu/performanceMods/premier/IventoryAPIResponce.csv";
    private final String INVENTORY_BASE_URL = "https://api.premierwd.com/api/v5/inventory?itemNumbers=";
    private final String PRICE_FILE_PATH = "/home/ubuntu/performanceMods/premier/PriceAPIResponce.csv";
    private final String PRICE_BASE_URL = "https://api.premierwd.com/api/v5/pricing?itemNumbers=";
    private final String API_KEY = "";

    @Autowired
    PremierDao premierDao;
    @Autowired
    TurnDao turnDao;

    @Override
    public String getSessionToken() {
        String sessionToken = null;
        try {
            HttpGet get = new HttpGet("https://api.premierwd.com/api/v5/authenticate?apiKey=" + API_KEY);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            System.out.println("Jsono oadsand=======" + json);
            Type typeList = new TypeToken<PremierTokenResponse>() {
            }.getType();
            PremierTokenResponse resp = new Gson().fromJson(json, typeList);
            System.out.println("Resp-===" + resp);
            premierDao.updateTokenOfPremier(resp.getSessionToken());
            sessionToken=resp.getSessionToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionToken;
    }

    @Override
    public void getInventory() {
        try {
            File f = new File(INVENTORY_FILE_PATH);
            FileOutputStream fout = new FileOutputStream(f);
            executeParallel(4, INVENTORY_BASE_URL, fout, "INV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPrice() {
        try {
            File f = new File(PRICE_FILE_PATH);
            FileOutputStream fout = new FileOutputStream(f);
            executeParallel(4, PRICE_BASE_URL, fout, "PRI");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeParallel(int times, final String url, FileOutputStream fout, final String apiName) {

        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        final List<String> feedEntries = this.premierDao.getFeedEntries();
        final int loopCount = Math.round(feedEntries.size() / times);
        for (int i = 0; i < times; i++) {
            final int start = (loopCount * i) + 1;
            final int cal = i;
            final FileOutputStream fout1 = fout;
            Runnable r = new Runnable() {
                int end = (loopCount * cal) + loopCount;

                @Override
                public void run() {
                    int count = 0;
                    StringBuilder sb = new StringBuilder();
                    for (int j = start; j <= end; j++) {
//                        callTurnAPI(get, fout1);
                        sb.append(feedEntries.get(j));
                        count++;
                        if (count == 50) {
                            HttpGet get = new HttpGet(url + sb);
                            callPremierAPI(get, fout1, apiName);
                            count = 0;
                        }
                    }
                }
            };
        }
        exec.shutdown();
        while (!exec.isTerminated()) {

        }
    }

    private void callPremierAPI(HttpGet get, FileOutputStream fout, String apiName) {
        try {
            TokenResponse token = this.turnDao.getToken("P");
            HttpClient client = new DefaultHttpClient();
            get.setHeader("Authorization", token.getToken_type() + " " + token.getAccess_token());
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            if (res.getStatusLine().getStatusCode() == 200) {
            if (apiName.equals("INV")) {

                Type typeList = new TypeToken<PremierInventoryApiResponse>() {
                }.getType();

                PremierInventoryApiResponse resp = new Gson().fromJson(json, typeList);
                List<PremierInventoryResponse> data = resp.getData();
            } else if (apiName.equals("PRI")) {
                Type typeList = new TypeToken<PremierPriceApiResponse>() {
                }.getType();

                PremierPriceApiResponse resp = new Gson().fromJson(json, typeList);
                List<PremierPriceResponse> data = resp.getData();
            }
            }else{
                getSessionToken();
                callPremierAPI(get, fout, apiName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
