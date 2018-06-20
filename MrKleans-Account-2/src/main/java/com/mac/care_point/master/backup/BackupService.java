package com.mac.care_point.master.backup;

import com.mac.care_point.master.backup.model.MBackup;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BackupService {

    @Autowired
    private BackupRepository backupRepository;

    public String getLastBackupDate() {
        return backupRepository.findOne(1).getDate();
         
    }

    public Integer updateNewDate() {
        MBackup backup = new MBackup();
        backup.setIndexNo(1);
        backup.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Integer indexNo = backupRepository.save(backup).getIndexNo();
        if (indexNo>0) {
            System.out.println("Backup Creater Successfully !");
        }
        return indexNo;
    }

}
