/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting;

import com.mac.care_point.account_setting.model.MAccSetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface AccountSettingRepository extends JpaRepository<MAccSetting, Integer> {

    public MAccSetting findByName(String name);
}
