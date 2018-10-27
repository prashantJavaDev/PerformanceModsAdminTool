/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO.mapper;

import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class BadDataDTORowMapper implements RowMapper<BadDataDTO> {

    @Override
    public BadDataDTO mapRow(ResultSet rs, int i) throws SQLException {
        BadDataDTO badData = new BadDataDTO();
        badData.setStatus(rs.getBoolean("STATUS"));
        badData.setProductCount(rs.getInt("PRODUCT_COUNT"));

        return badData;
    }
}
