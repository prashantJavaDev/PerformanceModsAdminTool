/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO.mapper;

import com.aspirant.performanceModsAdminTool.model.DTO.AccessLogReportDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author gaurao
 */
public class AccessLogReportDTORowMapper implements RowMapper<AccessLogReportDTO> {

    @Override
    public AccessLogReportDTO mapRow(ResultSet rs, int i) throws SQLException {
        AccessLogReportDTO accessLogReportDTO = new AccessLogReportDTO();
        accessLogReportDTO.setIpAddress(rs.getString("IP"));
        accessLogReportDTO.setUsername(rs.getString("USERNAME"));
        accessLogReportDTO.setUserId(rs.getInt("USER_ID"));
        accessLogReportDTO.setAccessDate(rs.getTimestamp("DATE"));
        accessLogReportDTO.setOutcome(rs.getString("MESSAGE"));

        return accessLogReportDTO;
    }
}
