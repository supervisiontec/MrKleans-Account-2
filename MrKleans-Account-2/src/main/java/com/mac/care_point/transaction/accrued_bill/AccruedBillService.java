/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.accrued_bill;

import com.mac.care_point.common.Constant;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.accrued_bill.model.AccruedBillMix;
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
public class AccruedBillService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private BranchRepository branchRepository;

    public int saveAccruedBill(AccruedBillMix accruedBillMix) {
        System.out.println("1");
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.ACCRUED);
        System.out.println("2");
        int deleteNumber = journalRepository.getDeleteNumber();
        System.out.println("3");
        String searchCode = getSearchCode(Constant.CODE_ACCRUED_BILL, SecurityUtil.getCurrentUser().getBranch(), number);
        int count = 0;
        accruedBillMix.getData().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        System.out.println("4");
        accruedBillMix.getData().setNumber(number);
        accruedBillMix.getData().setDeleteRefNo(deleteNumber);
        accruedBillMix.getData().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        accruedBillMix.getData().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        System.out.println("5");
        accruedBillMix.getData().setUser(SecurityUtil.getCurrentUser().getBranch());
        accruedBillMix.getData().setReconcileGroup(0);
        accruedBillMix.getData().setBankReconciliation(false);
        accruedBillMix.getData().setIsMain(true);
        accruedBillMix.getData().setIsCheque(false);
        accruedBillMix.getData().setSearchCode(searchCode);
        System.out.println("6");

        TAccLedger save = journalRepository.save(accruedBillMix.getData());
        System.out.println("7");
        save.setReconcileGroup(save.getIndexNo());
        journalRepository.save(save);
        System.out.println("8");

        for (TAccLedger tAccLedger : accruedBillMix.getDataList()) {
            System.out.println("9");
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            System.out.println("10");
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setTransactionDate(accruedBillMix.getData().getTransactionDate());
            tAccLedger.setReconcileGroup(0);
            System.out.println("11");
            tAccLedger.setIsMain(Boolean.FALSE);
            tAccLedger.setIsCheque(Boolean.FALSE);
            tAccLedger.setBankReconciliation(false);
            tAccLedger.setSearchCode(searchCode);

            journalRepository.save(tAccLedger);
            System.out.println("12");
            count++;
        }
        if (count == accruedBillMix.getDataList().size()) {
            System.out.println("13");
            return number;
        }

        System.out.println("14");
        return -1;

    }
     private String getSearchCode(String code, Integer branch, int number) {
         MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> findAccruedBillsByNumberAndBranch(Integer number, Integer branch) {
        return journalRepository.findByNumberAndBranchAndType(number, branch, Constant.ACCRUED);
    }

}
