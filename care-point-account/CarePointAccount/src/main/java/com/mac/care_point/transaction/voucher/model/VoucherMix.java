/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.voucher.model;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;

/**
 *
 * @author 'Kasun Chamara'
 */
public class VoucherMix {
    private TAccLedger voucher;
    private List<TAccLedger> voucherList;

    public VoucherMix() {
    }

    public VoucherMix(TAccLedger voucher, List<TAccLedger> voucherList) {
        this.voucher = voucher;
        this.voucherList = voucherList;
    }

    public TAccLedger getVoucher() {
        return voucher;
    }

    public void setVoucher(TAccLedger voucher) {
        this.voucher = voucher;
    }

    public List<TAccLedger> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<TAccLedger> voucherList) {
        this.voucherList = voucherList;
    }

    
    
}
