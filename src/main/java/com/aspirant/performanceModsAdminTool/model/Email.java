/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author Ritesh
 */
public class Email implements Serializable{
    private int emailerId;
    private Ticket ticket;
    private String toSend;
    private String ccToSend;
    private String subject;
    private String body;
    private Date createdDate;
    private Date lastModifiedDate;
    private Date toSendDate;
    private int status;
    private int attempts;
    private String response;
    private String sendFrom;
    private List<MimeBodyPart> attachementList;
    private String recipients;
    private int level;

    public int getEmailerId() {
        return emailerId;
    }

    public void setEmailerId(int emailerId) {
        this.emailerId = emailerId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getToSend() {
        return toSend;
    }

    public void setToSend(String toSend) {
        this.toSend = toSend;
    }

    public String getCcToSend() {
        return ccToSend;
    }

    public void setCcToSend(String ccToSend) {
        this.ccToSend = ccToSend;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getToSendDate() {
        return toSendDate;
    }

    public void setToSendDate(Date toSendDate) {
        this.toSendDate = toSendDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public List<MimeBodyPart> getAttachementList() {
        return attachementList;
    }

    public void setAttachementList(List<MimeBodyPart> attachementList) {
        this.attachementList = attachementList;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
