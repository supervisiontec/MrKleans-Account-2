/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.zutil.SecurityUtil;
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
@RequestMapping("/api/care-point/transaction/journal")
public class JournalController {
    
    @Autowired
    private JournalService journalService;
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer saveJournal(@RequestBody List<TAccLedger> list) {
        return journalService.saveJournal(list);
    }
    @RequestMapping(value = "/find-journal-and-branch/{number}", method = RequestMethod.GET)
    public List<TAccLedger> findJournalByNumberAndBranch(@PathVariable("number")Integer number) {
        return journalService.findJournalByNumberAndBranch(number, SecurityUtil.getCurrentUser().getBranch());
    }
    @RequestMapping(value = "/find-general-voucher-number-and-branch/{number}", method = RequestMethod.GET)
    public List<TAccLedger> findGeneralVoucherByNumberAndBranch(@PathVariable("number")Integer number) {
        return journalService.findGeneralVoucherByNumberAndBranch(number, SecurityUtil.getCurrentUser().getBranch());
    }
    
}
