/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.common.Constant;
import com.mac.care_point.common.SearchCodeGenarator;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
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
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
   
    @Autowired
    private BranchRepository branchRepository;

    public Integer saveJournal(List<TAccLedger> list) {
        int count = 0;
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.JOURNAL);
        int deleteNumber = journalRepository.getDeleteNumber();
        String searchCode=getSearchCode(Constant.CODE_JOURNAL,SecurityUtil.getCurrentUser().getBranch(),number);
        String transactionDate=null;
        for (TAccLedger tAccLedger : list) {
            transactionDate=tAccLedger.getTransactionDate();
            tAccLedger.setNumber(number);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setFormName(Constant.FORM_JOURNAL);
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setType(Constant.JOURNAL);
            tAccLedger.setTransactionDate(transactionDate);
            tAccLedger.setReconcileAccount(null);
            tAccLedger.setBankReconciliation(false);
            tAccLedger.setIsMain(Boolean.FALSE);
            tAccLedger.setIsCheque(Boolean.FALSE);
            TAccLedger save = journalRepository.save(tAccLedger);
            if (save.getIndexNo() != null) {
                count++;
            }
        }
        if (count == list.size()) {
            return count;
        }
        return null;
    }

    private String getSearchCode(String code, Integer branch, int number) {
         MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> findJournalByNumberAndBranch(Integer number, Integer branch) {
        return journalRepository.findByNumberAndBranchAndType(number, branch, Constant.JOURNAL);
    }

    public List<TAccLedger> findGeneralVoucherByNumberAndBranch(Integer number, Integer branch) {
        return journalRepository.findByNumberAndBranchAndType(number, branch, Constant.VOUCHER);
    }


}
