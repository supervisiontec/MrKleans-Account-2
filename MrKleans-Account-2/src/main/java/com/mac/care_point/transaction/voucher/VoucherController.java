/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.voucher;

//import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.voucher.model.VoucherMix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/care-point/transaction/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer saveJournal(@RequestBody VoucherMix voucherMix) {
        return voucherService.saveVoucher(voucherMix);

    }
}
