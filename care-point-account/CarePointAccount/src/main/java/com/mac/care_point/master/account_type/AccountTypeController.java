/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account_type;

import com.mac.care_point.master.account_type.model.MAccAccountType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/account-type")
public class AccountTypeController {
    
    @Autowired
    private AccountTypeService accountTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MAccAccountType> findAllAccType() {
        return accountTypeService.findAllAccType();
    }
}
