/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.dash_board;

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
@RequestMapping("/api/care-point/transaction/dash-board")
public class DashBoardController {
    @Autowired
    private DashBoardService dashBoardService;

    @RequestMapping(value = "/dash-board-main", method = RequestMethod.GET)
    public List<Object[]> getDashBoardMain() {
        return dashBoardService.getDashBoardMain();
    }
}
