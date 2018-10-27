/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Email;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class EmailerRowMapper implements RowMapper<Email> {

    @Override
    public Email mapRow(ResultSet rs, int i) throws SQLException {
        Email email = new Email();
        email.setEmailerId(rs.getInt("EMAIL_ID"));
        email.setToSend(rs.getString("TO_SEND"));
        email.setCcToSend(rs.getString("CC_SEND_TO"));
        email.setSubject(rs.getString("SUBJECT"));
        email.setBody(rs.getString("BODY"));
        email.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        email.setToSendDate(rs.getTimestamp("TO_SEND_DATE"));
        email.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        email.setStatus(rs.getInt("STATUS"));
        email.setAttempts(rs.getInt("ATTEMPTS"));
        email.setResponse(rs.getString("RESPONSE"));
        return email;
    }
}
