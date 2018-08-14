/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.supplier_payment;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.supplier_payment.model.SupplierPaymentMix;
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
@RequestMapping("/api/care-point/transaction/supplier-payment")
public class SupplierPaymentController {

    @Autowired
    private SupplierPaymentSevrice paymentSevrice;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public TAccLedger saveSupplierPayment(@RequestBody SupplierPaymentMix paymentMix) {
        return paymentSevrice.saveSupplierPayment(paymentMix);

    }

    @RequestMapping(value = "/get-payable-bills/{account}", method = RequestMethod.GET)
    public List<TAccLedger> getPayableBills(@PathVariable Integer account) {
        return paymentSevrice.getPayableBills(account);

    }

    @RequestMapping(value = "/get-over-payment-amount/{supplier}", method = RequestMethod.GET)
    public double getOverPaymentAmount(@PathVariable Integer supplier) {
        return paymentSevrice.getOverPaymentAmount(supplier);

    }
    
     @RequestMapping(value = "/find-supplier-payments-by-number-and-branch/{number}", method = RequestMethod.GET)
    public List<TAccLedger> findSupplierPaymentByNumberAndBranch(@PathVariable("number")Integer number) {
        return paymentSevrice.findSupplierPaymentByNumberAndBranch(number, SecurityUtil.getCurrentUser().getBranch());
    }

}
