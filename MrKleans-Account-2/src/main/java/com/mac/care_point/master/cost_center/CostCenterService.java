/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.cost_center;

import com.mac.care_point.master.cost_center.model.MCostCenter;
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
public class CostCenterService {

    @Autowired
    private CostCenterRepository costCenterRepository;

    public List<MCostCenter> findAll() {
        return costCenterRepository.findAll();
    }

    public MCostCenter save(MCostCenter mCostCenter) {
        return costCenterRepository.save(mCostCenter);
    }

    public void delete(Integer indexNo) {
         try {
            costCenterRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete because there are details in other transaction");
        }
    }

    public List<MCostCenter> getActiveList() {
        return costCenterRepository.findByIsActive(true);
    }

}
