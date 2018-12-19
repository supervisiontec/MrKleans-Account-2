/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_acc_ledger")
@XmlRootElement
public class TAccLedger implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @Column(name = "transaction_date")
    private String transactionDate;

    @Basic(optional = false)
    @Column(name = "number")
    private Integer number;

    @Basic(optional = false)
    @Column(name = "search_code")
    private String searchCode;

    @Basic(optional = false)
    @Column(name = "`current_date`")
    private String currentDate;

    @Basic(optional = false)
    @Column(name = "`time`")
    private String time;

    @Basic(optional = false)
    @Column(name = "`branch`")
    private int branch;

    @Basic(optional = false)
    @Column(name = "`current_branch`")
    private int currentBranch;

    @Basic(optional = false)
    @Column(name = "`user`")
    private int user;

    @Basic(optional = false)
    @Column(name = "`debit`")
    private BigDecimal debit=new BigDecimal(0);

    @Basic(optional = false)
    @Column(name = "`credit`")
    private BigDecimal credit=new BigDecimal(0);

    @Column(name = "form_name")
    private String formName;

    @Column(name = "ref_number")
    private String refNumber;

    @Column(name = "`type`")
    private String type;

    @Column(name = "type_index_no")
    private Integer typeIndexNo;

    @Column(name = "delete_ref_no")
    private Integer deleteRefNo;

    @Column(name = "`description`")
    private String description;

    @Column(name = "acc_account")
    private Integer accAccount;

    @Column(name = "cheque_date")
    private String chequeDate;

    @Column(name = "bank_reconciliation")
    private Boolean bankReconciliation;

    @Column(name = "reconcile_account")
    private Integer reconcileAccount;

    @Column(name = "reconcile_group")
    private Integer reconcileGroup;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "is_cheque", columnDefinition="tinyint(1) default false")
    private boolean isCheque=false;

    @Column(name = "financial_year")
    private Integer financialYear;

    @Column(name = "cost_department")
    private Integer costDepartment;

    @Column(name = "cost_center")
    private Integer costCenter;

    @Column(name = "`is_edit`")
    private Integer isEdit=0;

    public TAccLedger() {
    }

    public TAccLedger(Integer indexNo, String transactionDate, Integer number, String searchCode, String currentDate, String time, int branch, int currentBranch, int user, BigDecimal debit, BigDecimal credit, String formName, String refNumber, String type, Integer typeIndexNo, Integer deleteRefNo, String description, Integer accAccount, String chequeDate, Boolean bankReconciliation, Integer reconcileAccount, Integer reconcileGroup, boolean isMain, boolean isCheque, Integer financialYear, Integer costDepartment, Integer costCenter, Integer isEdit) {
        this.indexNo = indexNo;
        this.transactionDate = transactionDate;
        this.number = number;
        this.searchCode = searchCode;
        this.currentDate = currentDate;
        this.time = time;
        this.branch = branch;
        this.currentBranch = currentBranch;
        this.user = user;
        this.debit = debit;
        this.credit = credit;
        this.formName = formName;
        this.refNumber = refNumber;
        this.type = type;
        this.typeIndexNo = typeIndexNo;
        this.deleteRefNo = deleteRefNo;
        this.description = description;
        this.accAccount = accAccount;
        this.chequeDate = chequeDate;
        this.bankReconciliation = bankReconciliation;
        this.reconcileAccount = reconcileAccount;
        this.reconcileGroup = reconcileGroup;
        this.isMain = isMain;
        this.isCheque = isCheque;
        this.financialYear = financialYear;
        this.costDepartment = costDepartment;
        this.costCenter = costCenter;
        this.isEdit = isEdit;
    }

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public Integer getCostDepartment() {
        return costDepartment;
    }

    public void setCostDepartment(Integer costDepartment) {
        this.costDepartment = costDepartment;
    }

    public Integer getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(Integer costCenter) {
        this.costCenter = costCenter;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
    
    
    public boolean getIsCheque() {
        return isCheque;
    }

    public void setIsCheque(boolean isCheque) {
        this.isCheque = isCheque;
    }

    public boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public Integer getReconcileAccount() {
        return reconcileAccount;
    }

    public void setReconcileAccount(Integer reconcileAccount) {
        this.reconcileAccount = reconcileAccount;
    }

    public Integer getReconcileGroup() {
        return reconcileGroup;
    }

    public void setReconcileGroup(Integer reconcileGroup) {
        this.reconcileGroup = reconcileGroup;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(int currentBranch) {
        this.currentBranch = currentBranch;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeIndexNo() {
        return typeIndexNo;
    }

    public void setTypeIndexNo(Integer typeIndexNo) {
        this.typeIndexNo = typeIndexNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAccAccount() {
        return accAccount;
    }

    public void setAccAccount(Integer accAccount) {
        this.accAccount = accAccount;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Boolean getBankReconciliation() {
        return bankReconciliation;
    }

    public void setBankReconciliation(Boolean bankReconciliation) {
        this.bankReconciliation = bankReconciliation;
    }

    public Integer getDeleteRefNo() {
        return deleteRefNo;
    }

    public void setDeleteRefNo(Integer deleteRefNo) {
        this.deleteRefNo = deleteRefNo;
    }

}
