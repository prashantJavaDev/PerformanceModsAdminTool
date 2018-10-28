/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.InvoiceDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Invoice;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import jxl.Workbook;
import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pallavi
 */
@Repository
public class InvoiceDaoImpl implements InvoiceDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public void loadInvoiceFeedDataLocally(String path, int warehouseId) {

        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`temp_invoice`";
        this.jdbcTemplate.update(sql);
        //query load data from bulk order tracking csv file into tesy_temp_bulk_tracking
        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `tel_easy_admin_tool`.`temp_invoice` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`PO_NUMBER`, `MPN`,`COST`); ";
        this.jdbcTemplate.execute(sql);
        sql = "UPDATE temp_invoice ti SET ti.`WAREHOUSE_ID`=?";
        this.jdbcTemplate.update(sql, warehouseId);
        //query update data into tesy_order table  
        sql = "UPDATE temp_invoice ti LEFT JOIN tesy_current_warehouse_product tcwp ON tcwp.`WAREHOUSE_ID`=ti.`WAREHOUSE_ID`\n"
                + "SET ti.`INVOICE_DIFF`=(ti.`COST`- (tcwp.`PRICE`+ tcwp.`SHIPPING`)) WHERE tcwp.`MPN`=ti.`MPN`";
        this.jdbcTemplate.update(sql);

    }

    @Override
    public List<Invoice> invoiceDifference() {
        String sql = "SELECT ti.`PO_NUMBER`,tw.`WAREHOUSE_NAME`,ti.`MPN`,tcwp.`PRICE`,tcwp.`SHIPPING`,ti.`COST`,ti.`INVOICE_DIFF`\n"
                + "FROM temp_invoice ti \n"
                + "LEFT JOIN tesy_current_warehouse_product tcwp\n"
                + "ON tcwp.`WAREHOUSE_ID`=ti.`WAREHOUSE_ID`\n"
                + "LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=ti.`WAREHOUSE_ID`\n"
                + "WHERE tcwp.`MPN`=ti.`MPN` AND ti.`INVOICE_DIFF`!= 0;";

        return this.jdbcTemplate.query(sql, new RowMapper<Invoice>() {

            @Override
            public Invoice mapRow(ResultSet rs, int i) throws SQLException {
                Invoice invoice = new Invoice();
                invoice.setPoNumber(rs.getString("PO_NUMBER"));
                invoice.setMpn(rs.getString("MPN"));
                invoice.setInvoicePrice(rs.getDouble("COST"));
                invoice.setInvoiceDifferene(rs.getDouble("INVOICE_DIFF"));

                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
                invoice.setWarehouse(warehouse);

                CurrentWarehouseProduct cwp = new CurrentWarehouseProduct();
                cwp.setPrice(rs.getDouble("PRICE"));
                cwp.setShipping(rs.getDouble("SHIPPING"));
                invoice.setCwp(cwp);
                return invoice;
            }

        });

    }

    // this method read Excel file with extension .xslx
    @Override
    public void loadInvoiceFeedDataLocallyExcelXLSX(String path, int warehouseId) throws FileNotFoundException, IOException {

        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`temp_invoice`";
        this.jdbcTemplate.update(sql);
        FileInputStream file = new FileInputStream(new File(path));
        XSSFWorkbook workbook;
        workbook = new XSSFWorkbook(file);
        XSSFSheet mySheet = workbook.getSheetAt(0);
        Row row;
        for (int i = 1; i <= mySheet.getLastRowNum(); i++) {
            row = (Row) mySheet.getRow(i);

            String poNumber;
            if (row.getCell(0) == null) {
                poNumber = null;
            } else {
                poNumber = row.getCell(0).toString();
            }
            String mpn;
            if (row.getCell(1) == null) {
                mpn = null;
            } else {
                mpn = row.getCell(1).toString();
                String last2CharOfMpn = mpn.substring(mpn.length() - 2);
                if (last2CharOfMpn.equals(".0")) {
                    mpn = mpn.substring(0, mpn.length() - 2);
                }
            }
            String price;
            if (row.getCell(2) == null) {
                price = null;
            } else {
                price = row.getCell(2).toString();
            }
            Invoice invoice = new Invoice();
            invoice.setPoNumber(poNumber);
            invoice.setMpn(mpn);
            invoice.setInvoicePrice(Double.parseDouble(price));

            String sqlString = "INSERT INTO temp_invoice(`PO_NUMBER`,`MPN`,`COST`) VALUES(?,?,?)";
            this.jdbcTemplate.update(sqlString, invoice.getPoNumber(), invoice.getMpn(), invoice.getInvoicePrice());

        }
        sql = "UPDATE temp_invoice ti SET ti.`WAREHOUSE_ID`=?";
        this.jdbcTemplate.update(sql, warehouseId);
        //query update data into tesy_order table  
        sql = "UPDATE temp_invoice ti LEFT JOIN tesy_current_warehouse_product tcwp ON tcwp.`WAREHOUSE_ID`=ti.`WAREHOUSE_ID`\n"
                + "SET ti.`INVOICE_DIFF`=(ti.`COST`- (tcwp.`PRICE`+ tcwp.`SHIPPING`)) WHERE tcwp.`MPN`=ti.`MPN`";
        this.jdbcTemplate.update(sql);

    }

    //this method read Excel file with extension .xls
    @Override
    public void loadInvoiceFeedDataLocallyExcelXLS(String path, int warehouseId) throws FileNotFoundException, IOException {

        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`temp_invoice`";
        this.jdbcTemplate.update(sql);
        FileInputStream File = new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(File);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Row row;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = (Row) sheet.getRow(i);

            String poNumber;
            if (row.getCell(0) == null) {
                poNumber = null;
            } else {
                poNumber = row.getCell(0).toString();
            }
            String mpn;
            if (row.getCell(1) == null) {
                mpn = null;
            } else {
                mpn = row.getCell(1).toString();
                String last2CharOfMpn = mpn.substring(mpn.length() - 2);
                if (last2CharOfMpn.equals(".0")) {
                    mpn = mpn.substring(0, mpn.length() - 2);
                }
            }
            String price;
            if (row.getCell(2) == null) {
                price = null;
            } else {
                price = row.getCell(2).toString();
            }
            Invoice invoice = new Invoice();
            invoice.setPoNumber(poNumber);
            invoice.setMpn(mpn);
            invoice.setInvoicePrice(Double.parseDouble(price));

            String sqlString = "INSERT INTO temp_invoice(`PO_NUMBER`,`MPN`,`COST`) VALUES(?,?,?)";
            this.jdbcTemplate.update(sqlString, invoice.getPoNumber(), invoice.getMpn(), invoice.getInvoicePrice());
        }
        sql = "UPDATE temp_invoice ti SET ti.`WAREHOUSE_ID`=?";
        this.jdbcTemplate.update(sql, warehouseId);
        //query update data into tesy_order table  
        sql = "UPDATE temp_invoice ti LEFT JOIN tesy_current_warehouse_product tcwp ON tcwp.`WAREHOUSE_ID`=ti.`WAREHOUSE_ID`\n"
                + "SET ti.`INVOICE_DIFF`=(ti.`COST`- (tcwp.`PRICE`+ tcwp.`SHIPPING`)) WHERE tcwp.`MPN`=ti.`MPN`";
        this.jdbcTemplate.update(sql);
    }
}
