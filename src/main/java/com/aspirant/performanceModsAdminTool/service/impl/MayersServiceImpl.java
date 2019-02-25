/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.MayersDao;
import com.aspirant.performanceModsAdminTool.dao.TurnDao;
import com.aspirant.performanceModsAdminTool.model.DTO.MayerItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.MayerTokenResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierInventory;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierInventoryResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.PremierTokenResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.aspirant.performanceModsAdminTool.service.MayersService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pk
 */
@Service
public class MayersServiceImpl implements MayersService {

    private final String INVENTORY_FILE_PATH = "/home/ubuntu/performanceMods/mayer/InventoryAPIResponce.csv";
//    private final String INVENTORY_FILE_PATH = "/home/pk/performanceMods/mayer/InventoryAPIResponce.csv";
    private final String INVENTORY_BASE_URL = "http://meyerapi.meyerdistributing.com/http/default/ProdAPI/v2/ItemInformation?ItemNumber=";

    private final String username = "PerformancemodsAPI";
    private final String password = "Y$ggeL11pWN51#Szf8rw";

    @Autowired
    MayersDao mayersDao;
    @Autowired
    TurnDao turnDao;

    @Override
    public String getSessionToken() {
        HttpPost post = new HttpPost("http://meyerapi.meyerdistributing.com/http/default/ProdAPI/v2/Authentication");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));
        String body="{\n \"username\":\""+username+"\",\n"+"\"password\":\""+password+"\"}";
//        System.out.println("Bodyyyy======"+body);
        try {
        StringEntity stringEntity= new StringEntity(body);
//            post.setEntity(new UrlEncodedFormEntity(pairs));
            post.setEntity(stringEntity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(post);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            Type typeList = new TypeToken<MayerTokenResponse>() {
            }.getType();
//            System.out.println("ress==="+res+"9999999999999"+json);
            if (res.getStatusLine().getStatusCode() == 200) {
                MayerTokenResponse t = new Gson().fromJson(json, typeList);
                System.out.println("Token==="+t.toString());
                int updateTokenEntry = this.mayersDao.updateTokenEntry(t);
                System.out.println("dasdsada==="+updateTokenEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void getInventoryAndPrice() {
        try {
            getSessionToken();
            File f = new File(INVENTORY_FILE_PATH);
            FileOutputStream fout = new FileOutputStream(f);
            executeParallel(4, INVENTORY_BASE_URL, fout, "INV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeParallel(int times, final String url, FileOutputStream fout, final String apiName) {

        System.out.println("inTime" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
        ExecutorService exec = Executors.newFixedThreadPool(times);
        final List<String> feedEntries = this.mayersDao.getFeedEntries();
        final int loopCount = Math.round(feedEntries.size() / times) == 0 ? 1 : Math.round(feedEntries.size() / times);
        System.out.println("loop Count==" + loopCount);
        for (int i = 0; i < times; i++) {
            System.out.println("In Count==" + i);
            final int start = (loopCount * i) + 1;
            final int cal = i;
            final FileOutputStream fout1 = fout;
            Runnable r = new Runnable() {
                int end = (loopCount * cal) + loopCount;

                @Override   
                public void run() {
                    int count = 0;
                    StringBuilder sb = new StringBuilder();
                    System.out.println("start==" + start);
                    System.out.println("end==" + end);
                    if ((end - start) > 99) {
                        for (int j = start; j <= end; j++) {
//                        callTurnAPI(get, fout1);
                            sb.append(feedEntries.get(j));
                            sb.append(",");
                            count++;
                            if (count == 99) {
                                HttpGet get = new HttpGet(url + sb);
                                callMayerAPI(get, fout1, apiName);
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
                        callMayerAPI(get, fout1, apiName);
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
            this.mayersDao.addItemFile(INVENTORY_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("All thead Finished");
        System.out.println("Out Time" + System.currentTimeMillis() + " Namo Time" + System.nanoTime() + "Date Time===" + new Date());
    }

    private void callMayerAPI(HttpGet get, FileOutputStream fout, String apiName) {
        try {
            TokenResponse token = this.turnDao.getToken("M");
            HttpClient client = new DefaultHttpClient();
            get.setHeader("Authorization", "Espresso "+token.getAccess_token() + ":1");
            HttpResponse res = client.execute(get);
            InputStream content = res.getEntity().getContent();
            String json = IOUtils.toString(content);
            System.out.println("res.getStatusLine().getStatusCode()====" + res.getStatusLine().getStatusCode());
            System.out.println("Json====" + json);
            if (res.getStatusLine().getStatusCode() == 200) {
                Type typeList = new TypeToken<List<MayerItemResponse>>() {
                }.getType();
                List<MayerItemResponse> resp = new Gson().fromJson(json, typeList);
                writeInInventoryFile(resp, fout);
            } else if (res.getStatusLine().getStatusCode() == 401) {
                getSessionToken();
                callMayerAPI(get, fout, apiName);
            } else {
                HttpResponse res1 = client.execute(get);
                InputStream content1 = res1.getEntity().getContent();
                String json1 = IOUtils.toString(content1);
                System.out.println("res.getStatusLine().getStatusCode()22222222222====" + res.getStatusLine().getStatusCode());
                if (res1.getStatusLine().getStatusCode() == 200) {
                    Type typeList1 = new TypeToken<List<MayerItemResponse>>() {
                    }.getType();
                    List<MayerItemResponse> resp = new Gson().fromJson(json1, typeList1);
                    writeInInventoryFile(resp, fout);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeInInventoryFile(List<MayerItemResponse> data, FileOutputStream fout) {
        System.out.println("size===" + data.size());
        StringBuilder sb = new StringBuilder();
        for (MayerItemResponse response : data) {
            sb.append(response.getItemNumber());
            sb.append(";");
            sb.append(response.getQtyAvailable());
            sb.append(";");
            sb.append(response.getMinAdvertisedPrice()==null?0:response.getMinAdvertisedPrice());
            sb.append(";");
            sb.append(response.getCustomerPrice());
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
