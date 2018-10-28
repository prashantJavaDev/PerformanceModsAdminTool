/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

import java.util.ArrayList;

/**
 *
 * @author akil
 */
public class POIWorkBookSheet {

    private ArrayList<POIParameter> parameters;
    private ArrayList<POIRow> rows;
    boolean printTitle;
    private String title;
    private String sheetName;
    private boolean autoSizeColumns;

    public POIWorkBookSheet(String sheetName) {
        printTitle = true;
        this.sheetName = sheetName;
        this.autoSizeColumns = true;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.title = "Report";
    }

    public POIWorkBookSheet() {
        printTitle = true;
        this.autoSizeColumns = true;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.title = "Report";
    }

    public POIWorkBookSheet(String sheetName, boolean autoSizeColumns) {
        printTitle = true;
        this.sheetName = sheetName;
        this.autoSizeColumns = autoSizeColumns;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.title = "Report";
    }

    public POIWorkBookSheet(boolean autoSizeColumns) {
        printTitle = true;
        this.autoSizeColumns = autoSizeColumns;
        this.parameters = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.title = "Report";
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

    public boolean isAutoSizeColumns() {
        return autoSizeColumns;
    }
    
}
