/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.trial_balance;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface TrialBalanceRepository extends JpaRepository<TAccLedger, Integer> {

    @Query(value = "select \n"
            + "	acc.index_no,\n"
            + "	acc.acc_code,\n"
            + "	acc.name as acc_name,\n"
            + "	ifnull((select ifnull(if(m_acc_main.increment='debit',sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00),0.00) as debit1\n"
            + "		from m_acc_account acc2\n"
            + "			 left JOIN t_acc_ledger on t_acc_ledger.acc_account=acc2.index_no\n"
            + "			 left JOIN m_acc_main on m_acc_main.index_no=acc2.acc_main\n"
            + "		where left(acc2.acc_code,length(acc.acc_code))=acc.acc_code \n"
            + "			 and t_acc_ledger.transaction_date<=:date\n"
            + "		group by acc.acc_code),0.00)	as debit,\n"
            + "	ifnull((select ifnull(if(m_acc_main.increment='CREDIT',sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit),0.00),0.00)\n"
            + "		from m_acc_account acc3\n"
            + "			 left JOIN t_acc_ledger on t_acc_ledger.acc_account=acc3.index_no\n"
            + "			 left JOIN m_acc_main on m_acc_main.index_no=acc3.acc_main\n"
            + "		where left(acc3.acc_code,length(acc.acc_code))=acc.acc_code \n"
            + "			 and t_acc_ledger.transaction_date<=:date\n"
            + "		group by acc.acc_code),0.00)	as credit,\n"
            + "	acc.`level` as level,\n"
            + "	acc.is_acc_account,\n"
            + " acc.sub_account_count,\n"
            +"  ifnull(acc.sub_account_of,0) as subAccountOf \n"
            + "from m_acc_account acc\n"
            + "	left JOIN t_acc_ledger on t_acc_ledger.acc_account=acc.index_no\n"
            + "	left JOIN m_acc_main on m_acc_main.index_no=acc.acc_main\n"
            + "group by acc.index_no\n"
            + "order by acc.acc_code", nativeQuery = true)
    public List<Object[]> loadMainAcc(@Param("date") String date);

}
