/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.card_reader;

import com.mac.care_point.master.card_reader.model.MCardReader;
import com.mac.care_point.zutil.SecurityUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/card-reader")
public class CardReaderController {
    
@Autowired
    private CardReaderService cardReaderService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MCardReader> findAll() {
        return cardReaderService.findAll();
    }
    @RequestMapping(value = "/by-branch",method = RequestMethod.GET)
    public List<MCardReader> findByBranch() {
        return cardReaderService.findByBranch(SecurityUtil.getCurrentUser().getBranch());
    }


}
