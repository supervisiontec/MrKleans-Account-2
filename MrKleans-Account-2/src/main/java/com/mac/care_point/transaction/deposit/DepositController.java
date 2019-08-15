/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.deposit;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.deposit.model.DepositMix;
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
@RequestMapping("/api/care-point/transaction/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public TAccLedger saveJournal(@RequestBody DepositMix depositMix) {
        return depositService.saveFundTransfer(depositMix);
    }
    @RequestMapping(value = "/find-deposit-by-number-and-branch/{number}", method = RequestMethod.GET)
    public List<TAccLedger> findDepositByNumberAndBranch(@PathVariable("number") Integer number) {
        return depositService.findDepositByNumberAndBranch(number, SecurityUtil.getCurrentUser().getBranch());
    }
}
