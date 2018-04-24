/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.voucher;

import com.mac.care_point.account_setting.acc_code_setting.AccCodeSettingRepository;
import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.voucher.model.VoucherMix;
import com.mac.care_point.zutil.SecurityUtil;
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

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AccCodeSettingRepository accCodeSettingRepository;

    public Integer saveVoucher(VoucherMix voucherMix) {

        AccCodeSetting accCodeSetting = accCodeSettingRepository.findByAccount(voucherMix.getVoucher().getAccAccount());
        if (accCodeSetting == null) {
            throw new RuntimeException("Accout Code Setting not Found for this Account !");
        }
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.VOUCHER);
        int deleteNumber = journalRepository.getDeleteNumber();
        MAccSetting unrealisticChequeAccount = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
        String searchCode = getSearchCode(Constant.CODE_PAY, SecurityUtil.getCurrentUser().getBranch(), accCodeSetting);

        voucherMix.getVoucher().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        voucherMix.getVoucher().setNumber(number);
        voucherMix.getVoucher().setDeleteRefNo(deleteNumber);
        voucherMix.getVoucher().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        voucherMix.getVoucher().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        voucherMix.getVoucher().setUser(SecurityUtil.getCurrentUser().getBranch());
        voucherMix.getVoucher().setReconcileGroup(0);
        voucherMix.getVoucher().setIsMain(true);
        voucherMix.getVoucher().setSearchCode(searchCode);
        voucherMix.getVoucher().setIsCheque(false);
        TAccLedger save = journalRepository.save(voucherMix.getVoucher());
        if (voucherMix.getVoucher().getBankReconciliation()) {
            save.setReconcileAccount(voucherMix.getVoucher().getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setIsCheque(true);
            save.setAccAccount(unrealisticChequeAccount.getAccAccount());
            journalRepository.save(save);
        }
        for (TAccLedger tAccLedger : voucherMix.getVoucherList()) {
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTransactionDate(voucherMix.getVoucher().getTransactionDate());
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setIsMain(false);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setIsCheque(false);
            journalRepository.save(tAccLedger);
        }
        accCodeSetting.setMaxNo(accCodeSetting.getMaxNo() + 1);
        AccCodeSetting save1 = accCodeSettingRepository.save(accCodeSetting);
        if (save1.getIndexNo() > 0) {
            return save1.getIndexNo();
        } else {
            throw new RuntimeException("Voucher Save Fail !");
        }
    }

    private String getSearchCode(String code, Integer branch, AccCodeSetting accCodeSetting) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + accCodeSetting.getCode() + "/" + (accCodeSetting.getMaxNo() + 1);
    }

}
