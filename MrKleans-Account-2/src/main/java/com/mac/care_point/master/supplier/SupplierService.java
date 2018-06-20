/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.supplier;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountService;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.supplier.model.MSupplier;
import com.mac.care_point.zutil.SecurityUtil;
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
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    List<MSupplier> findAll() {
        return supplierRepository.findAll();
    }
    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccAccountService accAccountService;

    MSupplier saveSupplier(MSupplier supplier) {
        MAccSetting supplierSubAccountOf=null;
        if ("NORMAL".equals(supplier.getType())) {
            supplierSubAccountOf = accountSettingRepository.findByName(Constant.SUPPLIER_SUB_ACCOUNT_OF);
        }
        else if ("ACCRUED".equals(supplier.getType())) {
            supplierSubAccountOf = accountSettingRepository.findByName(Constant.NON_CREDIT_SUPPLIER_ACCOUNT);
        }
        if (supplierSubAccountOf==null) {
            throw new RuntimeException(supplier.getType()+" Supplier Sub Account of Setting is Empty.. !");
        }
        if (supplier.getAccAccount() == null) {
            //create supplier
            MAccAccount account = new MAccAccount();
            account.setAccCode(null);
            account.setAccMain(null);
            account.setAccType("COMMON");
            account.setCop(false);
            account.setDescription("supplier account");
            account.setIsAccAccount(true);
            account.setLevel(null);
            account.setName(supplier.getName());
            account.setSubAccountCount(0);
            account.setSubAccountOf(supplierSubAccountOf.getAccAccount());
            account.setUser(SecurityUtil.getCurrentUser().getIndexNo());

            Integer supplierAccAccount = accAccountService.saveNewAccount(account).getIndexNo();
            supplier.setAccAccount(supplierAccAccount);
        } 
        return supplierRepository.save(supplier);
    }

    public List<MSupplier> getAccruedSuppliers() {
        return supplierRepository.findByType(Constant.ACCRUED);
    }

}
