/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.common;

import com.mac.care_point.master.items.items.model.MItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface CommonRepository extends JpaRepository< MItem, Integer> {

    @Query(value = "select ifnull(max(t_job_card.number)+1,1) as number\n"
            + "from t_job_card\n"
            + "where t_job_card.branch=:branch and t_job_card.form_name =:form_name", nativeQuery = true)
    public int getNextJobNumber(@Param("branch") Integer branch, @Param("form_name") String form_name);

    @Query(value = "select ifnull(max(t_invoice.number)+1,1) as number\n"
            + "from t_invoice\n"
            + "where t_invoice.branch=:branch and t_invoice.`status` =:status", nativeQuery = true)
    public int getNextInvoiceNumber(@Param("branch") Integer branch, @Param("status") String status);
}
