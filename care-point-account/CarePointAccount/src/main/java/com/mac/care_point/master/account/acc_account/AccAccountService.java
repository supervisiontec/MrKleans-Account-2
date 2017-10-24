/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.acc_account;

import com.mac.care_point.master.account.category1.Category1Repository;
import com.mac.care_point.master.account.category2.Category2Repository;
import com.mac.care_point.master.account.category3.Category3Repository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.account.model.MAccCategory1;
import com.mac.care_point.master.account.model.MAccCategory2;
import com.mac.care_point.master.account.model.MAccCategory3;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AccAccountService {

    @Autowired
    private AccAccountRepository accAccountRepository;

    @Autowired
    private Category1Repository category1Repository;

    @Autowired
    private Category2Repository category2Repository;

    @Autowired
    private Category3Repository category3Repository;

    public List<MAccAccount> findAll() {
        return accAccountRepository.findAll();
    }

    public MAccAccount saveNewAccount(MAccAccount accAccount) {
        MAccCategory1 category1 = accAccount.getAccCategory1();
        MAccCategory2 category2 = accAccount.getAccCategory2();
        MAccCategory3 category3 = accAccount.getAccCategory3();

        category1 = category1Repository.save(category1);
        category2 = category2Repository.save(category2);
        if (category3.getName() != null) {
            category3.setName(category2.getName());
        }
        category3 = category3Repository.save(category3);

        accAccount.setAccCategory1(category1);
        accAccount.setAccCategory2(category2);
        accAccount.setAccCategory3(category3);
        return accAccountRepository.save(accAccount);
    }

    public Integer deleteAccount(Integer index) {
        try {
            accAccountRepository.delete(index);
            return index;
        } catch (RuntimeException re) {
            throw new RuntimeException("Can't delete because there are another transaction with relation  !");
        }
    }

    public BigDecimal findAccountValue(Integer branch, Integer accAccount) {
        System.out.println(branch);
        System.out.println(accAccount);
        String fDate="2017-01-01";
        String tDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(fDate);
        System.out.println(tDate);
        
        return accAccountRepository.findAccountValue(branch, accAccount,fDate , tDate);

    }

}
