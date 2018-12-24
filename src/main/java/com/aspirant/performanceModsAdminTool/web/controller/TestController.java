/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author pk
 */
@Controller
public class TestController {
    @RequestMapping(value="ingram/test.htm",method = RequestMethod.GET)
    public String testPostMethod() {
        HttpPost post = new HttpPost("https://api.turn14.com/v1/token");
//        post.setHeader("Content-type", "application/json");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
        pairs.add(new BasicNameValuePair("client_id", "4f4cb12a9ff2d0433f4304809cad2e956fb6900f"));
        pairs.add(new BasicNameValuePair("client_secret", "589a422699942aad13ccc844f6155ec5489c8ff3"));
        try {
        post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpClient client =new DefaultHttpClient();
            HttpResponse res = client.execute(post);
            InputStream content = res.getEntity().getContent();
           
String json = IOUtils.toString(content);
            Type typeList = new TypeToken<TokenResponse>() {}.getType();
            System.out.println("Responce==="+res.toString());
            System.out.println("Responce==="+res.getStatusLine());
            System.out.println("Responce==="+content.toString());
            System.out.println("Responce==="+json);
            if(res.getStatusLine().getStatusCode()==200){
            TokenResponse t  = new Gson().fromJson(json, typeList);
                System.out.println("trerer"+t.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/home/home";
    }
}
