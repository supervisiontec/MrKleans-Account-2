/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account;

import com.mac.care_point.master.account.model.MAccAccount;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface AccAccountRepository extends JpaRepository<MAccAccount, Integer> {

    @Query(value = "select ifnull(sum(t_acc_ledger.debit) - sum(t_acc_ledger.credit),0.00) as balance\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.branch=:branch and  t_acc_ledger.acc_account=:accAccount \n"
            + "and t_acc_ledger.transaction_date >=:fDate and t_acc_ledger.transaction_date<=:tDate \n"
            + "and (t_acc_ledger.bank_reconciliation is null or t_acc_ledger.bank_reconciliation=false)", nativeQuery = true)
    public BigDecimal findAccountValue(@Param("branch") Integer branch,
            @Param("accAccount") Integer accAccount,
            @Param("fDate") String fDate, @Param("tDate") String tDate);

    public List<MAccAccount> findBySubAccountOf(Integer subAccountOf);

    public List<MAccAccount> findByIsAccAccount(boolean b);

    @Query(value = "select ifnull(sum(t_acc_ledger.debit) - sum(t_acc_ledger.credit),0.00) as balance\n"
            + "from t_acc_ledger\n"
            + "where t_acc_ledger.acc_account=:accAccount \n"
            + "and t_acc_ledger.transaction_date >=:fDate and t_acc_ledger.transaction_date<=:tDate \n"
            + "and (t_acc_ledger.bank_reconciliation is null or t_acc_ledger.bank_reconciliation=false)", nativeQuery = true)
    public BigDecimal findAccountValue(@Param("accAccount") Integer index, @Param("fDate") String fDate, @Param("tDate") String tDate);

    public List<MAccAccount> findByIsAccAccountAndAccTypeOrAccType(boolean b, String cash, String bank);

    @Query(value = "select m_acc_account.*\n"
            + "from m_acc_account\n"
            + "left JOIN m_acc_main on m_acc_main.index_no=m_acc_account.acc_main\n"
            + "where m_acc_main.name=:type and m_acc_account.is_acc_account=true", nativeQuery = true)
    public List<MAccAccount> findTypeAccount(@Param("type") String type);

    @Query(value = "select m_acc_account.*\n"
            + "from m_acc_account\n"
            + "left JOIN m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "where m_acc_setting.name=:overPaymentIssue", nativeQuery = true)
    public MAccAccount getOverPaymentIssueAccount(@Param("overPaymentIssue") String overPaymentIssue);
}
