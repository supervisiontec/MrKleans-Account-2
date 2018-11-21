/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.profit_lost;

import com.mac.care_point.transaction.profit_lost.model.TProfitLostModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/profit-lost")
public class ProfitLostController {
    @Autowired 
    private ProfitLostService profitLostService;
    
    @RequestMapping(value = "/load-profit-lost-main/{financialYear}/{fromDate}/{toDate}", method = RequestMethod.GET)
    public List<TProfitLostModel> loadProfitLostMain(@PathVariable String financialYear,@PathVariable String fromDate ,@PathVariable String toDate ) {
        return profitLostService.loadProfitLostMain(financialYear,fromDate,toDate);
    }
    @RequestMapping(value = "/get-sub-list/{index}/{financialYear}/{fromDate}/{toDate}", method = RequestMethod.GET)
    public List<TProfitLostModel> getSubList(@PathVariable Integer index,@PathVariable String financialYear ,@PathVariable String fromDate ,@PathVariable String toDate) {
        return profitLostService.getSubList(index,financialYear,fromDate,toDate);
    }
}
