
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ritesh
 */
public class TestFTP {

    public static void main(String[] args) throws IOException {
        String server = "partnerreports.ingrammicro.com";
        int port = 21;
        String user = "885347";
        String pass = "fs8qKX";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: using retrieveFile(String, OutputStream)
//            String remoteFile1 = "FUSION/US/AVAIL/TOTAL.ZIP";
//            File downloadFile1 = new File("/home/ubuntu/Desktop/TOTAL.ZIP");
//            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
//            ftpClient.retrieveFile(remoteFile1, outputStream1);
//            outputStream1.close();
//
//            String remoteFile2 = "/FUSION/US/ELYK8N/PRICE.ZIP";
//            File downloadFile2 = new File("/home/ubuntu/Desktop/PRICE.ZIP");
//            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
//            ftpClient.retrieveFile(remoteFile2, outputStream2);
//            outputStream2.close();
//
//            String zipTotalFilePath = "/home/ubuntu/Desktop/TOTAL.ZIP";
//            String destDir = "/home/ubuntu/Desktop/Ingram";
//            unzip(zipTotalFilePath, destDir);
//
//            String zipPriceFilePath = "/home/ubuntu/Desktop/PRICE.ZIP";
//            unzip(zipPriceFilePath, destDir);

            String FILENAME = "/home/ubuntu/Desktop/Ingram/PRICE.TXT";
            String[] dataFields = new String[24];
            try {
                BufferedReader br;
                br = new BufferedReader(new FileReader(FILENAME));
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    FeedUpload fileupload = new FeedUpload();
                    dataFields = sCurrentLine.split(",", -1);
                    System.out.println("Action Indicator----" + dataFields[0].trim());
                    System.out.println("Ingram Part Number----" + dataFields[1].trim());
                    System.out.println("Ingram Use----" + dataFields[2].trim());
                    System.out.println("Vendor Name----" + dataFields[3].trim());
                    System.out.println("Description Line 1----" + dataFields[4].trim());
                    System.out.println("Description Line 2----" + dataFields[5].trim());
                    System.out.println("Retail price----" + dataFields[6].trim());
                    System.out.println("Vendor Part Number----" + dataFields[7].trim());
                    System.out.println("Weight----" + dataFields[8].trim());
                    System.out.println("UPC----" + dataFields[9].trim());
                    System.out.println("Customer Price----" + dataFields[14].trim());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
