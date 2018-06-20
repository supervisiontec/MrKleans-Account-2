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
import org.springframework.data.repository.query.Param;

/**
 *
 * @author chama
 */
public interface DashBoardRepository extends JpaRepository<TAccLedger, Integer> {

    @Query(value = "select \n"
            + "   if((sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))<0,(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))*-1,(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit))) as value,\n"
            + "   m_acc_main.name as name\n"
            + "   from t_acc_ledger\n"
            + "   left JOIN m_acc_account on m_acc_account.index_no =t_acc_ledger.acc_account\n"
            + "   left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "   where t_acc_ledger.financial_year=(select m_financial_year.index_no from m_financial_year where m_financial_year.is_current=1)\n"
            + "   and t_acc_ledger.transaction_date<=:toDate\n"
            + "   GROUP by m_acc_account.acc_main", nativeQuery = true)
    public List<Object[]> getDashBoardMain(@Param("toDate") String date);

    @Query(value = "select account.name,\n"
            + "	(select \n"
            + "    sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit) as value\n"
            + "    from t_acc_ledger\n"
            + "    left JOIN m_acc_account on m_acc_account.index_no =t_acc_ledger.acc_account\n"
            + "    where t_acc_ledger.financial_year=(select m_financial_year.index_no from m_financial_year where m_financial_year.is_current=1)\n"
            + "    and t_acc_ledger.transaction_date<=:date\n"
            + "    and m_acc_account.acc_code LIKE CONCAT( account.acc_code, '%')\n"
            + "	  ) as value\n"
            + "from m_acc_account account\n"
            + "where account.sub_account_of = (select acc_account from m_acc_setting where name='current_assets')", nativeQuery = true)
    public List<Object[]> getDashBoard4(@Param("date") String date);

    @Query(value = "select account.name,\n"
            + "	(select \n"
            + "   sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit) as value\n"
            + "    from t_acc_ledger\n"
            + "    left JOIN m_acc_account on m_acc_account.index_no =t_acc_ledger.acc_account\n"
            + "    where t_acc_ledger.financial_year=(select m_financial_year.index_no from m_financial_year where m_financial_year.is_current=1)\n"
            + "    and t_acc_ledger.transaction_date<=:date\n"
            + "    and m_acc_account.acc_code LIKE CONCAT( account.acc_code, '%')\n"
            + "	  ) as value\n"
            + "from m_acc_account account\n"
            + "where account.sub_account_of = (select acc_account from m_acc_setting where name='current_liability')", nativeQuery = true)
    public List<Object[]> getDashBoard5(@Param("date") String date);

}
