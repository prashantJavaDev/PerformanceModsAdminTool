/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.EmailerDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Email;
import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import com.aspirant.performanceModsAdminTool.service.EmailerService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class EmailerServiceImpl implements EmailerService {

    @Autowired
    private EmailerDao emailerDao;
    @Autowired
    @Qualifier("javaMailSender")
    JavaMailSenderImpl javaMailSend;
    

    @Override
    public EmailTemplate getEmailTemplateByTemplateId(int emailTemplateId) {
        return this.emailerDao.getEmailTemplateByTemplateId(emailTemplateId);
    }

    @Override
    public int addEmail(Email email) {
        return this.emailerDao.addEmail(email);
    }

    @Override
    public Email buildEmail(int emailTemplateId, String toSend, String[] subjectParam, String[] bodyParam, EmailTemplate emailTemplate) {
        return this.emailerDao.buildEmail(emailTemplateId, toSend, subjectParam, bodyParam, emailTemplate);
    }

    @Override
    public void sendEmail(Email email) {
        int status = email.getStatus(), attempts = email.getAttempts();
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog("EmailId =" + email.getEmailerId() + " Status =" + status + " Attempts=" + attempts, GlobalConstants.TAG_SYSTEMLOG));
        String response = null;
        try {

            Address addressFrom;
            MimeMessage msg = this.javaMailSend.createMimeMessage();
            addressFrom = new InternetAddress("ritesh.b@ubuntu.cc", "performanceMods Admin");//this can be anything
            msg.setFrom(addressFrom);
            msg.setRecipients(MimeMessage.RecipientType.TO, email.getToSend());
            if (email.getCcToSend() != null) {
                msg.setRecipients(MimeMessage.RecipientType.CC, email.getCcToSend());
            }
            msg.setSubject(email.getSubject());
            msg.setContent(email.getBody(), "text/html");
            this.javaMailSend.send(msg);
            status = 2;
            attempts++;
            response = "Success";
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Email sent successfully", GlobalConstants.TAG_SYSTEMLOG));
            this.emailerDao.updateEmail(status, attempts, email.getEmailerId(), response);
        } catch (MessagingException ex) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog("Email failed", GlobalConstants.TAG_SYSTEMLOG));
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
            response = ex.toString();
            attempts++;
            status = 1;
            this.emailerDao.updateEmail(status, attempts, email.getEmailerId(), response);
        } catch (UnsupportedEncodingException ex) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog("Email failed", GlobalConstants.TAG_SYSTEMLOG));
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
            response = ex.toString();
            attempts++;
            status = 1;
            this.emailerDao.updateEmail(status, attempts, email.getEmailerId(), response);
        } catch (Exception ex) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Email failed", GlobalConstants.TAG_SYSTEMLOG));
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(ex, GlobalConstants.TAG_SYSTEMLOG));
            response = ex.toString();
            attempts++;
            status = 1;
            this.emailerDao.updateEmail(status, attempts, email.getEmailerId(), response);
        }
    }

    @Override
    public List<Email> getEmailerList() {
        return this.emailerDao.getEmailerList();
    }

    
}
