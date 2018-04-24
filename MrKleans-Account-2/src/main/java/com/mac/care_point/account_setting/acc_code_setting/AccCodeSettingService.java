/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.acc_code_setting;

import com.mac.care_point.account_setting.acc_code_setting.model.AccCodeSetting;
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
public class AccCodeSettingService {

    @Autowired
    private AccCodeSettingRepository accCodeSettingRepository;

    public List<AccCodeSetting> findAll() {
        return accCodeSettingRepository.findAll();
    }

    public Integer save(AccCodeSetting codeSetting) {
        try {
            if (codeSetting.getIndexNo() == null || codeSetting.getIndexNo()==0) {
                Integer no = chechCodeISExists(codeSetting.getCode());
                if (no == 0) {
                    AccCodeSetting setting = accCodeSettingRepository.findByAccount(codeSetting.getAccount());
                    if (setting != null) {
                        setting.setCode(codeSetting.getCode());
                        setting.setMaxNo(codeSetting.getMaxNo());
                        return accCodeSettingRepository.save(setting).getIndexNo();
                    }
                    return accCodeSettingRepository.save(codeSetting).getIndexNo();
                }
            } else {
                return accCodeSettingRepository.save(codeSetting).getIndexNo();
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    private Integer chechCodeISExists(String code) {
        return accCodeSettingRepository.findByCode(code).size();
    }

    public Integer delete(Integer indexNo) {
        accCodeSettingRepository.delete(indexNo);
        return indexNo;
    }

}
