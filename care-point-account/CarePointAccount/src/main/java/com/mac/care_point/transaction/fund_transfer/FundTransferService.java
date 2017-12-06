/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.fund_transfer;

import com.mac.care_point.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.common.SearchCodeGenarator;
import com.mac.care_point.master.account.AccAccountRepository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.fund_transfer.model.FundTransferMix;
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
public class FundTransferService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccAccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    Integer saveFundTransfer(FundTransferMix fundTransferMix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.FUND_TRANSFER);
        int deleteNumber = journalRepository.getDeleteNumber();
        MAccSetting unrealizedIssued = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
        MAccSetting unrealizedReceived = accountSettingRepository.findByName(Constant.UNREALIZED_RECEIVED);
        String searchCode = getSearchCode(Constant.CODE_FUND_TRANSFER, SecurityUtil.getCurrentUser().getBranch(), number);

        fundTransferMix.getData().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        fundTransferMix.getData().setNumber(number);
        fundTransferMix.getData().setDeleteRefNo(deleteNumber);
        fundTransferMix.getData().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        fundTransferMix.getData().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        fundTransferMix.getData().setUser(SecurityUtil.getCurrentUser().getBranch());
        fundTransferMix.getData().setIsMain(true);
        fundTransferMix.getData().setIsCheque(false);
        fundTransferMix.getData().setSearchCode(searchCode);
        TAccLedger save = journalRepository.save(fundTransferMix.getData());

        MAccAccount account = accountRepository.findOne(fundTransferMix.getData().getAccAccount());
        if ("BANK".equals(account.getAccType())) {
            save.setReconcileAccount(fundTransferMix.getData().getAccAccount());
            save.setAccAccount(unrealizedIssued.getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setBankReconciliation(true);
            journalRepository.save(save);

        }
        for (TAccLedger tAccLedger : fundTransferMix.getDataList()) {
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setIsMain(false);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setIsCheque(false);
            tAccLedger.setTransactionDate(fundTransferMix.getData().getTransactionDate());
            TAccLedger save1 = journalRepository.save(tAccLedger);

            if ("BANK".equals(account.getAccType())) {
                save1.setReconcileAccount(tAccLedger.getAccAccount());
                save1.setAccAccount(unrealizedReceived.getAccAccount());
                save1.setReconcileGroup(save1.getIndexNo());
                save1.setBankReconciliation(true);
                journalRepository.save(save1);

            }
        }

        return 1;
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

}
