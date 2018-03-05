/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.card_reader;

import com.mac.care_point.master.card_reader.model.MCardReader;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CardReaderService {

    @Autowired
    private CardReaderRepository cardReaderRepository;
    
    public List<MCardReader> findAll() {
        return cardReaderRepository.findAll();
    }

    public List<MCardReader> findByBranch(Integer branch) {
        return cardReaderRepository.findByBranch(branch);
    }
    
}
