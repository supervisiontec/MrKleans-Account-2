/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.supplier;

import com.mac.care_point.master.supplier.model.MSupplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kasun
 */
public interface SupplierRepository extends JpaRepository<MSupplier, Integer> {

    public List<MSupplier> findByType(String ACCRUED);

    @Query(value = "SELECT m_supplier.NAME,\n"
            + "	t_type_index_detail.master_ref_id AS code\n"
            + "FROM m_supplier\n"
            + "LEFT JOIN t_type_index_detail ON t_type_index_detail.account_ref_id=m_supplier.index_no\n"
            + "WHERE t_type_index_detail.`type`='SUPPLIER'", nativeQuery = true)
    public List<Object[]> getSuppliersGRN();

}
