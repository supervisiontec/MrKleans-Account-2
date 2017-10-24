/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.setting.model;

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
 * @author 'Kasun Chamara'
 */
@Entity
@Table(name = "m_acc_setting")
public class MAccSetting implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;
   
    @Column(name = "name")
    private String name;
    
    @Column(name = "acc_account")
    private int accAccount;

    public MAccSetting() {
    }

    public MAccSetting(Integer indexNo, String name, int accAccount) {
        this.indexNo = indexNo;
        this.name = name;
        this.accAccount = accAccount;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccAccount() {
        return accAccount;
    }

    public void setAccAccount(int accAccount) {
        this.accAccount = accAccount;
    }
    
    
}
