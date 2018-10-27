/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 *
 * @author gaurao
 */
public class Resource {

    public static String getWebInfPath() throws FileNotFoundException {
        String fileName = "Resource.class";
        String tmpPath = getPath(fileName);
        String classPath = "classes/cc/aspirant/performanceModsAdminTool/utils/";
        return tmpPath.substring(0, tmpPath.length() - classPath.length());
    }

    public static String getPath(String fileName) throws FileNotFoundException {
        String fp = getFullPath(fileName);
        return fp.substring(0, fp.length() - fileName.length());
    }

    public static String getFullPath(String fileName) throws FileNotFoundException {
        URL url = Resource.class.getResource(fileName);
        if (url == null) {
            throw new FileNotFoundException();
        } else {
            String fullPath;
            try {
                fullPath = java.net.URLDecoder.decode(url.getPath(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new FileNotFoundException();
            }
            return fullPath;
        }
    }
}
