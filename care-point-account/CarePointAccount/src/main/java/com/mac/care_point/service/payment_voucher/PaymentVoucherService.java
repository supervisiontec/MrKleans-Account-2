/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.payment_voucher;

import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.master.client.ClientRepository;
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
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private JournalRepository accountLedgerRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TypeIndexDetailRepository typeIndexDetailRepository;

    public Object getClientVehicles(Integer client) {
        return paymentVoucherRepository.getClientVehicles(client);

    }

    public Double getClientBalance(Integer client) {
        return paymentVoucherRepository.getClientBalance(client);
    }

    public Double getClientOverPayment(Integer client) {
        return paymentVoucherRepository.getClientOverPayment(client);
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

        TPayment savePayment = paymentRepository.save(voucherModel.getPayment());
        for (TPaymentInformation paymentInformation : voucherModel.getPaymentInformationList()) {
            paymentInformation.setFormName(Constant.FORM_ADVANCE);
            paymentInformation.setPayment(savePayment.getIndexNo());
            paymentInformationRepostory.save(paymentInformation);
        }
        voucherModel.getCustomerLedger().setCreditAmount(savePayment.getTotalAmount());
        voucherModel.getCustomerLedger().setInvoice(null);
        voucherModel.getCustomerLedger().setPayment(savePayment.getIndexNo());
        voucherModel.getCustomerLedger().setType(Constant.ADVANCE);
        voucherModel.getCustomerLedger().setFormName(Constant.FORM_ADVANCE);
        voucherModel.getCustomerLedger().setRefNumber(savePayment.getIndexNo());
        TCustomerLedger save = clientLegerRepository.save(voucherModel.getCustomerLedger());

        saveCustomerPaymentToAccountSystem(voucherModel);
        return save.getIndexNo();
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

    private void saveCustomerPaymentToAccountSystem(PaymentVoucherModel voucherModel) {
        saveClientAccount(voucherModel);
        savePaymentDetails(voucherModel);
    }

    private void saveClientAccount(PaymentVoucherModel voucherModel) {
        int number = accountLedgerRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.SUPPLIER_PAYMENT);
        int deleteNumber = accountLedgerRepository.getDeleteNumber();
        int clientAccAccount = clientRepository.findByAccAccount(voucherModel.getCustomerLedger().getClient());
        int reconcileGroup = typeIndexDetailRepository.findByTypeAndAccountRefId(Constant.INVOICE, voucherModel.getCustomerLedger().getInvoice());
        String searchCode = getSearchCode(Constant.CODE_SUPPLIER_PAYMENT, SecurityUtil.getCurrentUser().getBranch(), number);

        TAccLedger accountLedger = new TAccLedger();
        accountLedger.setAccAccount(clientAccAccount);
        accountLedger.setBankReconciliation(false);
        accountLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
        accountLedger.setChequeDate(null);
        accountLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        accountLedger.setCredit(voucherModel.getCustomerLedger().getCreditAmount());
        accountLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        accountLedger.setDebit(new BigDecimal(0));
        accountLedger.setDeleteRefNo(deleteNumber);
        accountLedger.setDescription("Customer Payment Balance Payment");
        accountLedger.setFormName(Constant.FORM_CUSTOMER_PAYMENT);
        accountLedger.setIsCheque(false);
        accountLedger.setIsMain(true);
        accountLedger.setNumber(number);
        accountLedger.setReconcileAccount(null);
        accountLedger.setReconcileGroup(reconcileGroup);
        accountLedger.setRefNumber(voucherModel.getCustomerLedger().getPayment() + "");
        accountLedger.setSearchCode(searchCode);
        accountLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        accountLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(voucherModel.getCustomerLedger().getDate()));
        accountLedger.setType(Constant.CUSTOMER_PAYMENT);
        accountLedger.setTypeIndexNo(voucherModel.getCustomerLedger().getPayment());
        accountLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

//        accountLedgerRepository.save(accountLedger);
//        HashMap<Integer,String> map = new HashMap<>();
//        map.put(1, searchCode)

    }

    private void savePaymentDetails(PaymentVoucherModel voucherModel) {

    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }
}
