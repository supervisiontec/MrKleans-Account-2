/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.acc_code_setting.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_acc_code_setting")
public class AccCodeSetting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Column(name = "account")
    private Integer account;

    @Column(name = "max_no")
    private Integer maxNo;

    public AccCodeSetting() {
    }

    public AccCodeSetting(Integer indexNo, String code, Integer account, Integer maxNo) {
        this.indexNo = indexNo;
        this.code = code;
        this.account = account;
        this.maxNo = maxNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getMaxNo() {
        return maxNo;
    }

    public void setMaxNo(Integer maxNo) {
        this.maxNo = maxNo;
    }

}
