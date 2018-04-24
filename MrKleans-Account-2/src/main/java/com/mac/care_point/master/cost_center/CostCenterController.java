/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.cost_center;

import com.mac.care_point.master.cost_center.model.MCostCenter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping("/api/care-point/master/cost-center")
public class CostCenterController {

    @Autowired
    private CostCenterService costCenterService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MCostCenter> findAll() {
        return costCenterService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MCostCenter insertDetail(@RequestBody MCostCenter mCostCenter) {
        return costCenterService.save(mCostCenter);
    }
    @RequestMapping(value = "/get-active-list", method = RequestMethod.GET)
    public List<MCostCenter> getActiveList() {
        return costCenterService.getActiveList();
    }

    @RequestMapping(value = "/delete/{indexNo}", method = RequestMethod.DELETE)
    public Integer deleteDetail(@PathVariable Integer indexNo) {
        costCenterService.delete(indexNo);
        return indexNo;
    }
}
