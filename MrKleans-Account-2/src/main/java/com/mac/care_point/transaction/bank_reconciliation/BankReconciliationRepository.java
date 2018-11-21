/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.bank_reconciliation;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface BankReconciliationRepository extends JpaRepository<TAccLedger, Integer> {

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "    t_acc_ledger.number,\n"
            + "    t_acc_ledger.transaction_date,\n"
            + "    t_acc_ledger.current_date,\n"
            + "    t_acc_ledger.time,\n"
            + "    t_acc_ledger.branch,\n"
            + "    t_acc_ledger.current_branch,\n"
            + "    t_acc_ledger.user,\n"
            + "    sum(t_acc_ledger.debit) as debit,\n"
            + "    SUM(t_acc_ledger.credit) as credit,\n"
            + "    t_acc_ledger.acc_account,\n"
            + "    t_acc_ledger.form_name,\n"
            + "    t_acc_ledger.ref_number,\n"
            + "    t_acc_ledger.`type`,\n"
            + "    t_acc_ledger.type_index_no,\n"
            + "    t_acc_ledger.description,\n"
            + "    t_acc_ledger.cheque_date,\n"
            + "    t_acc_ledger.bank_reconciliation,\n"
            + "    t_acc_ledger.reconcile_account,\n"
            + "    t_acc_ledger.delete_ref_no,\n"
            + "    t_acc_ledger.reconcile_group,\n"
            + "    t_acc_ledger.is_main,\n"
            + "    t_acc_ledger.is_cheque,\n"
            + "    t_acc_ledger.search_code,\n"
            + "     t_acc_ledger.financial_year,\n"
            + "     t_acc_ledger.cost_department,\n"
            + "     t_acc_ledger.cost_center,\n"
            + "     t_acc_ledger.is_edit\n"
            + "    from t_acc_ledger\n"
            + "    where t_acc_ledger.bank_reconciliation=1\n"
            + "    and (t_acc_ledger.cheque_date <= :date or t_acc_ledger.cheque_date is null) and t_acc_ledger.reconcile_account=:accAccount\n"
            + "    group by t_acc_ledger.reconcile_group\n"
            + "    having debit!=credit", nativeQuery = true)
    public List<TAccLedger> getReconciliationDetails(@Param("date") String date, @Param("accAccount") Integer accAccount);

    @Query(value = "select ifnull(sum(t_acc_ledger.debit) - sum(t_acc_ledger.credit),0.00) as balance\n"
            + "      from t_acc_ledger\n"
            + "      where t_acc_ledger.acc_account=:accAccount\n"
            + "      and t_acc_ledger.transaction_date >=:fDate and t_acc_ledger.transaction_date<=:lDate \n"
            + "      and (t_acc_ledger.bank_reconciliation is null or t_acc_ledger.bank_reconciliation=false) \n"
            + "and (t_acc_ledger.is_edit !=2 or t_acc_ledger.is_edit is null)", nativeQuery = true)
    public double getStartBalance(@Param("fDate") String fDate, @Param("lDate") String lDate, @Param("accAccount") Integer accAccount);

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "   t_acc_ledger.number,\n"
            + "   t_acc_ledger.transaction_date,\n"
            + "   t_acc_ledger.current_date,\n"
            + "   t_acc_ledger.time,\n"
            + "   t_acc_ledger.branch,\n"
            + "   t_acc_ledger.current_branch,\n"
            + "   t_acc_ledger.user,\n"
            + "   t_acc_ledger.debit ,\n"
            + "   t_acc_ledger.credit ,\n"
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
            + "where t_acc_ledger.acc_account=:accAccount\n"
            +" and (is_edit !=2 or is_edit is null)\n"
            + "and year(t_acc_ledger.transaction_date) =:year and month(t_acc_ledger.transaction_date)=:month \n"
            + "and (t_acc_ledger.bank_reconciliation is null or t_acc_ledger.bank_reconciliation=false)\n"
            + "order by t_acc_ledger.transaction_date", nativeQuery = true)
    public List<TAccLedger> loadTransactions(@Param("year") Integer year, @Param("month") Integer month, @Param("accAccount") Integer accAccount);

    @Modifying
    @Query(value = "delete from t_acc_ledger where t_acc_ledger.delete_ref_no = :index", nativeQuery = true)
    public void deleteByDeleteRefNo(@Param("index") Integer index);

}
