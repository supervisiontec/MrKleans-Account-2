/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.common;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface Constant {
    //acc setting
    public static final String UNREALIZED_ISSUED="unrealized_issued";
    public static final String UNREALIZED_RECEIVED="unrealized_received";
    public static final String OVER_PAYMENT_ISSUED="over_payment_issue";
    public static final String OVER_PAYMENT_RECEIVED="over_payment_received";
    
    // ledger type
    public static final String VOUCHER="voucher";
    public static final String JOURNAL="journal";
    public static final String ACCRUED="accrued";
    public static final String RECONCILIATION="reconciliation";
    public static final String SUPPLIER_PAYMENT="supplier_payment";
    public static final String CHEQUE_RETURN="cheque_return";
    public static final String FUND_TRANSFER="transfer";
    
    //form Name
    public static final String FORM_JOURNAL="journal_form";
    public static final String FORM_BANK_RECONCILIATION="bank_reconciliation";
    public static final String FORM_SUPPLIER_PAYMENT="supplier_payment";
    public static final String FORM_ACCRUED_BILL="accrued_bill";
    public static final String FORM_FUND_TRANSFER="fund_transfer";
    public static final String FORM_CHEQUE_RETURN="cheque_return";
    public static final String FORM_PAYMENT_VOUCHER="payment_voucher";
    
    //form Code
    public static final String CODE_JOURNAL="JNL";
    public static final String CODE_BANK_RECONCILIATION="RECON";
    public static final String CODE_SUPPLIER_PAYMENT="SUPPAY";
    public static final String CODE_ACCRUED_BILL="ACRDBIL";
    public static final String CODE_FUND_TRANSFER="FNDTRA";
    public static final String CODE_CHEQUE_RETURN="CHQRTN";
    public static final String CODE_PAYMENT_VOUCHER="PAYVUC";
    
}
