/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.bank_reconciliation;

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
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), "RECONCILIATION");
        Integer count = 0;
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
            tAccLedger1.setFormName("BANK RECONCILIATION");
            tAccLedger1.setNumber(number);
            tAccLedger1.setDeleteRefNo(deleteNo);
            tAccLedger1.setReconciliationGroup(findOne.getReconciliationGroup());
            tAccLedger1.setRefNumber(findOne.getRefNumber());
            tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger1.setTransactionDate(tAccLedger.getTransactionDate());
            tAccLedger1.setType("RECONCILIATION");
            tAccLedger1.setTypeIndexNo(null);
            tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

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
            bankLedger.setDescription(findOne.getDescription());
            bankLedger.setFormName("BANK RECONCILIATION");
            bankLedger.setNumber(number);
            bankLedger.setDeleteRefNo(deleteNo);
            bankLedger.setReconciliationGroup(0);
            bankLedger.setRefNumber(null);
            bankLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            bankLedger.setTransactionDate(tAccLedger.getTransactionDate());
            bankLedger.setType("RECONCILIATION");
            bankLedger.setTypeIndexNo(null);
            bankLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            bankReconciliationRepository.save(bankLedger);
            count++;

        }
        if (count == mix.getDataList().size()) {
            System.out.println(count);
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
        int count = 0;
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), "RECONCILIATION");
        for (TAccLedger tAccLedger : data.getDataList()) {
            int deleteNo = journalRepository.getDeleteNumber();
            tAccLedger.setBankReconciliation(false);
            tAccLedger.setChequeDate(null);
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setDeleteRefNo(deleteNo);
            tAccLedger.setDescription("other bank reconciliation");
            tAccLedger.setFormName("BANK RECONCILIATION");
            tAccLedger.setNumber(number);
            tAccLedger.setReconciliationGroup(0);
            tAccLedger.setRefNumber(null);
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setType("RECONCILIATION");
//            tAccLedger.setTransactionDate(new SimpleDateFormat("HH:mm:ss").format(tAccLedger.getTransactionDate()));
            tAccLedger.setTypeIndexNo(null);
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
            bankLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            bankLedger.setDescription(tAccLedger.getDescription());
            bankLedger.setFormName("BANK RECONCILIATION");
            bankLedger.setNumber(number);
            bankLedger.setDeleteRefNo(deleteNo);
            bankLedger.setReconciliationGroup(0);
            bankLedger.setRefNumber(null);
            bankLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            bankLedger.setTransactionDate(tAccLedger.getTransactionDate());
            bankLedger.setType("RECONCILIATION");
            bankLedger.setTypeIndexNo(null);
            bankLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            bankReconciliationRepository.save(bankLedger);
            count++;
        }
//        if (count == data.getDataList().size()) {
//            return true;
//        }
//        return false;
    }

}
