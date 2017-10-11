/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface JournalRepository extends JpaRepository<TAccLedger, Integer>{
    
}
