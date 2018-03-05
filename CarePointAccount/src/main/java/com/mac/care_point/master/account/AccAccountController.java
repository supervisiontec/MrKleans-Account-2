/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account;

import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
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
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/account/master/acc-account")
public class AccAccountController {

    private final String overPaymentIssue="over_payment_issue";
    
    @Autowired
    private AccAccountService accAccountService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MAccAccount> findAll() {
        return accAccountService.findAll();
    }

    @RequestMapping(value = "/save-new-account", method = RequestMethod.POST)
    public MAccAccount saveNewAccount(@RequestBody MAccAccount accAccount) {
        accAccount.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        return accAccountService.saveNewAccount(accAccount);
    }
//
    @RequestMapping(value = "/delete-account/{index}", method = RequestMethod.DELETE)
    public Integer saveNewAccount(@PathVariable Integer index) {
        return accAccountService.deleteAccount(index);
    }
    @RequestMapping(value = "/find-only-account", method = RequestMethod.GET)
    public List<MAccAccount> findAccount() {
        return accAccountService.findByIsAccAccount(true);
    }
    @RequestMapping(value = "/find-only-cahs-bank", method = RequestMethod.GET)
    public List<MAccAccount> findCahsBank() {
        return accAccountService.findByIsAccAccountAndAccTypeOrAccType(true,"CASH","BANK");
    }
    @RequestMapping(value = "/find-over-payment-issue-account", method = RequestMethod.GET)
    public MAccAccount getOverPaymentIssueAccount() {
        return accAccountService.getOverPaymentIssueAccount(overPaymentIssue);
    }
//    type='income','expense','asset','liability','capital'
    @RequestMapping(value = "/find-type-accounts", method = RequestMethod.GET)
    public List<MAccAccount> findTypeAccount() {
        return accAccountService.findTypeAccount("EXPENSE");
    }
    @RequestMapping(value = "/get-account-flow/{acc}", method = RequestMethod.GET)
    public List<MAccAccount> getAccountFlow(@PathVariable Integer acc) {
        return accAccountService.getAccFlow(acc);
    }
    @RequestMapping(value = "/find-account-value/{index}", method = RequestMethod.GET)
    public BigDecimal findAccountValue(@PathVariable Integer index) {
        return accAccountService.findAccountValue(SecurityUtil.getCurrentUser().getBranch(), index);
    }
    @RequestMapping(value = "/find-account-value-all/{index}", method = RequestMethod.GET)
    public BigDecimal findAccountValuewithBranch(@PathVariable Integer index) {
        return accAccountService.findAccountValue(index);
    }
}
