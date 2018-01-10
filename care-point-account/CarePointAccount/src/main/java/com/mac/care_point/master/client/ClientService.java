/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.client;

import com.mac.care_point.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountService;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.client.model.Client;
import com.mac.care_point.zutil.SecurityUtil;
import java.util.Date;
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
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;
    
     @Autowired
    private AccAccountService accAccountService;


    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client saveDetail(Client client) {
        MAccSetting customerSubAccountOf = accountSettingRepository.findByName(Constant.CUSTOMER_SUB_ACCOUNT_OF);
        if (client.getAccAccount() == null) {
            //create supplier
            MAccAccount account = new MAccAccount();
            account.setAccCode(null);
            account.setAccMain(null);
            account.setAccType("COMMON");
            account.setCop(false);
            account.setDescription("client account");
            account.setIsAccAccount(true);
            account.setLevel(null);
            account.setName(client.getResident()+" - "+client.getName()+" - ("+client.getMobile()+")");
            account.setSubAccountCount(0);
            account.setSubAccountOf(customerSubAccountOf.getAccAccount());
            account.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            Integer clientAccAccount = accAccountService.saveNewAccount(account).getIndexNo();
            client.setAccAccount(clientAccAccount);
            client.setDate(client.getDate()==null?new Date():client.getDate());
            client.setIsNew(client.isIsNew()?true:false);
           return clientRepository.save(client);
        }else{
            return clientRepository.save(client);
        }
    }

    public void deleteDetail(Integer indexNo) {
        try {
            clientRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete this client because there are details in other transaction");
        }
    }

    public List<Client> getNewClientList() {
        return clientRepository.findByIsNewOrderByName(true);
    }

}
