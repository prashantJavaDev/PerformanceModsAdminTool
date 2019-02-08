/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates   
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.model.DTO.LocationsDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.LogisticsDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.OrderDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.OrderItemDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.RecipientDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.ShippingDTO;
import com.aspirant.performanceModsAdminTool.service.TurnService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pk
 */
@Controller
public class TurnFourteenApiController {

    @Autowired
    TurnService turnService;

    @RequestMapping("turn/location.htm")
    public String getLocationOfTurn() {
        turnService.getLocation();
        return "home/home";
    }

    @Scheduled(cron = "0 0 4 ? * SUN")
    @RequestMapping("turn/items.htm")
    public void getitemsOfTurn() {
        turnService.getItems(1);
//        return "home/home";
    }

    @Scheduled(cron = "0 0 5 ? * SUN")
    @RequestMapping("turn/itemsT2.htm")
    public void getitemsOfTurnt2() {
        turnService.getItems(8);
//        return "home/home";
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @RequestMapping("turn/prices.htm")
    public void getPriceOfTurn() {
        turnService.getPrice(1);
        //return null;
    }

    @Scheduled(cron = "0 0 11 * * ?")
    @RequestMapping("turn/pricesT2.htm")
    public void getPriceOfTurnT2() {
        turnService.getPrice(8);
        //return null;
    }

    @Scheduled(cron = "0 0 */3 * * *")
    @RequestMapping("turn/inventory.htm")
    public void getInventoryOfTurn() {
        turnService.getInventory(1);
//        return "home/home";
    }

    @Scheduled(cron = "0 0 */4 * * *")
    @RequestMapping("turn/inventoryT2.htm")
    public void getInventoryOfTurnT2() {
        turnService.getInventory(8);
//        return "home/home";
    }

    @RequestMapping("turn/token.htm")
    public String gettokenOfTurn() {
        turnService.getApiTokenOfTurn(1);
        return "home/home";
    }

    @RequestMapping("turn/itemFile.htm")
    public String getItemFileOfTurn() {
        turnService.addItemByFile();
        return "home/home";
    }

    @RequestMapping("turn/priceFile.htm")
    public String getPriceFileOfTurn() {
        turnService.addPriceByFile();
        return "home/home";
    }

    @RequestMapping("turn/inventoryFile.htm")
    public String getinventoryFileOfTurn() {
        turnService.addInventoryByFile();
        return "home/home";
    }
    @RequestMapping("turn/getShipping.htm")
    public String getShippingOfTurn() {
        turnService.getShipping(1);
        return "home/home";
    }
    @RequestMapping("turn/getShippingT2.htm")
    public String getShippingOfTurnT2() {
        turnService.getShipping(8);
        return "home/home";
    }

    @RequestMapping("turn/test.htm")
    public String getinventoryFileOfTurnTest() {
//        turnService.addInventoryByFile();
        OrderDTO d = new OrderDTO();
        d.setEnvironment("testing");
        d.setOrder_notes("demo");
        d.setPo_number("12345");
        RecipientDTO r = new RecipientDTO();
        r.setAddress("1");
        r.setAddress_2("sde");
        r.setCity("NGO");
        r.setCountry("INd");
        r.setEmail_address("Abc@gmail.com");
        r.setName("XYZ");
        r.setPhone_number("999");
        r.setState("MH");
        r.setZip("44000012");
        d.setRecipient(r);
        LogisticsDTO log=new LogisticsDTO();
        log.setDays("5");
        log.setDropship_controller_id(2);
        log.setLocation("01");
        List<LogisticsDTO> expedited_logistics=new ArrayList<>();
        expedited_logistics.add(log);
        d.setExpedited_logistics(expedited_logistics);
       //
        List<LocationsDTO> locations=new ArrayList<>();
        //
        LocationsDTO loc=new LocationsDTO();
        loc.setLocation("01");
        //
        ShippingDTO shipping=new ShippingDTO();
        shipping.setShipping_code(0);
        shipping.setSaturday_delivery(false);
        shipping.setSignature_required(false);
        loc.setShipping(shipping);
        //
        List<OrderItemDTO> items=new ArrayList<>();
        OrderItemDTO ord=new OrderItemDTO();
        ord.setItem_identifier("12222");
        ord.setItem_identifier_type("item_no");
        ord.setQuantity(1);
        items.add(ord);
        loc.setItems(items);
        
        locations.add(loc);
        d.setLocations(locations);
        
        System.out.println("=======" + new Gson().toJson(d));

        return "home/home";
    }
}
