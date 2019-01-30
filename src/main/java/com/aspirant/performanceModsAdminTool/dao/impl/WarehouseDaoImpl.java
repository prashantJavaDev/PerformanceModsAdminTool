
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.WarehouseDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.mapper.BadDataDTORowMapper;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.model.rowmapper.FeedUploadRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.WarehouseRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shrutika
 */
@Repository
public class WarehouseDaoImpl implements WarehouseDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Warehouse> getWarehouseList(boolean active) {
        String sql = "SELECT tw.* FROM pm_warehouse tw";
        if (active) {
            sql += " WHERE tw.ACTIVE";
        }
        sql += " ORDER BY tw.`LAST_MODIFIED_DATE` DESC";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new WarehouseRowMapper());
    }

    @Override
    @Transactional
    public int saveAndUpdateFeed(int warehouseId) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String curDate1 = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        String sqlString;
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        //Insert into pm_warehouse_feed table 
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleJdbcInsert feedInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_warehouse_feed").usingGeneratedKeyColumns("FEED_ID");

        params.put("WAREHOUSE_ID", warehouseId);
        params.put("FEED_DATE", curDate1);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser);

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into pm_warehouse_feed :", params, GlobalConstants.TAG_SYSTEMLOG));
        int feedId = feedInsert.executeAndReturnKey(params).intValue();
        params.clear();

        //insert all new products into product table
        sqlString = "INSERT INTO pm_product"
                + " SELECT NULL,NULL,tt.MANUFACTURER_ID,tt.MPN,NULL,"
                + " UPPER(CONCAT('PM-', tm.`MANUFACTURER_CODE`,'-',tt.`MPN`)),tt.MAP,tt.MSRP,tt.WEIGHT,tt.ESTIMATED_SHIP_WEIGHT,tt.`LENGTH`,tt.`WIDTH`,tt.`HEIGHT`,tt.UPC,NULL,NULL,NULL,NULL,NULL,NULL,NULL,"
                + " tt.SHORT_DESC,tt.LONG_DESC,NULL,NULL,tt.RESIZE_IMAGE_URL,NULL,NULL,NULL,:createdDate,:createdBy,:lastModifiedDate,:lastModifiedBy,:active,:productStatusId"
                + " FROM temp_table tt"
                + " LEFT JOIN pm_manufacturer tm ON tm.`MANUFACTURER_ID`=tt.`MANUFACTURER_ID`"
                + " WHERE tt.`STATUS` AND tt.`PRODUCT_STATUS`=1";

        params.put("createdDate", curDate);
        params.put("createdBy", curUser);
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser);
        params.put("active", 1);
        params.put("productStatusId", 2);

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
        int result = nm.update(sqlString, params);

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result + " Products inserted", GlobalConstants.TAG_SYSTEMLOG));
        params.clear();

        //insert large images into pm_product_image
        sqlString = "INSERT INTO pm_product_image"
                + " SELECT tp.`PRODUCT_ID`,tt.LARGE_IMAGE_URL,1 FROM temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " WHERE tt.STATUS AND tt.`PRODUCT_STATUS`=1 AND tt.`LARGE_IMAGE_URL`!='' AND tt.`LARGE_IMAGE_URL`IS NOT NULL";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        int largeImages = jdbcTemplate.update(sqlString);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+largeImages + " Product images inserted", GlobalConstants.TAG_SYSTEMLOG));

        //insert mapping for different warehouse MPN to unique performanceModsMpn for single product which is new
        sqlString = "INSERT INTO pm_warehouse_product_mpn"
                + " SELECT ?,tp.`PRODUCT_ID`,tt.`WAREHOUSE_IDENTIFICATION_NO` FROM temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " WHERE tt.`STATUS` AND (tt.`PRODUCT_STATUS` =1 OR tt.`PRODUCT_STATUS`=2)";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{warehouseId}, GlobalConstants.TAG_SYSTEMLOG));
        int mapping = jdbcTemplate.update(sqlString, warehouseId);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+mapping + " Product mapping inserted", GlobalConstants.TAG_SYSTEMLOG));

        //update product table if existing product
        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`MANUFACTURER_MPN`=tt.`MPN` WHERE (tp.`MANUFACTURER_MPN`='' OR tp.`MANUFACTURER_MPN` IS NULL) "
                + " AND tt.`MPN`!='' AND tt.`MPN` IS NOT NULL AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`MAP`=tt.`MAP`"
                + " WHERE tp.`MAP`=0.00 AND tt.`MAP` IS NOT NULL AND tt.`MAP`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`MSRP`=tt.`MSRP`"
                + " WHERE tp.`MSRP`=0.00 AND tt.`MSRP` IS NOT NULL AND tt.`MSRP`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`WEIGHT`=tt.`WEIGHT`"
                + " WHERE tp.`WEIGHT`=0.00 AND tt.`WEIGHT` IS NOT NULL AND tt.`WEIGHT`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`EST_SHIPPING_WT`=tt.`ESTIMATED_SHIP_WEIGHT`"
                + " WHERE tp.`EST_SHIPPING_WT`=0.00 AND tt.`ESTIMATED_SHIP_WEIGHT` IS NOT NULL AND tt.`ESTIMATED_SHIP_WEIGHT`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`LENGTH`=tt.`LENGTH`"
                + " WHERE tp.`LENGTH`=0.00 AND tt.`LENGTH` IS NOT NULL AND tt.`LENGTH`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`WIDTH`=tt.`WIDTH`"
                + " WHERE tp.`WIDTH`=0.00 AND tt.`WIDTH` IS NOT NULL AND tt.`WIDTH`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`HEIGHT`=tt.`HEIGHT`"
                + " WHERE tp.`HEIGHT`=0.00 AND tt.`HEIGHT` IS NOT NULL AND tt.`HEIGHT`!='0.00' AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`UPC`=tt.`UPC` WHERE (tp.`UPC`='' OR tp.`UPC` IS NULL) "
                + " AND tt.`UPC`!='' AND tt.`UPC` IS NOT NULL AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`SHORT_DESC`=tt.`SHORT_DESC` WHERE (tp.`SHORT_DESC`='' OR tp.`SHORT_DESC` IS NULL) "
                + " AND tt.`SHORT_DESC`!='' AND tt.`SHORT_DESC` IS NOT NULL AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`LONG_DESC`=tt.`LONG_DESC` WHERE (tp.`LONG_DESC`='' OR tp.`LONG_DESC` IS NULL) "
                + " AND tt.`LONG_DESC`!='' AND tt.`LONG_DESC` IS NOT NULL AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " SET tp.`RESIZE_IMAGE_URL`=tt.`RESIZE_IMAGE_URL` WHERE (tp.`RESIZE_IMAGE_URL`='' OR tp.`RESIZE_IMAGE_URL` IS NULL) "
                + " AND tt.`RESIZE_IMAGE_URL`!='' AND tt.`RESIZE_IMAGE_URL` IS NOT NULL AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`STATUS`";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        jdbcTemplate.update(sqlString);

        sqlString = "INSERT INTO pm_product_image"
                + " SELECT tp.`PRODUCT_ID`,tt.LARGE_IMAGE_URL,1 FROM temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " WHERE tp.`PRODUCT_ID` NOT IN (SELECT tpi.`PRODUCT_ID` FROM pm_product_image tpi)"
                + " AND tt.STATUS AND tt.`PRODUCT_STATUS` IN (2,3) AND tt.`LARGE_IMAGE_URL`!='' AND tt.`LARGE_IMAGE_URL`IS NOT NULL";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        int images = jdbcTemplate.update(sqlString);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+images + " Product images updated", GlobalConstants.TAG_SYSTEMLOG));

        // Delete from pm_current_warehouse_product
//        sqlString = "DELETE FROM pm_current_warehouse_product WHERE WAREHOUSE_ID=?";
//       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{warehouseId}, GlobalConstants.TAG_SYSTEMLOG));
//        this.jdbcTemplate.update(sqlString, warehouseId);
        //insert into pm_current_warehouse_product table only todays data
        sqlString = "REPLACE INTO pm_current_warehouse_product"
                + " SELECT tp.PRODUCT_ID,:warehouseId,:feedId,tp.`MANUFACTURER_MPN`,tt.`PRICE`,tt.`QUANTITY`,COALESCE(tc.CONDITION_ID,1),"
                + " tt.`WAREHOUSE_IDENTIFICATION_NO`,COALESCE(tt.`SHIPPING`,0),ROUND((tt.`PRICE`+COALESCE(tt.`SHIPPING`,0)),2),:createdDate,:createdBy,0,:createdDate,:createdBy"
                + " FROM temp_table tt"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " LEFT JOIN pm_condition tc ON tc.CONDITION_DESC=UPPER(tt.CONDITION) "
                + " WHERE tt.`STATUS`";

        params.put("warehouseId", warehouseId);
        params.put("feedId", feedId);
        params.put("createdDate", curDate);
        params.put("createdBy", curUser);

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
        int result1 = nm.update(sqlString, params);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result1 + " Product inserted in current warehouses", GlobalConstants.TAG_SYSTEMLOG));
        params.clear();

        if (result1 > 0) {
            //append data into pm_warehouse_feed_data table daily
            sqlString = "INSERT INTO pm_warehouse_feed_data"
                    + " SELECT NULL,:feedId,:warehouseId,tp.PRODUCT_ID,tp.`MANUFACTURER_ID`,NULL,tp.`ADMIN_TOOL_MPN`,tt.MAP,tt.MSRP,"
                    + " tt.PRICE,tt.QUANTITY,COALESCE(tc.CONDITION_ID,1),tt.WAREHOUSE_IDENTIFICATION_NO,"
                    + " tt.WEIGHT,tt.`ESTIMATED_SHIP_WEIGHT`,tt.LENGTH,tt.WIDTH,tt.HEIGHT,tt.UPC,tt.`SHIPPING`,:createdDate,:createdBy,NULL"
                    + " FROM temp_table tt"
                    + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tt.`MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                    + " LEFT JOIN pm_condition tc ON tc.CONDITION_DESC=UPPER(tt.CONDITION)"
                    + " WHERE tt.`STATUS`";

            params.put("feedId", feedId);
            params.put("warehouseId", warehouseId);
            params.put("createdDate", curDate);
            params.put("createdBy", curUser);

            // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
            int result2 = nm.update(sqlString, params);
            // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result2 + " Product inserted in warehouse feed data", GlobalConstants.TAG_SYSTEMLOG));
        }
        return result1;
    }
//    new performanceMods
//    :ORIGINAL METHOD FOR CSV UPLOAD , DO NOT CHANGE THIS ORIGINAL CODE

    @Override
    //@Transactional
    public List<BadDataDTO> loadDataLocally(String path, int warehouseId) {
        String sql = "TRUNCATE TABLE `performance_mods`.`temp_table`";
        this.jdbcTemplate.update(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate temp_table done.", GlobalConstants.TAG_SYSTEMLOG));
        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MANUFACTURER_NAME`, `MPN`, `MAP`, `MSRP`, `PRICE`, `QUANTITY`, `CONDITION`, `WAREHOUSE_IDENTIFICATION_NO`, `WEIGHT`, `ESTIMATED_SHIP_WEIGHT`, `LENGTH`, `WIDTH`, `HEIGHT`, `UPC`, `SHORT_DESC`, `LONG_DESC`, `RESIZE_IMAGE_URL`, `LARGE_IMAGE_URL`, `SHIPPING`) ";
        this.jdbcTemplate.execute(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", G  lobalConstants.TAG_SYSTEMLOG));

//        if (extension.equals("csv")) {
//        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MANUFACTURER_NAME`, `MPN`, `MAP`, `MSRP`, `PRICE`, `QUANTITY`, `CONDITION`, `WAREHOUSE_IDENTIFICATION_NO`, `WEIGHT`, `ESTIMATED_SHIP_WEIGHT`, `LENGTH`, `WIDTH`, `HEIGHT`, `UPC`, `SHORT_DESC`, `LONG_DESC`, `RESIZE_IMAGE_URL`, `LARGE_IMAGE_URL`, `SHIPPING`) ";
//        this.jdbcTemplate.execute(sql);
//       // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));
//        } else if (extension.equals("xlsx")) {
//
//            FileInputStream file = new FileInputStream(new File(path));
//            XSSFWorkbook workbook;
//            workbook = new XSSFWorkbook(file);
//            XSSFSheet mySheet = workbook.getSheetAt(0);
//            Row row;
//            for (int i = 1; i <= mySheet.getLastRowNum(); i++) {
//                row = (Row) mySheet.getRow(i);
//                String manufacturerName;
//                if (row.getCell(0) == null) {
//                    manufacturerName = null;
//                } else {
//                    manufacturerName = row.getCell(0).toString();
//                }
//                String productMpn;
//                if (row.getCell(1) == null) {
//                    productMpn = null;
//                } else {
//                    productMpn = row.getCell(1).toString();
//                    String last2CharOfPM = productMpn.substring(productMpn.length() - 2);
//                    if (last2CharOfPM.equals(".0")) {
//                        productMpn = productMpn.substring(0, productMpn.length() - 2);
//                    }
//                }
//                String productMap;
//                if (row.getCell(2) == null) {
//                    productMap = null;
//                } else {
//                    productMap = row.getCell(2).toString();
//                }
//                String productMsrp;
//                if (row.getCell(3) == null) {
//                    productMsrp = null;
//                } else {
//                    productMsrp = row.getCell(3).toString();
//                }
//                String productPrice;
//                if (row.getCell(4) == null) {
//                    productPrice = null;
//                } else {
//                    productPrice = row.getCell(4).toString();
//                }
//                String productQuantity;
//                if (row.getCell(5) == null) {
//                    productQuantity = null;
//                } else {
//                    productQuantity = row.getCell(5).toString();
//                    String last2CharOfPQ = productQuantity.substring(productQuantity.length() - 2);
//                    if (last2CharOfPQ.equals(".0")) {
//                        productQuantity = productQuantity.substring(0, productQuantity.length() - 2);
//                    }
//
//                }
//                String productCondition;
//                if (row.getCell(6) == null) {
//                    productCondition = null;
//                } else {
//                    productCondition = row.getCell(6).toString();
//                }
//                String warehouseIdentificationNo;
//                if (row.getCell(7) == null) {
//                    warehouseIdentificationNo = null;
//                } else {
//                    warehouseIdentificationNo = row.getCell(7).toString();
//                    String last2CharOfWIN = warehouseIdentificationNo.substring(warehouseIdentificationNo.length() - 2);
//                    if (last2CharOfWIN.equals(".0")) {
//                        warehouseIdentificationNo = warehouseIdentificationNo.substring(0, warehouseIdentificationNo.length() - 2);
//                    }
//                }
//                String productWeight;
//                if (row.getCell(8) == null) {
//                    productWeight = null;
//                } else {
//                    productWeight = row.getCell(8).toString();
//                }
//                String estimatedShipWeight;
//                if (row.getCell(9) == null) {
//                    estimatedShipWeight = null;
//                } else {
//                    estimatedShipWeight = row.getCell(9).toString();
//                }
//                String productLength;
//                if (row.getCell(10) == null) {
//                    productLength = null;
//                } else {
//                    productLength = row.getCell(10).toString();
//                }
//                String productWidth;
//                if (row.getCell(11) == null) {
//                    productWidth = null;
//                } else {
//                    productWidth = row.getCell(11).toString();
//                }
//                String productHeight;
//                if (row.getCell(12) == null) {
//                    productHeight = null;
//                } else {
//                    productHeight = row.getCell(12).toString();
//                }
//                String upc;
//                if (row.getCell(13) == null) {
//                    upc = null;
//                } else {
//                    upc = row.getCell(13).toString();
//                    String last2CharOfUPC = upc.substring(upc.length() - 2);
//                    if (last2CharOfUPC.equals(".0")) {
//                        upc = upc.substring(0, upc.length() - 2);
//                    }
//                }
//                String shortDesc;
//                if (row.getCell(14) == null) {
//                    shortDesc = null;
//                } else {
//                    shortDesc = row.getCell(14).toString();
//                }
//                String longDesc;
//                if (row.getCell(15) == null) {
//                    longDesc = null;
//                } else {
//                    longDesc = row.getCell(15).toString();
//                }
//                String resizeImageUrl;
//                if (row.getCell(16) == null) {
//                    resizeImageUrl = null;
//                } else {
//                    resizeImageUrl = row.getCell(16).toString();
//                }
//                String largeImageUrl;
//                if (row.getCell(17) == null) {
//                    largeImageUrl = null;
//                } else {
//                    largeImageUrl = row.getCell(17).toString();
//                }
//                String shipping;
//                if (row.getCell(18) == null) {
//                    shipping = null;
//                } else {
//                    shipping = row.getCell(18).toString();
//                }
//                FeedUpload f = new FeedUpload();
//                f.setManufacturerName(manufacturerName);
//                f.setProductMpn(productMpn);
//                f.setProductMap(productMap);
//                f.setProductMsrp(productMsrp);
//                f.setProductPrice(productPrice);
//                f.setProductQuantity(productQuantity);
//                f.setProductCondition(productCondition);
//                f.setWarehouseIdentificationNo(warehouseIdentificationNo);
//                f.setProductWeight(productWeight);
//                f.setEstimatedShipWeight(estimatedShipWeight);
//                f.setProductLength(productLength);
//                f.setProductWidth(productWidth);
//                f.setProductHeight(productHeight);
//                f.setUpc(upc);
//                f.setShortDesc(shortDesc);
//                f.setLongDesc(longDesc);
//
//                f.setResizeImageUrl(resizeImageUrl);
//                f.setLargeImageUrl(largeImageUrl);
//                f.setShipping(shipping);
//                try{
//
//                String sqlString = "INSERT INTO temp_table(`MANUFACTURER_NAME`,`MPN`,`MAP`,`MSRP`,`PRICE`,`QUANTITY`,`CONDITION`,`WAREHOUSE_IDENTIFICATION_NO`,`WEIGHT`,`ESTIMATED_SHIP_WEIGHT`,`LENGTH`,`WIDTH`,`HEIGHT`,`UPC`,`SHORT_DESC`,`LONG_DESC`,`RESIZE_IMAGE_URL`,`LARGE_IMAGE_URL`,`SHIPPING`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                this.jdbcTemplate.update(sqlString, f.getManufacturerName(),
//                        f.getProductMpn(),
//                        f.getProductMap(),
//                        f.getProductMsrp(),
//                        f.getProductPrice(),
//                        f.getProductQuantity(),
//                        f.getProductCondition(),
//                        f.getWarehouseIdentificationNo(),
//                        f.getProductWeight(),
//                        f.getEstimatedShipWeight(),
//                        f.getProductLength(),
//                        f.getProductWidth(),
//                        f.getProductHeight(),
//                        f.getUpc(),
//                        f.getShortDesc(),
//                        f.getLongDesc(),
//                        f.getResizeImageUrl(),
//                        f.getLargeImageUrl(),
//                        f.getShipping());
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//                
//            }
//
//        } else if (extension.equals("xls")) {
//
//            FileInputStream File = new FileInputStream(new File(path));
//            HSSFWorkbook workbook = new HSSFWorkbook(File);
//            HSSFSheet mySheet = workbook.getSheetAt(0);
//            Row row;
//            for (int i = 1; i <= mySheet.getLastRowNum(); i++) {
//                row = (Row) mySheet.getRow(i);
//                String manufacturerName;
//                if (row.getCell(0) == null) {
//                    manufacturerName = null;
//                } else {
//                    manufacturerName = row.getCell(0).toString();
//                }
//                String productMpn;
//                if (row.getCell(1) == null) {
//                    productMpn = null;
//                } else {
//                    productMpn = row.getCell(1).toString();
//                    String last2CharOfPM = productMpn.substring(productMpn.length() - 2);
//                    if (last2CharOfPM.equals(".0")) {
//                        productMpn = productMpn.substring(0, productMpn.length() - 2);
//                    }
//                }
//                String productMap;
//                if (row.getCell(2) == null) {
//                    productMap = null;
//                } else {
//                    productMap = row.getCell(2).toString();
//                }
//                String productMsrp;
//                if (row.getCell(3) == null) {
//                    productMsrp = null;
//                } else {
//                    productMsrp = row.getCell(3).toString();
//                }
//                String productPrice;
//                if (row.getCell(4) == null) {
//                    productPrice = null;
//                } else {
//                    productPrice = row.getCell(4).toString();
//                }
//                String productQuantity;
//                if (row.getCell(5) == null) {
//                    productQuantity = null;
//                } else {
//                    productQuantity = row.getCell(5).toString();
//                    String last2CharOfPQ = productQuantity.substring(productQuantity.length() - 2);
//                    if (last2CharOfPQ.equals(".0")) {
//                        productQuantity = productQuantity.substring(0, productQuantity.length() - 2);
//                    }
//
//                }
//                String productCondition;
//                if (row.getCell(6) == null) {
//                    productCondition = null;
//                } else {
//                    productCondition = row.getCell(6).toString();
//                }
//                String warehouseIdentificationNo;
//                if (row.getCell(7) == null) {
//                    warehouseIdentificationNo = null;
//                } else {
//                    warehouseIdentificationNo = row.getCell(7).toString();
//                    String last2CharOfWIN = warehouseIdentificationNo.substring(warehouseIdentificationNo.length() - 2);
//                    if (last2CharOfWIN.equals(".0")) {
//                        warehouseIdentificationNo = warehouseIdentificationNo.substring(0, warehouseIdentificationNo.length() - 2);
//                    }
//                }
//                String productWeight;
//                if (row.getCell(8) == null) {
//                    productWeight = null;
//                } else {
//                    productWeight = row.getCell(8).toString();
//                }
//                String estimatedShipWeight;
//                if (row.getCell(9) == null) {
//                    estimatedShipWeight = null;
//                } else {
//                    estimatedShipWeight = row.getCell(9).toString();
//                }
//                String productLength;
//                if (row.getCell(10) == null) {
//                    productLength = null;
//                } else {
//                    productLength = row.getCell(10).toString();
//                }
//                String productWidth;
//                if (row.getCell(11) == null) {
//                    productWidth = null;
//                } else {
//                    productWidth = row.getCell(11).toString();
//                }
//                String productHeight;
//                if (row.getCell(12) == null) {
//                    productHeight = null;
//                } else {
//                    productHeight = row.getCell(12).toString();
//                }
//                String upc;
//                if (row.getCell(13) == null) {
//                    upc = null;
//                } else {
//                    upc = row.getCell(13).toString();
//                    String last2CharOfUPC = upc.substring(upc.length() - 2);
//                    if (last2CharOfUPC.equals(".0")) {
//                        upc = upc.substring(0, upc.length() - 2);
//                    }
//                }
//                String shortDesc;
//                if (row.getCell(14) == null) {
//                    shortDesc = null;
//                } else {
//                    shortDesc = row.getCell(14).toString();
//                }
//                String longDesc;
//                if (row.getCell(15) == null) {
//                    longDesc = null;
//                } else {
//                    longDesc = row.getCell(15).toString();
//                }
//                String resizeImageUrl;
//                if (row.getCell(16) == null) {
//                    resizeImageUrl = null;
//                } else {
//                    resizeImageUrl = row.getCell(16).toString();
//                }
//                String largeImageUrl;
//                if (row.getCell(17) == null) {
//                    largeImageUrl = null;
//                } else {
//                    largeImageUrl = row.getCell(17).toString();
//                }
//                String shipping;
//                if (row.getCell(18) == null) {
//                    shipping = null;
//                } else {
//                    shipping = row.getCell(18).toString();
//                }
//                FeedUpload f = new FeedUpload();
//                f.setManufacturerName(manufacturerName);
//                f.setProductMpn(productMpn);
//                f.setProductMap(productMap);
//                f.setProductMsrp(productMsrp);
//                f.setProductPrice(productPrice);
//                f.setProductQuantity(productQuantity);
//                f.setProductCondition(productCondition);
//                f.setWarehouseIdentificationNo(warehouseIdentificationNo);
//                f.setProductWeight(productWeight);
//                f.setEstimatedShipWeight(estimatedShipWeight);
//                f.setProductLength(productLength);
//                f.setProductWidth(productWidth);
//                f.setProductHeight(productHeight);
//                f.setUpc(upc);
//                f.setShortDesc(shortDesc);
//                f.setLongDesc(longDesc);
//
//                f.setResizeImageUrl(resizeImageUrl);
//                f.setLargeImageUrl(largeImageUrl);
//                f.setShipping(shipping);
//                try{
//
//                String sqlString = "INSERT INTO temp_table(`MANUFACTURER_NAME`,`MPN`,`MAP`,`MSRP`,`PRICE`,`QUANTITY`,`CONDITION`,`WAREHOUSE_IDENTIFICATION_NO`,`WEIGHT`,`ESTIMATED_SHIP_WEIGHT`,`LENGTH`,`WIDTH`,`HEIGHT`,`UPC`,`SHORT_DESC`,`LONG_DESC`,`RESIZE_IMAGE_URL`,`LARGE_IMAGE_URL`,`SHIPPING`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                 
//                
//                this.jdbcTemplate.update(sqlString, f.getManufacturerName(),
//                        f.getProductMpn(),
//                        f.getProductMap(),
//                        f.getProductMsrp(),
//                        f.getProductPrice(),
//                        f.getProductQuantity(),
//                        f.getProductCondition(),
//                        f.getWarehouseIdentificationNo(),
//                        f.getProductWeight(),
//                        f.getEstimatedShipWeight(),
//                        f.getProductLength(),
//                        f.getProductWidth(),
//                        f.getProductHeight(),
//                        f.getUpc(),
//                        f.getShortDesc(),
//                        f.getLongDesc(),
//                        f.getResizeImageUrl(),
//                        f.getLargeImageUrl(),
//                        f.getShipping());
//                }catch(Exception e)
//                {
//                    e.printStackTrace();
//                }                        
//            }
//
//        }
//>>>>>>> .r209
        //replace all single quotes
        sql = "UPDATE temp_table tt SET tt.`MANUFACTURER_NAME`=TRIM(REPLACE(tt.`MANUFACTURER_NAME`,'\'','')),tt.`MPN`=TRIM(REPLACE(tt.`MPN`,'\'','')),"
                + " tt.`MAP`=TRIM(REPLACE(tt.`MAP`,'\'','')),tt.`MSRP`=TRIM(REPLACE(tt.`MSRP`,'\'','')),tt.`PRICE`=TRIM(REPLACE(tt.`PRICE`,'\'','')),"
                + " tt.`QUANTITY`=TRIM(REPLACE(tt.`QUANTITY`,'\'','')),tt.`CONDITION`=TRIM(REPLACE(tt.`CONDITION`,'\'','')),"
                + " tt.`WAREHOUSE_IDENTIFICATION_NO`=TRIM(REPLACE(tt.`WAREHOUSE_IDENTIFICATION_NO`,'\'','')),tt.`WEIGHT`=TRIM(REPLACE(tt.`WEIGHT`,'\'','')),"
                + " tt.`ESTIMATED_SHIP_WEIGHT`=TRIM(REPLACE(tt.`ESTIMATED_SHIP_WEIGHT`,'\'','')),tt.`LENGTH`=TRIM(REPLACE(tt.`LENGTH`,'\'','')),"
                + " tt.`WIDTH`=TRIM(REPLACE(tt.`WIDTH`,'\'','')),tt.`HEIGHT`=TRIM(REPLACE(tt.`HEIGHT`,'\'','')),tt.`UPC`=TRIM(REPLACE(tt.`UPC`,'\'','')),"
                + " tt.`SHORT_DESC`=TRIM(REPLACE(tt.`SHORT_DESC`,'\'','')),tt.`LONG_DESC`=TRIM(REPLACE(tt.`LONG_DESC`,'\'','')),"
                + " tt.`RESIZE_IMAGE_URL`=TRIM(REPLACE(tt.`RESIZE_IMAGE_URL`,'\'','')),tt.`LARGE_IMAGE_URL`=TRIM(REPLACE(tt.`LARGE_IMAGE_URL`,'\'',''))";
        this.jdbcTemplate.update(sql);

        //replace all commas
        sql = "UPDATE temp_table tt SET tt.`MAP`=REPLACE(tt.`MAP`,',',''),tt.`MSRP`=REPLACE(tt.`MSRP`,',',''),"
                + " tt.`PRICE`=REPLACE(tt.`PRICE`,',',''),tt.`QUANTITY`=REPLACE(tt.`QUANTITY`,',',''),tt.`WEIGHT`=REPLACE(tt.`WEIGHT`,',',''),"
                + " tt.`ESTIMATED_SHIP_WEIGHT`=REPLACE(tt.`ESTIMATED_SHIP_WEIGHT`,',',''),tt.`LENGTH`=REPLACE(tt.`LENGTH`,',',''),"
                + "tt.`WIDTH`=REPLACE(tt.`WIDTH`,',',''),tt.`HEIGHT`=REPLACE(tt.`HEIGHT`,',',''),tt.`SHIPPING`=REPLACE(tt.`SHIPPING`,',','')";
        this.jdbcTemplate.update(sql);
        //update decimal values with null where blank
        sql = "UPDATE temp_table tt SET tt.`MAP`=IF(tt.`MAP`='',NULL,tt.`MAP`),tt.`MSRP`=IF(tt.`MSRP`='',NULL,tt.`MSRP`),"
                + " tt.`WEIGHT`=IF(tt.`WEIGHT`='',NULL,tt.`WEIGHT`),tt.`ESTIMATED_SHIP_WEIGHT`=IF(tt.`ESTIMATED_SHIP_WEIGHT`='',NULL,tt.`ESTIMATED_SHIP_WEIGHT`),"
                + " tt.`LENGTH`=IF(tt.`LENGTH`='',NULL,tt.`LENGTH`),tt.`WIDTH`=IF(tt.`WIDTH`='',NULL,tt.`WIDTH`),tt.`HEIGHT`=IF(tt.`HEIGHT`='',NULL,tt.`HEIGHT`),"
                + " tt.`SHIPPING`=IF(tt.`SHIPPING`='',NULL,tt.`SHIPPING`)";
        this.jdbcTemplate.update(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Removal of comma,white spaces done..", GlobalConstants.TAG_SYSTEMLOG));

        //make default status true
        sql = "UPDATE temp_table tt SET tt.`STATUS`=1,tt.`REASON`=''";
        this.jdbcTemplate.update(sql);

        //make all manufacturer in upper case
        sql = "UPDATE temp_table tt SET tt.`MANUFACTURER_NAME`=UPPER(tt.`MANUFACTURER_NAME`)";
        this.jdbcTemplate.update(sql);

        //update manufacturer id 
        sql = "UPDATE temp_table tt LEFT JOIN pm_manufacturer_mapping tmm ON tmm.`WH_MANUFACTURER_NAME`=tt.`MANUFACTURER_NAME`"
                + " SET tt.`MANUFACTURER_ID`=tmm.`MANUFACTURER_ID`;";
        this.jdbcTemplate.update(sql);

        //check manufacturer which is not exist in db
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Invalid manufacturer name.') WHERE tt.`MANUFACTURER_ID` IS NULL";
        this.jdbcTemplate.update(sql);

        //check MPN name required
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'MPN is required.') WHERE tt.`MPN` IS NULL OR tt.`MPN`=''";
        this.jdbcTemplate.update(sql);

        //check MPN length too long
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'MPN is too long.') WHERE LENGTH(tt.`MPN`)>40";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for MAP
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Map :Invalid decimal value.') WHERE (tt.`MAP` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`MAP`!='' AND tt.`MAP` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for MSRP
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Msrp :Invalid decimal value.') WHERE (tt.`MSRP` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`MSRP`!='' AND tt.`MSRP` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check price name required
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Price is required.') WHERE tt.`PRICE` IS NULL OR tt.`PRICE`=''";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for Price
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Price :Invalid decimal value.') WHERE (tt.`PRICE` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`PRICE`!='' AND tt.`PRICE` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check quantity name required
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Quantity is required.') WHERE tt.`QUANTITY` IS NULL OR tt.`QUANTITY`=''";
        this.jdbcTemplate.update(sql);

        //check quantity name required
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Invalid quantity.')"
                + " WHERE (tt.`QUANTITY` NOT REGEXP '^[0-9]+$') AND tt.`QUANTITY`!='' AND tt.`QUANTITY` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check warehouse MPN name required
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Warehouse identification no is required.') WHERE tt.`WAREHOUSE_IDENTIFICATION_NO` IS NULL OR tt.`WAREHOUSE_IDENTIFICATION_NO`=''";
        this.jdbcTemplate.update(sql);

        //check warehouse MPN length too long
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Warehouse identification no is too long.') WHERE LENGTH(tt.`WAREHOUSE_IDENTIFICATION_NO`)>40";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for Weight
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Weight :Invalid decimal value.') WHERE (tt.`WEIGHT` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`WEIGHT`!='' AND tt.`WEIGHT` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for Estimated ship weight
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Est Ship Wt :Invalid decimal value.') WHERE (tt.`ESTIMATED_SHIP_WEIGHT` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`ESTIMATED_SHIP_WEIGHT`!='' AND tt.`ESTIMATED_SHIP_WEIGHT` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for length
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Length :Invalid decimal value.') WHERE (tt.`LENGTH` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`LENGTH`!='' AND tt.`LENGTH` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for width
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Width :Invalid decimal value.') WHERE (tt.`WIDTH` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`WIDTH`!='' AND tt.`WIDTH` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for height
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Height :Invalid decimal value.') WHERE (tt.`HEIGHT` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`HEIGHT`!='' AND tt.`HEIGHT` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //check upc contains only number and is greater than 11 digits
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Invalid UPC.')"
                + " WHERE (tt.`UPC` NOT REGEXP '^[0-9]+$' OR LENGTH(tt.`UPC`)<11) AND tt.`UPC`!='' AND tt.`UPC` IS NOT NULL";
        this.jdbcTemplate.update(sql);

        //to remove new line character from large image
        sql = "UPDATE temp_table tt SET tt.`LARGE_IMAGE_URL`=REPLACE(REPLACE(tt.`LARGE_IMAGE_URL`, '\\r', ''), '\\n', '')";
        this.jdbcTemplate.update(sql);
        
        //to remove new line character from Shipping
        sql = "UPDATE temp_table tt SET tt.`SHIPPING`=REPLACE(REPLACE(tt.`SHIPPING`, '\\r', ''), '\\n', '')";
        this.jdbcTemplate.update(sql);

        //check valid decimal number for Shipping
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Shipping :Invalid decimal value.') "
                + " WHERE (tt.`SHIPPING` NOT REGEXP '^[0-9]*[.]?[0-9]+$') AND tt.`SHIPPING`!='' AND tt.`SHIPPING`IS NOT NULL";
        this.jdbcTemplate.update(sql);

        
//            if (warehouseId == 18 || warehouseId == 16 || warehouseId == 21 || warehouseId == 22) {
//            double shipping = 0.00;
//            if (warehouseId == 18) {
//                shipping = 15.00;
//            } else if (warehouseId == 16) {
//                shipping = 9.99;
//            } else if (warehouseId == 21) {
//                shipping = 5.95;
//            } else if (warehouseId == 22) {
//                shipping = 10;
//            }
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=?";
//            this.jdbcTemplate.update(sql, shipping);
//            //Synnex
//        } else if (warehouseId == 8) {
//
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`< 50,15,IF(tt.`PRICE`>= 50 AND tt.`PRICE`<=250 ,5,IF(tt.`PRICE` > 250,0,0)))";
//
//            this.jdbcTemplate.update(sql);
//            //TechData
//        } else if (warehouseId == 27) {
//
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE` > 500,0,IF(tt.`LENGTH`> 30 || tt.`WIDTH`>30 || tt.`HEIGHT`>30 ,100,IF(tt.`WEIGHT`>10,(tt.`WEIGHT`*2),IF(tt.`WEIGHT`<=10,12,IF(tt.`PRICE`<=500,10,10)))));";
//
//            this.jdbcTemplate.update(sql);
//            //Petra
//        } else if (warehouseId == 4) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`WEIGHT`>=0 AND tt.`WEIGHT`<=0.8,6,IF(tt.`WEIGHT`>=0.8 AND tt.`WEIGHT`<=1,12.25,IF(tt.`WEIGHT`>1 AND tt.`WEIGHT`<=2,12.75,IF(tt.`WEIGHT`>2 AND tt.`WEIGHT`<=3,12.85,IF(tt.`WEIGHT`>3 AND tt.`WEIGHT`<=4,13.25,13.25)))))";
//            this.jdbcTemplate.update(sql);
//            //Teledynamics
//        } else if (warehouseId == 5) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`>=200 OR tt.`MANUFACTURER_ID` IN(1553,1560,2382,2532,2407,878),0,IF(tt.`WEIGHT`>=0 AND tt.`WEIGHT`<=0.85,3.65,IF(tt.`WEIGHT`>0.85 AND tt.`WEIGHT`<=7,7,IF(tt.`WEIGHT`>7 AND tt.`WEIGHT`<=9,7.85,(tt.`WEIGHT`+1)))))";
//            this.jdbcTemplate.update(sql);
//            //Essendent , AE Warehouse
//        } else if (warehouseId == 14 || warehouseId == 9) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`>50,0,IF(tt.`PRICE`=<50,10,10))";
//            this.jdbcTemplate.update(sql);
//            //Fontel
//        } else if (warehouseId == 19) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`>100,0,IF(tt.`PRICE`<=100,5,5))";
//            this.jdbcTemplate.update(sql);
//            //CWR
//        } else if (warehouseId == 24) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`WEIGHT`>15,(tt.`WEIGHT`*2),IF(tt.`WEIGHT`<=15,9.95,9.95))";
//            this.jdbcTemplate.update(sql);
//            //D and H
//        } else if (warehouseId == 25) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`WEIGHT`>8,(tt.`WEIGHT`*2),IF(tt.`WEIGHT`>=2 AND tt.`WEIGHT`<=8,10,IF(tt.`WEIGHT`<2,6,6)))";
//            this.jdbcTemplate.update(sql);
//
//        }//ma labs
//        else if (warehouseId == 26) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`WEIGHT`>7,(tt.`WEIGHT`*2),IF(tt.`WEIGHT`>5 AND tt.`WEIGHT`<=7,20,IF(tt.`WEIGHT`>3 AND tt.`WEIGHT`<=5,15,IF(tt.`WEIGHT`<=3,10,10))))";
//            this.jdbcTemplate.update(sql);
//            //ScanSource Catalyst, Jenne
//        } else if (warehouseId == 7 || warehouseId == 3) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`>100,0,IF(tt.`PRICE`<=100,10,10))";
//            this.jdbcTemplate.update(sql);
//            //Ingram
//        } else if (warehouseId == 20) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`LENGTH`>48,(2*tt.`LENGTH`),IF(tt.`WEIGHT`>30,(3*tt.`WEIGHT`),\n"
//                    + "IF(tt.`WEIGHT`>10 AND tt.`WEIGHT`<=30,(2*tt.`WEIGHT`),IF(tt.`WEIGHT`>6 AND tt.`WEIGHT`<=10,15,\n"
//                    + "IF(tt.`WEIGHT`>2 AND tt.`WEIGHT`<=6,10,IF(tt.`WEIGHT`>0 AND tt.`WEIGHT`<=2,6,6))))))";
//            this.jdbcTemplate.update(sql);
//        } //Bluestar
//        else if (warehouseId == 11) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`PRICE`>200,0,IF(tt.`PRICE`<=200,10,10))";
//            this.jdbcTemplate.update(sql);
//            //Capitol Sales, Wynit
//        } else if (warehouseId == 1 || warehouseId == 6) {
//            sql = "UPDATE temp_table tt SET tt.`SHIPPING`=IF(tt.`WEIGHT`<=20,15,IF(tt.`WEIGHT`>20, (2 * tt.`WEIGHT`),15))";
//            this.jdbcTemplate.update(sql);
//        }

        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Invalid resize/large image url')"
                + " WHERE (tt.`RESIZE_IMAGE_URL`!='' AND tt.`RESIZE_IMAGE_URL` IS NOT NULL"
                + " AND LOWER(RIGHT(tt.`RESIZE_IMAGE_URL`,4)) NOT IN ('.jpg','jpeg','.png','.gif')) OR"
                + " (tt.`LARGE_IMAGE_URL`!='' AND tt.`LARGE_IMAGE_URL` IS NOT NULL"
                + " AND LOWER(RIGHT(tt.`LARGE_IMAGE_URL`,4)) NOT IN ('.jpg','jpeg','.png','.gif'))";
        this.jdbcTemplate.update(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Field validations done.", GlobalConstants.TAG_SYSTEMLOG));

        //categorize products : new/need to map/existing/invalid
        sql = "UPDATE temp_table tt SET tt.`PRODUCT_STATUS`=4 WHERE tt.`STATUS`=1";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sql);

        sql = "UPDATE temp_table tt LEFT JOIN pm_product tp ON tp.`MANUFACTURER_ID`=tt.`MANUFACTURER_ID` AND tp.`MANUFACTURER_MPN`=tt.`MPN`"
                + " SET tt.`PRODUCT_STATUS`=1 WHERE tp.`PRODUCT_ID` IS NULL AND tt.`STATUS`=1";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        int result = this.jdbcTemplate.update(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result + " New products found", GlobalConstants.TAG_SYSTEMLOG));

        sql = "UPDATE temp_table tt "
                + " LEFT JOIN pm_product tp ON tt.`MPN`=tp.`MANUFACTURER_MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " LEFT JOIN pm_warehouse_product_mpn twpm ON twpm.`WAREHOUSE_MPN`=tt.`WAREHOUSE_IDENTIFICATION_NO` AND twpm.`WAREHOUSE_ID`=?"
                + " SET tt.`PRODUCT_STATUS`=2 "
                + " WHERE twpm.`PRODUCT_ID` IS NULL AND tt.`PRODUCT_STATUS`=4";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        result = this.jdbcTemplate.update(sql, warehouseId);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result + " products need to map", GlobalConstants.TAG_SYSTEMLOG));

        sql = "UPDATE temp_table tt"
                + " LEFT JOIN pm_product tp ON tt.`MPN`=tp.`MANUFACTURER_MPN` AND tt.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                + " LEFT JOIN pm_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tp.`PRODUCT_ID` AND twpm.`WAREHOUSE_MPN`=tt.`WAREHOUSE_IDENTIFICATION_NO` "
                + " SET tt.`PRODUCT_STATUS`=3 WHERE tt.`PRODUCT_STATUS`=4 AND twpm.`WAREHOUSE_ID`=?";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        result = this.jdbcTemplate.update(sql, warehouseId);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result + " Existing products found.", GlobalConstants.TAG_SYSTEMLOG));

        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'Existing product with invalid warehouse MPN.') WHERE tt.`PRODUCT_STATUS`=4";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sql);

        sql = "UPDATE temp_table tt"
                + " SET tt.`STATUS`=0,tt.`PRODUCT_STATUS`=4,tt.`REASON`=CONCAT(tt.`REASON`,'New product with invalid/duplicate warehouse MPN.') WHERE tt.`PRODUCT_STATUS`=1 AND tt.`STATUS`=1"
                + " AND tt.`WAREHOUSE_IDENTIFICATION_NO` IN (SELECT twpm.`WAREHOUSE_MPN` FROM pm_warehouse_product_mpn twpm"
                + " WHERE twpm.`WAREHOUSE_ID`=?)";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sql, warehouseId);

        sql = "SELECT tt.`STATUS`,COUNT(*) PRODUCT_COUNT FROM temp_table tt GROUP BY tt.`STATUS`; ";
        return this.jdbcTemplate.query(sql, new BadDataDTORowMapper());
    }

    @Override
    public List<FeedUpload> badFeedforExport() {
        String sql = "SELECT * FROM temp_table WHERE !STATUS";
        return this.jdbcTemplate.query(sql, new FeedUploadRowMapper());
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert warehouseInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_warehouse").usingGeneratedKeyColumns("WAREHOUSE_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("WAREHOUSE_NAME", warehouse.getWarehouseName());
        params.put("WAREHOUSE_ADDRESS", warehouse.getWarehouseAddress());
        params.put("WAREHOUSE_PHONE", warehouse.getWarehousePhone());
        params.put("WAREHOUSE_REP_NAME", warehouse.getWarehouseRepName());
        params.put("WAREHOUSE_REP_EMAIL", warehouse.getWarehouseRepEmail());
        params.put("WAREHOUSE_CUST_SER_EMAIL", warehouse.getWarehouseCustServiceEmail());
        params.put("ACTIVE", warehouse.isActive());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int warehouseId = warehouseInsert.executeAndReturnKey(params).intValue();
        return warehouseId;
    }

    @Override
    public Warehouse getWarehouseByWarehouseId(int warehouseId) {
        String sqlString = "SELECT tw.* FROM pm_warehouse tw"
                + " WHERE tw.`WAREHOUSE_ID`=?";
        Object params[] = new Object[]{warehouseId};
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForObject(sqlString, params, new WarehouseRowMapper());
    }

    @Override
    public int updateWarehouse(Warehouse warehouse) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        sqlString = "UPDATE pm_warehouse SET WAREHOUSE_NAME=?, WAREHOUSE_ADDRESS=?, WAREHOUSE_PHONE=?, WAREHOUSE_REP_NAME=?, WAREHOUSE_REP_EMAIL=?, WAREHOUSE_CUST_SER_EMAIL=?, ACTIVE=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE WAREHOUSE_ID=?";
        params = new Object[]{warehouse.getWarehouseName(), warehouse.getWarehouseAddress(), warehouse.getWarehousePhone(), warehouse.getWarehouseRepName(), warehouse.getWarehouseRepEmail(), warehouse.getWarehouseCustServiceEmail(), warehouse.isActive(), curUser, curDate, warehouse.getWarehouseId()};
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Warehouse> getShippingWarehouseList(boolean active) {
        String sql = "SELECT tw.* FROM pm_warehouse tw "
                + " LEFT JOIN pm_shipping_details tsp ON tw.`WAREHOUSE_ID`=tsp.`WAREHOUSE_ID` "
                + " WHERE tsp.`WAREHOUSE_ID` IS NULL";
        if (active) {
            sql += " AND tw.ACTIVE";
        }
        sql += " ORDER BY tw.`LAST_MODIFIED_DATE` DESC";
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new WarehouseRowMapper());
    }
}
