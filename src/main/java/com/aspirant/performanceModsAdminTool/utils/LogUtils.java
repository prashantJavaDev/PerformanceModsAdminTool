
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.utils;

import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
//import com.aspirantutils.StringUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author gaurao
 */
public class LogUtils {

    @Autowired
    public static Logger systemLogger = Logger.getLogger("systemLogging");

    public static String buildStringForLog(Exception e, String tags) {
        StringWriter sWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(sWriter));
        return new StringBuilder(getIpAddress()).append("','").append(getUsername()).append("','").append(sWriter).append("','").append(tags).toString();
    }

    public static String buildStringForLog(String ipAddress, String username, String message, String tags) {
        StringBuilder sb = new StringBuilder(ipAddress).append("','").append(username).append("','").append(message).append("','").append(tags);
        return (sb.toString());
    }

    public static String buildStringForLog(String msg, String tags) {
        return new StringBuilder(getIpAddress()).append("','").append(getUsername()).append("','").append(msg).append("','").append(tags).toString();
    }

    public static String buildStringForLog(String sqlString, Object[] params, String tags) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append("','").append(getUsername()).append("','").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Object tmpParam : params) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpParam);
            } else {
                sb.append(", ").append(tmpParam.toString());
            }
            sb.append("','").append(tags);
        }
        return (sb.toString());
    }

    public static String buildStringForLog(String sqlString, List<Object[]> paramList, String tags) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append("','").append(getUsername()).append("','").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Object params[] : paramList) {
            sb.append(" (");
            for (Object tmpParam : params) {
                if (firstRun) {
                    firstRun = false;
                    sb.append(tmpParam.toString());
                } else {
                    sb.append(", ").append(tmpParam.toString());
                }
            }
            sb.append(")").append("','").append(tags);
        }
        return (sb.toString());
    }

    public static String buildStringForLog(String sqlString, Map<String, Object> params, String tags) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append("','").append(getUsername()).append("','").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Map.Entry tmpEntry : params.entrySet()) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            } else {
                sb.append(", ").append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            }
        }
        sb.append(")").append("','").append(tags);
        return (sb.toString());
    }

    private static String getIpAddress() {
        try {
            return ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        } catch (NullPointerException n) {
            return "0.0.0.0";
        }
    }
 
    private static String getUsername() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            } else {
                return "";
            }
        } catch (NullPointerException n) {
            return "blank";
        }
    }
}
