/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface JournalRepository extends JpaRepository<TAccLedger, Integer> {

    @Query(value = "select ifnull( max(t_acc_ledger.number)+1,1) as number\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.current_branch=:branch and t_acc_ledger.`type` =:type", nativeQuery = true)
    public int getNumber(@Param("branch") Integer branch, @Param("type") String voucher);

    @Query(value = "select ifnull( max(t_acc_ledger.delete_ref_no)+1,1) as number\n"
            + "from t_acc_ledger", nativeQuery = true)
    public int getDeleteNumber();

    public List<TAccLedger> findByNumberAndBranchAndType(Integer number, Integer branch, String JOURNAL);

    @Query(value = "select t_acc_ledger.`*`\n"
            + "     from t_acc_ledger\n"
            + "     where t_acc_ledger.`type`=:name and\n"
            + "     (:fromDate is null or t_acc_ledger.transaction_date>=:fromDate) and\n"
            + "     (:toDate is null or t_acc_ledger.transaction_date<=:toDate) and\n"
            + "     (:branch is null or t_acc_ledger.branch=:branch) and\n"
            + "     (:year is null or t_acc_ledger.financial_year=:year)\n"
            + "     group by t_acc_ledger.delete_ref_no\n"
            + "     order by t_acc_ledger.index_no", nativeQuery = true)
    public List<TAccLedger> getLedgerTypeDataList(
            @Param("name") String name,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("year") String year
    );

    public List<TAccLedger> findByDeleteRefNo(Integer number);

    @Query(value = " select t_acc_ledger.`type`\n"
            + "from t_acc_ledger\n"
            + "group by t_acc_ledger.`type` ", nativeQuery = true)
    public List<Object> getLedgerTypes();

    @Query(value = "select  COUNT(*) as detailCount\n"
            + "from t_acc_ledger\n"
            + "left join m_financial_year on m_financial_year.index_no=t_acc_ledger.financial_year\n"
            + "where m_financial_year.is_current=true and t_acc_ledger.`type`=:type\n"
            + "GROUP by t_acc_ledger.delete_ref_no", nativeQuery = true)
    public List<Object> getLedgerTypeCount(@Param("type") String type);

    @Query(value = " select DATE_FORMAT(ledger.transaction_date,'%Y-%b') as date,\n"
            + " \n"
            + " 	(select ifnull(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00) as val\n"
            + "	from t_acc_ledger\n"
            + "	left JOIN m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "	left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "	where m_acc_main.name='EXPENSE' and year(t_acc_ledger.transaction_date)=year(ledger.transaction_date) \n"
            + "	and MONTH(t_acc_ledger.transaction_date)=MONTH(ledger.transaction_date)) as expense,\n"
            + "	\n"
            + "	(select ifnull(sum(t_acc_ledger.credit )-sum(t_acc_ledger.debit),0.00) as val\n"
            + "	from t_acc_ledger\n"
            + "	left JOIN m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "	left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "	where m_acc_main.name='INCOME' and year(t_acc_ledger.transaction_date)=year(ledger.transaction_date) \n"
            + "	and MONTH(t_acc_ledger.transaction_date)=MONTH(ledger.transaction_date)) as income\n"
            + "	\n"
            + " from t_acc_ledger ledger\n"
            + " left join m_financial_year on m_financial_year.index_no=ledger.financial_year\n"
            + " where m_financial_year.is_current=true\n"
            + " GROUP by DATE_FORMAT(ledger.transaction_date,'%Y-%b') order by ledger.transaction_date desc limit 6", nativeQuery = true)
    public List<Object[]> getDashBoard2();

}
