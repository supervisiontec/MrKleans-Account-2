/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.supplier_payment;

import com.mac.care_point.account_setting.acc_code_setting.AccCodeSettingRepository;
import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountRepository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.supplier_payment.model.DataSub;
import com.mac.care_point.transaction.supplier_payment.model.SupplierPaymentMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.apache.el.lang.ELArithmetic.subtract;
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
public class SupplierPaymentSevrice {

    @Autowired
    private SupplierPaymentRepository paymentRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccAccountRepository accAccountRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AccCodeSettingRepository accCodeSettingRepository;

    public List<TAccLedger> getPayableBills(Integer account) {
        return paymentRepository.getPayableBills(account);
    }

    @Transactional
    public TAccLedger saveSupplierPayment(SupplierPaymentMix mix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.SUPPLIER_PAYMENT);
        int deleteNumber = journalRepository.getDeleteNumber();
        String code;
        AccCodeSetting accCodeSetting = accCodeSettingRepository.findByAccount(mix.getData().getAccAccount());
        if (accCodeSetting == null && !"SETTLEMENT".equals(mix.getData().getAccType())) {
            throw new RuntimeException("Accout Code Setting not Found for this Account !");
        } else if (accCodeSetting == null) {
            code = getSearchCode2(Constant.CODE_SETTLEMENT, SecurityUtil.getCurrentUser().getBranch(), number);
        } else {
            code = getSearchCode(Constant.CODE_PAY, SecurityUtil.getCurrentUser().getBranch(), accCodeSetting);
        }
        String searchCode = getSearchCode(Constant.CODE_SUPPLIER_PAYMENT, SecurityUtil.getCurrentUser().getBranch(), number);
        BigDecimal overPayment = new BigDecimal(0);
        overPayment = (BigDecimal) subtract(mix.getData().getCredit(), mix.getData().getBillTotal());
        TAccLedger save = new TAccLedger();

        if ("BANK".equals(mix.getData().getAccType())) {
            MAccSetting unrealizedIssued = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
            TAccLedger tAccLedger = new TAccLedger();
            tAccLedger.setAccAccount(unrealizedIssued.getAccAccount());// reconcile account
            tAccLedger.setBankReconciliation(true);//
            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setChequeDate(mix.getData().getChequeDate());
            tAccLedger.setCredit(mix.getData().getCredit());
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setDebit(BigDecimal.ZERO);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setDescription(mix.getData().getDescription());
            tAccLedger.setFormName(mix.getData().getFormName());
            tAccLedger.setIsMain(true);
            tAccLedger.setIsCheque(true);
            tAccLedger.setSearchCode(code);
            tAccLedger.setNumber(number);
            tAccLedger.setReconcileAccount(mix.getData().getAccAccount());//
            tAccLedger.setReconcileGroup(0);//
            tAccLedger.setRefNumber(mix.getData().getRefNumber());
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
            tAccLedger.setType(mix.getData().getType());
            tAccLedger.setTypeIndexNo(mix.getData().getTypeIndexNo());
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            save = paymentRepository.save(tAccLedger);
            save.setReconcileGroup(save.getIndexNo());//
            paymentRepository.save(save);

            for (DataSub sub : mix.getDataList()) {
                if (sub.getPay().doubleValue() > 0) {

                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(sub.getAccAccount());//
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(sub.getChequeDate());
                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(sub.getPay());
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(code);
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(null);//
                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
                    tAccLedger1.setRefNumber(sub.getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                    paymentRepository.save(tAccLedger1);

                }
            }
            updateCodeSetting(accCodeSetting);
        }
        if ("CASH".equals(mix.getData().getAccType())) {
            TAccLedger tAccLedger = new TAccLedger();
            tAccLedger.setAccAccount(mix.getData().getAccAccount());//
            tAccLedger.setBankReconciliation(false);//
            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setChequeDate(mix.getData().getChequeDate());
            tAccLedger.setCredit(mix.getData().getCredit());
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setDebit(BigDecimal.ZERO);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setDescription(mix.getData().getDescription());
            tAccLedger.setFormName(mix.getData().getFormName());
            tAccLedger.setIsMain(true);
            tAccLedger.setSearchCode(code);
            tAccLedger.setNumber(number);
            tAccLedger.setReconcileAccount(null);//
            tAccLedger.setReconcileGroup(0);//
            tAccLedger.setRefNumber(mix.getData().getRefNumber());
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
            tAccLedger.setType(mix.getData().getType());
            tAccLedger.setTypeIndexNo(null);
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            save = paymentRepository.save(tAccLedger);

            for (DataSub sub : mix.getDataList()) {
                if (sub.getPay().doubleValue() > 0) {

                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(sub.getAccAccount());//
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(sub.getChequeDate());
                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(sub.getPay());
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(code);
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(null);//
                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
                    tAccLedger1.setRefNumber(sub.getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                    paymentRepository.save(tAccLedger1);

                }
            }
            updateCodeSetting(accCodeSetting);
        }
        if ("ONLINE".equals(mix.getData().getAccType())) {
            TAccLedger tAccLedger = new TAccLedger();
            tAccLedger.setAccAccount(mix.getData().getAccAccount());// reconcile account
            tAccLedger.setBankReconciliation(false);//
            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setChequeDate(mix.getData().getChequeDate());
            tAccLedger.setCredit(mix.getData().getCredit());
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setDebit(BigDecimal.ZERO);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setDescription(mix.getData().getDescription());
            tAccLedger.setFormName(mix.getData().getFormName());
            tAccLedger.setIsMain(true);
            tAccLedger.setNumber(number);
            tAccLedger.setSearchCode(code);
            tAccLedger.setReconcileAccount(0);//
            tAccLedger.setReconcileGroup(0);//
            tAccLedger.setRefNumber(mix.getData().getRefNumber());
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
            tAccLedger.setType(mix.getData().getType());
            tAccLedger.setTypeIndexNo(null);
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            save = paymentRepository.save(tAccLedger);
            save.setReconcileGroup(save.getIndexNo());//
            paymentRepository.save(tAccLedger);

            for (DataSub sub : mix.getDataList()) {
                if (sub.getPay().doubleValue() > 0) {

                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(sub.getAccAccount());//
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(sub.getChequeDate());
                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(sub.getPay());
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(code);
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(null);//
                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
                    tAccLedger1.setRefNumber(sub.getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                    paymentRepository.save(tAccLedger1);

                }
            }
            updateCodeSetting(accCodeSetting);
        }
        MAccSetting overPaymentIssue = accountSettingRepository.findByName(Constant.OVER_PAYMENT_ISSUED);
        if (overPaymentIssue.getIndexNo() <= 0) {
            throw new RuntimeException("Over Payment Issue Account Setting not found !");
        }

        if ("OVER_PAYMENT".equals(mix.getData().getAccType())) {
            BigDecimal credit = mix.getData().getCredit();
            List<TAccLedger> fifoList = paymentRepository.getOverPaymentFifoList(mix.getData().getAccAccount(), mix.getData().getTypeIndexNo());
            for (TAccLedger tAccLedger : fifoList) {
                BigDecimal balance = (BigDecimal) subtract(tAccLedger.getDebit(), tAccLedger.getCredit());
                if (credit.doubleValue() > balance.doubleValue()) {
                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(mix.getData().getAccAccount());
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(mix.getData().getChequeDate());
                    tAccLedger1.setCredit(balance);
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(BigDecimal.ZERO);
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(searchCode);
                    tAccLedger1.setIsCheque(tAccLedger.getIsCheque());
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(0);
                    tAccLedger1.setReconcileGroup(tAccLedger.getReconcileGroup());
                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setIsEdit(0);
                    tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
                    paymentRepository.save(tAccLedger1);
                    credit = (BigDecimal) subtract(credit, balance);

                }
                if (credit.doubleValue() <= balance.doubleValue()) {
                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(mix.getData().getAccAccount());// over payment account
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(mix.getData().getChequeDate());
                    tAccLedger1.setCredit(credit);
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(BigDecimal.ZERO);
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(searchCode);
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(0);//
                    tAccLedger1.setReconcileGroup(tAccLedger.getReconcileGroup());//
                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
                    tAccLedger1.setIsEdit(0);
                    paymentRepository.save(tAccLedger1);
                    break;
                }

            }
//            
            for (DataSub sub : mix.getDataList()) {
                if (sub.getPay().doubleValue() > 0) {

                    TAccLedger tAccLedger1 = new TAccLedger();
                    tAccLedger1.setAccAccount(sub.getAccAccount());//
                    tAccLedger1.setBankReconciliation(false);//
                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setChequeDate(sub.getChequeDate());
                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    tAccLedger1.setDebit(sub.getPay());
                    tAccLedger1.setDeleteRefNo(deleteNumber);
                    tAccLedger1.setDescription(mix.getData().getDescription());
                    tAccLedger1.setFormName(mix.getData().getFormName());
                    tAccLedger1.setIsMain(false);
                    tAccLedger1.setSearchCode(searchCode);
                    tAccLedger1.setNumber(number);
                    tAccLedger1.setReconcileAccount(null);//
                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                    tAccLedger1.setType(mix.getData().getType());
                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                    paymentRepository.save(tAccLedger1);
                }
            }
        }
        if ("SETTLEMENT".equals(mix.getData().getAccType())) {
            if ("RETURN".equals(mix.getData().getAccTypeSub())) {
                for (DataSub detail : mix.getDataList()) {
                    if (detail.getPay().doubleValue() != 0) {
                        TAccLedger tAccLedger = new TAccLedger();
                        tAccLedger.setAccAccount(detail.getAccAccount());//
                        tAccLedger.setBankReconciliation(false);//
                        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setChequeDate(null);
                        tAccLedger.setDebit(detail.getPay().doubleValue() > 0 ? detail.getPay() : new BigDecimal(0));
                        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        tAccLedger.setCredit(detail.getPay().doubleValue() < 0 ? (detail.getPay().multiply(new BigDecimal(-1))) : new BigDecimal(0));
                        tAccLedger.setDeleteRefNo(deleteNumber);
                        tAccLedger.setDescription(mix.getData().getDescription());
                        tAccLedger.setFormName(mix.getData().getFormName());
                        tAccLedger.setIsMain(false);
                        tAccLedger.setSearchCode(searchCode);
                        tAccLedger.setNumber(number);
                        tAccLedger.setReconcileAccount(null);//
                        tAccLedger.setReconcileGroup(detail.getReconcileGroup());//
                        tAccLedger.setRefNumber(mix.getData().getRefNumber());
                        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                        tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
                        tAccLedger.setType(mix.getData().getType());
                        tAccLedger.setTypeIndexNo(null);
                        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                        paymentRepository.save(tAccLedger);
                    }
                }
            }
            if ("ACCOUNT".equals(mix.getData().getAccTypeSub())) {
                TAccLedger tAccLedger1 = new TAccLedger();
                tAccLedger1.setAccAccount(mix.getData().getAccAccount());
                tAccLedger1.setBankReconciliation(false);
                tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger1.setChequeDate(null);
                tAccLedger1.setDebit(mix.getData().getBillTotal().doubleValue() < 0 ? mix.getData().getBillTotal().multiply(new BigDecimal(-1)) : new BigDecimal(BigInteger.ZERO));
                tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger1.setCredit(mix.getData().getBillTotal().doubleValue() > 0 ? mix.getData().getBillTotal() : new BigDecimal(BigInteger.ZERO));
                tAccLedger1.setDeleteRefNo(deleteNumber);
                tAccLedger1.setDescription(mix.getData().getDescription());
                tAccLedger1.setFormName(mix.getData().getFormName());
                tAccLedger1.setIsMain(false);
                tAccLedger1.setSearchCode(searchCode);
                tAccLedger1.setNumber(number);
                tAccLedger1.setReconcileAccount(null);
                tAccLedger1.setReconcileGroup(null);
                tAccLedger1.setRefNumber(mix.getData().getRefNumber());
                tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
                tAccLedger1.setType(mix.getData().getType());
                tAccLedger1.setTypeIndexNo(null);
                tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
                tAccLedger1.setCostDepartment(mix.getData().getCostDepartment());
                tAccLedger1.setCostCenter(mix.getData().getCostCenter());
                tAccLedger1.setFinancialYear(mix.getData().getFinancialYear());

                TAccLedger save1 = paymentRepository.save(tAccLedger1);
                if (save1.getIndexNo() <= 0) {
                    throw new RuntimeException("Save Fail !");
                }
                for (DataSub detail : mix.getDataList()) {
                    if (detail.getPay().doubleValue() != 0) {
                        TAccLedger tAccLedger = new TAccLedger();
                        tAccLedger.setAccAccount(detail.getAccAccount());
                        tAccLedger.setBankReconciliation(false);
                        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setChequeDate(null);
                        tAccLedger.setDebit(detail.getPay().doubleValue() > 0 ? detail.getPay() : new BigDecimal(0));
                        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        tAccLedger.setCredit(detail.getPay().doubleValue() < 0 ? (detail.getPay().multiply(new BigDecimal(-1))) : new BigDecimal(0));
                        tAccLedger.setDeleteRefNo(deleteNumber);
                        tAccLedger.setDescription(mix.getData().getDescription());
                        tAccLedger.setFormName(mix.getData().getFormName());
                        tAccLedger.setIsMain(false);
                        tAccLedger.setSearchCode(searchCode);
                        tAccLedger.setNumber(number);
                        tAccLedger.setReconcileAccount(null);
                        tAccLedger.setReconcileGroup(detail.getReconcileGroup());
                        tAccLedger.setRefNumber(mix.getData().getRefNumber());
                        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                        tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
                        tAccLedger.setType(mix.getData().getType());
                        tAccLedger.setTypeIndexNo(null);
                        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                        paymentRepository.save(tAccLedger);
                    }
                }
            }
        }
        //over payment save
        if (overPayment.doubleValue() > 0) {
            TAccLedger tAccLedger1 = new TAccLedger();
            tAccLedger1.setAccAccount(overPaymentIssue.getAccAccount());
            tAccLedger1.setBankReconciliation(false);
            tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger1.setChequeDate(null);
            tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
            tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger1.setDebit(overPayment);
            tAccLedger1.setDeleteRefNo(deleteNumber);
            tAccLedger1.setDescription(mix.getData().getDescription());
            tAccLedger1.setFormName(mix.getData().getFormName());
            tAccLedger1.setIsMain(false);
            tAccLedger1.setNumber(number);
            tAccLedger1.setSearchCode(searchCode);
            tAccLedger1.setReconcileAccount(null);
            tAccLedger1.setReconcileGroup(0);
            tAccLedger1.setRefNumber(mix.getData().getRefNumber());
            tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
            tAccLedger1.setType(mix.getData().getType());
            tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
            tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            TAccLedger save1 = paymentRepository.save(tAccLedger1);
            save1.setReconcileGroup(save1.getIndexNo());
            paymentRepository.save(save1);
        }

        return save;

    }

    public double getOverPaymentAmount(Integer supplier) {
        MAccAccount overPaymentAccount = accAccountRepository.getOverPaymentIssueAccount(Constant.OVER_PAYMENT_ISSUED);
        return paymentRepository.getOverPaymentAmount(supplier, overPaymentAccount.getIndexNo());
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> findSupplierPaymentByNumberAndBranch(Integer number, Integer branch) {
        return paymentRepository.findByNumberAndBranchAndType(number, branch, Constant.SUPPLIER_PAYMENT);
    }

    private String getSearchCode(String code, Integer branch, AccCodeSetting accCodeSetting) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + accCodeSetting.getCode() + "/" + (accCodeSetting.getMaxNo() + 1);
    }

    private Integer updateCodeSetting(AccCodeSetting accCodeSetting) {
        accCodeSetting.setMaxNo(accCodeSetting.getMaxNo() + 1);
        AccCodeSetting save1 = accCodeSettingRepository.save(accCodeSetting);
        if (save1.getIndexNo() > 0) {
            return save1.getIndexNo();
        } else {
            throw new RuntimeException("Voucher Save Fail !");
        }
    }

    private String getSearchCode2(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }
}
