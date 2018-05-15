/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.security;

import com.mac.care_point.security.model.MUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kasun
 */
public interface MUserRepository extends JpaRepository<MUser, Integer> {

    public List<MUser> findByUsername(String userName);

    @Query(value = "select m_user.index_no,\n"
            + "m_user.name,\n"
            + "1 as branch,\n"
            + "2 as username,\n"
            + "3 as 'password'\n"
            + "from m_user", nativeQuery = true)
    public List<MUser> findAllUserNames();
}
