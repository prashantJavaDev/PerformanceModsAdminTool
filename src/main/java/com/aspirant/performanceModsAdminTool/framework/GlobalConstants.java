/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.framework;

/**
 *
 * @author gaurao
 */
public class GlobalConstants {

    // General values
    public static String majorVersion;
    public static String minorVersion;
    /**
     * sets the default value for PAGE_SIZE=100
     *
     */
    public static int PAGE_SIZE = 50;
    /**
     * sets the value for array of ALLOWED_IP_RANGE={"127.0.0.1",
     * "10.1.2.1-10.1.2.254","10.1.3.1-10.1.3.254"}
     *
     */
    public static String[] ALLOWED_IP_RANGE = new String[]{"127.0.0.1", "10.1.2.1-10.1.2.254", "10.1.3.1-10.1.3.254"};

    public static String TAG_ACCESSLOG = "ACCESS";
    public static String TAG_SYSTEMLOG = "SYSTEM";
    public static String TAG_SCHEDULERLOG = "SCHEDULER";
}
