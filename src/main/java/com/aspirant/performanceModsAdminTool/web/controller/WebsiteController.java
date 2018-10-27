/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.EmailerBlock;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Pallavi
 */
@Controller
public class WebsiteController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/website/uploadWebsiteProduct.htm", method = RequestMethod.GET)
    public String showUploadForWebsiteGet(ModelMap model) {

        List<Company> companyList = this.productService.getCompanyList(true);
        model.addAttribute("companyList", companyList);

        UploadFeed uploadFeed = new UploadFeed();
        model.addAttribute("uploadFeed", uploadFeed);

        return "/website/uploadWebsiteProduct";
    }

    @RequestMapping(value = "/website/uploadWebsiteProduct.htm", method = RequestMethod.POST)
    public String showUploadForWebsitePost(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {

        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            uploadFeed = null;
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("upload for website data validation started...", GlobalConstants.TAG_SYSTEMLOG));
            int companyId = ServletRequestUtils.getIntParameter(request, "companyId", 0);
            String companyName = ServletRequestUtils.getStringParameter(request, "companyName", null);
            model.addAttribute("companyId", companyId);
            model.addAttribute("companyName", companyName);
            int result = this.productService.saveMultipartFileDataForWebsiteProduct(uploadFeed, companyId);
            String msg;
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../website/uploadWebsiteProduct.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                msg = " Wesite Products uploaded successfully.";
                return "redirect:../website/uploadWebsiteProduct.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    

    
    
//    @RequestMapping(value = "/website/uploadProductImageForWebsite.htm", method = RequestMethod.GET)
//    public String showImageUploadForWebsiteGet() {
//        return "/website/uploadProductImageForWebsite";
//    }

//    @RequestMapping(value = "/website/uploadProductImageForWebsite.htm", method = RequestMethod.POST)
//    public String showImageUploadForWebsitePost(@ModelAttribute("uploadFeed") UploadFeed uploadFeed, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
//
//        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
//        if (cancel != null) {
//            uploadFeed = null;
//            return "redirect:../home/home.htm?msg=Action cancelled";
//        } else {
//            LogUtils.systemLogger.info(LogUtils.buildStringForLog("upload for website data validation started...", GlobalConstants.TAG_SYSTEMLOG));
//            int result = this.productService.UploadImageServiceImpl(uploadFeed);
//            String msg;
//            if (result == 0) {
//                String error = "Sorry! Image  not saved.";
//                return "redirect:../website/uploadProductImageForWebsite.htm?error=" + URLEncoder.encode(error, "UTF-8");
//            } else {
//                msg = " Image uploaded successfully.";
//                return "redirect:../website/uploadProductImageForWebsite.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
//            }
//        }
//    }
}
