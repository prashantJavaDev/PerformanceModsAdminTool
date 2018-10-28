/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

import java.util.ArrayList;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author akil
 */
public class POIRow {

    private int rowType;
    private ArrayList<POICell> cells;
    public static final short HEADER_ROW = 1;
    public static final short DATA_ROW = 2;

    public POIRow(int rowType) {
        this.rowType = rowType;
        this.cells = new ArrayList<POICell>();
    }

    public POIRow() {
        rowType = DATA_ROW;
        this.cells = new ArrayList<POICell>();
    }

    public void addCell(Object value, short cellType, short align, int size) {
        POICell c = new POICell(value, cellType, align, size);
        this.cells.add(c);
    }

    public void addCell(Object value, short cellType, short align) {
        POICell c = new POICell(value, cellType, align);
        this.cells.add(c);
    }

    public void addCell(Object value, short cellType) {
        POICell c = new POICell(value, cellType);
        this.cells.add(c);
    }

    public void addCell(Object value) {
        POICell c = new POICell(value);
        this.cells.add(c);
    }

    public void addCell(Object value, boolean mergeRegion, CellRangeAddress cellRangeAddress) {
        POICell c = new POICell(value, mergeRegion, cellRangeAddress);
        this.cells.add(c);
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    public ArrayList<POICell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<POICell> cells) {
        this.cells = cells;
    }
}
