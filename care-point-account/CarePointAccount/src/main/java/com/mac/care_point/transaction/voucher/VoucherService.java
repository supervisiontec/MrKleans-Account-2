/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.voucher;

import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.voucher.model.VoucherMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VoucherService {

    @Autowired
    private JournalRepository journalRepository;

    public Integer saveVoucher(VoucherMix voucherMix) {

        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), "VOUCHER");
        int deleteNumber = journalRepository.getDeleteNumber();

        voucherMix.getVoucher().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        voucherMix.getVoucher().setNumber(number);
        voucherMix.getVoucher().setDeleteRefNo(deleteNumber);
        voucherMix.getVoucher().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        voucherMix.getVoucher().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        voucherMix.getVoucher().setUser(SecurityUtil.getCurrentUser().getBranch());
        TAccLedger save = journalRepository.save(voucherMix.getVoucher());
        if (voucherMix.getVoucher().getBankReconciliation()) {
            save.setReconciliationGroup(save.getIndexNo());
            journalRepository.save(save);

        }
        for (TAccLedger tAccLedger : voucherMix.getVoucherList()) {
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            journalRepository.save(tAccLedger);
        }

        return 1;
    }

}
