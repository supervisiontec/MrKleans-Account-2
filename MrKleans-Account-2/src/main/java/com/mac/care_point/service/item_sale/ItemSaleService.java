/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.item_sale;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.CommonRepository;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountService;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.master.card_reader.CardReaderRepository;
import com.mac.care_point.master.card_reader.model.MCardReader;
import com.mac.care_point.master.client.model.Client;
import com.mac.care_point.service.invoice.InvoiceRepository;
import com.mac.care_point.service.item_sale.model.ItemSaleModel;
import com.mac.care_point.service.item_sale.model.JobCard;
import com.mac.care_point.service.item_sale.model.TCustomerLedger;
import com.mac.care_point.service.item_sale.model.TInvoice;
import com.mac.care_point.service.job_item.model.TJobItem;
import com.mac.care_point.service.item_sale.model.TPayment;
import com.mac.care_point.service.item_sale.model.TPaymentInformation;
import com.mac.care_point.service.job_card.JobCardRepository;
import com.mac.care_point.service.job_item.JobItemRepository;
import com.mac.care_point.service.payment.ClientLegerRepository;
import com.mac.care_point.service.payment.PaymentInformationRepostory;
import com.mac.care_point.service.payment.PaymentRepository;
import com.mac.care_point.service.payment_voucher.PaymentVoucherRepository;
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

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemSaleService {

    @Autowired
    private JobCardRepository jobCardRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private JobItemRepository jobItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentInformationRepostory paymentInformationRepostory;

    @Autowired
    public PaymentVoucherRepository paymentVoucherRepository;

    @Autowired
    private ClientLegerRepository clientLegerRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private CustomerRepository clientRepository;

    @Autowired
    private AccAccountService accAccountService;

    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private CardReaderRepository cardReaderRepository;

    @Transactional
    public Integer saveItemSale(ItemSaleModel itemSaleModel) {
        TPayment savePayment = new TPayment();
        JobCard jobCard = new JobCard();
        //save job card
        jobCard.setClient(itemSaleModel.getCustomerLedger().getClient());
        jobCard.setBranch(SecurityUtil.getCurrentUser().getBranch());
        jobCard.setDate(new Date());
        jobCard.setNumber(commonRepository.getNextJobNumber(itemSaleModel.getInvoice().getBranch(), Constant.FORM_ITEM_SALES));
        jobCard.setTransaction(1);
        jobCard.setVehicleImages(Boolean.FALSE);
        jobCard.setServiceChagers(Boolean.FALSE);
        jobCard.setFinalCheck(Boolean.FALSE);
        jobCard.setAttenctions(Boolean.FALSE);
        jobCard.setDefaultFinalCheck(Boolean.FALSE);
        jobCard.setInvoice(Boolean.TRUE);
        jobCard.setStatus(Constant.STATUS_FINISHED);
        jobCard.setFormName(Constant.FORM_ITEM_SALES);
        JobCard job = jobCardRepository.save(jobCard);

        //save invoice
        if (itemSaleModel.getInvoice().getNetAmount().doubleValue() == 0.00) {
            itemSaleModel.getInvoice().setNetAmount(itemSaleModel.getInvoice().getAmount());
        }
        itemSaleModel.getInvoice().setJobCard(job.getIndexNo());
        itemSaleModel.getInvoice().setDate(new Date());
        itemSaleModel.getInvoice().setNumber(commonRepository.getNextInvoiceNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.FORM_ITEM_SALES));
        itemSaleModel.getInvoice().setStatus(Constant.FORM_ITEM_SALES);
        TInvoice saveInvoice = invoiceRepository.save(itemSaleModel.getInvoice());

        //save customer ledger
        TCustomerLedger customerLedger1 = new TCustomerLedger();
        customerLedger1.setClient(itemSaleModel.getCustomerLedger().getClient());
        customerLedger1.setCreditAmount(BigDecimal.ZERO);
        customerLedger1.setDate(new Date());
        customerLedger1.setDebitAmount(itemSaleModel.getInvoice().getNetAmount());
        customerLedger1.setFormName(Constant.FORM_ITEM_SALES);
        customerLedger1.setInvoice(saveInvoice.getIndexNo());
        customerLedger1.setPayment(null);
        customerLedger1.setRefNumber(saveInvoice.getIndexNo());
        customerLedger1.setType("INVOICE");
        clientLegerRepository.save(customerLedger1);

        //save job items
        for (TJobItem jobitem : itemSaleModel.getJobItem()) {
            jobitem.setJobCard(job.getIndexNo());
            jobitem.setIsChange(Boolean.FALSE);
            jobItemRepository.save(jobitem);
        }

        //save payment
        if (itemSaleModel.getPayment().getTotalAmount().doubleValue() > 0.00) {
            savePayment = paymentRepository.save(itemSaleModel.getPayment());

            double overPaymentSettlementAmount = 0.00;

            for (TPaymentInformation paymentInformation : itemSaleModel.getPaymentInformationList()) {
                if ("OVER_PAYMENT_SETTLEMENT".equals(paymentInformation.getType())) {
                    overPaymentSettlementAmount = paymentInformation.getAmount().doubleValue();
                }
                if (!"OVER_PAYMENT_SETTLEMENT".equals(paymentInformation.getType())) {
                    paymentInformation.setFormName(Constant.FORM_ITEM_SALES);
                    paymentInformation.setPayment(savePayment.getIndexNo());
                    paymentInformationRepostory.save(paymentInformation);
                }
            }

            savePayment.setOverPaymentAmount(new BigDecimal(overPaymentSettlementAmount));
            savePayment = paymentRepository.save(savePayment);

            //over payment settlement
            if (overPaymentSettlementAmount > 0.00) {
                //save over payment
                List<Object[]> fifoList = new ArrayList<>();
                while (overPaymentSettlementAmount > 0.00) {
                    fifoList = getFIFOList(itemSaleModel.getCustomerLedger().getClient());

                    for (int i = 0; i < fifoList.size(); i++) {
                        Object[] selectFirst = fifoList.get(i);
                        Integer paymentIndex = Integer.parseInt(selectFirst[0].toString());
                        Double overAmount = Double.parseDouble(selectFirst[1].toString());

                        String type = selectFirst[2].toString();

                        if (overPaymentSettlementAmount > overAmount) {
                            //save over payment settlement

                            TCustomerLedger customerLedger = new TCustomerLedger();
                            customerLedger.setClient(itemSaleModel.getCustomerLedger().getClient());
                            customerLedger.setCreditAmount(new BigDecimal(overAmount));
                            customerLedger.setDate(new Date());
                            customerLedger.setDebitAmount(new BigDecimal(0));
                            customerLedger.setFormName(Constant.FORM_ITEM_SALES);
                            customerLedger.setInvoice(null);
                            customerLedger.setPayment(paymentIndex);
                            customerLedger.setRefNumber(paymentIndex);
                            customerLedger.setType(type);
                            clientLegerRepository.save(customerLedger);

                            overPaymentSettlementAmount -= overAmount;

//                          
                        } else {
                            //save over payment settlement
                            if (overPaymentSettlementAmount > 0.00) {

                                TCustomerLedger customerLedger = new TCustomerLedger();
                                customerLedger.setClient(itemSaleModel.getCustomerLedger().getClient());
                                customerLedger.setCreditAmount(new BigDecimal(overPaymentSettlementAmount));
                                customerLedger.setDate(new Date());
                                customerLedger.setDebitAmount(new BigDecimal(0));
                                customerLedger.setFormName(Constant.FORM_ITEM_SALES);
                                customerLedger.setInvoice(null);
                                customerLedger.setPayment(paymentIndex);
                                customerLedger.setRefNumber(paymentIndex);
                                customerLedger.setType(type);
                                clientLegerRepository.save(customerLedger);
                            }
                            overPaymentSettlementAmount = 0.00;
                        }

                    }
                }
            }
        }

        double totalPaidAmount = itemSaleModel.getPayment().getTotalAmount().doubleValue();
        double payAmountTotal = itemSaleModel.getPayment().getCardAmount().doubleValue() + itemSaleModel.getPayment().getCashAmount().doubleValue() + itemSaleModel.getPayment().getChequeAmount().doubleValue();
        //save over payment
        if (payAmountTotal <= totalPaidAmount && totalPaidAmount > 0.00 && payAmountTotal > 0.00) {
            TCustomerLedger customerLedger = new TCustomerLedger();
            customerLedger.setClient(itemSaleModel.getCustomerLedger().getClient());
            customerLedger.setCreditAmount(BigDecimal.ZERO);
            customerLedger.setDate(new Date());
            customerLedger.setDebitAmount(new BigDecimal(payAmountTotal));
            customerLedger.setFormName(Constant.FORM_ITEM_SALES);
            customerLedger.setInvoice(null);
            customerLedger.setPayment(savePayment.getIndexNo());
            customerLedger.setRefNumber(savePayment.getIndexNo());
            customerLedger.setType(Constant.PAYMENT);
            clientLegerRepository.save(customerLedger);
        }

        // account link
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.ITEM_SALES);
        int deleteNumber = journalRepository.getDeleteNumber();
        String searchCode = getSearchCode(Constant.CODE_ITEM_SALES, SecurityUtil.getCurrentUser().getBranch(), number);

        Client selectedCustomer = clientRepository.findOne(itemSaleModel.getCustomerLedger().getClient());
        Integer customerAccount = selectedCustomer.getAccAccount();
        if (selectedCustomer.getAccAccount() == null) {
            //create supplier
            MAccSetting customerSubAccountOf = accountSettingRepository.findByName(Constant.CUSTOMER_SUB_ACCOUNT_OF);
            MAccAccount account = new MAccAccount();
            account.setAccCode(null);
            account.setAccMain(null);
            account.setAccType("COMMON");
            account.setCop(false);
            account.setDescription("Item Sales");
            account.setIsAccAccount(true);
            account.setLevel(null);
            account.setName(selectedCustomer.getName());
            account.setSubAccountCount(0);
            account.setSubAccountOf(customerSubAccountOf.getAccAccount());
            account.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            customerAccount = accAccountService.saveNewAccount(account).getIndexNo();
            selectedCustomer.setAccAccount(customerAccount);
            clientRepository.save(selectedCustomer);
        }
        //client account
        TAccLedger tAccLedger = new TAccLedger();
        tAccLedger.setAccAccount(customerAccount);
        tAccLedger.setBankReconciliation(false);
        tAccLedger.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setChequeDate(null);
        tAccLedger.setDebit(itemSaleModel.getInvoice().getNetAmount());
        tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger.setCredit(new BigDecimal(0));
        tAccLedger.setDeleteRefNo(deleteNumber);
        tAccLedger.setDescription("Item Sales");
        tAccLedger.setFormName(Constant.FORM_ITEM_SALES);
        tAccLedger.setIsCheque(false);
        tAccLedger.setIsMain(false);
        tAccLedger.setNumber(number);
        tAccLedger.setReconcileAccount(null);
        tAccLedger.setReconcileGroup(null);
        tAccLedger.setRefNumber(saveInvoice.getNumber() + "");
        tAccLedger.setSearchCode(searchCode);
        tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
        tAccLedger.setType(Constant.ITEM_SALES);
        tAccLedger.setTypeIndexNo(selectedCustomer.getIndexNo());
        tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());

        TAccLedger detail = journalRepository.save(tAccLedger);
        detail.setReconcileGroup(detail.getIndexNo());
        journalRepository.save(tAccLedger);

        //item sale account
        MAccSetting itemSales = accountSettingRepository.findByName(Constant.ITEM_SALES);
        TAccLedger tAccLedger2 = new TAccLedger();
        tAccLedger2.setAccAccount(itemSales.getAccAccount());
        tAccLedger2.setBankReconciliation(false);
        tAccLedger2.setBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setChequeDate(null);
        tAccLedger2.setCredit(itemSaleModel.getInvoice().getNetAmount());
        tAccLedger2.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        tAccLedger2.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tAccLedger2.setDebit(new BigDecimal(0));
        tAccLedger2.setDeleteRefNo(deleteNumber);
        tAccLedger2.setDescription("Item Sales");
        tAccLedger2.setFormName(Constant.FORM_ITEM_SALES);
        tAccLedger2.setIsCheque(false);
        tAccLedger2.setIsMain(false);
        tAccLedger2.setNumber(number);
        tAccLedger2.setReconcileAccount(null);
        tAccLedger2.setReconcileGroup(null);
        tAccLedger2.setRefNumber(saveInvoice.getNumber() + "");
        tAccLedger2.setSearchCode(searchCode);
        tAccLedger2.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        tAccLedger2.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
        tAccLedger2.setType(Constant.ITEM_SALES);
        tAccLedger2.setTypeIndexNo(selectedCustomer.getIndexNo());
        tAccLedger2.setUser(SecurityUtil.getCurrentUser().getIndexNo());

        journalRepository.save(tAccLedger2);

        //discount
        if (itemSaleModel.getInvoice().getDiscountAmount().doubleValue() > 0) {

            //client account set discount
            TAccLedger tAccLedger3 = new TAccLedger();
            tAccLedger3.setAccAccount(customerAccount);
            tAccLedger3.setBankReconciliation(false);
            tAccLedger3.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger3.setChequeDate(null);
            tAccLedger3.setCredit(itemSaleModel.getInvoice().getDiscountAmount());
            tAccLedger3.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger3.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger3.setDebit(new BigDecimal(0));
            tAccLedger3.setDeleteRefNo(deleteNumber);
            tAccLedger3.setDescription("Item Sales Discount");
            tAccLedger3.setFormName(Constant.FORM_ITEM_SALES);
            tAccLedger3.setIsCheque(false);
            tAccLedger3.setIsMain(false);
            tAccLedger3.setNumber(number);
            tAccLedger3.setReconcileAccount(null);
            tAccLedger3.setReconcileGroup(detail.getIndexNo());
            tAccLedger3.setRefNumber(saveInvoice.getNumber() + "");
            tAccLedger3.setSearchCode(searchCode);
            tAccLedger3.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger3.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
            tAccLedger3.setType(Constant.ITEM_SALES);
            tAccLedger3.setTypeIndexNo(selectedCustomer.getIndexNo());
            tAccLedger3.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            journalRepository.save(tAccLedger3);

            //discount account
            MAccSetting discountOut = accountSettingRepository.findByName(Constant.ITEM_DISCOUNT_OUT);
            TAccLedger tAccLedger4 = new TAccLedger();
            tAccLedger4.setAccAccount(discountOut.getAccAccount());
            tAccLedger4.setBankReconciliation(false);
            tAccLedger4.setBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger4.setChequeDate(null);
            tAccLedger4.setDebit(itemSaleModel.getInvoice().getDiscountAmount());
            tAccLedger4.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger4.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger4.setCredit(new BigDecimal(0));
            tAccLedger4.setDeleteRefNo(deleteNumber);
            tAccLedger4.setDescription("Item Sales Discount");
            tAccLedger4.setFormName(Constant.FORM_ITEM_SALES);
            tAccLedger4.setIsCheque(false);
            tAccLedger4.setIsMain(false);
            tAccLedger4.setNumber(number);
            tAccLedger4.setReconcileAccount(null);
            tAccLedger4.setReconcileGroup(null);
            tAccLedger4.setRefNumber(saveInvoice.getNumber() + "");
            tAccLedger4.setSearchCode(searchCode);
            tAccLedger4.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger4.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
            tAccLedger4.setType(Constant.ITEM_SALES);
            tAccLedger4.setTypeIndexNo(selectedCustomer.getIndexNo());
            tAccLedger4.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            journalRepository.save(tAccLedger4);
        }
        // payment
        for (TPaymentInformation tPaymentInformation : itemSaleModel.getPaymentInformationList()) {

            if (tPaymentInformation.getType().equals("CASH")) {

                //client account set payment
                TAccLedger tAccLedger3 = new TAccLedger();
                tAccLedger3.setAccAccount(customerAccount);
                tAccLedger3.setBankReconciliation(false);
                tAccLedger3.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setChequeDate(null);
                tAccLedger3.setCredit(tPaymentInformation.getAmount());
                tAccLedger3.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger3.setDebit(new BigDecimal(0));
                tAccLedger3.setDeleteRefNo(deleteNumber);
                tAccLedger3.setDescription("Item Sales Cash");
                tAccLedger3.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger3.setIsCheque(false);
                tAccLedger3.setIsMain(false);
                tAccLedger3.setNumber(number);
                tAccLedger3.setReconcileAccount(null);
                tAccLedger3.setReconcileGroup(detail.getIndexNo());
                tAccLedger3.setRefNumber(saveInvoice.getNumber() + "");
                tAccLedger3.setSearchCode(searchCode);
                tAccLedger3.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger3.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger3.setType(Constant.ITEM_SALES);
                tAccLedger3.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger3.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger3);

                //cash in
                MAccSetting cashIn = accountSettingRepository.findByName(Constant.ITEM_SALES_CASH_IN);
                TAccLedger tAccLedger4 = new TAccLedger();
                tAccLedger4.setAccAccount(cashIn.getAccAccount());
                tAccLedger4.setBankReconciliation(false);
                tAccLedger4.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setChequeDate(null);
                tAccLedger4.setDebit(tPaymentInformation.getAmount());
                tAccLedger4.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger4.setCredit(new BigDecimal(0));
                tAccLedger4.setDeleteRefNo(deleteNumber);
                tAccLedger4.setDescription("Item Sales Cash");
                tAccLedger4.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger4.setIsCheque(false);
                tAccLedger4.setIsMain(false);
                tAccLedger4.setNumber(number);
                tAccLedger4.setReconcileAccount(null);
                tAccLedger4.setReconcileGroup(null);
                tAccLedger4.setRefNumber(saveInvoice.getNumber() + "");
                tAccLedger4.setSearchCode(searchCode);
                tAccLedger4.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger4.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger4.setType(Constant.ITEM_SALES);
                tAccLedger4.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger4.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger4);
            }
            if (tPaymentInformation.getType().equals("CHEQUE")) {

                //client account set payment
                TAccLedger tAccLedger3 = new TAccLedger();
                tAccLedger3.setAccAccount(customerAccount);
                tAccLedger3.setBankReconciliation(false);
                tAccLedger3.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setChequeDate(null);
                tAccLedger3.setCredit(tPaymentInformation.getAmount());
                tAccLedger3.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger3.setDebit(new BigDecimal(0));
                tAccLedger3.setDeleteRefNo(deleteNumber);
                tAccLedger3.setDescription("Item Sales Cheque");
                tAccLedger3.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger3.setIsCheque(false);
                tAccLedger3.setIsMain(false);
                tAccLedger3.setNumber(number);
                tAccLedger3.setReconcileAccount(null);
                tAccLedger3.setReconcileGroup(detail.getIndexNo());
                tAccLedger3.setRefNumber(saveInvoice.getNumber() + "");
                tAccLedger3.setSearchCode(searchCode);
                tAccLedger3.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger3.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger3.setType(Constant.ITEM_SALES);
                tAccLedger3.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger3.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger3);

                //Cheque in
                MAccSetting chequeInHand = accountSettingRepository.findByName(Constant.CHEQUE_IN_HAND);
                TAccLedger tAccLedger4 = new TAccLedger();
                tAccLedger4.setAccAccount(chequeInHand.getAccAccount());
                tAccLedger4.setBankReconciliation(false);
                tAccLedger4.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setChequeDate(new SimpleDateFormat("yyyy-MM-dd").format(tPaymentInformation.getChequeDate()));
                tAccLedger4.setDebit(tPaymentInformation.getAmount());
                tAccLedger4.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger4.setCredit(new BigDecimal(0));
                tAccLedger4.setDeleteRefNo(deleteNumber);
                tAccLedger4.setDescription("Item Sales Cheque");
                tAccLedger4.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger4.setIsCheque(true);
                tAccLedger4.setIsMain(false);
                tAccLedger4.setNumber(number);
                tAccLedger4.setReconcileAccount(null);
                tAccLedger4.setReconcileGroup(null);
                tAccLedger4.setRefNumber(tPaymentInformation.getNumber());
                tAccLedger4.setSearchCode(searchCode);
                tAccLedger4.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger4.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger4.setType(Constant.ITEM_SALES);
                tAccLedger4.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger4.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger4);
            }
            if (tPaymentInformation.getType().equals("CARD")) {

                //client account set payment
                TAccLedger tAccLedger3 = new TAccLedger();
                tAccLedger3.setAccAccount(customerAccount);
                tAccLedger3.setBankReconciliation(false);
                tAccLedger3.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setChequeDate(null);
                tAccLedger3.setCredit(tPaymentInformation.getAmount());
                tAccLedger3.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger3.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger3.setDebit(new BigDecimal(0));
                tAccLedger3.setDeleteRefNo(deleteNumber);
                tAccLedger3.setDescription("Item Sales Card");
                tAccLedger3.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger3.setIsCheque(false);
                tAccLedger3.setIsMain(false);
                tAccLedger3.setNumber(number);
                tAccLedger3.setReconcileAccount(null);
                tAccLedger3.setReconcileGroup(detail.getIndexNo());
                tAccLedger3.setRefNumber(saveInvoice.getNumber() + "");
                tAccLedger3.setSearchCode(searchCode);
                tAccLedger3.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger3.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger3.setType(Constant.ITEM_SALES);
                tAccLedger3.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger3.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger3);

                //card in
                MAccSetting unrealizeReceived = accountSettingRepository.findByName(Constant.UNREALIZED_RECEIVED);
                MCardReader cardReaderModel = cardReaderRepository.findOne(tPaymentInformation.getCardReader());
                TAccLedger tAccLedger4 = new TAccLedger();
                tAccLedger4.setAccAccount(unrealizeReceived.getAccAccount());
                tAccLedger4.setBankReconciliation(true);
                tAccLedger4.setBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setChequeDate(null);
                tAccLedger4.setDebit(tPaymentInformation.getAmount());
                tAccLedger4.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
                tAccLedger4.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                tAccLedger4.setCredit(new BigDecimal(0));
                tAccLedger4.setDeleteRefNo(deleteNumber);
                tAccLedger4.setDescription("Item Sales Cheque");
                tAccLedger4.setFormName(Constant.FORM_ITEM_SALES);
                tAccLedger4.setIsCheque(false);
                tAccLedger4.setIsMain(false);
                tAccLedger4.setNumber(number);
                tAccLedger4.setReconcileAccount(cardReaderModel.getAccAccount());
                tAccLedger4.setReconcileGroup(null);
                tAccLedger4.setRefNumber(saveInvoice.getNumber() + "");
                tAccLedger4.setSearchCode(searchCode);
                tAccLedger4.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
                tAccLedger4.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(jobCard.getDate()));
                tAccLedger4.setType(Constant.ITEM_SALES);
                tAccLedger4.setTypeIndexNo(selectedCustomer.getIndexNo());
                tAccLedger4.setUser(SecurityUtil.getCurrentUser().getIndexNo());

                journalRepository.save(tAccLedger4);
            }
        }

        return savePayment.getIndexNo();
    }

    private List<Object[]> getFIFOList(int client) {
        return paymentVoucherRepository.getFIFOList(client);
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

}
