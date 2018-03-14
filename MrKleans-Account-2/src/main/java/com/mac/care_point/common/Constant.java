/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.common;

/**
 *
 * @author kasun
 */
public interface Constant {
    //acc setting
    public static final String UNREALIZED_ISSUED = "unrealized_issued";
    public static final String UNREALIZED_RECEIVED = "unrealized_received";
    public static final String OVER_PAYMENT_ISSUED = "over_payment_issue";
    public static final String OVER_PAYMENT_RECEIVED = "over_payment_received";
    public static final String INVENTORY = "inventory";
    public static final String SUPPLIER_SUB_ACCOUNT_OF = "supplier_sub_account_of";
    public static final String ITEM_SALES = "item_sales";
    public static final String CUSTOMER_SUB_ACCOUNT_OF = "customer_sub_account_of";
    public static final String ITEM_DISCOUNT_OUT = "item_discount_out";
    public static final String ITEM_SALES_CASH_IN = "item_sales_cash_in";
    public static final String CHEQUE_IN_HAND = "cheque_in_hand";

    // ledger type
    public static final String VOUCHER = "voucher";
    public static final String JOURNAL = "journal";
    public static final String ACCRUED = "accrued";
    public static final String RECONCILIATION = "reconciliation";
    public static final String SUPPLIER_PAYMENT = "supplier_payment";
    public static final String CUSTOMER_PAYMENT = "customer_payment";
    public static final String CHEQUE_RETURN = "cheque_return";
    public static final String FUND_TRANSFER = "transfer";
    public static final String ADVANCE = "advance";
    public static final String GRN = "grn";
    public static final String DIRECT_GRN = "direct_grn";

    //form Name
    public static final String FORM_JOURNAL = "journal_form";
    public static final String FORM_BANK_RECONCILIATION = "bank_reconciliation";
    public static final String FORM_SUPPLIER_PAYMENT = "supplier_payment";
    public static final String FORM_ACCRUED_BILL = "accrued_bill";
    public static final String FORM_FUND_TRANSFER = "fund_transfer";
    public static final String FORM_CHEQUE_RETURN = "cheque_return";
    public static final String FORM_PAYMENT_VOUCHER = "payment_voucher";
    public static final String FORM_CUSTOMER_PAYMENT = "customer_payment";
    public static final String FORM_ITEM_SALES = "item_sales";
    public static final String FORM_ADVANCE = "ADVANCE_FORM";
    public static final String PAYMENT_VOUCHER_FORM = "PAYMENT_VOUCHER_FORM";
    public static final String PAYMENT_FORM = "PAYMENT_FORM";
    public static final String FORM_STOCK = "STOCK_FORM";
    public static final String FORM_GRN_APPROVE = "GRN_APPROVE_FORM";
    public static final String FORM_DIRECT_GRN = "DIRECT_GRN_FORM";
    
    //form Code
    public static final String CODE_JOURNAL = "JNL";
    public static final String CODE_BANK_RECONCILIATION = "RECON";
    public static final String CODE_SUPPLIER_PAYMENT = "SUPPAY";
    public static final String CODE_ACCRUED_BILL = "ACRDBIL";
    public static final String CODE_FUND_TRANSFER = "FNDTRA";
    public static final String CODE_CHEQUE_RETURN = "CHQRTN";
    public static final String CODE_PAYMENT_VOUCHER = "PAYVUC";
    public static final String CODE_DIRECT_GRN = "DGRN";
    public static final String CODE_GRN = "GRN";
    public static final String CODE_ITEM_SALES = "ITMSAL";

    //  store name
    public static final String MAIN_STOCK = "MAIN_STOCK";
    public static final String FRONT_STOCK = "FRONT_STOCK";
    public static final String BULK_STOCK = "BULK_STOCK";

    // other
    public static final String PAYMENT = "PAYMENT";
    public static final String NOT_CHECK_STATUS = "NOT_CHECK";
    
    //status
    public static final String STATUS_FINISHED = "FINISHED";
    
     //Branch Type
    public static final String MAIN_BRANCH = "MAIN_BRANCH";
    public static final String OTHER_BRANCH = "OTHER_BRANCH";
 
    //type index detail types
    public static final String SUPPLIER = "SUPPLIER";
    public static final String ITEM = "ITEM";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String VEHICLE = "VEHICLE";
    public static final String INVOICE = "INVOICE";
}
