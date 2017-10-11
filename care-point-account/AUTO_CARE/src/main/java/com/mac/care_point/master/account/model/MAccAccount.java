/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author 'Kasun Chamara'
 */
@Entity
@Table(name = "m_acc_account")
public class MAccAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "cop")
    private boolean cop;

    @Basic(optional = false)
    @Column(name = "user")
    private int user;

    @JoinColumn(name = "acc_category1", referencedColumnName = "index_no")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MAccCategory1 accCategory1;

    @JoinColumn(name = "acc_category2", referencedColumnName = "index_no")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MAccCategory2 accCategory2;

    @JoinColumn(name = "acc_category3", referencedColumnName = "index_no")
    @ManyToOne(fetch = FetchType.EAGER)
    private MAccCategory3 accCategory3;

    @JoinColumn(name = "acc_main", referencedColumnName = "index_no")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MAccMain accMain;

    public MAccAccount() {
    }

    public MAccAccount(Integer indexNo, String name, boolean cop, int user, MAccCategory1 accCategory1, MAccCategory2 accCategory2, MAccCategory3 accCategory3, MAccMain accMain) {
        this.indexNo = indexNo;
        this.name = name;
        this.cop = cop;
        this.user = user;
        this.accCategory1 = accCategory1;
        this.accCategory2 = accCategory2;
        this.accCategory3 = accCategory3;
        this.accMain = accMain;
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

    public boolean isCop() {
        return cop;
    }

    public void setCop(boolean cop) {
        this.cop = cop;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public MAccCategory1 getAccCategory1() {
        return accCategory1;
    }

    public void setAccCategory1(MAccCategory1 accCategory1) {
        this.accCategory1 = accCategory1;
    }

    public MAccCategory2 getAccCategory2() {
        return accCategory2;
    }

    public void setAccCategory2(MAccCategory2 accCategory2) {
        this.accCategory2 = accCategory2;
    }

    public MAccCategory3 getAccCategory3() {
        return accCategory3;
    }

    public void setAccCategory3(MAccCategory3 accCategory3) {
        this.accCategory3 = accCategory3;
    }

    public MAccMain getAccMain() {
        return accMain;
    }

    public void setAccMain(MAccMain accMain) {
        this.accMain = accMain;
    }

    
}
