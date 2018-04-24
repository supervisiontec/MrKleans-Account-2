/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.acc_code_setting;

import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface AccCodeSettingRepository extends JpaRepository<AccCodeSetting, Integer> {

    public List<AccCodeSetting> findAll();

    public AccCodeSetting findByAccount(Integer account);

    public List<AccCodeSetting> findByCode(String code);

}
