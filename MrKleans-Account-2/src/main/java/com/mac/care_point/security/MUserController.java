/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.security;

import com.mac.care_point.security.model.MUser;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/user")
public class MUserController {

    @Autowired
    private MUserService mUserService;

    @Value("${cost-department-required}")
    private Boolean costDepartmentRequired;
    
    @Value("${cost-center-required}")
    private Boolean costCenterRequired;

    @RequestMapping(method = RequestMethod.GET)
    public List<MUser> findAll() {
        return mUserService.findAllUserNames();
    }
    
    @RequestMapping(value = "/get-required-field", method = RequestMethod.GET)
    public HashMap getRequiredField() {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("cost_center", costCenterRequired);
        map.put("cost_department", costDepartmentRequired);
        
        return map;
    }
    
    
}
