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
import org.springframework.web.bind.annotation.PathVariable;

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

    public List<Object[]> getDashBoardMain(@PathVariable String date) {
        List<Object[]> dashBoardMain = dashBoardRepository.getDashBoardMain(date);
        System.out.println(dashBoardMain);
        return dashBoardMain;
    }

    public List<Object> getDashBoard2(String fDate, String tDate) {
        System.out.println(fDate+" *&^^&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(tDate+" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        List<Object[]> ledgerTypes = journalRepository.getLedgerTypes(fDate,tDate);
        List<Integer> countList = new ArrayList<>();
        List<String> lblList = new ArrayList<>();
        List<Object> retuenList = new ArrayList<>();
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        for (Object[] ledgerType : ledgerTypes) {
            System.out.println(ledgerType[0].toString());
            System.out.println(ledgerType[1].toString());
            countList.add(journalRepository.getLedgerTypeCount(ledgerType[1].toString(),fDate,tDate).size());
            lblList.add(ledgerType[0].toString());
        }
        retuenList.add(lblList);
        retuenList.add(countList);
        System.out.println(retuenList.toString());
        return retuenList;
    }

    public List<Object[]> getDashBoard3(String fDate, String tDate) {
        return journalRepository.getDashBoard3(fDate,tDate);
    }

    public List<Object[]> getDashBoard4(String date) {
        List<Object[]> dashBoardMain = dashBoardRepository.getDashBoard4(date);
        return dashBoardMain;
    }
    public List<Object[]> getDashBoard5(String date) {
        List<Object[]> dashBoardMain = dashBoardRepository.getDashBoard5(date);
        return dashBoardMain;
    }
}
