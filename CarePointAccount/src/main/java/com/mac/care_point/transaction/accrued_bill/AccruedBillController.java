/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.accrued_bill;

import com.mac.care_point.transaction.accrued_bill.model.AccruedBillMix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/accred-bill")
public class AccruedBillController {

    @Autowired
    private AccruedBillService accruedBillService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer saveAccruedBill(@RequestBody AccruedBillMix accruedBillMix) {
        return accruedBillService.saveAccruedBill(accruedBillMix);

    }

}
