/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account;

import com.mac.care_point.master.account.model.MAccAccount;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
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
    public List<MAccAccount> findAll() {
        return accAccountRepository.findAll();
    }

    @Transactional
    public MAccAccount saveNewAccount(MAccAccount accAccount) {
        if (accAccount.getIndexNo() != null) {
            return accAccountRepository.save(accAccount);
        } else {
            MAccAccount subAccountOf = accAccountRepository.findOne(accAccount.getSubAccountOf());
            accAccount.setLevel(Integer.parseInt(subAccountOf.getLevel()) + 1 + "");
            accAccount.setAccMain(subAccountOf.getAccMain());
            accAccount.setSubAccountCount(0);
            accAccount.setAccCode(subAccountOf.getSubAccountCount() == 0 ? subAccountOf.getAccCode()
                    + ".01" : subAccountOf.getAccCode() + (subAccountOf.getSubAccountCount() <= 9 ? (".0"
                    + (subAccountOf.getSubAccountCount() + 1)) : "." + (subAccountOf.getSubAccountCount() + 1)));

            subAccountOf.setIsAccAccount(false);
            subAccountOf.setSubAccountCount(subAccountOf.getSubAccountCount() + 1);
            return accAccountRepository.save(accAccount);
        }
    }
//

    @Transactional
    public Integer deleteAccount(Integer index) {
        try {
            MAccAccount account = accAccountRepository.findOne(index);
            if (account.getSubAccountOf() != null) {

                MAccAccount accountUpdate = accAccountRepository.findOne(account.getSubAccountOf());
                accountUpdate.setSubAccountCount(accountUpdate.getSubAccountCount() == 1 ? 0 : accountUpdate.getSubAccountCount() - 1);
                accountUpdate.setIsAccAccount(accountUpdate.getSubAccountCount().equals(0) ? true : false);
                if (!accountUpdate.getSubAccountCount().equals(0)) {
//
                    String[] splitMain = account.getAccCode().split(Pattern.quote("."));
                    Integer mainLast = Integer.parseInt(splitMain[splitMain.length - 1]);
                    List<MAccAccount> accUpdateList = accAccountRepository.findBySubAccountOf(account.getSubAccountOf());

                    for (MAccAccount mAccAccount : accUpdateList) {
                        String[] split = mAccAccount.getAccCode().split(Pattern.quote("."));
                        Integer last = Integer.parseInt(split[split.length - 1]);
                        if (last > mainLast) {
                            System.out.println("calculate code");
                            mAccAccount.setAccCode(accountUpdate.getAccCode() + ".0" + (last - 1));
                            accAccountRepository.save(mAccAccount);
                        }

                    }
                }
                accAccountRepository.save(accountUpdate);
            }
            accAccountRepository.delete(index);

            return index;
        } catch (RuntimeException re) {
            throw new RuntimeException("Can't delete because there are another transaction with relation  !");
        }
    }
//

    public BigDecimal findAccountValue(Integer branch, Integer accAccount) {
        String fDate = "2017-01-01";
        String tDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return accAccountRepository.findAccountValue(branch, accAccount, fDate, tDate);

    }
//

    public List<MAccAccount> findByIsAccAccount(boolean b) {
        return accAccountRepository.findByIsAccAccount(b);
    }

    public List<MAccAccount> getAccFlow(Integer acc) {
        ArrayList<MAccAccount> flowList = new ArrayList<MAccAccount>();
        Integer accNo = acc;
        for (int i = 0; i < 10; i++) {
            MAccAccount findOne = accAccountRepository.findOne(accNo);
            flowList.add(findOne);
            if (findOne.getSubAccountOf() == null) {
                break;
            }
            accNo = findOne.getSubAccountOf();

        }
        return flowList;
    }

    public BigDecimal findAccountValue(Integer index) {
        String fDate = "2017-01-01";
        String tDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return accAccountRepository.findAccountValue(index, fDate, tDate);
    }

    public List<MAccAccount> findByIsAccAccountAndAccTypeOrAccType(boolean b, String cash, String bank) {
        ArrayList<MAccAccount> returnList = new ArrayList<MAccAccount>();
        List<MAccAccount> list = accAccountRepository.findByIsAccAccountAndAccTypeOrAccType(b, cash, bank);
        for (MAccAccount mAccAccount : list) {
            if (mAccAccount.getIsAccAccount()) {
                returnList.add(mAccAccount);
            }
        }
        return returnList;
    }

    public List<MAccAccount> findTypeAccount(String expense) {
        return accAccountRepository.findTypeAccount(expense);
    }

    public MAccAccount getOverPaymentIssueAccount(String overPaymentIssue) {
        return accAccountRepository.getOverPaymentIssueAccount(overPaymentIssue);
    }
}
