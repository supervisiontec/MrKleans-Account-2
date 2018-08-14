/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class ParamModel implements Serializable {

    private String name;
    private String fromDate;
    private String toDate;
    private String branch;
    private String financialYear;
    private Integer account;
    private String invDate;
    private String refNo;

    public ParamModel() {
    }

    public ParamModel(String name, String fromDate, String toDate, String branch, String financialYear, Integer account, String invDate, String refNo) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.branch = branch;
        this.financialYear = financialYear;
        this.account = account;
        this.invDate = invDate;
        this.refNo = refNo;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

}
