/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.cost_department;

import com.mac.care_point.master.cost_center.CostCenterRepository;
import com.mac.care_point.master.cost_center.model.MCostCenter;
import com.mac.care_point.master.cost_department.model.MCostDepartment;
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
public class CostDepartmentService {
      @Autowired
    private CostDepartmentRepository costDepartmentRepository;

    public List<MCostDepartment> findAll() {
        return costDepartmentRepository.findAll();
    }

    public MCostDepartment save(MCostDepartment MCostDepartment) {
        return costDepartmentRepository.save(MCostDepartment);
    }

    public void delete(Integer indexNo) {
         try {
            costDepartmentRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete because there are details in other transaction");
        }
    }

    public List<MCostDepartment> getActiveList() {
       return costDepartmentRepository.findByIsActive(true); 
    }
}
