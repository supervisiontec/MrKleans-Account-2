/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.trial_balance;

import com.mac.care_point.transaction.trial_balance.model.AccMainListModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TrialBalanceService {

    @Autowired
    private TrialBalanceRepository trialBalanceRepository;

    public List<AccMainListModel> loadMainAcc(String date) {
        ArrayList<AccMainListModel> list = new ArrayList<AccMainListModel>();
        List<Object[]> loadMainAcc = trialBalanceRepository.loadMainAcc(date);
        for (Object object[] : loadMainAcc) {
            AccMainListModel model = new AccMainListModel();
            model.setAccNo(Integer.parseInt(object[0].toString()));
            model.setAccCode(object[1].toString());
            model.setAccName(object[2].toString());
            model.setDebit(new BigDecimal(object[3].toString()));
            model.setCredit(new BigDecimal(object[4].toString()));
            model.setLevel(Integer.parseInt(object[5].toString()));
            model.setIsAccAccount(Integer.parseInt((object[6].toString())));
            model.setCount(Integer.parseInt((object[7].toString())));
            model.setSubAccountOf(Integer.parseInt((object[8].toString())));
            list.add(model);
        }
        return list;
    }

}
