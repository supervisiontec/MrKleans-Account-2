/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.dash_board;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/dash-board-main/{toDate}", method = RequestMethod.GET)
    public List<Object[]> getDashBoardMain(@PathVariable String toDate) {
        return dashBoardService.getDashBoardMain(toDate);
    }
    @RequestMapping(value = "/dash-board-2/{fDate}/{tDate}", method = RequestMethod.GET)
    public List<Object> getDashBoard2(@PathVariable String fDate,@PathVariable String tDate ) {
        return dashBoardService.getDashBoard2(fDate,tDate);
    }
    @RequestMapping(value = "/dash-board-3/{fDate}/{tDate}", method = RequestMethod.GET)
    public List<Object[]> getDashBoard3(@PathVariable String fDate,@PathVariable String tDate) {
        System.out.println("rest "+fDate+" "+tDate);
        return dashBoardService.getDashBoard3(fDate,tDate);
    }
    @RequestMapping(value = "/dash-board-4/{tDate}", method = RequestMethod.GET)
    public List<Object[]> getDashBoard4(@PathVariable String tDate) {
        return dashBoardService.getDashBoard4(tDate);
    }
    @RequestMapping(value = "/dash-board-5/{tDate}", method = RequestMethod.GET)
    public List<Object[]> getDashBoard5(@PathVariable String tDate) {
        return dashBoardService.getDashBoard5(tDate);
    }
}
