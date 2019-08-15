/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.deposit;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountRepository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.deposit.model.DepositMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class DepositService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccAccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    TAccLedger saveFundTransfer(DepositMix depositMix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.DEPOSIT);
        int deleteNumber = journalRepository.getDeleteNumber();
        MAccSetting unrealizedIssued = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
        MAccSetting unrealizedReceived = accountSettingRepository.findByName(Constant.UNREALIZED_RECEIVED);
        String searchCode = getSearchCode(Constant.CODE_DEPOSIT, SecurityUtil.getCurrentUser().getBranch(), number);
        boolean isCheque=false;
        isCheque = depositMix.getData().getIsCheque();
        depositMix.getData().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        depositMix.getData().setNumber(number);
        depositMix.getData().setDeleteRefNo(deleteNumber);
        depositMix.getData().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        depositMix.getData().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        depositMix.getData().setUser(SecurityUtil.getCurrentUser().getBranch());
        depositMix.getData().setIsMain(true);
        depositMix.getData().setIsEdit(0);
        depositMix.getData().setSearchCode(searchCode);
        depositMix.getData().setType(Constant.DEPOSIT);
        TAccLedger save = journalRepository.save(depositMix.getData());
        save.setIsEdit(0);

        MAccAccount account = accountRepository.findOne(depositMix.getData().getAccAccount());
        if ("BANK".equals(account.getAccType())) {
            save.setReconcileAccount(depositMix.getData().getAccAccount());
            save.setAccAccount(unrealizedIssued.getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setBankReconciliation(true);
            save=journalRepository.save(save);
        }
        if ("CHEQUE".equals(account.getAccType())) {
            save.setReconcileAccount(depositMix.getData().getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setBankReconciliation(true);
            journalRepository.save(save);
        }
        int size=0;
        for (TAccLedger tAccLedger : depositMix.getDataList()) {
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setIsMain(false);
            tAccLedger.setIsEdit(0);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setIsCheque(false);
            tAccLedger.setType(Constant.DEPOSIT);
            if (isCheque) {
                tAccLedger.setIsCheque(true);
            }
            tAccLedger.setTransactionDate(depositMix.getData().getTransactionDate());
            TAccLedger save1 = journalRepository.save(tAccLedger);
            save1.setIsEdit(0);
            
            if ("BANK".equals(account.getAccType())) {
                save1.setReconcileAccount(tAccLedger.getAccAccount());
                save1.setAccAccount(unrealizedReceived.getAccAccount());
                save1.setReconcileGroup(save1.getIndexNo());
                save1.setBankReconciliation(true);
                journalRepository.save(save1);
            }
            if ("CHEQUE".equals(account.getAccType())) {
                save1.setRefNumber(depositMix.getData().getRefNumber());
                journalRepository.save(save1);
            }
            size++;
        }
        
        if (depositMix.getDataList().size()==size) {
            return save;
            
        }
        throw new RuntimeException("Deposit Save Fail !");
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> findDepositByNumberAndBranch(Integer number, Integer branch) {
        return journalRepository.findByNumberAndBranchAndType(number, branch, Constant.DEPOSIT);
    }

}
