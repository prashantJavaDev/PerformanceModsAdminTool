/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Email;
import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import java.util.List;


/**
 *
 * @author Ritesh
 */


public interface EmailerService {
    /**
     * This method used for get Email template of email by email template Id 
     * 
     * @param emailTemplateId template ID of an email
     * @return EmailTemplate
     */
    public EmailTemplate getEmailTemplateByTemplateId(int emailTemplateId);
    
    /**
     * This method is used to insert email data in to table
     * 
     * @param email object of email
     * @return id of an email
     */
    public int addEmail(Email email);

    /**
     * This method is used to build email to send
     * 
     * @param emailTemplateId template ID of an email
     * @param toSend email address for sender
     * @param subjectParam parameter for the subject
     * @param bodyParam parameter for the body
     * @param emailTemplate template of an email
     * @return object of an email
     */
    public Email buildEmail(int emailTemplateId, String toSend, String subjectParam[], String bodyParam[], EmailTemplate emailTemplate);

    /**
     * This method is used to send actual email
     * 
     * @param email object of an email
     */
    public void sendEmail(Email email);

    /**
     * This method is used to get list of email
     * 
     * @return list of email
     */
    public List<Email> getEmailerList();
}
