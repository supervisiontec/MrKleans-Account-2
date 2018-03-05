/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.account_setting.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author kasun
 */
public class SettingMix implements Serializable {

    private List<MAccSetting> accSettingList;

    public SettingMix() {
    }

    public SettingMix(List<MAccSetting> accSettingList) {
        this.accSettingList = accSettingList;
    }

    public List<MAccSetting> getAccSettingList() {
        return accSettingList;
    }

    public void setAccSettingList(List<MAccSetting> accSettingList) {
        this.accSettingList = accSettingList;
    }
    

    

}
