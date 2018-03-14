/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission;

import com.mac.care_point.account_setting.user_permission.model.TUserPermissionDetails;
import com.mac.care_point.account_setting.user_permission.model.UserPermissionMix;
import com.mac.care_point.master.form_name.FormNameRepository;
import com.mac.care_point.master.form_name.model.MFormName;
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
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository permissionRepository;

    @Autowired
    private FormNameRepository formNameRepository;

    public List<TUserPermissionDetails> findAll() {
        return permissionRepository.findAll();
    }

    public List<TUserPermissionDetails> findByUser(Integer user) {
        return permissionRepository.findByUser(user);
    }

    public Integer save(UserPermissionMix mix) {
        Integer count = 0;
        for (TUserPermissionDetails detail : mix.getUserPermissionList()) {
            permissionRepository.save(detail);
            count++;
        }
        if (count == mix.getUserPermissionList().size()) {
            return count;
        }
        return -1;
    }

    public TUserPermissionDetails findByUserAndFormName(Integer user, String form) {
        MFormName formName = formNameRepository.findByName(form);
        if (formName!=null) {
            List<TUserPermissionDetails> list = permissionRepository.findByUserAndFormName(user, formName.getIndexNo());
            if (list.size() != 0) {
                return list.get(0);
            }
        }
        return null;
    }

    public Object[] findViewTrue(Integer user) {
       return permissionRepository.findViewTrue(user);
    }
}
