/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.budget;

import com.mac.care_point.master.budget.model.MBudget;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<MBudget> getFilteredData(Integer year, Integer month, Integer department) {
        return budgetRepository.findByFinancialYearAndBudgetMonthAndCostDepartment(year,month,department);
    }

    public Integer save(List<MBudget> budgetList, Integer branch) {
        int count=0;
        for (MBudget mBudget : budgetList) {
            mBudget.setBranch(branch);
            MBudget budget=budgetRepository.findByFinancialYearAndBudgetMonthAndCostDepartmentAndCostCenterAndBranch(
                    mBudget.getFinancialYear(),
                    mBudget.getBudgetMonth(),
                    mBudget.getCostDepartment(),
                    mBudget.getCostCenter(),
                    branch);
            if (budget!=null) {
                mBudget.setIndexNo(budget.getIndexNo());
            }
            
            budgetRepository.save(mBudget);
            count++;
        }
        if (count==budgetList.size()) {
            return count;
        }
        return -1;
    }

}
