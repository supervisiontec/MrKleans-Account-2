/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.backup_detail.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_backup_detail")
public class MBackupDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;  
    
    @Column(name = "host")
    private String host;  
    
    @Column(name = "user_name")
    private String userName;  
    
    @Column(name = "pswd")
    private String password;  
    
    @Column(name = "schema")
    private String schema; 

    public MBackupDetail() {
    }

    public MBackupDetail(Integer indexNo, String host, String userName, String password, String schema) {
        this.indexNo = indexNo;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.schema = schema;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    
}
