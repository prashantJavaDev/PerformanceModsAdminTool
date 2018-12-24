/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.TurnDao;
import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
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
public class TurnDaoImpl implements TurnDao{
 private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public int updateTokenEntry(TokenResponse tokenResponse) {
        String sql="UPDATE token t SET t.`ACCESS_TOKEN`=?,t.`EXPIRES_IN`=?,t.`SCOPE`=?,t.`TOKEN_TYPE`=?;";
        return this.jdbcTemplate.update(sql, tokenResponse.getAccess_token(),tokenResponse.getExprires_in(),tokenResponse.getScope(),tokenResponse.getToken_type());
    }

    @Override
    public TokenResponse getToken() {
        try {
            String sql="SELECT * FROM token t;";
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<TokenResponse>() {

                @Override
                public TokenResponse mapRow(ResultSet rs, int i) throws SQLException {
                    TokenResponse t=new TokenResponse();
                    t.setAccess_token(rs.getString("ACCESS_TOKEN"));
                    t.setExprires_in(rs.getInt("EXPIRES_IN"));
                    t.setScope(rs.getString("SCOPE"));
                    t.setToken_type(rs.getString("TOKEN_TYPE"));
                    return t;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
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
    
}
