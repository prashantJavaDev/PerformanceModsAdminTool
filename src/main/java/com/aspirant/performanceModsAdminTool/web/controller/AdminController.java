/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.AdminService;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shrutika
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "/admin/addCategory.htm", method = RequestMethod.GET)
    public String showAddCategoryForm(ModelMap model) {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        model.addAttribute("mainCategoryList", applicationSession.getMainCategoryListActive());

        model.addAttribute("subCategoryList", this.productService.getListOfSubCategory());

        MainCategory mainCategory = new MainCategory();
        model.addAttribute("mainCategory", mainCategory);
        return "/admin/addCategory";
    }

    @RequestMapping(value = "/admin/addCategory.htm", method = RequestMethod.POST)
    public String mappingCategoryPost(@ModelAttribute("mainCategory") MainCategory mainCategory, Errors errors, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            mainCategory = null;
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int result = this.adminService.insertCategoryMapping(mainCategory.getMainCategoryId(), mainCategory.getSubCategoryId());
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addCategory.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                return "redirect:../home/home.htm?&msg=msg.successfullyAddedData";
            }
        }
    }

    @RequestMapping(value = "/admin/addChildCategory.htm", method = RequestMethod.GET)
    public String showAddChildCategoryForm(ModelMap model) {
        //ApplicationSession applicationSession = ApplicationSession.getCurrent();
        model.addAttribute("subCategoryList", this.productService.getListOfSubCategory());
        model.addAttribute("childCategoryList", this.productService.getListOfChildCategory());

        SubCategory subCategory = new SubCategory();
        model.addAttribute("subCategory", subCategory);
        return "/admin/addChildCategory";
    }

    @RequestMapping(value = "/admin/addChildCategory.htm", method = RequestMethod.POST)
    public String mappingChildCategoryPost(@ModelAttribute("subCategory") SubCategory subCategory, Errors errors, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            subCategory = null;
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int result = this.adminService.insertChildCategoryMapping(subCategory.getSubCategoryId(), subCategory.getChildCategoryId());
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addChildCategory.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                return "redirect:../admin/addChildCategory.htm?&msg=msg.successfullyAddedData";
            }
        }
    }
    
    
    @RequestMapping(value = "/admin/addSubChildCategory.htm", method = RequestMethod.GET)
    public String showAddSubChildCategoryForm(ModelMap model) {
        model.addAttribute("childCategoryList", this.productService.getListOfChildCategory());
        model.addAttribute("subChildCategoryList", this.productService.getListOfSubChildCategory());

        ChildOfSubCategory childCategory = new ChildOfSubCategory();
        model.addAttribute("childCategory", childCategory);
        return "/admin/addSubChildCategory";
    }

    @RequestMapping(value = "/admin/addSubChildCategory.htm", method = RequestMethod.POST)
    public String mappingSubChildCategoryPost(@ModelAttribute("childCategory") ChildOfSubCategory childCategory, Errors errors, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            childCategory = null;
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int result = this.adminService.insertSubChildCategoryMapping(childCategory.getChildCategoryId(), childCategory.getSubChildCategoryId());
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addSubChildCategory.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                return "redirect:../admin/addSubChildCategory.htm?&msg=msg.successfullyAddedData";
            }
        }
    }
    
    @RequestMapping(value = "/admin/addNewListing.htm", method = RequestMethod.GET)
    public String showaddNewListing(ModelMap model) {
        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        List<Manufacturer> manufacturerList = this.productService.getListOfManufacturer();
        model.addAttribute("marketplaceList", marketplaceList);
        model.addAttribute("manufacturerList", manufacturerList);
        Listing listing = new Listing();
        listing.setPack(1);
        model.addAttribute("listing", listing);
        return "admin/addNewListing";
    }

    @RequestMapping(value = "/admin/addNewListing.htm", method = RequestMethod.POST)
    public String onaddListingSubmit(@ModelAttribute("listing") Listing listing, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int result = this.adminService.addNewListing(listing);
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addNewListing.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                return "redirect:../home/home.htm?&msg=msg.successfullyAddedListing";
            }
        }

    }

    @RequestMapping(value = "admin/addCompany.htm", method = RequestMethod.GET)
    public String showAddCompany(ModelMap model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "admin/addCompany";
    }

    @RequestMapping(value = "/admin/addCompany.htm", method = RequestMethod.POST)
    public String onaddCompanySubmit(@ModelAttribute("company") Company company, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            int result = this.adminService.addCompany(company);
            if (result == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addCompany.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                return "redirect:../admin/companyList.htm?&msg=msg.successfullyAddedCompany";
            }
        }

    }

    @RequestMapping(value = "/admin/companyList.htm", method = RequestMethod.GET)
    public String showUserListPage(ModelMap model) {
        model.addAttribute("companyList", this.adminService.getCompanyList(false));
        return "/admin/companyList";
    }

    @RequestMapping(value = "/admin/editCompany.htm", method = RequestMethod.GET)
    public String showEditWarehouseForm(@RequestParam(value = "companyId", required = true) int companyId, ModelMap model) {
        Company company = this.adminService.getcompanyBycompanyID(companyId);
        model.addAttribute("company", company);
        return "/admin/editCompany";
    }

    @RequestMapping(value = "/admin/editCompany.htm", method = RequestMethod.POST)
    public String onEditCompanySubmit(@ModelAttribute("company") Company company, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../admin/companyList.htm?msg=msg.actionCancelled";
        } else {
            int id = this.adminService.updateCompany(company);
            if (id == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/editCompany.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.companyUpdatedSuccessfully";
                return "redirect:../admin/companyList.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/admin/addWarehouseShipping.htm", method = RequestMethod.GET)
    public String showWarehouseShipping(ModelMap model) {
        List<Warehouse> warehouseList = this.warehouseService.getShippingWarehouseList(true);
        model.addAttribute("warehouseList", warehouseList);

        List<ShippingCriteria> shippingCriteriaList = this.adminService.getshippingCriteriaList();
        model.addAttribute("shippingCriteriaList", shippingCriteriaList);

        ShippingDetails shippingDetails = new ShippingDetails();
        model.addAttribute("shippingDetails", shippingDetails);

        return "/admin/addWarehouseShipping";
    }

    @RequestMapping(value = "/admin/addWarehouseShipping.htm", method = RequestMethod.POST)
    public String onWarehouseShippingSubmit(@ModelAttribute("shippingDetails") ShippingDetails shippingDetails, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        int criteriaId = ServletRequestUtils.getIntParameter(request, "criteriaId", 0);
        int warehouseId = ServletRequestUtils.getIntParameter(request, "warehouseId", 0);
        if (cancel != null) {
            return "redirect:../admin/warehouseList.htm?msg=msg.actionCancelled";
        } else {
            //System.out.println("shippingDetails MaxWeight: " + shippingDetails.getMaxValueWeight() + "shippingDetails MinWeight:" + shippingDetails.getMinValueWeight());


            int shippingDetailsId = this.adminService.addWarehouseShipping(shippingDetails, criteriaId, warehouseId);
            if (shippingDetailsId == 0) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../admin/addWarehouseShipping.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "msg.warehouseShippingAddedSuccessfully";
                return "redirect:warehouseList.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/admin/editWarehouseShipping.htm", method = RequestMethod.GET)
    public String showEditWarehouseShippingForm(@RequestParam(value = "shippingDetailsId", required = true) int warehouseShippingDetailsId, ModelMap model) {
        ShippingDetails shippingDetails = this.adminService.getWarehouseShippingDetailsByDetailsId(warehouseShippingDetailsId);
        model.addAttribute("shippingDetails", shippingDetails);
        return "/admin/editCompany";
    }
    
    
}
