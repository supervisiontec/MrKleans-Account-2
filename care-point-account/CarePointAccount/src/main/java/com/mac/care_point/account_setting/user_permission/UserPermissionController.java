/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission;

import com.mac.care_point.account_setting.user_permission.model.TUserPermissionDetails;
import com.mac.care_point.account_setting.user_permission.model.UserPermissionMix;
import com.mac.care_point.zutil.SecurityUtil;
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
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/account/account-setting/user-permission")
public class UserPermissionController {

    @Autowired
    private UserPermissionService permissionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TUserPermissionDetails> findAll() {
        return permissionService.findAll();
    }

    @RequestMapping(value = "/by-user/{user}", method = RequestMethod.GET)
    public List<TUserPermissionDetails> findByUser(@PathVariable Integer user) {
        return permissionService.findByUser(user);
    }

    @RequestMapping(value = "/by-form/{form}", method = RequestMethod.GET)
    public TUserPermissionDetails findByUser(@PathVariable String form) {
        System.out.println(SecurityUtil.getCurrentUser().getIndexNo());
        System.out.println(form);
        return permissionService.findByUserAndFormName(SecurityUtil.getCurrentUser().getIndexNo(), form);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer save(@RequestBody UserPermissionMix mix) {
        return permissionService.save(mix);
    }
//
}
