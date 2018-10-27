/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.Invoice;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Pallavi
 */
public interface InvoiceDao { 
    /**
     * this method will load data into table of .csv file
     * @param path
     * @param warehouseId 
     */
    public void loadInvoiceFeedDataLocally(String path,int warehouseId); 
    /**
     * method return the difference between feeds and invoice uploaded file
     * @return 
     */
    public List<Invoice> invoiceDifference();
    /**
     * this method will load data into table of .XLSX file
     * @param path
     * @param warehouseId
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadInvoiceFeedDataLocallyExcelXLSX(String path,int warehouseId) throws FileNotFoundException, IOException;
    /**
     * this method will load data into table of .xls file
     * @param path
     * @param warehouseId
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadInvoiceFeedDataLocallyExcelXLS(String path,int warehouseId) throws FileNotFoundException, IOException;
}
