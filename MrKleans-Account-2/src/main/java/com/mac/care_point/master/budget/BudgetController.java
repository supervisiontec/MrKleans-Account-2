/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.budget;

import com.mac.care_point.master.budget.model.MBudget;
import com.mac.care_point.master.client.model.Client;
import com.mac.care_point.zutil.SecurityUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @RequestMapping(value = "/get-filtered-data-list/{year}/{budgetMonth}/{department}", method = RequestMethod.GET)
    public List<MBudget> getFilteredData(@PathVariable Integer year, @PathVariable Integer budgetMonth, @PathVariable Integer department) {
        return budgetService.getFilteredData(year, budgetMonth, department);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer insertDetail(@RequestBody List<MBudget> budgetList) {
        Integer branch = SecurityUtil.getCurrentUser().getBranch();
        return budgetService.save(budgetList, branch);
    }
}
