/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.EmailerDao;
import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Email;
import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import com.aspirant.performanceModsAdminTool.model.rowmapper.EmailTemplateRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.EmailerRowMapper;
import com.aspirant.performanceModsAdminTool.service.EmailerService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ritesh
 */
@Repository
public class EmailerDaoImpl implements EmailerDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    UserDao userDao;
    @Autowired
    EmailerService emailerService;

    @Override
    public EmailTemplate getEmailTemplateByTemplateId(int emailTemplateId) {
        String selectSql = "SELECT et.* FROM email_template et "
                + "  WHERE et.`EMAIL_TEMPLATE_ID`=?";
        EmailTemplate emailTemplate = null;
        try {
            emailTemplate = (EmailTemplate) this.jdbcTemplate.queryForObject(selectSql, new EmailTemplateRowMapper(), emailTemplateId);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
        return emailTemplate;
    }

    @Override
    public Email buildEmail(int emailTemplateId, String toSend, String[] subjectParam, String[] bodyParam, EmailTemplate emailTemplate) {
        try {
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            String subjectString = emailTemplate.getSubject();
            String emailBodyString = emailTemplate.getBody();
            String[] eTemplateSubjectParamList = emailTemplate.getSubjectParam().split(",");
            for (int i = 0; i < subjectParam.length; i++) {
                subjectString = subjectString.replaceAll("<%" + eTemplateSubjectParamList[i] + "%>", subjectParam[i]);
            }
            String[] eTemplateBodyParamList = emailTemplate.getBodyParam().split(",");
            for (int i = 0; i < bodyParam.length; i++) {
                if (bodyParam[i] == null) {
                    bodyParam[i] = "";
                }
                emailBodyString = emailBodyString.replace("<%" + eTemplateBodyParamList[i] + "%>", bodyParam[i]);
            }
            Email email = new Email();
            email.setBody(emailBodyString);
            email.setCreatedDate(curDate);
            email.setLastModifiedDate(curDate);
            email.setSubject(subjectString);
            email.setToSend(toSend);
            email.setToSendDate(curDate);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Email build successfully for email id :" + toSend, GlobalConstants.TAG_SYSTEMLOG));
            return email;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Error occured while building mail for email id :" + toSend, GlobalConstants.TAG_SYSTEMLOG));
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }

    @Override
    public int addEmail(Email email) {
        try {
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            email.setCreatedDate(curDate);
            email.setLastModifiedDate(curDate);
            email.setToSendDate(curDate);
            SimpleJdbcInsert emailInsert = new SimpleJdbcInsert(this.dataSource).withTableName("emailer").usingGeneratedKeyColumns("EMAIL_ID");
            Map<String, Object> params = new HashMap<String, Object>();
            if (email.getTicket() != null) {
                params.put("TICKET_ID", email.getTicket().getTicketId());
            }
            params.put("LEVEL", email.getLevel());
            params.put("TO_SEND", email.getToSend());
            params.put("CC_SEND_TO", email.getCcToSend());
            params.put("SUBJECT", email.getSubject());
            params.put("BODY", email.getBody());
            params.put("CREATED_DATE", email.getCreatedDate());
            params.put("TO_SEND_DATE", email.getToSendDate());
            params.put("LAST_MODIFIED_DATE", email.getLastModifiedDate());
            params.put("RESPONSE", email.getResponse());
            params.put("ATTEMPTS", email.getAttempts());
            params.put("STATUS", email.getStatus());
            int emailId = emailInsert.executeAndReturnKey(params).intValue();
            return emailId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Email> getEmailerList() {
        String query = "SELECT * FROM emailer WHERE emailer.`STATUS`<2 AND emailer.`ATTEMPTS`<= 3 AND emailer.`TO_SEND`!='' LIMIT 99";
        return this.jdbcTemplate.query(query, new EmailerRowMapper());
    }

    @Override
    public void updateEmail(int status, int attempts, int emailerId, String response) {
        try {
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            String query = "UPDATE emailer e SET e.`STATUS`=?,e.`ATTEMPTS`=?, e.`LAST_MODIFIED_DATE`=?, e.`RESPONSE`=? "
                    + " WHERE e.`EMAIL_ID`=?";
            this.jdbcTemplate.update(query, status, attempts, curDate, response, emailerId);
        } catch (Exception ex) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
        }
    }
}
