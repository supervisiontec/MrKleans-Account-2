/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.acc_code_setting;

import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
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
@RequestMapping("/api/care-point/account/account-code-setting")
public class AccCodeSettingController {

    @Autowired
    private AccCodeSettingService accountCodeSettingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccCodeSetting> findAll() {
        return accountCodeSettingService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer save(@RequestBody AccCodeSetting codeSetting) {
        return accountCodeSettingService.save(codeSetting);
    }

    @RequestMapping(value = "/delete/{indexNo}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable Integer indexNo) {
        return accountCodeSettingService.delete(indexNo);
    }

}
