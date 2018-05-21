/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.dash_board;

import com.mac.care_point.transaction.account_ledger.JournalRepository;
import java.util.ArrayList;
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
    @Autowired
    private JournalRepository journalRepository;

    public List<Object[]> getDashBoardMain() {
        return dashBoardRepository.getDashBoardMain();
    }

    public List<Object> getDashBoard1() {
        List<Object> ledgerTypes = journalRepository.getLedgerTypes();
        List<Integer> countList = new ArrayList<>();
        List<Object> retuenList = new ArrayList<>();
        for (Object ledgerType : ledgerTypes) {
            countList.add(journalRepository.getLedgerTypeCount(ledgerType.toString()).size());
        }
        retuenList.add(ledgerTypes);
        retuenList.add(countList);
        return retuenList;
    }

    public List<Object[]> getDashBoard2() {
//        List<Object[]> dataList = journalRepository.getDashBoard2();
        return journalRepository.getDashBoard2();
    }

}
