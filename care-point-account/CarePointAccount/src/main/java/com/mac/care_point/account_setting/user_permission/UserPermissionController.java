/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission;

import com.mac.care_point.account_setting.user_permission.model.TUserPermissionDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/account/account-setting/user-permission")
public class UserPermissionController {
 @Autowired
 private UserPermissionService permissionService;
    
    @RequestMapping( method = RequestMethod.GET)
    public List<TUserPermissionDetails> findAll() {
        return permissionService.findAll();
    }
}
