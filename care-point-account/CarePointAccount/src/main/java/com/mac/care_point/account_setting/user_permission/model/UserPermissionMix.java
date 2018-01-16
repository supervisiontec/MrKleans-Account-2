/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author kasun
 */
public class UserPermissionMix implements Serializable {

    private List<TUserPermissionDetails> userPermissionList;

    public UserPermissionMix() {
    }

    public UserPermissionMix(List<TUserPermissionDetails> userPermissionList) {
        this.userPermissionList = userPermissionList;
    }

    public List<TUserPermissionDetails> getUserPermissionList() {
        return userPermissionList;
    }

    public void setUserPermissionList(List<TUserPermissionDetails> userPermissionList) {
        this.userPermissionList = userPermissionList;
    }
    
}
