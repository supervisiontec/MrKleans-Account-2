/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.budget.model;

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
@Table(name = "m_budget")
public class MBudget implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "financial_year")
    private Integer financialYear;

    @Column(name = "budget_month")
    private Integer budgetMonth;

    @Column(name = "cost_department")
    private Integer costDepartment;

    @Column(name = "cost_center")
    private Integer costCenter;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "budget")
    private BigDecimal budget;

    public MBudget() {
    }

    public MBudget(Integer indexNo, Integer financialYear, Integer budgetMonth, Integer costDepartment, Integer costCenter, Integer branch, BigDecimal budget) {
        this.indexNo = indexNo;
        this.financialYear = financialYear;
        this.budgetMonth = budgetMonth;
        this.costDepartment = costDepartment;
        this.costCenter = costCenter;
        this.branch = branch;
        this.budget = budget;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public Integer getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(Integer budgetMonth) {
        this.budgetMonth = budgetMonth;
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

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

}
