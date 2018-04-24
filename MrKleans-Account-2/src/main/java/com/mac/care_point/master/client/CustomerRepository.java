/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.client;

import com.mac.care_point.master.client.model.Client;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface CustomerRepository extends JpaRepository<Client, Integer> {

    public List<Client> findByNic(String nic);

    public List<Client> findByIsNewOrderByName(boolean b);

    public Client findByAccAccount(int client);

    @Query(value = "select ifnull(m_client.acc_account,0) as acc_account from m_client \n"
            + "where m_client.index_no=:client", nativeQuery = true)
    public Integer findByAccAccountFromClientIndex(@Param("client") int client);

}
