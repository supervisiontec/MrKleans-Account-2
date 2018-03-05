/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.item_department;

import com.mac.care_point.master.item_department.model.MItemDepartmentMain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface ItemDepartmentRepository extends JpaRepository<MItemDepartmentMain, Integer> {

    public List<MItemDepartmentMain> findByName(String name);

}
