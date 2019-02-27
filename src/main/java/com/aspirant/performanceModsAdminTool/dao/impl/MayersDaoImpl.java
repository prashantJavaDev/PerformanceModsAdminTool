/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.MayersDao;
import com.aspirant.performanceModsAdminTool.model.DTO.MayerTokenResponse;
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
public class MayersDaoImpl implements MayersDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int updateTokenEntry(MayerTokenResponse tokenResponse) {
        try {
            String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=?,t.`EXPIRES_IN`=?,t.`SCOPE`=?,t.`TOKEN_TYPE`=? WHERE t.`TOKEN_API`='M';";
            return this.jdbcTemplate.update(sql, tokenResponse.getApikey(), tokenResponse.getExpiration(), null, "Bearer");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<String> getFeedEntries() {
        try {
            String sql = "SELECT t.`WAREHOUSE_IDENTIFICATION_NO` FROM pm_current_warehouse_product t WHERE t.`WAREHOUSE_ID`='2'";
            return this.jdbcTemplate.queryForList(sql, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addItemFile(String path) {

        try {
            this.jdbcTemplate.execute("TRUNCATE TABLE `mayer_item_temp`");
            String sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`mayer_item_temp` FIELDS ESCAPED BY '\\\"' TERMINATED BY ';' LINES TERMINATED BY '\\n' (`ITEM_NUMBER`,`QTY`, `MAP`, `PRICE`) ";
            this.jdbcTemplate.execute(sql);
            String sql1 = "UPDATE pm_current_warehouse_product t \n"
                    + "  LEFT JOIN mayer_item_temp p ON p.`ITEM_NUMBER`=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "  SET t.`PRICE`=p.`PRICE`,t.`MAP`=p.`MAP`,t.`QUANTITY`=p.`QTY`\n"
                    + "  WHERE p.`ITEM_NUMBER`=t.`WAREHOUSE_IDENTIFICATION_NO`\n"
                    + "  AND t.`WAREHOUSE_ID`=? ";
            this.jdbcTemplate.update(sql1, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
