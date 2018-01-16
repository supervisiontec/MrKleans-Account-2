/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission.model;

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
@Table(name = "t_user_permission_details")
public class TUserPermissionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "`user`")
    private Integer user;
    
    @Column(name = "form_name")
    private Integer formName;
    
    @Column(name = "`view`")
    private boolean view;
    
    @Column(name = "`add`")
    private boolean add;
    
    @Column(name = "`update`")
    private boolean update;
    
    @Column(name = "`delete`")
    private boolean delete;

    public TUserPermissionDetails() {
    }

    public TUserPermissionDetails(Integer indexNo, Integer user, Integer formName, boolean view, boolean add, boolean update, boolean delete) {
        this.indexNo = indexNo;
        this.user = user;
        this.formName = formName;
        this.view = view;
        this.add = add;
        this.update = update;
        this.delete = delete;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getFormName() {
        return formName;
    }

    public void setFormName(Integer formName) {
        this.formName = formName;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    
}
