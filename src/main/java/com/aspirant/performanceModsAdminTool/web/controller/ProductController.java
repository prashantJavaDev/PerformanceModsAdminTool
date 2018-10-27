/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.MarketplaceListingSkuDTO;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author shrutika
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService WarehouseService;

    @RequestMapping(value = "/product/addProduct.htm", method = RequestMethod.GET)
    public String addProduct(ModelMap model) {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();

        List<Manufacturer> manufacturerList = this.productService.getListOfManufacturer();
        model.addAttribute("manufacturerList", manufacturerList);

        List<MainCategory> mainCategoryList = applicationSession.getMainCategoryListActive();
        model.addAttribute("mainCategoryList", mainCategoryList);

        Product product = new Product();
        product.setActive(true);
        product.setReturnable(true);
        model.addAttribute("product", product);
        return "/product/addProduct";
    }

    @RequestMapping(value = "/product/addProduct.htm", method = RequestMethod.POST)
    public String onaddProductSubmit(@ModelAttribute("product") Product product, Errors errors, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            product = null;
            return "redirect:../product/listProduct.htm?msg=msg.actionCancelled";
        } else {
            try {
                int productId = this.productService.addProduct(product);
                return "redirect:../product/listProduct.htm?msg=msg.productAddedSuccessfully";
            } catch (Exception e) {
                String msg;
                if (e instanceof DataIntegrityViolationException) {
                    //String performanceModsMpn = this.productService.getperformanceModsMpnByManufacturerMpn(product.getperformanceModsMpn(),product.getManufacturer().getManufacturerId());
                    msg = "Product already exists with the same Manufacturer MPN : " + product.getManufacturerMpn();
                    return "redirect:../product/addProduct.htm?error=" + URLEncoder.encode(msg, "UTF-8");
                } else {
                    msg = "Sorry! your data not saved.";
                    LogUtils.systemLogger.info(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                    return "redirect:../product/listProduct.htm?error=" + URLEncoder.encode(msg, "UTF-8");
                }
            }
        }
    }

    @RequestMapping(value = "/product/listProduct.htm")
    public String showproductListPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String go = ServletRequestUtils.getStringParameter(request, "btnSubmit", null);
        int pageNo = 0;

        int productStatusId = ServletRequestUtils.getIntParameter(request, "productStatusId", 0);
        int manufacturerId = ServletRequestUtils.getIntParameter(request, "manufacturerId", 0);
        String performanceModsMpn = ServletRequestUtils.getStringParameter(request, "performanceModsMpn", null);
        String warehouseMpn = ServletRequestUtils.getStringParameter(request, "warehouseMPN", null);
        String productMpn = ServletRequestUtils.getStringParameter(request, "productMpn", null);
        String productName = ServletRequestUtils.getStringParameter(request, "productName", null);
        String startDate= null;
        String stopDate= null;

        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDate = ServletRequestUtils.getStringParameter(request, "startDate", df.format(date.getTime()));
        stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        if (go != null) {
            pageNo = 1;
        }

        int rowCount = this.productService.getProductListCount(productStatusId, manufacturerId, performanceModsMpn, productName, warehouseMpn, productMpn,startDate,stopDate);
        model.addAttribute("rowCount", rowCount);

        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);

        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);

        List<Product> productList = this.productService.getProductList(productStatusId, manufacturerId, performanceModsMpn, productName, pageNo, warehouseMpn, productMpn,startDate,stopDate);
        model.addAttribute("productList", productList);

        List<ProductStatus> productStatusList = ApplicationSession.getCurrent().getProductStatusListActive();
        model.addAttribute("productStatusList", productStatusList);

        model.addAttribute("productStatusId", productStatusId);
        model.addAttribute("manufacturerList", this.productService.getListOfManufacturer());
        model.addAttribute("manufacturerId", manufacturerId);
        model.addAttribute("performanceModsMpn", performanceModsMpn);
        model.addAttribute("productName", productName);
        model.addAttribute("warehouseMPN", warehouseMpn);
        model.addAttribute("productMpn", productMpn);
        model.addAttribute("startDate", startDate);
        model.addAttribute("stopDate", stopDate);
        return "/product/listProduct";
    }

    @RequestMapping(value = "/product/editProduct.htm", method = RequestMethod.GET)
    public String editProduct(@RequestParam("productId") int productId, ModelMap model) {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();

        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        model.addAttribute("mainCategoryList", applicationSession.getMainCategoryListActive());
        return "/product/editProduct";
    }

    @RequestMapping(value = "/product/editProduct.htm", method = RequestMethod.POST)
    public String oneditProductSubmit(@ModelAttribute("product") Product product, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            product = null;
            return "redirect:../product/listProduct.htm?msg=msg.actionCancelled";
        } else {
            this.productService.updateProduct(product);
            return "redirect:../product/listProduct.htm?msg=msg.productUpdatedSuccessfully";
        }
    }

    @RequestMapping(value = "/product/viewProduct.htm")
    public String viewProductInfoPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String performanceModsMpn = ServletRequestUtils.getStringParameter(request, "performanceModsMpn", "");

        List<ProductDetails> productList = this.productService.getProductListWarehouseWise(performanceModsMpn);
        model.addAttribute("productList", productList);

        List<MarketplaceListingSkuDTO> marketplaceSkuList = this.productService.getMarketplaceListingsAndSku(performanceModsMpn);
        model.addAttribute("marketplaceSkuList", marketplaceSkuList);

        Product productInfo = this.productService.getProductInfoByperformanceModsMpn(performanceModsMpn);
        model.addAttribute("productInfo", productInfo);

        model.addAttribute("performanceModsMpn", performanceModsMpn);
        return "/product/viewProduct";
    }

    @RequestMapping(value = "product/productListExcel.htm")
    public void getProductListExcelReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            int productStatusId = ServletRequestUtils.getIntParameter(request, "productStatusId", 0);
            int manufacturerId = ServletRequestUtils.getIntParameter(request, "manufacturerId", 0);
            String performanceModsMpn = ServletRequestUtils.getStringParameter(request, "performanceModsMpn", null);
            String warehouseMpn = ServletRequestUtils.getStringParameter(request, "warehouseMPN", null);
            String productMpn = ServletRequestUtils.getStringParameter(request, "productMpn", null);
            String productName = ServletRequestUtils.getStringParameter(request, "productName", null);
            List<Product> productList = this.productService.getProductListForExcel(productStatusId, manufacturerId, performanceModsMpn, productName, warehouseMpn, productMpn);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=ProductList-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "ProductList");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Product Name");
            headerRow.addCell("performanceMods MPN");
            headerRow.addCell("Manufacturer Name");
            headerRow.addCell("Model Number(MPN)");
            headerRow.addCell("MAP");
            headerRow.addCell("Weight");
            headerRow.addCell("UPC");
            headerRow.addCell("Warehouse MPN");
            headerRow.addCell("Warehouse Name");

            mySheet.addRow(headerRow);
            for (Product data : productList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getProductName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getperformanceModsMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturer().getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturerMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductMap(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getProductWeight(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getUpc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getWarehouseMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getWarehouseName(), POICell.TYPE_TEXT);

                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(io, GlobalConstants.TAG_SYSTEMLOG));
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

    @RequestMapping(value = "/product/lowCountProduct.htm", method = RequestMethod.GET)
    public String lowProductCountGet(ModelMap model) {

        List<Order> lowCountProducts = this.productService.getLowCountProductForDashbored(0);
        model.addAttribute("lowCountProducts", lowCountProducts);
        return "/product/lowCountProduct";
    }

    @RequestMapping(value = "/product/missingProductDataList.htm")
    public String productWithMissingDataGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        int pageNo = 0;
// 
//
        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

//
        int rowCount = this.productService.getMissingProductDataListCount();
        model.addAttribute("rowCount", rowCount);
//
        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);
//
        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);
        List<Product> productMissing = this.productService.getMissingProductDataList(pageNo);
        model.addAttribute("productMissing", productMissing);
        return "/product/missingProductDataList";
    }

    @RequestMapping(value = "/product/deleteProduct.htm", method = RequestMethod.GET)
    public String deleteProductGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return "/product/deleteProduct";
    }

    @RequestMapping(value = "/product/deleteProduct.htm")
    public String deleteProductPost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String performanceModsMpn = ServletRequestUtils.getStringParameter(request, "performanceModsMpn", null);
        List<Product> productToDelete = this.productService.getProductListForDelet(performanceModsMpn);
        model.addAttribute("productToDelete", productToDelete);
        return "/product/deleteProduct";

    }

    @RequestMapping(value = "/product/mapProduct.htm", method = RequestMethod.GET)
    public String mapProductGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "/product/mapProduct";

    }

    @RequestMapping(value = "/product/downloadProduct.htm")
    public String getProductDownloadForWebSitePost(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String go = ServletRequestUtils.getStringParameter(request, "btnSubmit", null);
        int pageNo = 0;
        int productStatusId = ServletRequestUtils.getIntParameter(request, "productStatusId", 0);
        String productName = ServletRequestUtils.getStringParameter(request, "productName", null);
        int manufacturerId = ServletRequestUtils.getIntParameter(request, "manufacturerId", 0);
        int mainCategoryId = ServletRequestUtils.getIntParameter(request, "mainCategoryId", 0);
        int subCategoryId = ServletRequestUtils.getIntParameter(request, "subCategoryId", 0);
        int childCategoryId = ServletRequestUtils.getIntParameter(request, "childCategoryId", 0);

        model.addAttribute("manufacturerList", this.productService.getListOfManufacturer());
        model.addAttribute("mainCategoryList", ApplicationSession.getCurrent().getMainCategoryListActive());
        model.addAttribute("productStatusList", ApplicationSession.getCurrent().getProductStatusListActive());
        model.addAttribute("subCategoryList", this.productService.getAllSubCategoryList());
        model.addAttribute("childCategoryList", this.productService.getAllChildCategoryList());

        pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        if (go != null) {
            pageNo = 1;
        }

        int rowCount = this.productService.getCountDownloadProductList(productStatusId, manufacturerId, productName, pageNo, mainCategoryId, subCategoryId, childCategoryId);
        model.addAttribute("rowCount", rowCount);

        int pageCount = (int) Math.ceil((float) rowCount / GlobalConstants.PAGE_SIZE);
        model.addAttribute("pageCount", pageCount);

        if (pageNo > rowCount) {
            pageNo = 1;
        }
        model.addAttribute("pageNo", pageNo);

        List<Product> productDownloadList = this.productService.getDownloadProductList(productStatusId, manufacturerId, productName, pageNo, mainCategoryId, subCategoryId, childCategoryId);
        model.addAttribute("productDownloadList", productDownloadList);

        model.addAttribute("productStatusId", productStatusId);
        model.addAttribute("manufacturerId", manufacturerId);
        model.addAttribute("productName", productName);
        model.addAttribute("mainCategoryId", mainCategoryId);
        model.addAttribute("subCategoryId", subCategoryId);
        model.addAttribute("childCategoryId", childCategoryId);
        return "/product/downloadProduct";
    }

    @RequestMapping(value = "product/productDownloadListExcel.htm")
    public void getProductDownloadListExcelReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
            int productStatusId = ServletRequestUtils.getIntParameter(request, "productStatusId", 0);
            String productName = ServletRequestUtils.getStringParameter(request, "productName", null);
            int manufacturerId = ServletRequestUtils.getIntParameter(request, "manufacturerId", 0);
            int mainCategoryId = ServletRequestUtils.getIntParameter(request, "mainCategoryId", 0);
            int subCategoryId = ServletRequestUtils.getIntParameter(request, "subCategoryId", 0);
            int childCategoryId = ServletRequestUtils.getIntParameter(request, "childCategoryId", 0);
            List<Product> productDownloadList = this.productService.getDownloadProductList(productStatusId, manufacturerId, productName, -1, mainCategoryId, subCategoryId, childCategoryId);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=ProductForWebsiteList-" + curDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "ProductForWebsiteList");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Product Name");
            headerRow.addCell("Manufacturer Name");
            headerRow.addCell("MPN");
            headerRow.addCell("Weight");
            headerRow.addCell("Category");
            headerRow.addCell("Sub-Category");
            headerRow.addCell("Child Category");
            headerRow.addCell("Short Desc");
            headerRow.addCell("Long Desc");
            headerRow.addCell("Small Image Url");
            headerRow.addCell("Large Image Url");

            mySheet.addRow(headerRow);
            for (Product data : productDownloadList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getProductName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturer().getManufacturerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getManufacturerMpn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getProductWeight(), POICell.TYPE_DECIMAL);
                dataRow.addCell(data.getMainCategory().getMainCategoryDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getSubCategory1().getSubCategoryDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getChildCategory().getChildCategoryDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getShortDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getLongDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getResizeImageUrl(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getLargeImageUrl1(), POICell.TYPE_TEXT);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(io, GlobalConstants.TAG_SYSTEMLOG));
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

}
