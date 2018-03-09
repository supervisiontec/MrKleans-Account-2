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

}
