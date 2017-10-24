/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.setting;

import com.mac.care_point.transaction.setting.model.MAccSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface SettingRepository extends JpaRepository<MAccSetting, Integer> {

    public List<MAccSetting> findByName(String petty_cash);

}
