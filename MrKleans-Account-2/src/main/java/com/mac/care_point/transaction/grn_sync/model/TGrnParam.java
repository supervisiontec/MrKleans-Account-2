/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync.model;

/**
 *
 * @author kasun
 */
public class TGrnParam {
    private String branch;
    private String supplier;
    private String fDate;
    private String tDate;
    private String grnNo;
    private String referenceNo;

    public TGrnParam() {
    }

    public TGrnParam(String branch, String supplier, String fDate, String tDate, String grnNo, String referenceNo) {
        this.branch = branch;
        this.supplier = supplier;
        this.fDate = fDate;
        this.tDate = tDate;
        this.grnNo = grnNo;
        this.referenceNo = referenceNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String getGrnNo() {
        return grnNo;
    }

    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
    
}
