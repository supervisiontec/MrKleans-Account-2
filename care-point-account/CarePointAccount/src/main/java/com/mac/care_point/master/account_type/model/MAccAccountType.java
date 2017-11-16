/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account_type.model;

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
 * @author 'Kasun Chamara'
 */
@Entity
@Table(name = "m_acc_account_type")
public class MAccAccountType implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;
     
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    
    @Size(max = 50)
    @Column(name = "value")
    private String value;
    
    @Size(max = 50)
    @Column(name = "view_ref_no")
    private boolean viewRefNo;
   
    @Size(max = 50)
    @Column(name = "view_cheque_no")
    private boolean viewChequeNo;
   
    @Size(max = 50)
    @Column(name = "view_cheque_date")
    private boolean viewChequeDate;

    public MAccAccountType() {
    }

    public MAccAccountType(Integer indexNo, String name, String value, boolean viewRefNo, boolean viewChequeNo, boolean viewChequeDate) {
        this.indexNo = indexNo;
        this.name = name;
        this.value = value;
        this.viewRefNo = viewRefNo;
        this.viewChequeNo = viewChequeNo;
        this.viewChequeDate = viewChequeDate;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isViewRefNo() {
        return viewRefNo;
    }

    public void setViewRefNo(boolean viewRefNo) {
        this.viewRefNo = viewRefNo;
    }

    public boolean isViewChequeNo() {
        return viewChequeNo;
    }

    public void setViewChequeNo(boolean viewChequeNo) {
        this.viewChequeNo = viewChequeNo;
    }

    public boolean isViewChequeDate() {
        return viewChequeDate;
    }

    public void setViewChequeDate(boolean viewChequeDate) {
        this.viewChequeDate = viewChequeDate;
    }
    
    

}
