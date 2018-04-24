/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.financial_year.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_financial_year")
public class MFinancialYear implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @Column(name = "start_date")
    private String startDate;

    @Basic(optional = false)
    @Column(name = "end_date")
    private String endDate;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "is_balance_sheet")
    private boolean isBalanceSheet;

    @Basic(optional = false)
    @Column(name = "is_pl")
    private boolean isPl;

    @Basic(optional = false)
    @Column(name = "profit")
    private BigDecimal profit;

    @Basic(optional = false)
    @Column(name = "is_current")
    private boolean isCurrent;

    public MFinancialYear() {
    }

    public MFinancialYear(Integer indexNo, String startDate, String endDate, String name, boolean isBalanceSheet, boolean isPl, BigDecimal profit, boolean isCurrent) {
        this.indexNo = indexNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.isBalanceSheet = isBalanceSheet;
        this.isPl = isPl;
        this.profit = profit;
        this.isCurrent = isCurrent;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsBalanceSheet() {
        return isBalanceSheet;
    }

    public void setIsBalanceSheet(boolean isBalanceSheet) {
        this.isBalanceSheet = isBalanceSheet;
    }

    public boolean isIsPl() {
        return isPl;
    }

    public void setIsPl(boolean isPl) {
        this.isPl = isPl;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

}
