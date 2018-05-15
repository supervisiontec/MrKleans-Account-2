/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.acc_ledger_type;

import com.mac.care_point.master.acc_ledger_type.model.mAccLedgerType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface MAccLedgerTypeRepository extends JpaRepository<mAccLedgerType, Integer>{

    public List<mAccLedgerType> findByIsActive(boolean b);
    
}
