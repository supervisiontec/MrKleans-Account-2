/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.account_ledger;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "	t_acc_ledger.number,\n"
            + "   t_acc_ledger.transaction_date,\n"
            + "   t_acc_ledger.current_date,\n"
            + "   t_acc_ledger.time,\n"
            + "   t_acc_ledger.branch,\n"
            + "   t_acc_ledger.current_branch,\n"
            + "   t_acc_ledger.user,\n"
            + "   sum(t_acc_ledger.debit) as debit ,\n"
            + "   sum(t_acc_ledger.credit) as credit ,\n"
            + "   t_acc_ledger.acc_account,\n"
            + "   t_acc_ledger.form_name,\n"
            + "   t_acc_ledger.ref_number,\n"
            + "   t_acc_ledger.`type`,\n"
            + "   t_acc_ledger.type_index_no,\n"
            + "   t_acc_ledger.description,\n"
            + "   t_acc_ledger.cheque_date,\n"
            + "   t_acc_ledger.bank_reconciliation,\n"
            + "   t_acc_ledger.reconcile_account,\n"
            + "   t_acc_ledger.delete_ref_no,\n"
            + "   t_acc_ledger.reconcile_group,\n"
            + "   t_acc_ledger.is_main,\n"
            + "   t_acc_ledger.is_cheque,\n"
            + "   t_acc_ledger.search_code,\n"
            + "   t_acc_ledger.financial_year,\n"
            + "   t_acc_ledger.cost_department,\n"
            + "   t_acc_ledger.cost_center,\n"
            + "   t_acc_ledger.is_edit\n"
            + "   from t_acc_ledger\n"
            + "   where \n"
            + "	(:name is null or t_acc_ledger.`type`=:name) and\n"
            + "   (:fromDate is null or t_acc_ledger.transaction_date>=:fromDate) and\n"
            + "   (:toDate is null or t_acc_ledger.transaction_date<=:toDate) and\n"
            + "   (:branch is null or t_acc_ledger.branch=:branch) and\n"
            + "   (:year is null or t_acc_ledger.transaction_date BETWEEN (select m_financial_year.start_date from m_financial_year where m_financial_year.index_no=:year) and (select m_financial_year.end_date from m_financial_year where m_financial_year.index_no=:year) ) and \n"
            + "   (:account is null or t_acc_ledger.acc_account=:account) and\n"
            + "   (:invDate is null or t_acc_ledger.transaction_date=:invDate)and \n"
            + "   (:refNo is null or t_acc_ledger.ref_number=:refNo)\n"
            + "   group by t_acc_ledger.delete_ref_no\n"
            + "   order by t_acc_ledger.index_no desc", nativeQuery = true)
    public List<TAccLedger> getLedgerTypeDataList(
            @Param("name") String name,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("branch") String branch,
            @Param("year") String year,
            @Param("account") Integer account,
            @Param("invDate") String invDate,
            @Param("refNo") String refNo
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
            + "and t_acc_ledger.transaction_date>=:fDate and t_acc_ledger.transaction_date<=:tDate\n"
            + "GROUP by t_acc_ledger.delete_ref_no", nativeQuery = true)
    public List<Object> getLedgerTypeCount(@Param("type") String type,
            @Param("fDate") String fDate,
            @Param("tDate") String tDate);

    @Query(value = "select DATE_FORMAT(ledger.transaction_date,'%Y-%b') as date,\n"
            + "   (select ifnull(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00) as val\n"
            + "   from t_acc_ledger\n"
            + "   left JOIN m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "   left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "   where m_acc_main.name='EXPENSE' and year(t_acc_ledger.transaction_date)=year(ledger.transaction_date) \n"
            + "   and MONTH(t_acc_ledger.transaction_date)=MONTH(ledger.transaction_date)\n"
            + "   and t_acc_ledger.transaction_date>=:fDate and  t_acc_ledger.transaction_date<=:tDate) as expense,\n"
            + "   (select ifnull(sum(t_acc_ledger.credit )-sum(t_acc_ledger.debit),0.00) as val\n"
            + "   from t_acc_ledger\n"
            + "   left JOIN m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "   left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "   where m_acc_main.name='INCOME' and year(t_acc_ledger.transaction_date)=year(ledger.transaction_date) \n"
            + "   and MONTH(t_acc_ledger.transaction_date)=MONTH(ledger.transaction_date)\n"
            + "	  and t_acc_ledger.transaction_date>=:fDate and  t_acc_ledger.transaction_date<=:tDate) as income\n"
            + "   from t_acc_ledger ledger\n"
            + "   left join m_financial_year on m_financial_year.index_no=ledger.financial_year\n"
            + "   where m_financial_year.is_current=true\n"
            + "   and ledger.transaction_date>=:fDate and  ledger.transaction_date<=:tDate\n"
            + "   GROUP by DATE_FORMAT(ledger.transaction_date,'%Y-%b') order by ledger.transaction_date desc limit 6", nativeQuery = true)
    public List<Object[]> getDashBoard3(@Param("fDate") String fDate, @Param("tDate") String tDate);

    @Query(value = "select m_acc_ledger_type.label,\n"
            + "     t_acc_ledger.type \n"
            + "     from t_acc_ledger\n"
            + "     LEFT JOIN m_acc_ledger_type on m_acc_ledger_type.name=t_acc_ledger.`type`\n"
            + "     where t_acc_ledger.transaction_date>=:fDate and t_acc_ledger.transaction_date<=:tDate\n"
            + "     group by t_acc_ledger.`type` ", nativeQuery = true)
    public List<Object[]> getLedgerTypes(@Param("fDate") String fDate, @Param("tDate") String tDate);

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "   t_acc_ledger.number,\n"
            + "   t_acc_ledger.transaction_date,\n"
            + "   t_acc_ledger.current_date,\n"
            + "   t_acc_ledger.time,\n"
            + "   t_acc_ledger.branch,\n"
            + "   t_acc_ledger.current_branch,\n"
            + "   t_acc_ledger.user,\n"
            + "   sum(t_acc_ledger.debit) as debit,\n"
            + "   sum(t_acc_ledger.credit) as credit,\n"
            + "   t_acc_ledger.acc_account,\n"
            + "   t_acc_ledger.form_name,\n"
            + "   t_acc_ledger.ref_number,\n"
            + "   t_acc_ledger.`type`,\n"
            + "   t_acc_ledger.type_index_no,\n"
            + "   t_acc_ledger.description,\n"
            + "   t_acc_ledger.cheque_date,\n"
            + "   t_acc_ledger.bank_reconciliation,\n"
            + "   t_acc_ledger.reconcile_account,\n"
            + "   t_acc_ledger.delete_ref_no,\n"
            + "   t_acc_ledger.reconcile_group,\n"
            + "   t_acc_ledger.is_main,\n"
            + "   t_acc_ledger.is_cheque,\n"
            + "   t_acc_ledger.search_code,\n"
            + "   t_acc_ledger.financial_year,\n"
            + "   t_acc_ledger.cost_department,\n"
            + "   t_acc_ledger.cost_center,\n"
            + "   t_acc_ledger.is_edit\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.acc_account=:account\n"
            + "and (t_acc_ledger.reconcile_group!=null or t_acc_ledger.reconcile_group!=0)\n"
            + "group by t_acc_ledger.reconcile_group\n"
            + "having debit!=credit", nativeQuery = true)
    public List<TAccLedger> getPayableBills(@Param("account") Integer account);

    @Query(value = "select m_supplier.acc_account,\n"
            + "m_acc_account.name as supplier_name,\n"
            + "ifnull(sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit),0.00) as balance,\n"
            + "m_supplier.index_no as typeIndexNo\n"
            + "from m_supplier\n"
            + "left join m_acc_account on m_acc_account.index_no=m_supplier.acc_account\n"
            + "left join t_acc_ledger on t_acc_ledger.acc_account=m_acc_account.index_no\n"
            + "group by t_acc_ledger.acc_account\n"
            + "order by m_acc_account.name", nativeQuery = true)
    public Object[] getSupplierBalanceList();

    public List<TAccLedger> findByAccAccountOrderByTransactionDate(Integer account);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE t_acc_ledger SET `is_edit`='2' WHERE delete_ref_no=:number", nativeQuery = true)
    public void setDelete(@Param("number") Integer number);

    @Query(value = "select count(index_no) from t_acc_ledger where acc_account = :subAccountOf", nativeQuery = true)
    public Integer getTransactionCount(@Param ("subAccountOf")Integer subAccountOf);

}
