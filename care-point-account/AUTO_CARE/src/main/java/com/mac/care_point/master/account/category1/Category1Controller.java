/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.category1;

import com.mac.care_point.master.account.model.MAccCategory1;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 'Kasun Chamara'
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/account/master/category1")
public class Category1Controller {
    
     @Autowired
    private Category1Service category1Service;

    @RequestMapping(method = RequestMethod.GET)
    public List<MAccCategory1> findAll() {
        return category1Service.findAll();
    }
}
