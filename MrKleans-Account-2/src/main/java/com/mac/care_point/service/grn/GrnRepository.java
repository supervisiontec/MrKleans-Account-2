/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.grn;

import com.mac.care_point.service.grn.model.TGrn;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface GrnRepository extends JpaRepository<TGrn, Integer> {

    public List<TGrn> findAll();

    public TGrn findFirst1ByOrderByIndexNoDesc();

    @Query(value = "select \n"
            + "     ifnull(max(t_grn.number)+1,1 )as next_number\n"
            + "from\n"
            + "t_grn\n"
            + "where t_grn.branch=:branch and t_grn.`type`='supplier_return'", nativeQuery = true)
    public Integer getNextReturnNo(@Param("branch") Integer branch);

    public List<TGrn> findByBranchAndStatus(Integer branch, String status_pending);

    @Query(value = "select t_grn.*\n"
            + "from t_grn \n"
            + "where t_grn.number=:number\n"
            + "and t_grn.branch =:branch\n"
            + "and (type=:type or type=:type1)", nativeQuery = true)
    public TGrn findByNumberAndBranchAndTypeOrType(@Param("number") Integer number, @Param("branch") Integer branch,
            @Param("type") String type, @Param("type1") String type1);

    public TGrn findByNumberAndBranchAndType(Integer number, Integer branch, String type);

}
