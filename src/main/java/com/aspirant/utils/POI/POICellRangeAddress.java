/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.utils.POI;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ubuntu
 */
public class POICellRangeAddress extends CellRangeAddress {

    private int firstRow;
    private int lastRow;
    private int firstColumn;
    private int lastColumn;

    public POICellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol) {
        super(firstRow, lastRow, firstCol, lastCol);
    }

    public POICellRangeAddress(RecordInputStream in) {
        super(in);
    }
}
