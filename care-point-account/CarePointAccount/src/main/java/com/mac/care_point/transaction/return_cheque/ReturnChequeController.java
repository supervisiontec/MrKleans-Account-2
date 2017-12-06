package com.mac.care_point.transaction.return_cheque;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.return_cheque.model.ReturnChequeMix;
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
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/return-cheque")
public class ReturnChequeController {

    @Autowired
    private ReturnChequeService returnChequeService;

    @RequestMapping(value = "/get-cheques/{type}", method = RequestMethod.GET)
    public List<TAccLedger> getPayableBills(@PathVariable String type) {
        return returnChequeService.getCheques(type);

    }

    @RequestMapping(value = "/get-selected-cheque-details/{delIndex}", method = RequestMethod.GET)
    public List<TAccLedger> getSelectedChequeDetails(@PathVariable Integer delIndex) {
        return returnChequeService.getSelectedChequeDetails(delIndex);

    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer getSelectedChequeDetails(@RequestBody ReturnChequeMix mix) {
        return returnChequeService.save(mix);

    }
}
