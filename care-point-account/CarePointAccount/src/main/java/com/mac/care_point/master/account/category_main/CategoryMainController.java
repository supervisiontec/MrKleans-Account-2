/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.category_main;

import com.mac.care_point.master.account.category3.*;
import com.mac.care_point.master.account.model.MAccCategory1;
import com.mac.care_point.master.account.model.MAccMain;
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
@RequestMapping("/api/care-point/account/master/categoryMain")
public class CategoryMainController {
    
     @Autowired
    private CategoryMainService categoryMainService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MAccMain> findAll() {
        return categoryMainService.findAll();
    }
}
