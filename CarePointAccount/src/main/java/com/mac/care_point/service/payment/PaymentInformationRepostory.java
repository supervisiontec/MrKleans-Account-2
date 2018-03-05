/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.payment;

import com.mac.care_point.service.item_sale.model.TPaymentInformation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface PaymentInformationRepostory extends JpaRepository<TPaymentInformation, Integer>{

    public List<TPaymentInformation> findByPayment(Integer indexNo);
    
}
