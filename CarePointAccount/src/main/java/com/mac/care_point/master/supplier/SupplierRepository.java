/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.supplier;

import com.mac.care_point.master.supplier.model.MSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface SupplierRepository extends JpaRepository<MSupplier, Integer>{
    
}
