/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicle.model;

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
 * @author chama
 */
@Entity
@Table(name = "m_vehicle")
public class MVehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "client")
    private Integer client;
    
    @Column(name = "vehicle_no")
    private String vehicleNo;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "is_new")
    private Boolean isNew;
    
    @Column(name = "`date`")
    private String date;

    public MVehicle() {
    }

    public MVehicle(Integer indexNo, Integer client, String vehicleNo, String type, Boolean isNew, String date) {
        this.indexNo = indexNo;
        this.client = client;
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.isNew = isNew;
        this.date = date;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
