/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.AmazonProperties;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author pk
 */
public class AmazonPropertiesRowMapper implements RowMapper<AmazonProperties>{

    @Override
    public AmazonProperties mapRow(ResultSet rs, int i) throws SQLException {
        AmazonProperties am=new AmazonProperties();
        am.setCredentialId(rs.getInt("credential_id"));
        am.setAccessKey(rs.getString("access_key"));
        am.setSecretKey(rs.getString("secret_key"));
        am.setMwsAuthToken(rs.getString("mwsAuth_token"));
        am.setSellerId(rs.getString("seller_id"));
        am.setMarketplaceId(rs.getString("marketplace_id"));
        am.setMerchantId(rs.getString("merchant_id"));
        return am;
    }
    
}
