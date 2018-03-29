/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.dash_board;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author chama
 */
public interface DashBoardRepository extends JpaRepository<TAccLedger, Integer> {

    @Query(value = "select \n"
            + "if((sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))<0,(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))*-1,(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))) as value,\n"
            + "m_acc_main.name as name\n"
            + "from t_acc_ledger\n"
            + "left JOIN m_acc_account on m_acc_account.index_no =t_acc_ledger.acc_account\n"
            + "left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "GROUP by m_acc_account.acc_main", nativeQuery = true)
    public List<Object[]> getDashBoardMain();

}
