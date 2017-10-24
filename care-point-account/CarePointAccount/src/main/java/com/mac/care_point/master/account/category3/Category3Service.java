/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.category3;

import com.mac.care_point.master.account.model.MAccCategory3;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class Category3Service {

    @Autowired
    private Category3Repository category1Repository;
    
   public List<MAccCategory3> findAll() {
        return category1Repository.findAll();
    }
    
}
