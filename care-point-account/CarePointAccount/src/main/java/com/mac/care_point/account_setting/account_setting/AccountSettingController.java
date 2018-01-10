/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.account_setting;

import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/care-point/account/account-setting")
public class AccountSettingController {
    
    @Autowired
    private AccountSettingService accountSettingService;

    @RequestMapping( method = RequestMethod.GET)
    public List<MAccSetting> findAll() {
        return accountSettingService.findAll();
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer save(@RequestBody List<MAccSetting> settingList) {
        System.out.println(settingList.size());
        return accountSettingService.save(settingList);
    }

}
