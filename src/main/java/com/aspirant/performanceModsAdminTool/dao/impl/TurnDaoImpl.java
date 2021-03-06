/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.TurnDao;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.ShippingAPIResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.aspirant.performanceModsAdminTool.web.controller.ShippingTurnAPIResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pk
 */
@Repository
public class TurnDaoImpl implements TurnDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int updateTokenEntry(TokenResponse tokenResponse,int warehouseId) {
        if(warehouseId==1){
        String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=?,t.`EXPIRES_IN`=?,t.`SCOPE`=?,t.`TOKEN_TYPE`=? WHERE t.`TOKEN_API`='T';";
        return this.jdbcTemplate.update(sql, tokenResponse.getAccess_token(), tokenResponse.getExprires_in(), tokenResponse.getScope(), tokenResponse.getToken_type());
        }else{
        String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=?,t.`EXPIRES_IN`=?,t.`SCOPE`=?,t.`TOKEN_TYPE`=? WHERE t.`TOKEN_API`='T2';";
        return this.jdbcTemplate.update(sql, tokenResponse.getAccess_token(), tokenResponse.getExprires_in(), tokenResponse.getScope(), tokenResponse.getToken_type());
        }
    }

    @Override
    public TokenResponse getToken(String apiType) {
        try {
            String sql = "SELECT * FROM token t WHERE t.`TOKEN_API`=?;";
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<TokenResponse>() {

                @Override
                public TokenResponse mapRow(ResultSet rs, int i) throws SQLException {
                    TokenResponse t = new TokenResponse();
                    t.setAccess_token(rs.getString("ACCESS_TOKEN"));
                    t.setExprires_in(rs.getInt("EXPIRES_IN"));
                    t.setScope(rs.getString("SCOPE"));
                    t.setToken_type(rs.getString("TOKEN_TYPE"));
                    return t;
                }
            }, apiType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addItem(List<ItemResponse> data) {
        try {
//            System.out.println("=====IN CountriesList   ");
            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[data.size()];
            Map<String, Object> params = new HashMap<>();
            SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("item_master_temp").usingGeneratedKeyColumns("ID");
            int i = 0;
            for (ItemResponse item : data) {
                params.put("ITEM_ID", item.getId());
                params.put("ITEM_TYPE", item.getType());
                params.put("PRODUCT_NAME", item.getAttributes().getProduct_name());
                params.put("PART_NUMBER", item.getAttributes().getPart_number());
                params.put("MFR_PART_NUMBER", item.getAttributes().getMfr_part_number());
                params.put("BRAND_ID", item.getAttributes().getBrand_id());
                params.put("BRAND", item.getAttributes().getBrand());
                batchParams[i] = new MapSqlParameterSource(params);
                i++;
            }
            params.clear();
            int[] key = insert.executeBatch(batchParams);
            return 1;
        } catch (Exception e) {
            System.out.println("I am in exception");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int addItemByFile(String path,int warehouseId) {
        try {
            this.jdbcTemplate.update("DELETE FROM item_master_temp  WHERE API_ID=?",warehouseId);
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`item_master_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\\n' (`ITEM_ID`, `ITEM_TYPE`, `PART_NUMBER`, `MFR_PART_NUMBER`, `BRAND`, `API_ID`) ";
//            "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES 
            this.jdbcTemplate.execute(sql);

            String sql1 = "UPDATE pm_current_warehouse_product d\n"
                    + "LEFT JOIN item_master_temp t ON t.`PART_NUMBER`=d.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "SET d.`ITEM_ID`=t.`ITEM_ID`\n"
                    + "WHERE t.`PART_NUMBER`=d.`WAREHOUSE_IDENTIFICATION_NO` AND d.`WAREHOUSE_ID`=? AND t.`API_ID`=?";
            this.jdbcTemplate.update(sql1,warehouseId,warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addPriceFile(String path,int warehouseId) {
        try {
            this.jdbcTemplate.update("DELETE FROM item_price_temp  WHERE API_ID=?",warehouseId);
//            this.jdbcTemplate.execute("TRUNCATE TABLE `item_price_temp`");
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`item_price_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\\n' (`ITEM_ID`, `ITEM_TYPE`, `ITEM_PURCHASE_COST`, `IS_MAP`, `ITEM_MAP`, `API_ID`) ";
//            "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES 
            this.jdbcTemplate.execute(sql);
            String sql1 = "UPDATE pm_current_warehouse_product d\n"
                    + "LEFT JOIN item_price_temp p ON p.`ITEM_ID`=d.`ITEM_ID`\n"
                    + "SET d.`MAP`=p.`ITEM_MAP`,d.`PRICE`=p.`ITEM_PURCHASE_COST`\n"
                    + "WHERE p.`ITEM_ID`=d.`ITEM_ID` AND d.`WAREHOUSE_ID`=? AND p.`API_ID`=?";
            this.jdbcTemplate.update(sql1,warehouseId,warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addInventoryFile(String path,int warehouseId) {
        try {
            this.jdbcTemplate.update("DELETE FROM item_inventory_temp  WHERE API_ID=?",warehouseId);
//            this.jdbcTemplate.execute("TRUNCATE TABLE `item_inventory_temp`");
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`item_inventory_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\\n' (`ITEM_ID`, `ITEM_TYPE`, `LOCATION_STOCK`, `STOCK`, `API_ID`) ";
//            "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES 
            this.jdbcTemplate.execute(sql);

            String sql1 = "UPDATE pm_current_warehouse_product d\n"
                    + "LEFT JOIN item_inventory_temp i ON i.`ITEM_ID`=d.`ITEM_ID`\n"
                    + "SET d.`QUANTITY`=i.`LOCATION_STOCK`\n"
                    + "WHERE d.`ITEM_ID`=i.`ITEM_ID` AND d.`WAREHOUSE_ID`=? AND i.`API_ID`=?";
            this.jdbcTemplate.update(sql1,warehouseId,warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void addShipping(ShippingTurnAPIResponse data) {
        try {
            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[data.getData().size()];
            Map<String, Object> params = new HashMap<>();
            SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("shipping_carrier");
            int i = 0;
            for (ShippingAPIResponse item : data.getData()) {
                params.put("SHIPPING_ID", item.getId());
                params.put("TYPE", item.getType());
                params.put("TRANSPORTATION_NAME", item.getAttributes().getTransportation_name());
                params.put("CARRIER", item.getAttributes().getCarrier_name());
                batchParams[i] = new MapSqlParameterSource(params);
                i++;
            }
            params.clear();
            int[] key = insert.executeBatch(batchParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
