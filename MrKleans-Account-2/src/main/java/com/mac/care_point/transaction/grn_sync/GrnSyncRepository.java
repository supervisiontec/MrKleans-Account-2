/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync;

import com.mac.care_point.transaction.grn_sync.model.TGrnSync;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface GrnSyncRepository extends JpaRepository<TGrnSync, Integer> {

    @Query(value = "SELECT t_int_grn.index_no,\n"
            + "	t_int_grn.loc_id,\n"
            + "	concat(t_type_index_detail.master_ref_id,' - ',m_supplier.name) AS supplier,\n"
            + "	DATE_FORMAT(t_int_grn.grn_date,'%Y-%m-%d') AS grn_date ,\n"
            + "	t_int_grn.grn_no,\n"
            + "	t_int_grn.ref_no,\n"
            + "	t_int_grn.remar,\n"
            + "	t_int_grn.grn_total,\n"
            + "	t_int_grn.tot_dis,\n"
            + "	t_int_grn.tot_tax,\n"
            + "	t_int_grn.gross_tot,\n"
            + "	t_type_index_detail.account_ref_id AS supplier_index,\n"
            + "	t_type_index_detail.account_index AS supplier_account_index,\n"
            + "	t_int_grn.gross_tot as tot2,\n"
            + " m_branch.branch_code \n"
            + " from t_int_grn \n"
            + " LEFT JOIN t_type_index_detail ON t_type_index_detail.master_ref_id=t_int_grn.supp_code\n"
            + " LEFT JOIN m_supplier ON m_supplier.index_no=t_type_index_detail.account_ref_id\n"
            + "LEFT JOIN m_branch ON m_branch.reg_number=t_int_grn.loc_id \n"
            + " where t_int_grn.is_get_data=0 AND t_type_index_detail.`type`='SUPPLIER'\n"
            + " AND (:fDate IS NULL OR DATE_FORMAT(t_int_grn.grn_date,'%Y-%m-%d')>=:fDate)\n"
            + " AND (:tDate IS NULL OR DATE_FORMAT(t_int_grn.grn_date,'%Y-%m-%d')<=:tDate)\n"
            + " AND (:supplier IS NULL OR t_int_grn.supp_code=:supplier)\n"
            + " AND (:branch IS NULL OR t_int_grn.loc_id=:branch)\n"
            + " AND (:grnNo = '' OR t_int_grn.grn_no like concat('%',:grnNo,'%') )\n"
            + " AND (:referenceNo = '' OR t_int_grn.ref_no like concat('%',:referenceNo,'%') )\n"
            + "order by t_int_grn.grn_date,t_int_grn.grn_no", nativeQuery = true)
    public List<Object[]> getData(
            @Param("branch") String branch,
            @Param("supplier") String supplier,
            @Param("fDate") String fDate,
            @Param("tDate") String tDate,
            @Param("grnNo") String grnNo,
            @Param("referenceNo") String referenceNo);

    @Query(value = "select ifnull(max(t_acc_ledger.NUMBER+1),1) AS nxtNo FROM t_acc_ledger\n"
            + "WHERE t_acc_ledger.`type`=:type", nativeQuery = true)
    public Integer getNextNo(@Param("type") String type);

    @Query(value = "select ifnull(max(t_acc_ledger.delete_ref_no+1),1) AS nxtNo FROM t_acc_ledger", nativeQuery = true)
    public Integer getDeleteRefNo();

    @Query(value = "SELECT m_acc_setting.acc_account FROM m_acc_setting\n"
            + "WHERE m_acc_setting.NAME='stock_item_sub_account_of'", nativeQuery = true)
    public Integer getStockItemSubAccountOf();

    @Modifying
    @Query(value = "UPDATE t_int_grn SET t_int_grn.is_get_data='1' WHERE  t_int_grn.index_no=:index", nativeQuery = true)
    public int updateIntGrnStatus(@Param("index") int index);

    @Query(value = "select m_branch.branch_code FROM m_branch WHERE m_branch.reg_number=:locID", nativeQuery = true)
    public String getBranchCode(@Param("locID") String locID);

    @Query(value = "select m_branch.index_no FROM m_branch WHERE m_branch.reg_number=:locID", nativeQuery = true)
    public Integer getBranchID(@Param("locID") String toString);

}
