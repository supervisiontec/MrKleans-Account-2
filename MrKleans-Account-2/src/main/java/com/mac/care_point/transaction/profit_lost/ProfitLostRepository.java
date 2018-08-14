/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.profit_lost;

import com.mac.care_point.transaction.profit_lost.model.TProfitLostModel;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface ProfitLostRepository extends JpaRepository<TProfitLostModel, Integer> {

    @Query(value = "select ifnull(sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit),0.00) as credit\n"
            + "   from t_acc_ledger\n"
            + "   left join m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "   left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "   left join m_acc_main on m_acc_main.index_no=m_acc_account.acc_main  \n"
            + "   left join m_financial_year on m_financial_year.index_no=t_acc_ledger.financial_year\n"
            + "   where left(m_acc_account.acc_code,(select length(m_acc_account.acc_code) as leng\n"
            + "	   from m_acc_account,m_acc_setting\n"
            + "	   where m_acc_account.index_no=m_acc_setting.acc_account \n"
            + "	   and m_acc_main.name='INCOME'\n"
            + "	   and m_acc_setting.name='Revenue'))=(select m_acc_account.acc_code as code\n"
            + "	   from m_acc_account,m_acc_setting\n"
            + "	   where m_acc_account.index_no=m_acc_setting.acc_account and m_acc_setting.name='Revenue') \n"
            + "	and t_acc_ledger.financial_year=:financialYear\n"
            + "	and t_acc_ledger.transaction_date BETWEEN m_financial_year.start_date and m_financial_year.end_date", nativeQuery = true)
    public BigDecimal getRevenueTotal(@Param("financialYear") Integer financialYear);

    @Query(value = "select ifnull(sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit),0.00) as credit\n"
            + "from t_acc_ledger\n"
            + "left join m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "left join m_acc_main on m_acc_main.index_no=m_acc_account.acc_main  \n"
            + "where left(m_acc_account.acc_code,(select length(m_acc_account.acc_code) as leng\n"
            + "from m_acc_account,m_acc_setting\n"
            + "where m_acc_account.index_no=m_acc_setting.acc_account \n"
            + "and m_acc_main.name='INCOME'\n"
            + "and m_acc_setting.name='Revenue'))!=(select m_acc_account.acc_code as code\n"
            + "from m_acc_account,m_acc_setting\n"
            + "where m_acc_account.index_no=m_acc_setting.acc_account and m_acc_setting.name='Revenue') and t_acc_ledger.financial_year=(\n"
            + "	select index_no from m_financial_year where is_current=1\n"
            + "	) ", nativeQuery = true)
    public BigDecimal getOtherIncome();

    @Query(value = "select ifnull(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00) as credit\n"
            + "from t_acc_ledger\n"
            + "left join m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "left join m_acc_main on m_acc_main.index_no=m_acc_account.acc_main  \n"
            + "where left(m_acc_account.acc_code,2)='05' and t_acc_ledger.financial_year=(\n"
            + "	select index_no from m_financial_year where is_current=1) ", nativeQuery = true)
    public BigDecimal getOtherExpense();

    @Query(value = "select ifnull(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00) as tax_total\n"
            + "from t_acc_ledger\n"
            + "left join m_acc_account on m_acc_account.index_no=t_acc_ledger.acc_account\n"
            + "left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "left join m_acc_main on m_acc_main.index_no=m_acc_account.acc_main  \n"
            + "where left(m_acc_account.acc_code,(select length(m_acc_account.acc_code) as leng\n"
            + "from m_acc_account,m_acc_setting\n"
            + "where m_acc_account.index_no=m_acc_setting.acc_account \n"
            + "and m_acc_main.name='EXPENSE'\n"
            + "and m_acc_setting.name='tax main'))=(select m_acc_account.acc_code as code\n"
            + "from m_acc_account,m_acc_setting\n"
            + "where m_acc_account.index_no=m_acc_setting.acc_account and m_acc_setting.name='tax main') and t_acc_ledger.financial_year=(\n"
            + "select index_no from m_financial_year where is_current=1) ", nativeQuery = true)
    public BigDecimal getTaxExpense();

    @Query(value = "select sum(t_stock_ledger.in_qty * t_stock_ledger.avarage_price_in) -\n"
            + "sum(t_stock_ledger.out_qty * t_stock_ledger.avarage_price_out)  as open_stock\n"
            + "from t_stock_ledger\n"
            + "where t_stock_ledger.date<(select m_financial_year.start_date from m_financial_year where m_financial_year.is_current=1)", nativeQuery = true)
    public BigDecimal getOpenStock();

    @Query(value = "select sum(t_stock_ledger.in_qty * t_stock_ledger.avarage_price_in) as purchase\n"
            + "from t_stock_ledger\n"
            + "where t_stock_ledger.date>=(select m_financial_year.start_date from m_financial_year where m_financial_year.is_current=1)\n"
            + "and t_stock_ledger.date<=(select m_financial_year.end_date from m_financial_year where m_financial_year.is_current=1)\n"
            + "and t_stock_ledger.`type`='SYSTEM_INTEGRATION_GRN'", nativeQuery = true)
    public BigDecimal getPurchaseOfYear();

    @Query(value = "select sum(t_stock_ledger.in_qty * t_stock_ledger.avarage_price_in) -\n"
            + "sum(t_stock_ledger.out_qty * t_stock_ledger.avarage_price_out)  as close_stock\n"
            + "from t_stock_ledger\n"
            + "where t_stock_ledger.date<(select m_financial_year.end_date from m_financial_year where m_financial_year.is_current=1)", nativeQuery = true)
    public BigDecimal getCloseStock();

    @Query(value = "select m_acc_account.index_no ,\n"
            + "	m_acc_account.name,\n"
            + "	(select ifnull(sum(t_acc_ledger.credit)-sum(t_acc_ledger.debit),0.00) as credit\n"
            + "	from t_acc_ledger\n"
            + "	left join m_acc_account a on a.index_no=t_acc_ledger.acc_account\n"
            + "	where left(a.acc_code,(select length(m_acc_account.acc_code)))=(m_acc_account.acc_code)\n"
            + "		and t_acc_ledger.financial_year=(select index_no from m_financial_year where is_current=1)) as credit\n"
            + "from m_acc_account\n"
            + "left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "where m_acc_account.sub_account_of=(select m_acc_setting.acc_account from m_acc_setting where m_acc_setting.name='Revenue')", nativeQuery = true)
    public List<Object[]> getRevenueDetails();

    @Query(value = "select m_acc_account.index_no ,\n"
            + "	m_acc_account.name,\n"
            + "	(select ifnull(sum(t_acc_ledger.debit)-sum(t_acc_ledger.credit),0.00) as credit\n"
            + "	from t_acc_ledger\n"
            + "	left join m_acc_account a on a.index_no=t_acc_ledger.acc_account\n"
            + "	left join m_financial_year on m_financial_year.index_no=t_acc_ledger.financial_year\n"
            + "	where left(a.acc_code,(select length(m_acc_account.acc_code)))=(m_acc_account.acc_code)\n"
            + "	and t_acc_ledger.financial_year=(select index_no from m_financial_year where is_current=1)) as value\n"
            + "from m_acc_account\n"
            + "left join m_acc_setting on m_acc_setting.acc_account=m_acc_account.index_no\n"
            + "where left(m_acc_account.acc_code,2)='05' and m_acc_account.acc_code!='05' and m_acc_account.`level`=2 having value!=0", nativeQuery = true)
    public List<Object[]> getOtherExpenseDetails();

}
