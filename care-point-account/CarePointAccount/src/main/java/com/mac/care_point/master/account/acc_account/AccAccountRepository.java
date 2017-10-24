/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account.acc_account;

import com.mac.care_point.master.account.model.MAccAccount;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface AccAccountRepository extends JpaRepository<MAccAccount, Integer> {

    @Query(value = "select ifnull(sum(t_acc_ledger.debit) - sum(t_acc_ledger.credit),0.00) as balance\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.branch=:branch and  t_acc_ledger.acc_account=:accAccount \n"
            + "and t_acc_ledger.transaction_date >=:fDate and t_acc_ledger.transaction_date<=:tDate ", nativeQuery = true)
    public BigDecimal findAccountValue(@Param("branch") Integer branch,
            @Param("accAccount") Integer accAccount,
            @Param("fDate") String fDate, @Param("tDate") String tDate);
}
