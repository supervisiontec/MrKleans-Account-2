/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.type_index_detail;

import com.mac.care_point.service.type_index_detail.model.TTypeIndexDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author chama
 */
public interface TypeIndexDetailRepository extends JpaRepository<TTypeIndexDetail, Integer>{

    public int findByTypeAndAccountRefId(String INVOICE, Integer invoice);
    
}
