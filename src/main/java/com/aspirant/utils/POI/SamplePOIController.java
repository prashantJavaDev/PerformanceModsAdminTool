package com.aspirant.utils.POI;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package cc.altius.web.controller;

import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author akil
 */
//@Controller
public class SamplePOIController {

//    @RequestMapping(value = "samplePio")
    public String generateExcelEnrollment() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Enrollment");
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        Font bodyFont = workbook.createFont();
        bodyFont.setFontName("Arial");
        bodyFont.setFontHeightInPoints((short) 10);

        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setBorderBottom(BorderFormatting.BORDER_THIN);
        styleHeader.setBorderTop(BorderFormatting.BORDER_THIN);
        styleHeader.setBorderLeft(BorderFormatting.BORDER_THIN);
        styleHeader.setBorderRight(BorderFormatting.BORDER_THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleHeader.setWrapText(true);
        styleHeader.setFont(headerFont);

        CellStyle styleBody = workbook.createCellStyle();
        styleBody.setBorderBottom(BorderFormatting.BORDER_THIN);
        styleBody.setBorderTop(BorderFormatting.BORDER_THIN);
        styleBody.setBorderLeft(BorderFormatting.BORDER_THIN);
        styleBody.setBorderRight(BorderFormatting.BORDER_THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBody.setWrapText(true);
        styleBody.setFont(bodyFont);

        CellStyle styleBodyDate = workbook.createCellStyle();
        styleBodyDate.setBorderBottom(BorderFormatting.BORDER_THIN);
        styleBodyDate.setBorderTop(BorderFormatting.BORDER_THIN);
        styleBodyDate.setBorderLeft(BorderFormatting.BORDER_THIN);
        styleBodyDate.setBorderRight(BorderFormatting.BORDER_THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyDate.setWrapText(true);
        styleBodyDate.setFont(bodyFont);
        styleBodyDate.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

        CellStyle styleBodyDecimal = workbook.createCellStyle();
        styleBodyDecimal.setBorderBottom(BorderFormatting.BORDER_THIN);
        styleBodyDecimal.setBorderTop(BorderFormatting.BORDER_THIN);
        styleBodyDecimal.setBorderLeft(BorderFormatting.BORDER_THIN);
        styleBodyDecimal.setBorderRight(BorderFormatting.BORDER_THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyDecimal.setWrapText(true);
        styleBodyDecimal.setFont(bodyFont);
        styleBodyDecimal.setDataFormat(createHelper.createDataFormat().getFormat("#0.00"));

        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        Cell cell = null;
        int cellnum = 0;
        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Customer code");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleHeader);
        cell.setCellValue("CRM_NO1");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Enrollment date");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleHeader);
        cell.setCellValue("BabyShield");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Collection charges");
        // -----------
        row = sheet.createRow(rownum++);
        cellnum = 0;
        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleBody);
        cell.setCellValue("D0000258");
        sheet.autoSizeColumn(cellnum - 1);

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleBody);
        cell.setCellValue("130009500678");
        sheet.autoSizeColumn(cellnum - 1);

        cell = row.createCell(cellnum++);
        cell.setCellValue(DateUtils.getCurrentDateObject(DateUtils.IST));
        cell.setCellStyle(styleBodyDate);
        sheet.autoSizeColumn(cellnum - 1);

        cell = row.createCell(cellnum++);
        cell.setCellStyle(styleBody);
        cell.setCellValue(1);
        sheet.autoSizeColumn(cellnum - 1);

        cell = row.createCell(cellnum++);
        cell.setCellValue(19990.9901);
        cell.setCellStyle(styleBodyDecimal);
        sheet.autoSizeColumn(cellnum - 1);

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("/home/altius/lifecell/howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "samplePio";
    }

}
