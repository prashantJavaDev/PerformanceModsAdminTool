/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author akil
 */
public class POIWorkSheet implements Serializable {

    private OutputStream out;
    private String title;
    private String sheetName;
    private boolean autoSizeColumns;
    private ArrayList<POIParameter> parameters;
    private ArrayList<POIRow> rows;
    boolean printTitle;
    private SXSSFWorkbook workbook;
    private CreationHelper createHelper;
    private Font headerFont;
    private Font bodyFont;
    private Font titleFont;
    private CellStyle styleTitle;
    private CellStyle styleHeader;
    private CellStyle styleBody;
    private CellStyle styleBodyDate;
    private CellStyle styleBodyDateTime;
    private CellStyle styleBodyTime;
    private CellStyle styleBodyDecimal;
    private CellStyle styleBodyPercentage;
    private CellStyle styleBodyInteger;
    private static final String IntegerFormat = "#,##0";
    private static final String DecimalFormat = "#,##0.00";
    private static final String PercFormat = "#0.0%";
    private String DateFormat = "dd/MM/yyyy";
    private String DateTimeFormat = "dd/MM/yyyy HH:mm:ss";
    private String TimeFormat = "HH:mm";

    public void setDateFormat(String DateFormat) {
        this.DateFormat = DateFormat;
        styleBodyDateTime.setDataFormat(createHelper.createDataFormat().getFormat(this.DateFormat));
    }

    public void setDateTimeFormat(String DateTimeFormat) {
        this.DateTimeFormat = DateTimeFormat;
        styleBodyDateTime.setDataFormat(createHelper.createDataFormat().getFormat(this.DateTimeFormat));
    }

    private void initializeWorkSheet() {
        this.workbook = new SXSSFWorkbook();
        createHelper = this.workbook.getCreationHelper();

        headerFont = this.workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(HSSFColor.WHITE.index);
        
        bodyFont = this.workbook.createFont();
        bodyFont.setFontName("Arial");
        bodyFont.setFontHeightInPoints((short) 10);

        titleFont = this.workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 14);

        styleTitle = this.workbook.createCellStyle();
        styleTitle.setFont(titleFont);

        styleHeader = this.workbook.createCellStyle();

        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

        styleHeader.setWrapText(true);
        styleHeader.setFont(headerFont);

        styleBody = this.workbook.createCellStyle();
        styleBody.setBorderBottom(BorderStyle.THIN);
        styleBody.setBorderTop(BorderStyle.THIN);
        styleBody.setBorderLeft(BorderStyle.THIN);
        styleBody.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBody.setWrapText(true);
        styleBody.setFont(bodyFont);

        styleBodyDate = this.workbook.createCellStyle();
        styleBodyDate.setBorderBottom(BorderStyle.THIN);
        styleBodyDate.setBorderTop(BorderStyle.THIN);
        styleBodyDate.setBorderLeft(BorderStyle.THIN);
        styleBodyDate.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyDate.setWrapText(true);
        styleBodyDate.setFont(bodyFont);
        styleBodyDate.setDataFormat(createHelper.createDataFormat().getFormat(this.DateFormat));

        styleBodyDateTime = this.workbook.createCellStyle();
        styleBodyDateTime.setBorderBottom(BorderStyle.THIN);
        styleBodyDateTime.setBorderTop(BorderStyle.THIN);
        styleBodyDateTime.setBorderLeft(BorderStyle.THIN);
        styleBodyDateTime.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyDateTime.setWrapText(true);
        styleBodyDateTime.setFont(bodyFont);
        styleBodyDateTime.setDataFormat(createHelper.createDataFormat().getFormat(this.DateTimeFormat));

        styleBodyTime = this.workbook.createCellStyle();
        styleBodyTime.setBorderBottom(BorderStyle.THIN);
        styleBodyTime.setBorderTop(BorderStyle.THIN);
        styleBodyTime.setBorderLeft(BorderStyle.THIN);
        styleBodyTime.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyTime.setWrapText(true);
        styleBodyTime.setFont(bodyFont);
        styleBodyTime.setDataFormat(createHelper.createDataFormat().getFormat(this.TimeFormat));

        styleBodyDecimal = this.workbook.createCellStyle();
        styleBodyDecimal.setBorderBottom(BorderStyle.THIN);
        styleBodyDecimal.setBorderTop(BorderStyle.THIN);
        styleBodyDecimal.setBorderLeft(BorderStyle.THIN);
        styleBodyDecimal.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyDecimal.setWrapText(true);
        styleBodyDecimal.setFont(bodyFont);
        styleBodyDecimal.setDataFormat(createHelper.createDataFormat().getFormat(this.DecimalFormat));

        styleBodyPercentage = this.workbook.createCellStyle();
        styleBodyPercentage.setBorderBottom(BorderStyle.THIN);
        styleBodyPercentage.setBorderTop(BorderStyle.THIN);
        styleBodyPercentage.setBorderLeft(BorderStyle.THIN);
        styleBodyPercentage.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyPercentage.setWrapText(true);
        styleBodyPercentage.setFont(bodyFont);
        styleBodyPercentage.setDataFormat(createHelper.createDataFormat().getFormat(this.PercFormat));

        styleBodyInteger = this.workbook.createCellStyle();
        styleBodyInteger.setBorderBottom(BorderStyle.THIN);
        styleBodyInteger.setBorderTop(BorderStyle.THIN);
        styleBodyInteger.setBorderLeft(BorderStyle.THIN);
        styleBodyInteger.setBorderRight(BorderStyle.THIN);
//        styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleBodyInteger.setWrapText(true);
        styleBodyInteger.setFont(bodyFont);
        styleBodyInteger.setDataFormat(createHelper.createDataFormat().getFormat(this.IntegerFormat));
    }

    public POIWorkSheet(OutputStream out) {
        this.out = out;
        printTitle = true;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.title = "Report";
        this.autoSizeColumns = true;
        initializeWorkSheet();
    }

    public POIWorkSheet(OutputStream out, String sheetName) {
        this.out = out;
        printTitle = true;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.sheetName = sheetName;
        this.title = "Report";
        this.autoSizeColumns = true;
        initializeWorkSheet();
    }

    public POIWorkSheet(OutputStream out, String sheetName, boolean autoSizeColumns) {
        this.out = out;
        printTitle = true;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.sheetName = sheetName;
        this.title = "Report";
        this.autoSizeColumns = autoSizeColumns;
        initializeWorkSheet();
    }

    public void addRow(POIRow r) {
        this.rows.add(r);
    }

    public void addParameter(String label, Object value, short valueType) {
        POIParameter p1 = new POIParameter(label, value, valueType);
        this.parameters.add(p1);
    }

    public void addParameter(String label, Object value) {
        POIParameter p1 = new POIParameter(label, value);
        this.parameters.add(p1);
    }

    public boolean isPrintTitle() {
        return printTitle;
    }

    public void setPrintTitle(boolean printTitle) {
        this.printTitle = printTitle;
    }

    public ArrayList<POIParameter> getParameters() {
        return parameters;
    }

    public ArrayList<POIRow> getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void writeWorkBook() throws IOException {
        if (getRows().size() > POIDefaults.DEFAULT_ROW_COUNT) {
            throw new IOException();
        } else {

            Sheet sheet = null;
            if (getSheetName() == null) {
                sheet = this.workbook.createSheet();
            } else {
                sheet = this.workbook.createSheet(getSheetName());
            }

            int rownum = 0;
            int cellnum = 0;
            Row row = null;
            Cell cell = null;
            if (isPrintTitle()) {
                row = sheet.createRow(rownum++);
                cell = row.createCell(cellnum++);
                cell.setCellStyle(this.styleTitle);
                cell.setCellValue(getTitle());
                rownum++;
                for (POIParameter p : getParameters()) {
                    cellnum = 0;
                    row = sheet.createRow(rownum++);
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle(this.styleBody);
                    cell.setCellValue(p.getLabel());
                    cell = row.createCell(cellnum++);
                    setStyleValue(cell, p.getValueType(), p.getValue());
                }
            }
            if (getParameters().size() > 0) { // leave one row after the parameters
                rownum++;
            }
//        int rCnt = 1;
            for (POIRow r : getRows()) {

                cellnum = 0;
                row = sheet.createRow(rownum++);
                for (POICell c : r.getCells()) {
                    if (r.getRowType() == POIRow.HEADER_ROW) {
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle(this.styleHeader);
                        cell.setCellValue((String) c.getValue());
                        if (c.isMergeRegion()) {
                            sheet.addMergedRegion(c.getCellRangeAddress());
                        }
                    } else {
                        cell = row.createCell(cellnum++);
                        setStyleValue(cell, c.getCellType(), c.getValue());
                    }
//                if (rCnt == getRows().size()) { // this is the last row
//                    if (this.autoSizeColumns) {
//                        sheet.autoSizeColumn(cellnum - 1);
//                        if (sheet.getColumnWidth(cellnum - 1) > POIDefaults.MAX_COLUMN_WIDTH) {
//                            sheet.setColumnWidth(cellnum - 1, POIDefaults.MAX_COLUMN_WIDTH);
//                        } else {
//                            sheet.setColumnWidth(cellnum - 1, sheet.getColumnWidth(cellnum - 1) + 3 * 256);
//                        }
//                    }
//                }
                }
            }
        }

//            rCnt++;
        try {
            //Write the workbook in file system
            workbook.write(this.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CellStyle getCellStyle(short valueType) {
        switch (valueType) {
            case POICell.TYPE_AUTO:
                return this.styleBody;
            case POICell.TYPE_TEXT:
                return this.styleBody;
            case POICell.TYPE_DECIMAL:
                return this.styleBodyDecimal;
            case POICell.TYPE_PERCENTAGE:
                return this.styleBodyDecimal;
            case POICell.TYPE_DATE:
                return this.styleBodyDate;
            case POICell.TYPE_DATETIME:
                return this.styleBodyDate;
            case POICell.TYPE_INTEGER:
                return this.styleBodyInteger;
            case POICell.TYPE_TIME:
                return this.styleBodyTime;
            case POICell.TYPE_CURRENCY_INT:
                return this.styleBodyInteger;
            case POICell.TYPE_CURRENCY_DBL:
                return this.styleBodyDecimal;
            default:
                return this.styleBody;
        }
    }

    private void setStyleValue(Cell cell, short cellType, Object cellValue) {
        switch (cellType) {
            case POICell.TYPE_AUTO:
                cell.setCellStyle(this.styleBody);
                if (cellValue != null) {
                    cell.setCellValue(cellValue.toString());
                }
                break;
            case POICell.TYPE_TEXT:
                cell.setCellStyle(this.styleBody);
                if (cellValue != null) {
                    cell.setCellValue(cellValue.toString());
                }
                break;
            case POICell.TYPE_DECIMAL:
                cell.setCellStyle(this.styleBodyDecimal);
                if (cellValue != null) {
                    if (cellValue instanceof Double) {
                        cell.setCellValue((Double) cellValue);
                    } else {
                        try {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } catch (Exception e1) {
                        }
                    }
                }
                break;
            case POICell.TYPE_PERCENTAGE:
                cell.setCellStyle(this.styleBodyPercentage);
                if (cellValue != null) {
                    if (cellValue instanceof Double) {
                        cell.setCellValue((Double) cellValue);
                    } else {
                        try {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } catch (Exception e2) {
                        }
                    }
                }
                break;
            case POICell.TYPE_DATE:
                cell.setCellStyle(this.styleBodyDate);
                if (cellValue != null) {
                    cell.setCellValue((Date) cellValue);
                }
                break;
            case POICell.TYPE_DATETIME:
                cell.setCellStyle(this.styleBodyDateTime);
                if (cellValue != null) {
                    cell.setCellValue((Date) cellValue);
                }
                break;
            case POICell.TYPE_INTEGER:
                cell.setCellStyle(this.styleBodyInteger);
                if (cellValue != null) {
                    if (cellValue instanceof Double) {
                        cell.setCellValue((Double) cellValue);
                    } else {
                        try {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } catch (Exception e3) {
                        }
                    }
                }
                break;
            case POICell.TYPE_CURRENCY_INT:
                cell.setCellStyle(this.styleBodyInteger);
                if (cellValue != null) {
                    if (cellValue instanceof Double) {
                        cell.setCellValue((Double) cellValue);
                    } else {
                        try {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } catch (Exception e3) {
                        }
                    }
                }
                break;
            case POICell.TYPE_CURRENCY_DBL:
                cell.setCellStyle(this.styleBodyDecimal);
                if (cellValue != null) {
                    if (cellValue instanceof Double) {
                        cell.setCellValue((Double) cellValue);
                    } else {
                        try {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } catch (Exception e1) {
                        }
                    }
                }
                break;
            case POICell.TYPE_TIME:
                cell.setCellStyle(this.styleBodyTime);
                if (cellValue != null) {
                    cell.setCellValue((Date) cellValue);
                }
                break;
            default:
                cell.setCellStyle(this.styleBody);
                if (cellValue != null) {
                    cell.setCellValue(cellValue.toString());
                }
                break;
        }
    }
}
