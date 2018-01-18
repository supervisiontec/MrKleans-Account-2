/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.account_setting.user_permission;

import com.mac.care_point.account_setting.user_permission.model.TUserPermissionDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface UserPermissionRepository extends JpaRepository<TUserPermissionDetails, Integer> {

    public List<TUserPermissionDetails> findByUser(Integer user);

    public List<TUserPermissionDetails> findByUserAndFormName(Integer user, Integer form);

    @Query(value = "select \n"
            + "	m_form_name.name as form_name\n"
            + "from m_form_name\n"
            + "LEFT JOIN t_user_permission_details on t_user_permission_details.form_name=m_form_name.index_no\n"
            + "where t_user_permission_details.`view`=true\n"
            + "and t_user_permission_details.user=:user", nativeQuery = true)
    public Object[] findViewTrue(@Param("user") Integer user);

}
