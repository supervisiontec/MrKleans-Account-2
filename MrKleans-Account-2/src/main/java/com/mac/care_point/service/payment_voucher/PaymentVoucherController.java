/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.payment_voucher;

import com.mac.care_point.service.payment_voucher.model.BalancePaymentModel;
import com.mac.care_point.service.payment_voucher.model.PaymentVoucherModel;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.supplier_payment.model.SupplierPaymentMix;
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
@RequestMapping("/api/care-point/transaction/customer-payment-voucher")
public class PaymentVoucherController {

    @Autowired
    public PaymentVoucherService paymentVoucherService;

    @RequestMapping(value = "/get-client-balance/{client}", method = RequestMethod.GET)
    public Double loadclientBalance(@PathVariable Integer client) {
        return paymentVoucherService.getClientBalance(client);
    }

    @RequestMapping(value = "/get-over-payment-amount/{client}", method = RequestMethod.GET)
    public Double getClientOverPayment(@PathVariable Integer client) {
        return paymentVoucherService.getClientOverPayment(client);
    }
//    @RequestMapping(value = "/get-over-payment-amount/{supplier}", method = RequestMethod.GET)
//    public double getOverPaymentAmount(@PathVariable Integer supplier) {
//        return paymentSevrice.getOverPaymentAmount(supplier);
//
//    }

    @RequestMapping(value = "/get-balance-invoice-count/{client}", method = RequestMethod.GET)
    public int getBalanceInvoiceCount(@PathVariable Integer client) {
        return paymentVoucherService.getBalanceInvoiceCount(client);
    }

    @RequestMapping(value = "/get-balance-invoice/{client}", method = RequestMethod.GET)
    public List<Object> getBalanceInvoice(@PathVariable Integer client) {
        return paymentVoucherService.getBalanceInvoice(client);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer saveSupplierPayment(@RequestBody SupplierPaymentMix paymentMix) {
        return paymentVoucherService.save(paymentMix);

    }

    @RequestMapping(value = "/save-balance-payment-voucher", method = RequestMethod.POST)
    public Integer saveBalancePaymentVoucher(@RequestBody BalancePaymentModel balancePaymentModel) {
        return paymentVoucherService.saveBalancePaymentVoucher(balancePaymentModel);

    }
     @RequestMapping(value = "/get-payable-bills/{account}", method = RequestMethod.GET)
    public List<TAccLedger> getPayableBills(@PathVariable Integer account) {
        return paymentVoucherService.getPayableBills(account);

    }

}
