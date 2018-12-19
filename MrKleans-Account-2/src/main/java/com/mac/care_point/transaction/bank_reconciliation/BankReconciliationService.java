/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.bank_reconciliation;

import com.mac.care_point.common.Constant;
import com.mac.care_point.common.SearchCodeGenarator;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.bank_reconciliation.model.ReconciliationMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BankReconciliationService {

    @Autowired
    private BankReconciliationRepository bankReconciliationRepository;

    @Autowired
    private JournalRepository journalRepository;
    
     @Autowired
    private BranchRepository branchRepository;

    public List<TAccLedger> getReconciliationDetail(String date, Integer accAccount) {
        return bankReconciliationRepository.getReconciliationDetails(date, accAccount);
    }

    public double getStartBalance(String fDate, Integer accAccount) {
        String oneDate = "2017-01-01";
        return bankReconciliationRepository.getStartBalance(oneDate, fDate, accAccount);
    }

    public List<TAccLedger> loadTransactions(Integer year, Integer month, Integer accAccount) {
        return bankReconciliationRepository.loadTransactions(year, month, accAccount);
    }

    @Transactional
    public boolean save(ReconciliationMix mix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.RECONCILIATION);
        Integer count = 0;
        String searchCode=getSearchCode(Constant.CODE_BANK_RECONCILIATION,SecurityUtil.getCurrentUser().getBranch(),number);
        
        for (TAccLedger tAccLedger : mix.getDataList()) {
            int deleteNo = journalRepository.getDeleteNumber();

            TAccLedger findOne = bankReconciliationRepository.findOne(tAccLedger.getIndexNo());
            TAccLedger tAccLedger1 = new TAccLedger();
            tAccLedger1.setAccAccount(findOne.getAccAccount());
            tAccLedger1.setBankReconciliation(true);
            tAccLedger1.setBranch(findOne.getBranch());
            tAccLedger1.setChequeDate(findOne.getChequeDate());
            tAccLedger1.setCredit(findOne.getDebit());
            tAccLedger1.setDebit(findOne.getCredit());
            tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getIndexNo());
            tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger1.setDescription(findOne.getDescription());
            tAccLedger1.setFormName(Constant.FORM_BANK_RECONCILIATION);
            tAccLedger1.setNumber(number);
            tAccLedger1.setSearchCode(searchCode);
            tAccLedger1.setDeleteRefNo(deleteNo);
            tAccLedger1.setReconcileAccount(findOne.getReconcileAccount());
            tAccLedger1.setRefNumber(findOne.getRefNumber());
            tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger1.setTransactionDate(tAccLedger.getTransactionDate());
            tAccLedger1.setType(Constant.RECONCILIATION);
            tAccLedger1.setTypeIndexNo(null);
            tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            tAccLedger1.setReconcileAccount(findOne.getReconcileAccount());
            tAccLedger1.setReconcileGroup(findOne.getReconcileGroup());
            tAccLedger1.setIsMain(tAccLedger.getIsCheque());
            tAccLedger1.setIsCheque(true);
            tAccLedger1.setIsEdit(0);

            bankReconciliationRepository.save(tAccLedger1);

            TAccLedger bankLedger = new TAccLedger();
            bankLedger.setAccAccount(mix.getData().getAccAccount());
            bankLedger.setBankReconciliation(false);
            bankLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            bankLedger.setChequeDate(null);
            bankLedger.setCredit(findOne.getCredit());
            bankLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bankLedger.setDebit(findOne.getDebit());
            bankLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            bankLedger.setDescription(tAccLedger1.getDescription());
            bankLedger.setFormName(Constant.FORM_BANK_RECONCILIATION);
            bankLedger.setNumber(number);
            bankLedger.setSearchCode(searchCode);
            bankLedger.setDeleteRefNo(deleteNo);
            bankLedger.setReconcileAccount(0);
            bankLedger.setReconcileGroup(0);
            bankLedger.setRefNumber(null);
            bankLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            bankLedger.setTransactionDate(tAccLedger.getTransactionDate());
            bankLedger.setType(Constant.RECONCILIATION);
            bankLedger.setTypeIndexNo(null);
            bankLedger.setIsMain(false);
            bankLedger.setIsEdit(0);
            bankLedger.setIsCheque(false);
            bankLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            bankReconciliationRepository.save(bankLedger);
            count++;

        }
        if (count == mix.getDataList().size()) {
            return true;
        }
        return false;
    }

    public TAccLedger getDeleteReconciliationCheque(Integer index) {
        return bankReconciliationRepository.findOne(index);
    }

    @Modifying
    @Transactional
    public void delete(Integer index) {
        bankReconciliationRepository.deleteByDeleteRefNo(index);
    }

    @Transactional
    public void savePopup(ReconciliationMix data) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.RECONCILIATION);
        String searchCode=getSearchCode(Constant.CODE_BANK_RECONCILIATION,SecurityUtil.getCurrentUser().getBranch(),number);
        
        for (TAccLedger tAccLedger : data.getDataList()) {
            int deleteNo = journalRepository.getDeleteNumber();
            tAccLedger.setBankReconciliation(false);
            tAccLedger.setChequeDate(null);
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setDeleteRefNo(deleteNo);
            tAccLedger.setDescription(tAccLedger.getDescription());
            tAccLedger.setFormName(Constant.FORM_BANK_RECONCILIATION);
            tAccLedger.setNumber(number);
            tAccLedger.setReconcileAccount(null);
            tAccLedger.setReconcileGroup(0);
            tAccLedger.setRefNumber(null);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setType(Constant.RECONCILIATION);
            tAccLedger.setTypeIndexNo(null);
            tAccLedger.setIsMain(false);
            tAccLedger.setIsEdit(0);
            tAccLedger.setIsCheque(false);
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            bankReconciliationRepository.save(tAccLedger);

            BigDecimal credit = tAccLedger.getCredit();
            BigDecimal debit = tAccLedger.getDebit();
            TAccLedger bankLedger = new TAccLedger();
            bankLedger.setAccAccount(data.getData().getAccAccount());
            bankLedger.setBankReconciliation(false);
            bankLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            bankLedger.setChequeDate(null);
            bankLedger.setCredit(debit);
            bankLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bankLedger.setDebit(credit);
            bankLedger.setSearchCode(searchCode);
            bankLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            bankLedger.setDescription(tAccLedger.getDescription());
            bankLedger.setFormName(Constant.FORM_BANK_RECONCILIATION);
            bankLedger.setNumber(number);
            bankLedger.setDeleteRefNo(deleteNo);
            bankLedger.setReconcileAccount(null);
            bankLedger.setReconcileGroup(0);
            bankLedger.setRefNumber(null);
            bankLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            bankLedger.setTransactionDate(tAccLedger.getTransactionDate());
            bankLedger.setType(Constant.RECONCILIATION);
            bankLedger.setTypeIndexNo(null);
            bankLedger.setIsMain(true);
            bankLedger.setIsEdit(0);
            bankLedger.setIsCheque(false);
            bankLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            bankReconciliationRepository.save(bankLedger);
        }

    }
     private String getSearchCode(String code, Integer branch, int number) {
         MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }
    
}
