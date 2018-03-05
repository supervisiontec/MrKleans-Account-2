/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicle;

import com.mac.care_point.master.vehicle.model.MVehicle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chama
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/vehicle")
public class VehicleController {
     
    @Autowired
    private VehicleService VehicleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MVehicle> findAll() {
        return VehicleService.findAll();
    }
    
}
