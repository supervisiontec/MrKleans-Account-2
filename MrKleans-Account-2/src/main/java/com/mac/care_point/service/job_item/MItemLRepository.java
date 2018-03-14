/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;


import com.mac.care_point.service.job_item.model.MItemL;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface MItemLRepository extends JpaRepository<MItemL, Integer> {

    public List<MItemL> findByItemCategory(Integer itemCategory);

    @Query(value = "select \n"
            + " item.index_no,\n"
            + " price_category_details.normal_price,\n"
            + " price_category_details.register_price\n"
            + "from\n"
            + " m_item item\n"
            + "inner join\n"
            + " m_price_category_details price_category_details\n"
            + "on \n"
            + " item.index_no = price_category_details.item\n"
            + "where\n"
            + " item.name\n"
            + "like\n"
            + " CONCAT('%',:itemKey,'%') \n"
            + "and (item.type = \"SERVICE\" or item.type = \"PACKAGE\") \n"
            + "and \n"
            + " price_category_details.price_category = :priceCategory", nativeQuery = true)
    public List<Object[]> getQuickSeacrhItem(@Param("itemKey") String itemKey, @Param("priceCategory") Integer priceCategory);


}
