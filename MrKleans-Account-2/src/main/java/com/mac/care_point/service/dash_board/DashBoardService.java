/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.dash_board;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chama
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DashBoardService {
@Autowired
private DashBoardRepository dashBoardRepository;
            
    public List<Object[]> getDashBoardMain() {
        return dashBoardRepository.getDashBoardMain();
    }
    
}
