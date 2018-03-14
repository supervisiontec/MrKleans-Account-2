/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.form_name;

import com.mac.care_point.master.form_name.model.MFormName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface FormNameRepository extends JpaRepository<MFormName, Integer>{

    public MFormName findByName(String form);
    
}
