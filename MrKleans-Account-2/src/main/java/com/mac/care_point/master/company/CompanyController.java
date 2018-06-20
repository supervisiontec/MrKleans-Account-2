/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.company;

import com.mac.care_point.master.company.model.MCompany;
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
@RequestMapping("/api/care-point/master/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    
   @RequestMapping(value = "/find-company", method = RequestMethod.GET)
    public MCompany findCompany(){
       return  companyService.findOne();
    }
    
}
