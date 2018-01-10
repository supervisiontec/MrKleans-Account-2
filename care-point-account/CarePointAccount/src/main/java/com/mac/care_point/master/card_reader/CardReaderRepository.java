/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.card_reader;

import com.mac.care_point.master.card_reader.model.MCardReader;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface CardReaderRepository extends JpaRepository<MCardReader, Integer>{

    public List<MCardReader> findByBranch(Integer branch);
    public MCardReader findByBranchAndAccAccount(Integer branch,Integer account);
    
}
