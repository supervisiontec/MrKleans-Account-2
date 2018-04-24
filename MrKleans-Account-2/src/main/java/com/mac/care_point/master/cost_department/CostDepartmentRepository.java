/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.cost_department;

import com.mac.care_point.master.cost_department.model.MCostDepartment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface CostDepartmentRepository extends JpaRepository<MCostDepartment, Integer> {

    public List<MCostDepartment> findByIsActive(boolean b);
    
}
