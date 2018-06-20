/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.zutil;

import com.mac.care_point.master.backup_detail.model.MBackupDetail;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author kasun
 */
public class Backup {

    public static boolean startBackup(String lastBackupDate,MBackupDetail backupDetail) {

        String serverDate;
        serverDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (lastBackupDate == null ? serverDate == null : lastBackupDate.equals(serverDate)) {
            System.out.println("System Backup already exists !");
            return false;
        } else {
            System.out.println("backup creating !");
            return createBackup(backupDetail);
        }
    }

    public static boolean createBackup(MBackupDetail backupDetail) {
        try {
            /*NOTE: Creating Database Constraints and other properties*/
//            Properties prop = new Properties();
            InputStream input = null;
            String userName = backupDetail.getUserName();
            String host = backupDetail.getHost();
            String dbName = backupDetail.getSchema();
            String password = backupDetail.getPassword();
            String backupPath = "./backups\\";
            String fileName;


            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*NOTE: Used to create a cmd command*/
            Date date = new Date();
            String formatDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
//            fileName = dbName + "-" + formatDate + ".sql";
            fileName = dbName + "-" + formatDate + ".sql";
            String executeCmd = "mysqldump -h" + host + " -u" + userName + " -p" + password + " " + dbName + " -r " + backupPath + fileName;
            System.out.println(executeCmd);
            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int waitFor = runtimeProcess.waitFor();
            System.out.println("waitFor : "+waitFor);

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (1 == waitFor) {
                System.out.println(" ");
                System.out.println("Backup Completed ->" + executeCmd);
                System.out.println(" ");
                return true;
            } else {
                System.out.println("Backup Fail");
                return false;
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
        return false;
    }

}
