/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.form_name;

import com.mac.care_point.master.form_name.model.MFormName;
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
@RestController
@CrossOrigin
@RequestMapping("/api/care-point/master/form-name")
public class FormNameController {
    @Autowired
    private FormNameService formNameService;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<MFormName> findAll() {
        return formNameService.findAll();
    }
}
