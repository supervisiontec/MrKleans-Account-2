/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.return_cheque;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface ReturnChequeRepository extends JpaRepository<TAccLedger, Integer>{

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "      t_acc_ledger.number,\n"
            + "      t_acc_ledger.transaction_date,\n"
            + "      t_acc_ledger.current_date,\n"
            + "      t_acc_ledger.time,\n"
            + "      t_acc_ledger.branch,\n"
            + "      t_acc_ledger.current_branch,\n"
            + "      t_acc_ledger.user,\n"
            + "      sum(t_acc_ledger.debit) as debit,\n"
            + "      sum(t_acc_ledger.credit) as credit,\n"
            + "      t_acc_ledger.acc_account,\n"
            + "      t_acc_ledger.form_name,\n"
            + "      t_acc_ledger.ref_number,\n"
            + "      t_acc_ledger.`type`,\n"
            + "      t_acc_ledger.type_index_no,\n"
            + "      t_acc_ledger.description,\n"
            + "      t_acc_ledger.cheque_date,\n"
            + "      t_acc_ledger.bank_reconciliation,\n"
            + "      t_acc_ledger.reconcile_account,\n"
            + "      t_acc_ledger.delete_ref_no,\n"
            + "      t_acc_ledger.reconcile_group,\n"
            + "      t_acc_ledger.is_main,\n"
            + "      t_acc_ledger.is_cheque,\n"
            + "      t_acc_ledger.search_code,\n"
            + "      t_acc_ledger.financial_year,\n"
            + "      t_acc_ledger.cost_department,\n"
            + "      t_acc_ledger.cost_center,\n"
            + "      t_acc_ledger.is_edit\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.acc_account=:account and t_acc_ledger.is_cheque=1\n"
            + "group by t_acc_ledger.reconcile_group\n"
            + "having debit!=credit\n"
            + "order by t_acc_ledger.transaction_date", nativeQuery = true)
    public List<TAccLedger> getCheques(@Param("account") Integer account);

    public List<TAccLedger> findByDeleteRefNo(Integer delIndex);

}
