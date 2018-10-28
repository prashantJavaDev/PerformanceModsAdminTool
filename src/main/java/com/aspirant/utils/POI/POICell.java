/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author akil
 */
public class POICell {

    public static final short TYPE_AUTO = 0;
    public static final short TYPE_TEXT = 1;
    public static final short TYPE_DECIMAL = 2;
    public static final short TYPE_PERCENTAGE = 3;
    public static final short TYPE_DATE = 4;
    public static final short TYPE_DATETIME = 5;
    public static final short TYPE_INTEGER = 6;
    public static final short TYPE_CURRENCY_INT = 7;
    public static final short TYPE_CURRENCY_DBL = 8;
    public static final short TYPE_TIME = 9;
    public static final short ALIGN_DEFAULT = 0;
    public static final short ALIGN_LEFT = 1;
    public static final short ALIGN_RIGHT = 2;
    public static final short ALIGN_CENTER = 3;
    public static final short SIZE_AUTO = 0;
    private Object value;
    private short cellType;
    private short align;
    private int size;
    private boolean mergeRegion;
    private CellRangeAddress cellRangeAddress;
    private String currencySymbol;

    public POICell() {
        this.cellType = TYPE_AUTO;
        this.size = SIZE_AUTO;
        this.align = ALIGN_DEFAULT;
    }

    public POICell(Object value) {
        this.cellType = TYPE_AUTO;
        this.size = SIZE_AUTO;
        this.align = ALIGN_DEFAULT;
        this.value = value;
    }

    public POICell(Object value, short cellType) {
        this.size = SIZE_AUTO;
        this.align = ALIGN_DEFAULT;
        this.value = value;
        this.cellType = cellType;
    }

    public POICell(Object value, short cellType, String currencySymbol) {
        this.size = SIZE_AUTO;
        this.align = ALIGN_RIGHT;
        this.value = value;
        this.cellType = cellType;
        this.currencySymbol = currencySymbol;
    }

    public POICell(Object value, short cellType, short align) {
        this.size = SIZE_AUTO;
        this.value = value;
        this.cellType = cellType;
        this.align = align;
    }

    public POICell(Object value, short cellType, short align, int size) {
        this.value = value;
        this.cellType = cellType;
        this.align = align;
        this.size = size;
    }

    public POICell(Object value, boolean mergeRegion, CellRangeAddress cellRangeAddress) {
        this.value = value;
        this.mergeRegion = mergeRegion;
        this.cellRangeAddress = cellRangeAddress;
    }

    public boolean isMergeRegion() {
        return mergeRegion;
    }

    public void setMergeRegion(boolean mergeRegion) {
        this.mergeRegion = mergeRegion;
    }

    public CellRangeAddress getCellRangeAddress() {
        return cellRangeAddress;
    }

    public void setCellRangeAddress(CellRangeAddress cellRangeAddress) {
        this.cellRangeAddress = cellRangeAddress;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public short getCellType() {
        return cellType;
    }

    public void setCellType(short cellType) {
        this.cellType = cellType;
    }

    public short getAlign() {
        return align;
    }

    public void setAlign(short align) {
        this.align = align;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
