/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.return_cheque;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.common.SearchCodeGenarator;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.return_cheque.model.ReturnChequeMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
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
public class ReturnChequeService {

    @Autowired
    private ReturnChequeRepository returnChequeRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private BranchRepository branchRepository;

    public List<TAccLedger> getCheques(String type) {
        if ("INHAND".equals(type)) {
            return null;
        }
        if ("DEPOSITED".equals(type)) {
            return null;
        }
        if ("ISSUED".equals(type)) {
            MAccSetting setting = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
            return returnChequeRepository.getCheques(setting.getAccAccount());
        }
        return null;
    }

    public List<TAccLedger> getSelectedChequeDetails(Integer delIndex) {
        return returnChequeRepository.findByDeleteRefNo(delIndex);
    }

    public Integer save(ReturnChequeMix mix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.CHEQUE_RETURN);
        int deleteNumber = journalRepository.getDeleteNumber();
        String searchCode = getSearchCode(Constant.CODE_CHEQUE_RETURN, SecurityUtil.getCurrentUser().getBranch(), number);

        Integer saveCount = 0;
        for (TAccLedger tAccLedger : mix.getDataList()) {
            BigDecimal debit = tAccLedger.getDebit();
            BigDecimal credit = tAccLedger.getCredit();
            tAccLedger.setIndexNo(null);
            tAccLedger.setDescription(mix.getData().getDescription());
            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
            tAccLedger.setType(Constant.CHEQUE_RETURN);
            tAccLedger.setFormName(Constant.FORM_CHEQUE_RETURN);
            tAccLedger.setNumber(number);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            tAccLedger.setDebit(credit);
            tAccLedger.setCredit(debit);
            tAccLedger.setIsMain(false);

            TAccLedger save = returnChequeRepository.save(tAccLedger);
            save.setReconcileGroup(save.getIndexNo());
            returnChequeRepository.save(save);
            saveCount++;

        }
        if (saveCount == mix.getDataList().size()) {
            return saveCount;
        }
        return -1;
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }
}
