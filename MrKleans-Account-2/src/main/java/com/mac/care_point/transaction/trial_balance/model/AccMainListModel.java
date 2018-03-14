package com.mac.care_point.transaction.trial_balance.model;

import java.math.BigDecimal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 'Kasun Chamara'
 */
public class AccMainListModel {

    private Integer accNo;
    private String accCode;
    private String accName;
    private BigDecimal debit;
    private BigDecimal credit;
    private Integer level;
    private Integer isAccAccount;
    private Integer count;
    private Integer subAccountOf;

    public AccMainListModel() {
    }

    public AccMainListModel(Integer accNo, String accCode, String accName, BigDecimal debit, BigDecimal credit, Integer level, Integer isAccAccount, Integer count, Integer subAccountOf) {
        this.accNo = accNo;
        this.accCode = accCode;
        this.accName = accName;
        this.debit = debit;
        this.credit = credit;
        this.level = level;
        this.isAccAccount = isAccAccount;
        this.count = count;
        this.subAccountOf = subAccountOf;
    }

    public Integer getSubAccountOf() {
        return subAccountOf;
    }

    public void setSubAccountOf(Integer subAccountOf) {
        this.subAccountOf = subAccountOf;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIsAccAccount() {
        return isAccAccount;
    }

    public void setIsAccAccount(Integer isAccAccount) {
        this.isAccAccount = isAccAccount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAccNo() {
        return accNo;
    }

    public void setAccNo(Integer accNo) {
        this.accNo = accNo;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
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

}
