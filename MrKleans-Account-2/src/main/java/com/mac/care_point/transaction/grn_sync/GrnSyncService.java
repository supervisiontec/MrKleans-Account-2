/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync;

import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.grn_sync.model.TGrnMaster;
import com.mac.care_point.transaction.grn_sync.model.TGrnParam;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
public class GrnSyncService {

    @Autowired
    private GrnSyncRepository grnSyncRepository;

    @Autowired
    private JournalRepository ledgerRepository;

    public List<Object[]> getDetail(TGrnParam param) {
        return grnSyncRepository.getData(
                param.getBranch(),
                param.getSupplier(),
                param.getfDate(),
                param.gettDate(),
                param.getGrnNo(),
                param.getReferenceNo()
        );
    }

    @Transactional
    public int save(Object[] param) {
        try {
            Integer nxtNo = grnSyncRepository.getNextNo("SYSTEM_INTEGRATION_GRN");
            String branchCode = grnSyncRepository.getBranchCode(param[1].toString());
            Integer branchIndex = grnSyncRepository.getBranchID(param[1].toString());
            Integer nxtDeleteRefNo = grnSyncRepository.getDeleteRefNo();
            Integer stockAccount = grnSyncRepository.getStockItemSubAccountOf();
            if (stockAccount==null) {
                throw new RuntimeException("Empty of Stock item sub account setting!");
            }
            
            //save grn supplier
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(param[3].toString());

            TAccLedger supplerAccount = new TAccLedger();
            supplerAccount.setAccAccount(Integer.parseInt(param[12].toString()));
            supplerAccount.setBankReconciliation(false);
            supplerAccount.setBranch(branchIndex);
            supplerAccount.setCredit(new BigDecimal(param[13].toString()));
            supplerAccount.setCurrentBranch(branchIndex);
            supplerAccount.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            supplerAccount.setDebit(new BigDecimal(0));
            supplerAccount.setDeleteRefNo(nxtDeleteRefNo);
            supplerAccount.setDescription(param[4].toString() + " - grn by " + param[2].toString());
            supplerAccount.setFinancialYear(1);
            supplerAccount.setFormName("SYSTEM_INTEGRATION_GRN");
            supplerAccount.setIsCheque(false);
            supplerAccount.setIsEdit(0);
            supplerAccount.setIsMain(true);
            supplerAccount.setNumber(nxtNo);
            supplerAccount.setReconcileAccount(Integer.parseInt(param[0].toString()));
//        supplerAccount.setReconcileGroup();// save index
            supplerAccount.setRefNumber(param[5].toString());
            supplerAccount.setSearchCode("ISGRN/" + branchCode + "/" + nxtNo);
            supplerAccount.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            supplerAccount.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            supplerAccount.setType("SYSTEM_INTEGRATION_GRN");
            supplerAccount.setTypeIndexNo(Integer.parseInt(param[0].toString()));
            supplerAccount.setUser(1);
            TAccLedger saveGrnSupplier = ledgerRepository.save(supplerAccount);
            saveGrnSupplier.setReconcileGroup(saveGrnSupplier.getIndexNo());
            if (!param[13].toString().equals(param[10].toString())) {
                supplerAccount.setDescription(supplerAccount.getDescription()+" change price from "+param[10].toString()+" to "+param[13].toString());
            }
            TAccLedger save = ledgerRepository.save(saveGrnSupplier);
            
            if (save == null) {
                throw new RuntimeException("GRN Supplier Account save failed");
            }
            //save grn supplier
            TAccLedger grnAccount = new TAccLedger();
            grnAccount.setAccAccount(stockAccount);
            grnAccount.setBankReconciliation(false);
            grnAccount.setBranch(branchIndex);
            grnAccount.setCredit(new BigDecimal(0));
            grnAccount.setCurrentBranch(branchIndex);
            grnAccount.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            grnAccount.setDebit(new BigDecimal(param[13].toString()));
            grnAccount.setDeleteRefNo(nxtDeleteRefNo);
            grnAccount.setDescription(param[4].toString() + " - grn by " + param[2].toString());
            grnAccount.setFinancialYear(1);
            grnAccount.setFormName("SYSTEM_INTEGRATION_GRN");
            grnAccount.setIsCheque(false);
            grnAccount.setIsEdit(0);
            grnAccount.setIsMain(true);
            grnAccount.setNumber(nxtNo);
            grnAccount.setReconcileAccount(Integer.parseInt(param[0].toString()));
            grnAccount.setReconcileGroup(saveGrnSupplier.getIndexNo());
            grnAccount.setRefNumber(param[5].toString());
            grnAccount.setSearchCode("ISGRN/" + branchCode + "/" + nxtNo);
            grnAccount.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            grnAccount.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            grnAccount.setType("SYSTEM_INTEGRATION_GRN");
            grnAccount.setTypeIndexNo(Integer.parseInt(param[0].toString()));
            grnAccount.setUser(1);
            if (!param[13].toString().equals(param[10].toString())) {
                grnAccount.setDescription(grnAccount.getDescription()+" change price from "+param[10].toString()+" to "+param[13].toString());
            }
            TAccLedger save1 = ledgerRepository.save(grnAccount);
            if (save1 == null) {
                throw new RuntimeException("GRN Account save failed");
            }

            int updateIntGrnStatus = grnSyncRepository.updateIntGrnStatus(Integer.parseInt(param[0].toString()));
            if (updateIntGrnStatus < 1) {
                throw new RuntimeException("GRN status update failed");
            }
        } catch (ParseException ex) {
            Logger.getLogger(GrnSyncService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;

    }
}
