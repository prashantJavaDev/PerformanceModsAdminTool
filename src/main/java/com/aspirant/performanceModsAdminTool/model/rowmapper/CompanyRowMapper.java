/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Company;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int i) throws SQLException {
        Company c = new Company();
        c.setCompanyId(rs.getInt("COMPANY_ID"));
        c.setCompanyName(rs.getString("COMPANY_NAME"));
        c.setOwnerName(rs.getString("OWNER_NAME"));
        c.setContactNumber(rs.getString("CONTACT_NUMBER"));
        c.setCountryName(rs.getString("COUNTRY_NAME"));
        c.setActive(rs.getBoolean("ACTIVE"));
        return c;
    }
}
