/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.fund_transfer;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.fund_transfer.model.FundTransferMix;
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
@RequestMapping("/api/care-point/transaction/fund-transfer")
public class FundTransferController {
    @Autowired
    private FundTransferService fundTransferService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer saveJournal(@RequestBody FundTransferMix fundTransferMix) {
        return fundTransferService.saveFundTransfer(fundTransferMix);
    }
    @RequestMapping(value = "/find-fund-transfer-by-number-and-branch/{number}", method = RequestMethod.GET)
    public List<TAccLedger> findFundTransferByNumberAndBranch(@PathVariable("number") Integer number) {
        return fundTransferService.findFundTransferByNumberAndBranch(number, SecurityUtil.getCurrentUser().getBranch());
    }
}
