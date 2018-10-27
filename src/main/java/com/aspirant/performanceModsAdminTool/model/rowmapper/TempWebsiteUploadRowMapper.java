/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.TempWebsiteUpload;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class TempWebsiteUploadRowMapper implements RowMapper<TempWebsiteUpload>{

    @Override
    public TempWebsiteUpload mapRow(ResultSet rs, int i) throws SQLException {
        try {
            TempWebsiteUpload twu = new TempWebsiteUpload();
         twu.setProductID(rs.getInt("PRODUCT_ID"));
        twu.setImageURL1(rs.getString("i1"));
        twu.setImageURL2(rs.getString("i2"));
        twu.setImageURL3(rs.getString("i3"));
        twu.setImageURL4(rs.getString("i4"));
       
        return twu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
        
    }
    
    
}
