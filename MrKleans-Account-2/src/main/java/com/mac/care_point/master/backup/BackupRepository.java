/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.backup;

import com.mac.care_point.master.backup.model.MBackup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface BackupRepository extends JpaRepository<MBackup, Integer>{

    public MBackup findByIndexNo(int i);
    
}
