/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.bank_reconciliation;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.bank_reconciliation.model.ReconciliationMix;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/bank-reconciliation")
public class BankReconciliationController {

    @Autowired
    private BankReconciliationService bankReconciliationService;
    
    @RequestMapping(value = "/get-reconciliation-detail/{date}/{accAccount}", method = RequestMethod.GET)
    public List<TAccLedger> getReconciliationDetail(@PathVariable String date,@PathVariable Integer accAccount) {
        return bankReconciliationService.getReconciliationDetail(date,accAccount);
    }
    @RequestMapping(value = "/get-start-balance/{fDate}/{accAccount}", method = RequestMethod.GET)
    public double getStartBalance(@PathVariable String fDate,@PathVariable Integer accAccount) {
        return bankReconciliationService.getStartBalance(fDate,accAccount);
    }
    @RequestMapping(value = "/load-transactions/{year}/{month}/{accAccount}", method = RequestMethod.GET)
    public List<TAccLedger> loadTransactions(@PathVariable Integer year,@PathVariable Integer month,@PathVariable Integer accAccount) {
        return bankReconciliationService.loadTransactions(year,month,accAccount);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody ReconciliationMix data) {
        return bankReconciliationService.save(data);
    }
    @RequestMapping(value = "/savePopup", method = RequestMethod.POST)
    public void savePopup(@RequestBody ReconciliationMix data) {
        System.out.println("srart");
        System.out.println(data.getDataList().size());
         bankReconciliationService.savePopup(data);
    }
    @RequestMapping(value = "/get-delete-reconciliation-cheque/{index}", method = RequestMethod.GET)
    public TAccLedger save(@PathVariable Integer index) {
        return bankReconciliationService.getDeleteReconciliationCheque(index);
    }
    @RequestMapping(value = "/delete/{index}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable Integer index) {
         bankReconciliationService.delete(index);
         return index;
    }
}
