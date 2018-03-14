/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.model;

import java.io.Serializable;

/**
 *
 * @author chama
 */
public class MAccSettingMix implements Serializable{
    private Integer indexNo;
    private String accCode;
    private String name;
    private Integer accMain;

    public MAccSettingMix() {
    }

    public MAccSettingMix(Integer indexNo, String accCode, String name, Integer accMain) {
        this.indexNo = indexNo;
        this.accCode = accCode;
        this.name = name;
        this.accMain = accMain;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccMain() {
        return accMain;
    }

    public void setAccMain(Integer accMain) {
        this.accMain = accMain;
    }
    
}
