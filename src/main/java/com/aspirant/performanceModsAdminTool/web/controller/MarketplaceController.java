/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ritesh
 */
@Controller
public class MarketplaceController {

    @Autowired
    private MarketplaceService marketplaceService;

    @RequestMapping(value = "admin/addMarketplace.htm", method = RequestMethod.GET)
    public String showAddMarkeplace(ModelMap model) {
        Marketplace marketpalce = new Marketplace();
        marketpalce.setActive(true);
        model.addAttribute("marketplace", marketpalce);
        return "admin/addMarketplace";
    }

    @RequestMapping(value = "admin/addMarketplace.htm", method = RequestMethod.POST)
    public String onAddMarketplaceSubmit(@ModelAttribute("marketplace") Marketplace marketplace, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../admin/listMarketplace.htm?msg=msg.actionCancelled";
        } else {
            int marketplaceId = this.marketplaceService.addMarketplace(marketplace);
            if (marketplaceId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addMarketplace.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.marketplaceAddedSuccessfully";
                return "redirect:listMarketplace.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/admin/listMarketplace.htm", method = RequestMethod.GET)
    public String showMarketplaceListPage(ModelMap model) {
        model.addAttribute("marketplaceList", this.marketplaceService.getMarketplaceList(false));
        return "/admin/listMarketplace";
    }

    @RequestMapping(value = "/admin/editMarketplace.htm", method = RequestMethod.GET)
    public String showEditMarketplaceForm(@RequestParam(value = "marketplaceId", required = true) int marketplaceId, ModelMap model) {
        Marketplace marketplace = this.marketplaceService.getMarketplaceByMarketplaceId(marketplaceId);
        model.addAttribute("marketplace", marketplace);
        return "/admin/editMarketplace";
    }

    @RequestMapping(value = "/admin/editMarketplace.htm", method = RequestMethod.POST)
    public String onEditMarketplaceSubmit(@ModelAttribute("marketplace") Marketplace marketplace, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../admin/listMarketplace.htm?msg=msg.actionCancelled";
        } else {
            int id = this.marketplaceService.updateMarketplace(marketplace);
            if (id == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/editMarketplace.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.marketplaceUpdatedSuccessfully";
                return "redirect:../admin/listMarketplace.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }
}
