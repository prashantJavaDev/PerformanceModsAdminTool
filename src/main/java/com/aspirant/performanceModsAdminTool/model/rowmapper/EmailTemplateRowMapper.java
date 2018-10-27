/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class EmailTemplateRowMapper implements RowMapper<EmailTemplate> {

    @Override
    public EmailTemplate mapRow(ResultSet rs, int i) throws SQLException {
        EmailTemplate template = new EmailTemplate();
        template.setEmailTemplateId(rs.getInt("EMAIL_TEMPLATE_ID"));
        template.setSubject(rs.getString("SUBJECT"));
        template.setSubjectParam(rs.getString("SUBJECT_PARAM"));
        template.setBody(rs.getString("BODY"));
        template.setBodyParam(rs.getString("BODY_PARAM"));
        template.setActive(rs.getBoolean("ACTIVE"));

        return template;
    }
}
