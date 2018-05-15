/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.acc_ledger_type;

import com.mac.care_point.master.acc_ledger_type.model.mAccLedgerType;
import java.util.List;
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
@RequestMapping("/api/care-point/master/acc-ledger-type")
public class MAccLedgerTypeController {
    @Autowired
    private MAccLedgerTypeService accLedgerTypeService;
    
     @RequestMapping(value = "/find-active", method = RequestMethod.GET)
    public List<mAccLedgerType> findActive() {
        return accLedgerTypeService.findByIsActive();
    }
}
