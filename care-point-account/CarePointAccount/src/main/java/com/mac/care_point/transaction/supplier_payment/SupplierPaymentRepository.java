/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.supplier_payment;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface SupplierPaymentRepository extends JpaRepository<TAccLedger, Integer> {

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
            + "   t_acc_ledger.search_code\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.acc_account=:account\n"
            + "group by t_acc_ledger.reconcile_group\n"
            + "having debit!=credit", nativeQuery = true)
    public List<TAccLedger> getPayableBills(@Param("account") Integer account);

    @Query(value = "select t_acc_ledger.index_no,\n"
            + "     t_acc_ledger.number,\n"
            + "     t_acc_ledger.transaction_date,\n"
            + "     t_acc_ledger.current_date,\n"
            + "     t_acc_ledger.time,\n"
            + "     t_acc_ledger.branch,\n"
            + "     t_acc_ledger.current_branch,\n"
            + "     t_acc_ledger.user,\n"
            + "     sum(t_acc_ledger.debit) as debit,\n"
            + "     sum(t_acc_ledger.credit) as credit,\n"
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
            + "      t_acc_ledger.search_code\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.acc_account=:account and t_acc_ledger.type_index_no=:typeIndex\n"
            + "group by t_acc_ledger.reconcile_group\n"
            + "having debit!=credit\n"
            + "order by t_acc_ledger.transaction_date ", nativeQuery = true)
    public List<TAccLedger> getOverPaymentFifoList(@Param("account") Integer account, @Param("typeIndex") Integer typeIndex);

    @Query(value = "select \n"
            + "	ifnull(sum(t_acc_ledger.debit) -\n"
            + "	sum(t_acc_ledger.credit),0.00) as over_payment\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.type_index_no=:supplier and t_acc_ledger.acc_account=:over", nativeQuery = true)
    public double getOverPaymentAmount(@Param("supplier") Integer supplier, @Param("over") Integer over);

}
