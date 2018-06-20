/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.payment_voucher;

import com.mac.care_point.account_setting.acc_code_setting.AccCodeSettingRepository;
import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountRepository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.service.item_sale.model.TCustomerLedger;
import com.mac.care_point.service.item_sale.model.TPayment;
import com.mac.care_point.service.item_sale.model.TPaymentInformation;
import com.mac.care_point.service.payment.ClientLegerRepository;
import com.mac.care_point.service.payment.PaymentInformationRepostory;
import com.mac.care_point.service.payment.PaymentRepository;
import com.mac.care_point.service.payment_voucher.model.BalancePaymentModel;
import com.mac.care_point.service.payment_voucher.model.InvoiceCustomModel;
import com.mac.care_point.service.payment_voucher.model.PaymentVoucherModel;
import com.mac.care_point.service.type_index_detail.TypeIndexDetailRepository;
import com.mac.care_point.service.type_index_detail.model.TTypeIndexDetail;
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
import com.mac.care_point.master.client.CustomerRepository;
import com.mac.care_point.transaction.supplier_payment.SupplierPaymentRepository;
import com.mac.care_point.transaction.supplier_payment.model.DataSub;
import com.mac.care_point.transaction.supplier_payment.model.SupplierPaymentMix;
import java.math.BigInteger;
import static org.apache.el.lang.ELArithmetic.subtract;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaymentVoucherService {

    @Autowired
    public PaymentVoucherRepository paymentVoucherRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentInformationRepostory paymentInformationRepostory;

    @Autowired
    private ClientLegerRepository clientLegerRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CustomerRepository clientRepository;

    @Autowired
    private TypeIndexDetailRepository typeIndexDetailRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccCodeSettingRepository accCodeSettingRepository;

    @Autowired
    private AccAccountRepository accAccountRepository;
    
     @Autowired
    private SupplierPaymentRepository supplierPaymentRepository;

    public Object getClientVehicles(Integer client) {
        return paymentVoucherRepository.getClientVehicles(client);

    }

    public Double getClientBalance(Integer client) {
        return paymentVoucherRepository.getClientBalance(client);
    }

    public Double getClientOverPayment(Integer client) {
        MAccAccount overPaymentAccount = accAccountRepository.getOverPaymentIssueAccount(Constant.OVER_PAYMENT_RECEIVED);
        return paymentVoucherRepository.getClientOverPayment(client, overPaymentAccount.getIndexNo());
    }

    public int getBalanceInvoiceCount(Integer client) {
        List<Object> balanceInvoiceCount = paymentVoucherRepository.getBalanceInvoice(client);
        return balanceInvoiceCount.size();
    }

    public List<Object> getBalanceInvoice(Integer client) {
        return paymentVoucherRepository.getBalanceInvoice(client);
    }

    @Transactional
    public Integer savePaymentVoucher(PaymentVoucherModel voucherModel) {
        return saveCustomerPaymentToAccountSystem(voucherModel);
    }

    @Transactional
    public Integer saveBalancePaymentVoucher(BalancePaymentModel balancePaymentModel) {

        balancePaymentModel.getPayment().setOverPaymentAmount(BigDecimal.ZERO);
        TPayment savePayment = paymentRepository.save(balancePaymentModel.getPayment());
        double overPaymentSettlementAmount = 0.00;

        for (TPaymentInformation paymentInformation : balancePaymentModel.getPaymentInformationList()) {
            if ("OVER_PAYMENT_SETTLEMENT".equals(paymentInformation.getType())) {
                overPaymentSettlementAmount = paymentInformation.getAmount().doubleValue();
            }
            if (!"OVER_PAYMENT_SETTLEMENT".equals(paymentInformation.getType())) {
                paymentInformation.setFormName(Constant.PAYMENT_VOUCHER_FORM);
                paymentInformation.setPayment(savePayment.getIndexNo());
                paymentInformationRepostory.save(paymentInformation);
            }

        }
        savePayment.setOverPaymentAmount(new BigDecimal(overPaymentSettlementAmount));
        savePayment = paymentRepository.save(savePayment);

        double payAmountTotal = 0.00;
        for (InvoiceCustomModel invoice : balancePaymentModel.getInvoice()) {
            if (invoice.getPay() != 0.00) {
                payAmountTotal += invoice.getPay();
                //save defallt payment
                TCustomerLedger customerLedger = new TCustomerLedger();
                customerLedger.setClient(balancePaymentModel.getCustomerLedger().getClient());
                customerLedger.setCreditAmount(new BigDecimal(0));
                customerLedger.setDate(balancePaymentModel.getCustomerLedger().getDate());
                customerLedger.setDebitAmount(new BigDecimal(invoice.getPay()));
                customerLedger.setFormName(Constant.PAYMENT_FORM);
                customerLedger.setInvoice(invoice.getInvoice());
                customerLedger.setPayment(savePayment.getIndexNo());
                customerLedger.setRefNumber(savePayment.getIndexNo());
                customerLedger.setType(Constant.PAYMENT);
                clientLegerRepository.save(customerLedger);

            }
        }
//            over payment settlement
        if (overPaymentSettlementAmount > 0.00) {
//                    save over payment
            List<Object[]> fifoList = new ArrayList<>();
            while (overPaymentSettlementAmount > 0.00) {
                fifoList = getFIFOList(balancePaymentModel.getCustomerLedger().getClient());

                for (int i = 0; i < fifoList.size(); i++) {
                    Object[] selectFirst = fifoList.get(i);
                    Integer paymentIndex = Integer.parseInt(selectFirst[0].toString());
                    Double overAmount = Double.parseDouble(selectFirst[1].toString());

                    String type = selectFirst[2].toString();

                    if (overPaymentSettlementAmount > overAmount) {
                        //save over payment settlement

                        TCustomerLedger customerLedger = new TCustomerLedger();
                        customerLedger.setClient(balancePaymentModel.getCustomerLedger().getClient());
                        customerLedger.setCreditAmount(new BigDecimal(overAmount));
                        customerLedger.setDate(balancePaymentModel.getCustomerLedger().getDate());
                        customerLedger.setDebitAmount(new BigDecimal(0));
                        customerLedger.setFormName(Constant.PAYMENT_FORM);
                        customerLedger.setInvoice(null);
                        customerLedger.setPayment(paymentIndex);
                        customerLedger.setRefNumber(savePayment.getIndexNo());
                        customerLedger.setType(type);
                        clientLegerRepository.save(customerLedger);

                        overPaymentSettlementAmount -= overAmount;

//                          
                    } else {
                        //save over payment settlement
                        if (overPaymentSettlementAmount > 0.00) {

                            TCustomerLedger customerLedger = new TCustomerLedger();
                            customerLedger.setClient(balancePaymentModel.getCustomerLedger().getClient());
                            customerLedger.setCreditAmount(new BigDecimal(overPaymentSettlementAmount));
                            customerLedger.setDate(balancePaymentModel.getCustomerLedger().getDate());
                            customerLedger.setDebitAmount(new BigDecimal(0));
                            customerLedger.setFormName(Constant.PAYMENT_FORM);
                            customerLedger.setInvoice(null);
                            customerLedger.setPayment(paymentIndex);
                            customerLedger.setRefNumber(savePayment.getIndexNo());
                            customerLedger.setType(type);
                            clientLegerRepository.save(customerLedger);
                        }
                        overPaymentSettlementAmount = 0.00;
                    }

                }

            }
        }

        double totalPaidAmount = balancePaymentModel.getPayment().getTotalAmount().doubleValue();
        //save over payment
        if (payAmountTotal < totalPaidAmount) {
            double overPaymentAmount = totalPaidAmount - payAmountTotal;
            TCustomerLedger customerLedger = new TCustomerLedger();
            customerLedger.setClient(balancePaymentModel.getCustomerLedger().getClient());
            customerLedger.setCreditAmount(BigDecimal.ZERO);
            customerLedger.setDate(balancePaymentModel.getCustomerLedger().getDate());
            customerLedger.setDebitAmount(new BigDecimal(overPaymentAmount));
            customerLedger.setFormName(Constant.PAYMENT_FORM);
            customerLedger.setInvoice(null);
            customerLedger.setPayment(savePayment.getIndexNo());
            customerLedger.setRefNumber(savePayment.getIndexNo());
            customerLedger.setType(Constant.ADVANCE);
            clientLegerRepository.save(customerLedger);
        }
        return savePayment.getIndexNo();
    }

    private List<Object[]> getFIFOList(int client) {
        return paymentVoucherRepository.getFIFOList(client);
    }

    private Integer saveCustomerPaymentToAccountSystem(PaymentVoucherModel voucherModel) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.CUSTOMER_PAYMENT);
        int deleteNumber = journalRepository.getDeleteNumber();
        Integer client = clientRepository.findByAccAccountFromClientIndex(voucherModel.getCustomerLedger().getClient());
        TTypeIndexDetail typeIndex = typeIndexDetailRepository.findByTypeAndAccountRefId(Constant.INVOICE, voucherModel.getCustomerLedger().getInvoice());
        MAccSetting setting = null;
        String searchCode = null;
        for (TPaymentInformation paymentInformation : voucherModel.getPaymentInformationList()) {

            if ("CASH".equals(paymentInformation.getType())) {
                setting = accountSettingRepository.findByName("over_payment_received");
                if (setting.getName() == null) {
                    throw new RuntimeException("Cant find cash account setting !");
                }
//                item_sales_cash_in
            } else if ("CHEQUE".equals(paymentInformation.getType())) {
                setting = accountSettingRepository.findByName("cheque_in_hand");
                if (setting.getName() == null) {
                    throw new RuntimeException("Cant find check in hand account setting !");
                }
//            cheque_in_hand
            } else if ("CARD".equals(paymentInformation.getType())) {
                throw new RuntimeException("Card Payment not support");
            }
            searchCode = getSearchCode(Constant.CODE_PAYMENT_VOUCHER, SecurityUtil.getCurrentUser().getBranch(), number);
            TAccLedger accountLedger = new TAccLedger();
            accountLedger.setAccAccount(setting.getAccAccount());
            accountLedger.setBankReconciliation(false);
            accountLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
            accountLedger.setChequeDate(null);
            accountLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountLedger.setDebit(paymentInformation.getAmount());
            accountLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            accountLedger.setCredit(new BigDecimal(0));
            accountLedger.setDeleteRefNo(deleteNumber);
            accountLedger.setDescription("Customer Payment");
            accountLedger.setFormName(Constant.FORM_CUSTOMER_PAYMENT);
            accountLedger.setIsCheque(false);
            accountLedger.setIsMain(false);
            accountLedger.setNumber(number);
            accountLedger.setReconcileAccount(0);
            accountLedger.setReconcileGroup(typeIndex == null ? 0 : typeIndex.getAccountIndex());
            accountLedger.setRefNumber(voucherModel.getCustomerLedger().getPayment() + "");
            accountLedger.setSearchCode(searchCode);
            accountLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            accountLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(voucherModel.getCustomerLedger().getDate()));
            accountLedger.setType(Constant.CUSTOMER_PAYMENT);
            accountLedger.setTypeIndexNo(voucherModel.getCustomerLedger().getPayment());
            accountLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            journalRepository.save(accountLedger);

        }

        TAccLedger accountLedger = new TAccLedger();
        accountLedger.setAccAccount(client);
        accountLedger.setBankReconciliation(false);
        accountLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
        accountLedger.setChequeDate(null);
        accountLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        accountLedger.setCredit(voucherModel.getPayment().getTotalAmount());
        accountLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        accountLedger.setDebit(new BigDecimal(0));
        accountLedger.setDeleteRefNo(deleteNumber);
        accountLedger.setDescription("Customer Payment");
        accountLedger.setFormName(Constant.FORM_CUSTOMER_PAYMENT);
        accountLedger.setIsCheque(false);
        accountLedger.setIsMain(true);
        accountLedger.setNumber(number);
        accountLedger.setReconcileAccount(null);
        accountLedger.setReconcileGroup(typeIndex == null ? 0 : typeIndex.getAccountIndex());
        accountLedger.setRefNumber(voucherModel.getCustomerLedger().getPayment() + "");
        accountLedger.setSearchCode(searchCode);
        accountLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        accountLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(voucherModel.getCustomerLedger().getDate()));
        accountLedger.setType(Constant.CUSTOMER_PAYMENT);
        accountLedger.setTypeIndexNo(voucherModel.getCustomerLedger().getPayment());
        accountLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
        return journalRepository.save(accountLedger).getIndexNo();

    }

    private String getSearchCode(String code, Integer branch, Integer number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> getPayableBills(Integer account) {
        return journalRepository.getPayableBills(account);
    }

    public Integer save(SupplierPaymentMix mix) {
        if (!"SETTLEMENT".equals(mix.getData().getAccType())) {
            throw new RuntimeException("Other Transaction was blocked ! Customer Settlement only !");
        }
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

//        if ("BANK".equals(mix.getData().getAccType())) {
//            MAccSetting unrealizedIssued = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
//            TAccLedger tAccLedger = new TAccLedger();
//            tAccLedger.setAccAccount(unrealizedIssued.getAccAccount());// reconcile account
//            tAccLedger.setBankReconciliation(true);//
//            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setChequeDate(mix.getData().getChequeDate());
//            tAccLedger.setCredit(mix.getData().getCredit());
//            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            tAccLedger.setDebit(BigDecimal.ZERO);
//            tAccLedger.setDeleteRefNo(deleteNumber);
//            tAccLedger.setDescription(mix.getData().getDescription());
//            tAccLedger.setFormName(mix.getData().getFormName());
//            tAccLedger.setIsMain(true);
//            tAccLedger.setIsCheque(true);
//            tAccLedger.setSearchCode(code);
//            tAccLedger.setNumber(number);
//            tAccLedger.setReconcileAccount(mix.getData().getAccAccount());//
//            tAccLedger.setReconcileGroup(0);//
//            tAccLedger.setRefNumber(mix.getData().getRefNumber());
//            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
//            tAccLedger.setType(mix.getData().getType());
//            tAccLedger.setTypeIndexNo(mix.getData().getTypeIndexNo());
//            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//            TAccLedger save = journalRepository.save(tAccLedger);
//            save.setReconcileGroup(save.getIndexNo());//
//            journalRepository.save(tAccLedger);
//
//            for (DataSub sub : mix.getDataList()) {
//                if (sub.getPay().doubleValue() > 0) {
//
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(sub.getAccAccount());//
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(sub.getChequeDate());
//                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(sub.getPay());
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(code);
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(null);//
//                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
//                    tAccLedger1.setRefNumber(sub.getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//                    journalRepository.save(tAccLedger1);
//
//                }
//            }
//            updateCodeSetting(accCodeSetting);
//        }
//        if ("CASH".equals(mix.getData().getAccType())) {
//            TAccLedger tAccLedger = new TAccLedger();
//            tAccLedger.setAccAccount(mix.getData().getAccAccount());//
//            tAccLedger.setBankReconciliation(false);//
//            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setChequeDate(mix.getData().getChequeDate());
//            tAccLedger.setCredit(mix.getData().getCredit());
//            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            tAccLedger.setDebit(BigDecimal.ZERO);
//            tAccLedger.setDeleteRefNo(deleteNumber);
//            tAccLedger.setDescription(mix.getData().getDescription());
//            tAccLedger.setFormName(mix.getData().getFormName());
//            tAccLedger.setIsMain(true);
//            tAccLedger.setSearchCode(code);
//            tAccLedger.setNumber(number);
//            tAccLedger.setReconcileAccount(null);//
//            tAccLedger.setReconcileGroup(0);//
//            tAccLedger.setRefNumber(mix.getData().getRefNumber());
//            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
//            tAccLedger.setType(mix.getData().getType());
//            tAccLedger.setTypeIndexNo(null);
//            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//            journalRepository.save(tAccLedger);
//
//            for (DataSub sub : mix.getDataList()) {
//                if (sub.getPay().doubleValue() > 0) {
//
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(sub.getAccAccount());//
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(sub.getChequeDate());
//                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(sub.getPay());
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(code);
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(null);//
//                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
//                    tAccLedger1.setRefNumber(sub.getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//                    journalRepository.save(tAccLedger1);
//
//                }
//            }
//            updateCodeSetting(accCodeSetting);
//        }
//        if ("ONLINE".equals(mix.getData().getAccType())) {
//            TAccLedger tAccLedger = new TAccLedger();
//            tAccLedger.setAccAccount(mix.getData().getAccAccount());// reconcile account
//            tAccLedger.setBankReconciliation(false);//
//            tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setChequeDate(mix.getData().getChequeDate());
//            tAccLedger.setCredit(mix.getData().getCredit());
//            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            tAccLedger.setDebit(BigDecimal.ZERO);
//            tAccLedger.setDeleteRefNo(deleteNumber);
//            tAccLedger.setDescription(mix.getData().getDescription());
//            tAccLedger.setFormName(mix.getData().getFormName());
//            tAccLedger.setIsMain(true);
//            tAccLedger.setNumber(number);
//            tAccLedger.setSearchCode(code);
//            tAccLedger.setReconcileAccount(0);//
//            tAccLedger.setReconcileGroup(0);//
//            tAccLedger.setRefNumber(mix.getData().getRefNumber());
//            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//            tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
//            tAccLedger.setType(mix.getData().getType());
//            tAccLedger.setTypeIndexNo(null);
//            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//            TAccLedger save = journalRepository.save(tAccLedger);
//            save.setReconcileGroup(save.getIndexNo());//
//            journalRepository.save(tAccLedger);
//
//            for (DataSub sub : mix.getDataList()) {
//                if (sub.getPay().doubleValue() > 0) {
//
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(sub.getAccAccount());//
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(sub.getChequeDate());
//                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(sub.getPay());
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(code);
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(null);//
//                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
//                    tAccLedger1.setRefNumber(sub.getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//                    journalRepository.save(tAccLedger1);
//
//                }
//            }
//            updateCodeSetting(accCodeSetting);
//        }
//        MAccSetting overPaymentIssue = accountSettingRepository.findByName(Constant.OVER_PAYMENT_ISSUED);
//        if (overPaymentIssue.getIndexNo() <= 0) {
//            throw new RuntimeException("Over Payment Issue Account Setting not found !");
//        }
//
//        if ("OVER_PAYMENT".equals(mix.getData().getAccType())) {
//            BigDecimal credit = mix.getData().getCredit();
//            List<TAccLedger> fifoList = supplierPaymentRepository.getOverPaymentFifoList(mix.getData().getAccAccount(), mix.getData().getTypeIndexNo());
//            for (TAccLedger tAccLedger : fifoList) {
//                BigDecimal balance = (BigDecimal) subtract(tAccLedger.getDebit(), tAccLedger.getCredit());
//                if (credit.doubleValue() > balance.doubleValue()) {
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(mix.getData().getAccAccount());
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(mix.getData().getChequeDate());
//                    tAccLedger1.setCredit(balance);
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(BigDecimal.ZERO);
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(searchCode);
//                    tAccLedger1.setIsCheque(tAccLedger.getIsCheque());
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(0);
//                    tAccLedger1.setReconcileGroup(tAccLedger.getReconcileGroup());
//                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setIsEdit(0);
//                    tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//                    journalRepository.save(tAccLedger1);
//                    credit = (BigDecimal) subtract(credit, balance);
//
//                }
//                if (credit.doubleValue() <= balance.doubleValue()) {
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(mix.getData().getAccAccount());// over payment account
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(mix.getData().getChequeDate());
//                    tAccLedger1.setCredit(credit);
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(BigDecimal.ZERO);
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(searchCode);
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(0);//
//                    tAccLedger1.setReconcileGroup(tAccLedger.getReconcileGroup());//
//                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//                    tAccLedger1.setIsEdit(0);
//                    journalRepository.save(tAccLedger1);
//                    break;
//                }
//
//            }
////            
//            for (DataSub sub : mix.getDataList()) {
//                if (sub.getPay().doubleValue() > 0) {
//
//                    TAccLedger tAccLedger1 = new TAccLedger();
//                    tAccLedger1.setAccAccount(sub.getAccAccount());//
//                    tAccLedger1.setBankReconciliation(false);//
//                    tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setChequeDate(sub.getChequeDate());
//                    tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
//                    tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                    tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    tAccLedger1.setDebit(sub.getPay());
//                    tAccLedger1.setDeleteRefNo(deleteNumber);
//                    tAccLedger1.setDescription(mix.getData().getDescription());
//                    tAccLedger1.setFormName(mix.getData().getFormName());
//                    tAccLedger1.setIsMain(false);
//                    tAccLedger1.setSearchCode(searchCode);
//                    tAccLedger1.setNumber(number);
//                    tAccLedger1.setReconcileAccount(null);//
//                    tAccLedger1.setReconcileGroup(sub.getReconcileGroup());//
//                    tAccLedger1.setRefNumber(mix.getData().getRefNumber());
//                    tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                    tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//                    tAccLedger1.setType(mix.getData().getType());
//                    tAccLedger1.setTypeIndexNo(sub.getTypeIndexNo());
//                    tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//                    journalRepository.save(tAccLedger1);
//                }
//            }
//        }
        if ("SETTLEMENT".equals(mix.getData().getAccType())) {
//            if ("RETURN".equals(mix.getData().getAccTypeSub())) {
//                for (DataSub detail : mix.getDataList()) {
//                    if (detail.getPay().doubleValue() != 0) {
//                        TAccLedger tAccLedger = new TAccLedger();
//                        tAccLedger.setAccAccount(detail.getAccAccount());//
//                        tAccLedger.setBankReconciliation(false);//
//                        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
//                        tAccLedger.setChequeDate(null);
//                        tAccLedger.setDebit(detail.getPay().doubleValue() > 0 ? detail.getPay() : new BigDecimal(0));
//                        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//                        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                        tAccLedger.setCredit(detail.getPay().doubleValue() < 0 ? (detail.getPay().multiply(new BigDecimal(-1))) : new BigDecimal(0));
//                        tAccLedger.setDeleteRefNo(deleteNumber);
//                        tAccLedger.setDescription(mix.getData().getDescription());
//                        tAccLedger.setFormName(mix.getData().getFormName());
//                        tAccLedger.setIsMain(false);
//                        tAccLedger.setSearchCode(searchCode);
//                        tAccLedger.setNumber(number);
//                        tAccLedger.setReconcileAccount(null);//
//                        tAccLedger.setReconcileGroup(detail.getReconcileGroup());//
//                        tAccLedger.setRefNumber(mix.getData().getRefNumber());
//                        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//                        tAccLedger.setTransactionDate(mix.getData().getTransactionDate());
//                        tAccLedger.setType(mix.getData().getType());
//                        tAccLedger.setTypeIndexNo(null);
//                        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//                        journalRepository.save(tAccLedger);
//                    }
//                }
//            }
            if ("ACCOUNT".equals(mix.getData().getAccTypeSub())) {
                TAccLedger tAccLedger1 = new TAccLedger();
                tAccLedger1.setAccAccount(mix.getData().getAccAccount());
                tAccLedger1.setBankReconciliation(false);
                tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger1.setChequeDate(null);
                tAccLedger1.setCredit(mix.getData().getBillTotal().doubleValue() < 0 ? mix.getData().getBillTotal().multiply(new BigDecimal(-1)) : new BigDecimal(BigInteger.ZERO));
                tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger1.setDebit(mix.getData().getBillTotal().doubleValue() > 0 ? mix.getData().getBillTotal() : new BigDecimal(BigInteger.ZERO));
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

                TAccLedger save = journalRepository.save(tAccLedger1);
                if (save.getIndexNo() <= 0) {
                    throw new RuntimeException("Save Fail !");
                }
                for (DataSub detail : mix.getDataList()) {
                    if (detail.getPay().doubleValue() != 0) {
                        TAccLedger tAccLedger = new TAccLedger();
                        tAccLedger.setAccAccount(detail.getAccAccount());
                        tAccLedger.setBankReconciliation(false);
                        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setChequeDate(null);
                        tAccLedger.setCredit(detail.getPay().doubleValue() > 0 ? detail.getPay() : new BigDecimal(0));
                        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        tAccLedger.setDebit(detail.getPay().doubleValue() < 0 ? (detail.getPay().multiply(new BigDecimal(-1))) : new BigDecimal(0));
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

                        journalRepository.save(tAccLedger);
                    }
                }
            }
        }
//        //over payment save
//        if (overPayment.doubleValue() > 0) {
//            TAccLedger tAccLedger1 = new TAccLedger();
//            tAccLedger1.setAccAccount(overPaymentIssue.getAccAccount());
//            tAccLedger1.setBankReconciliation(false);
//            tAccLedger1.setBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger1.setChequeDate(null);
//            tAccLedger1.setCredit(new BigDecimal(BigInteger.ZERO));
//            tAccLedger1.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
//            tAccLedger1.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            tAccLedger1.setDebit(overPayment);
//            tAccLedger1.setDeleteRefNo(deleteNumber);
//            tAccLedger1.setDescription(mix.getData().getDescription());
//            tAccLedger1.setFormName(mix.getData().getFormName());
//            tAccLedger1.setIsMain(false);
//            tAccLedger1.setNumber(number);
//            tAccLedger1.setSearchCode(searchCode);
//            tAccLedger1.setReconcileAccount(null);
//            tAccLedger1.setReconcileGroup(0);
//            tAccLedger1.setRefNumber(mix.getData().getRefNumber());
//            tAccLedger1.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
//            tAccLedger1.setTransactionDate(mix.getData().getTransactionDate());
//            tAccLedger1.setType(mix.getData().getType());
//            tAccLedger1.setTypeIndexNo(mix.getData().getTypeIndexNo());
//            tAccLedger1.setUser(SecurityUtil.getCurrentUser().getIndexNo());
//
//            TAccLedger save = journalRepository.save(tAccLedger1);
//            save.setReconcileGroup(save.getIndexNo());
//            journalRepository.save(save);
//        }

        return 1;
    }

    private String getSearchCode2(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
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

}
