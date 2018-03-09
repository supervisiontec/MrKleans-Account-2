/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.grn;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountService;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.master.supplier.SupplierRepository;
import com.mac.care_point.master.supplier.model.MSupplier;
import com.mac.care_point.service.grn.model.MStore;
import com.mac.care_point.service.grn.model.TGrn;
import com.mac.care_point.service.grn.model.TGrnItem;
import com.mac.care_point.service.grn.model.TSupplierLedger;
import com.mac.care_point.service.purchase_order.PurchaseOrderDetailRepository;
import com.mac.care_point.service.purchase_order.PurchaseOrderRepository;
import com.mac.care_point.service.purchase_order.model.TPurchaseOrder;
import com.mac.care_point.service.purchase_order.model.TPurchaseOrderDetail;
import com.mac.care_point.service.stock.StockLedgerRepository;
import com.mac.care_point.service.stock.model.TStockLedger;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GrnService {

    @Autowired
    private GrnRepository grnRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StockLedgerRepository stockLedgerRepository;

    @Autowired
    private GrnItemRepository grnItemRepository;

    @Autowired
    private SupplierLedgerRepository supplierLedgerRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    
    @Autowired
    private AccountSettingRepository accountSettingRepository;
    
    @Autowired
    private JournalRepository journalRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private AccAccountService accAccountService;
    
    @Autowired
    private BranchRepository branchRepository;

    List<TPurchaseOrder> getApprovedPurchaseOrder(Integer branch, String status) {
        return purchaseOrderRepository.findByBranchAndStatusAndIsView(branch, status, true);
    }

    @Transactional
    TGrn saveGrnRecieve(TGrn grn) {
        TGrn findLastRow = grnRepository.findFirst1ByOrderByIndexNoDesc();

        if (findLastRow != null) {
            grn.setNumber(findLastRow.getNumber() + 1);
        } else {
            grn.setNumber(1);
        }
        List<TGrnItem> grnItemList = grn.getGrnItemList();
        grn.setGrnItemList(null);
        grn.setGrandAmount(grn.getAmount());
        grn.setBalanceAmount(grn.getGrandAmount());

        TGrn saveGrn = grnRepository.save(grn);

        for (TGrnItem detail : grnItemList) {
            detail.setGrn(saveGrn);
            grnItemRepository.save(detail);
            TPurchaseOrderDetail findDetail = purchaseOrderDetailRepository.findOne(detail.getPurchaseOrderItem());
            if (findDetail != null) {
                findDetail.setReceiveQty(findDetail.getReceiveQty().add(detail.getQty()));
                findDetail.setBalanceQty(findDetail.getBalanceQty().subtract(detail.getQty()));
                purchaseOrderDetailRepository.save(findDetail);
            }
        }

        return saveGrn;
    }

    List<TGrn> getPendingGrnList(Integer branch, String status_pending) {
        return grnRepository.findByBranchAndStatus(branch, status_pending);
    }

    List<TPurchaseOrderDetail> getApprovedPurchaseOrder() {
        return purchaseOrderDetailRepository.findAll();
    }

    TGrn approveGrnRecieve(TGrn grn) {
        for (TGrnItem grnItem : grn.getGrnItemList()) {
            grnItem.setGrn(grn);
//          stock ledger start
            TStockLedger ledger = new TStockLedger();
            ledger.setBranch(grn.getBranch());
            ledger.setDate(grn.getDate());
            ledger.setForm(Constant.FORM_GRN_APPROVE);
            ledger.setFormIndexNo(grn.getIndexNo());
            ledger.setAvaragePriceIn(grnItem.getNetValue());
            ledger.setAvaragePriceOut(new BigDecimal(0));
            ledger.setInQty(grnItem.getQty());
            ledger.setOutQty(new BigDecimal(0));

            TPurchaseOrderDetail findOne = purchaseOrderDetailRepository.findOne(grnItem.getPurchaseOrderItem());
            ledger.setItem(findOne.getItem());
            ledger.setOutQty(new BigDecimal(0));
            //store start
            List<MStore> storeList = storeRepository.findByBranchAndType(grn.getBranch(), Constant.MAIN_STOCK);
            MStore saveStore = new MStore();
            if (storeList.isEmpty()) {
                //default store save
                MStore store = new MStore();
                store.setName(Constant.MAIN_STOCK);
                store.setType(Constant.MAIN_STOCK);
                store.setBranch(grn.getBranch());
                MStore lastNumber = storeRepository.findFirst1ByOrderByNumberDesc();

                store.setNumber(lastNumber.getNumber() + 1);
                saveStore = storeRepository.save(store);
            } else {
                saveStore = storeList.get(0);
            }
            ledger.setStore(saveStore.getIndexNo());
            //store end
            stockLedgerRepository.save(ledger);
//          stock ledger end
        }
        TGrn saveObject = grnRepository.save(grn);

        TSupplierLedger supplierLedger = new TSupplierLedger();
        supplierLedger.setBranch(grn.getBranch());
        supplierLedger.setCreditAmount(grn.getBalanceAmount());
        supplierLedger.setDate(grn.getDate());
        supplierLedger.setDebitAmount(new BigDecimal(0));
        supplierLedger.setFormName(Constant.FORM_GRN_APPROVE);
        supplierLedger.setGrn(saveObject.getIndexNo());
        supplierLedger.setIsDelete(false);
        supplierLedger.setPayment(null);
        supplierLedger.setRefNumber(null);
        supplierLedger.setReturn1(null);
        supplierLedger.setSupplier(grn.getSupplier());

        supplierLedgerRepository.save(supplierLedger);
        
        // account link
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.GRN);
        int deleteNumber = journalRepository.getDeleteNumber();
        String searchCode=getSearchCode(Constant.CODE_GRN,SecurityUtil.getCurrentUser().getBranch(),number);
        
        
        MSupplier selectSupplier=supplierRepository.findOne(grn.getSupplier());
        Integer supplierAccount=selectSupplier.getAccAccount();
        if (selectSupplier.getAccAccount()==null) {
            //create supplier
            MAccSetting supplierSubAccountOf=accountSettingRepository.findByName(Constant.SUPPLIER_SUB_ACCOUNT_OF);
            MAccAccount account = new MAccAccount();
            account.setAccCode(null);
            account.setAccMain(null);
            account.setAccType("COMMON");
            account.setCop(false);
            account.setDescription("SUPPLIER ACCOUNT");
            account.setIsAccAccount(true);
            account.setLevel(null);
            account.setName(selectSupplier.getName());
            account.setSubAccountCount(0);
            account.setSubAccountOf(supplierSubAccountOf.getAccAccount());
            account.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            
            supplierAccount=accAccountService.saveNewAccount(account).getIndexNo();
            selectSupplier.setAccAccount(supplierAccount);
            supplierRepository.save(selectSupplier);
        }
        TAccLedger tAccLedger = new TAccLedger();
        tAccLedger.setAccAccount(supplierAccount);
        tAccLedger.setBankReconciliation(false);
        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setChequeDate(null);
        tAccLedger.setCredit(grn.getGrandAmount());
        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger.setDebit(new BigDecimal(0));
        tAccLedger.setDeleteRefNo(deleteNumber);
        tAccLedger.setDescription("direct grn entered");
        tAccLedger.setFormName(Constant.FORM_GRN_APPROVE);
        tAccLedger.setIsCheque(false);
        tAccLedger.setIsMain(false);
        tAccLedger.setNumber(number);
        tAccLedger.setReconcileAccount(null);
        tAccLedger.setReconcileGroup(null);
        tAccLedger.setRefNumber(grn.getRefNumber());
        tAccLedger.setSearchCode(searchCode);
        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(grn.getDate()));
        tAccLedger.setType(Constant.GRN);
        tAccLedger.setTypeIndexNo(selectSupplier.getIndexNo());
        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        
        TAccLedger detail = journalRepository.save(tAccLedger);
        detail.setReconcileGroup(detail.getIndexNo());
        journalRepository.save(tAccLedger);
        
        
        TAccLedger tAccLedger2 = new TAccLedger();
        MAccSetting inventoryAccount=accountSettingRepository.findByName(Constant.INVENTORY);
        tAccLedger2.setAccAccount(inventoryAccount.getAccAccount());
        tAccLedger2.setBankReconciliation(false);
        tAccLedger2.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setChequeDate(null);
        tAccLedger2.setDebit(grn.getGrandAmount());
        tAccLedger2.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger2.setCredit(new BigDecimal(0));
        tAccLedger2.setDeleteRefNo(deleteNumber);
        tAccLedger2.setDescription("direct grn entered");
        tAccLedger2.setFormName(Constant.FORM_GRN_APPROVE);
        tAccLedger2.setIsCheque(false);
        tAccLedger2.setIsMain(false);
        tAccLedger2.setNumber(number);
        tAccLedger2.setReconcileAccount(null);
        tAccLedger2.setReconcileGroup(null);
        tAccLedger2.setRefNumber(grn.getRefNumber());
        tAccLedger2.setSearchCode(searchCode);
        tAccLedger2.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger2.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(grn.getDate()));
        tAccLedger2.setType(Constant.GRN);
        tAccLedger2.setTypeIndexNo(selectSupplier.getIndexNo());
        tAccLedger2.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        
        journalRepository.save(tAccLedger2);
        
        return saveObject;
    }

    TGrn saveDirectGrn(TGrn grn) {
        List<TStockLedger> leadgerList = new ArrayList<>();
        for (TGrnItem grnItem : grn.getGrnItemList()) {
            grnItem.setGrn(grn);
//          stock ledger start
            TStockLedger ledger = new TStockLedger();
            ledger.setBranch(grn.getBranch());
            ledger.setDate(grn.getDate());
            ledger.setBranch(grn.getBranch());
            ledger.setForm(Constant.FORM_DIRECT_GRN);
            ledger.setInQty(grnItem.getQty());
            ledger.setAvaragePriceIn(grnItem.getNetValue());
            ledger.setAvaragePriceOut(new BigDecimal(0));
            ledger.setItem(grnItem.getItem());
            ledger.setOutQty(new BigDecimal(0));
            //store start
            List<MStore> storeList = storeRepository.findByBranchAndType(grn.getBranch(), Constant.MAIN_STOCK);
            MStore store = new MStore();
            if (storeList.isEmpty()) {
                //default store save
                store.setName(Constant.MAIN_STOCK);
                store.setType(Constant.MAIN_STOCK);
                store.setBranch(grn.getBranch());
                MStore lastNumber = storeRepository.findFirst1ByOrderByNumberDesc();

                store.setNumber(lastNumber.getNumber() + 1);
                store = storeRepository.save(store);
            } else {
                store = storeList.get(0);
            }
            ledger.setStore(store.getIndexNo());
            //store end
            leadgerList.add(ledger);
//          stock ledger end
        }
        TGrn saveObject = grnRepository.save(grn);
        for (TStockLedger stockLedger : leadgerList) {
            stockLedger.setFormIndexNo(saveObject.getIndexNo());
            stockLedgerRepository.save(stockLedger);
        }

        TSupplierLedger supplierLedger = new TSupplierLedger();
        supplierLedger.setBranch(grn.getBranch());
        supplierLedger.setCreditAmount(grn.getBalanceAmount());
        supplierLedger.setDate(grn.getDate());
        supplierLedger.setDebitAmount(new BigDecimal(0));
        supplierLedger.setFormName(Constant.FORM_DIRECT_GRN);
        supplierLedger.setGrn(saveObject.getIndexNo());
        supplierLedger.setIsDelete(false);
        supplierLedger.setPayment(null);
        supplierLedger.setRefNumber(null);
        supplierLedger.setReturn1(null);
        supplierLedger.setSupplier(grn.getSupplier());

        supplierLedgerRepository.save(supplierLedger);
        
        // account link
        MAccSetting inventoryAccount=accountSettingRepository.findByName(Constant.INVENTORY);
        MAccSetting supplierSubAccountOf=accountSettingRepository.findByName(Constant.SUPPLIER_SUB_ACCOUNT_OF);
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.DIRECT_GRN);
        int deleteNumber = journalRepository.getDeleteNumber();
        String searchCode=getSearchCode(Constant.CODE_DIRECT_GRN,SecurityUtil.getCurrentUser().getBranch(),number);
        
        
        MSupplier selectSupplier=supplierRepository.findOne(grn.getSupplier());
        Integer supplierAccount=selectSupplier.getAccAccount();
        if (selectSupplier.getAccAccount()==null) {
            //create supplier
            MAccAccount account = new MAccAccount();
            account.setAccCode(null);
            account.setAccMain(null);
            account.setAccType("COMMON");
            account.setCop(false);
            account.setDescription("SUPPLIER ACCOUNT");
            account.setIsAccAccount(true);
            account.setLevel(null);
            account.setName(selectSupplier.getName());
            account.setSubAccountCount(0);
            account.setSubAccountOf(supplierSubAccountOf.getAccAccount());
            account.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            
            supplierAccount=accAccountService.saveNewAccount(account).getIndexNo();
            selectSupplier.setAccAccount(supplierAccount);
            supplierRepository.save(selectSupplier);
        }
        TAccLedger tAccLedger = new TAccLedger();
        tAccLedger.setAccAccount(supplierAccount);
        tAccLedger.setBankReconciliation(false);
        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setChequeDate(null);
        tAccLedger.setCredit(grn.getGrandAmount());
        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger.setDebit(new BigDecimal(0));
        tAccLedger.setDeleteRefNo(deleteNumber);
        tAccLedger.setDescription("direct grn entered");
        tAccLedger.setFormName(Constant.FORM_DIRECT_GRN);
        tAccLedger.setIsCheque(false);
        tAccLedger.setIsMain(false);
        tAccLedger.setNumber(number);
        tAccLedger.setReconcileAccount(null);
        tAccLedger.setReconcileGroup(null);
        tAccLedger.setRefNumber(grn.getRefNumber());
        tAccLedger.setSearchCode(searchCode);
        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(grn.getDate()));
        tAccLedger.setType(Constant.DIRECT_GRN);
        tAccLedger.setTypeIndexNo(selectSupplier.getIndexNo());
        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        
        TAccLedger detail = journalRepository.save(tAccLedger);
        detail.setReconcileGroup(detail.getIndexNo());
        journalRepository.save(tAccLedger);
        
        
        TAccLedger tAccLedger2 = new TAccLedger();
        tAccLedger2.setAccAccount(inventoryAccount.getAccAccount());
        tAccLedger2.setBankReconciliation(false);
        tAccLedger2.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setChequeDate(null);
        tAccLedger2.setDebit(grn.getGrandAmount());
        tAccLedger2.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger2.setCredit(new BigDecimal(0));
        tAccLedger2.setDeleteRefNo(deleteNumber);
        tAccLedger2.setDescription("direct grn entered");
        tAccLedger2.setFormName(Constant.FORM_DIRECT_GRN);
        tAccLedger2.setIsCheque(false);
        tAccLedger2.setIsMain(false);
        tAccLedger2.setNumber(number);
        tAccLedger2.setReconcileAccount(null);
        tAccLedger2.setReconcileGroup(null);
        tAccLedger2.setRefNumber(grn.getRefNumber());
        tAccLedger2.setSearchCode(searchCode);
        tAccLedger2.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger2.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(grn.getDate()));
        tAccLedger2.setType(Constant.DIRECT_GRN);
        tAccLedger2.setTypeIndexNo(selectSupplier.getIndexNo());
        tAccLedger2.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        
        journalRepository.save(tAccLedger2);
        
        
        return saveObject;
    }

    private String getSearchCode(String code, Integer branch, int number) {
         MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public TGrn findGrnByNumber(Integer number, Integer branch) {
        return grnRepository.findByNumberAndBranch(number, branch);
    }

}
