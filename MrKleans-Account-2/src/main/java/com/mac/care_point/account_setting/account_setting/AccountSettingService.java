/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.account_setting;

import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
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
public class AccountSettingService {

    @Autowired
    private AccountSettingRepository accountSettingRepository;
    
    public Integer save(List<MAccSetting> settingList) {
        return accountSettingRepository.save(settingList).size();
    }

    public List<MAccSetting> findByView(boolean view) {
        return accountSettingRepository.findByView(view);
    }
    
}
