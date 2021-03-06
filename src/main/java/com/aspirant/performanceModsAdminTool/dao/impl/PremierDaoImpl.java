/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.PremierDao;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pk
 */
@Repository
public class PremierDaoImpl implements PremierDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> getFeedEntries() {
        try {
            String sql = "SELECT t.`WAREHOUSE_IDENTIFICATION_NO` FROM pm_current_warehouse_product t WHERE t.`WAREHOUSE_ID`='7' ";
            return this.jdbcTemplate.queryForList(sql, String.class);

//           List<String> list= new ArrayList<>();
//           list.add("BUB176757");
//           list.add("SKYB8507");
//           list.add("SKYM9592");
//           return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateTokenOfPremier(String sessionToken, int warehouseId) {
        try {
            if (warehouseId == 3) {

                String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=? WHERE t.`TOKEN_API`='P';";
                return this.jdbcTemplate.update(sql, sessionToken);
            } else {
                String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=? WHERE t.`TOKEN_API`='P2';";
                return this.jdbcTemplate.update(sql, sessionToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int addInventoryFile(String path, int warehousId) {
        try {
            this.jdbcTemplate.update("DELETE FROM premier_inventory_temp WHERE WAREHOUSE_ID=?", warehousId);
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`premier_inventory_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ';' LINES TERMINATED BY '\\n' (`ITEM_NO`, `QTY`, `API_ID`) ";

            this.jdbcTemplate.execute(sql);
            String sql1 = "UPDATE pm_current_warehouse_product t \n"
                    + "LEFT JOIN premier_inventory_temp p ON p.ITEM_NO=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "SET t.`QUANTITY`=p.QTY\n"
                    + "WHERE p.ITEM_NO=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "AND t.`WAREHOUSE_ID`=? AND p.`API_ID`=?";
            this.jdbcTemplate.update(sql1, warehousId, warehousId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addPriceFile(String path, int warehousId) {
        try {
//            this.jdbcTemplate.execute("TRUNCATE TABLE `premier_price_temp`");
            this.jdbcTemplate.update("DELETE FROM premier_price_temp WHERE WAREHOUSE_ID=?", warehousId);
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`premier_price_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ';' LINES TERMINATED BY '\\n' (`ITEM_NO`,`PRICE`, `MAP`, `API_ID`) ";

            this.jdbcTemplate.execute(sql);
            String sql1 = "UPDATE pm_current_warehouse_product t \n"
                    + "LEFT JOIN premier_price_temp p ON p.ITEM_NO=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "SET t.`PRICE`=p.`PRICE`,t.`MAP`=p.`MAP`\n"
                    + "WHERE p.ITEM_NO=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "AND t.`WAREHOUSE_ID`=? AND p.`API_ID`=? ;";
            this.jdbcTemplate.update(sql1, warehousId, warehousId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
