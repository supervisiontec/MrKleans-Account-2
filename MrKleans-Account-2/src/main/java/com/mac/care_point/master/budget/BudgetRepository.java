/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.budget;

import com.mac.care_point.master.budget.model.MBudget;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface BudgetRepository extends JpaRepository<MBudget, Integer> {

    public List<MBudget> findByFinancialYearAndBudgetMonthAndCostDepartment(Integer year, Integer month, Integer department);

    public MBudget findByFinancialYearAndBudgetMonthAndCostDepartmentAndCostCenterAndBranch(Integer financialYear, Integer budgetMonth, Integer costDepartment, Integer costCenter, Integer branch);

}
