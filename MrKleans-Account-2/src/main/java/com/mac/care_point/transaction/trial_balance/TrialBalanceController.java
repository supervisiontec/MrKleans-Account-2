/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.trial_balance;

import com.mac.care_point.transaction.trial_balance.model.AccMainListModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/view/trial-balance")
public class TrialBalanceController {
    @Autowired
    private TrialBalanceService trialBalanceService;
    
    @RequestMapping(value = "/get-main-acc-balance/{date}", method = RequestMethod.GET)
    public List<AccMainListModel> loadMainAcc(@PathVariable String date) {
        return trialBalanceService.loadMainAcc(date);
    }
    
}
