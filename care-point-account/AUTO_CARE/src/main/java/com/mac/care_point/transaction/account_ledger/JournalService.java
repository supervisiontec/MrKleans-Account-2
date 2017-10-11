/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.zutil.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    public Integer saveJournal(List<TAccLedger> list) {
        int count = 0;
        for (TAccLedger tAccLedger : list) {
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getIndexNo());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setFormName("JOURNAL");
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setType("JOURNAL");
            TAccLedger save = journalRepository.save(tAccLedger);
            if (save.getIndexNo() != null) {
                count++;
            }
        }
        if (count == list.size()) {
            return count;
        }
        return 0;
    }

}
