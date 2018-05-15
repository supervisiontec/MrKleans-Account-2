/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.security;

import com.mac.care_point.security.model.MUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @RequestMapping(method = RequestMethod.GET)
    public List<MUser> findAll() {
        return mUserService.findAllUserNames();
    }
}
