/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.service.ManufacturerService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ritesh
 */
@Controller
public class ManufacturerController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ManufacturerService manufacturerService;

    @RequestMapping(value = "admin/addManufacturer.htm", method = RequestMethod.GET)
    public String showAddManufacturerForm(ModelMap model) {
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        return "admin/addManufacturer";
    }

    @RequestMapping(value = "admin/addManufacturer.htm", method = RequestMethod.POST)
    public String onAddmanufacturerSubmit(@ModelAttribute("manufacturer") Manufacturer manufacturer, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int manufacturerId = this.manufacturerService.addManufacturer(manufacturer);
            if (manufacturerId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addManufacturer.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "Manufacturer added successfully.";
                return "redirect:../admin/addManufacturer.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "admin/mapManufacturer.htm", method = RequestMethod.GET)
    public String showMapManufacturer(ModelMap model) {
        List<Manufacturer> manufacturerList = this.productService.getListOfManufacturer();
        model.addAttribute("manufacturerList", manufacturerList);
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        return "admin/mapManufacturer";
    }

    @RequestMapping(value = "admin/mapManufacturer.htm", method = RequestMethod.POST)
    public String onMapManufacturerSubmit(@ModelAttribute("manufacturer") Manufacturer manufacturer, Errors errors, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int manufacturerId = this.manufacturerService.mapManufacturer(manufacturer);
            if (manufacturerId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/mapManufacturer.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "Manufacturer mapped successfully.";
                return "redirect:../admin/mapManufacturer.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }
}
