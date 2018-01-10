/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.card_reader.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_card_reader")
public class MCardReader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "branch")
    private Integer branch;

    @Basic(optional = false)
    @Column(name = "number")
    private Integer number;

    @Basic(optional = false)
    @Column(name = "acc_account")
    private Integer accAccount;

    public MCardReader() {
    }

    public MCardReader(Integer indexNo, String name, Integer branch, Integer number, Integer accAccount) {
        this.indexNo = indexNo;
        this.name = name;
        this.branch = branch;
        this.number = number;
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

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAccAccount() {
        return accAccount;
    }

    public void setAccAccount(Integer accAccount) {
        this.accAccount = accAccount;
    }

}
