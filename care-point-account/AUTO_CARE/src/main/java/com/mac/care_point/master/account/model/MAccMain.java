/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author 'Kasun Chamara'
 */
@Entity
@Table(name = "m_acc_main")
public class MAccMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "increment")
    private String increment;
    
    @Basic(optional = false)
    @Column(name = "is_expence")
    private boolean isExpence;
    
    @Basic(optional = false)
    @Column(name = "is_income")
    private boolean isIncome;
    
    @Basic(optional = false)
    @Column(name = "is_balance_sheet")
    private boolean isBalanceSheet;

    public MAccMain() {
    }

    public MAccMain(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public MAccMain(Integer indexNo, String name, String increment, boolean isExpence, boolean isIncome, boolean isBalanceSheet) {
        this.indexNo = indexNo;
        this.name = name;
        this.increment = increment;
        this.isExpence = isExpence;
        this.isIncome = isIncome;
        this.isBalanceSheet = isBalanceSheet;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public boolean getIsExpence() {
        return isExpence;
    }

    public void setIsExpence(boolean isExpence) {
        this.isExpence = isExpence;
    }

    public boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public boolean getIsBalanceSheet() {
        return isBalanceSheet;
    }

    public void setIsBalanceSheet(boolean isBalanceSheet) {
        this.isBalanceSheet = isBalanceSheet;
    }

}
