/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.backup_detail;

import com.mac.care_point.master.backup_detail.model.MBackupDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/backup_detail")
public class BackupDetailController {

    @Autowired
    private BackupDetailService backupDetailService;

   @RequestMapping(method = RequestMethod.GET)
    public MBackupDetail getDetail() {
        return backupDetailService.findAll();
    }
}
