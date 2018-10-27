/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.ReportDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.AccessLogReportDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.mapper.AccessLogReportDTORowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gaurao
 */
@Repository
public class ReportDaoImpl implements ReportDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success) {
        startDate += " 00:00:00";
        stopDate += " 23:59:59";
        StringBuilder sql = new StringBuilder("SELECT s.`DATE`, s.`IP`, s.`USERNAME`,s.`MESSAGE`,u.`USER_ID` FROM system_log s "
                + " LEFT JOIN `user` u ON u.`USERNAME`=s.`USERNAME` "
                + " WHERE s.`TAG`='ACCESS' AND s.`DATE` BETWEEN :startDate AND :stopDate");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);

        if (userId != -1) {
            sql.append(" AND u.`USER_ID`=:userId");
            params.put("userId", userId);
        }

        if (success != -1) {
            if (success == 1) {
                sql.append(" AND s.`MESSAGE`='Success'");
            } else {
                sql.append(" AND s.`MESSAGE`!='Success'");
            }
        }
        sql.append(" ORDER BY s.`DATE` DESC");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        return nm.query(sql.toString(), params, new AccessLogReportDTORowMapper());
    }
}
